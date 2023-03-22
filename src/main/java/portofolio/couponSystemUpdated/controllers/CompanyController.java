package portofolio.couponSystemUpdated.controllers;

import portofolio.couponSystemUpdated.entities.*;
import portofolio.couponSystemUpdated.services.CompanyService;
import portofolio.couponSystemUpdated.services.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("company")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CompanyController extends ClientController{

    @Autowired
    CompanyService companyService;

    @Autowired
    TokenManager tokenManager;


    @GetMapping("/login")
    @ResponseBody
    @CrossOrigin(exposedHeaders = "*")
    public ResponseEntity<?> login(@RequestHeader("email") String email,@RequestHeader("password") String password) {
        Company loginCompany = companyService.companyLogin(email, password);
        if (loginCompany == null) {
            return new ResponseEntity<>(loginCompany, HttpStatus.NOT_FOUND);
        }
        int loginCompanyId = loginCompany.getId();
        System.out.println(loginCompanyId);
        Token token = tokenManager.createToken(loginCompanyId);
        HttpHeaders tokenResponse = new HttpHeaders();
        tokenResponse.set("Company-Token", token.getToken());
        return ResponseEntity.ok().headers(tokenResponse).body(loginCompany);
    }

    @PostMapping("/addCoupon")
    @ResponseBody
    public ResponseEntity<?> addCoupon(@RequestBody Coupon coupon, @RequestHeader("token") String tokenString) {
        if (!tokenManager.isTokenExists(tokenString)) {
            String tokenExpired = "Token Expired";
            ResponseEntity<String> responseWrapper = new ResponseEntity<>(tokenExpired, HttpStatus.REQUEST_TIMEOUT);
            return responseWrapper;
        }
        Token token = tokenManager.findByTokenString(tokenString);
        int couponId = companyService.addCoupon1(coupon, token.getClientId());
        if (couponId == 0) {
            String error = "The title is all ready been used!";
            ResponseEntity<String> responseWrapper = new ResponseEntity<>(error, HttpStatus.ALREADY_REPORTED);
            return responseWrapper;
        }
//        Coupon addedCoupon = companyService.addCoupon1(coupon, token.getClientId());
        ResponseEntity<Integer> responseWrapper = new ResponseEntity<>(couponId, HttpStatus.OK);
        return responseWrapper;
    }

//    public ResponseEntity<?> updateCoupon(@RequestBody Coupon coupon, @RequestHeader("token") String tokenString) {
//        if (!tokenManager.isTokenExists(tokenString)) {
//            String tokenExpired = "Token Expired";
//            ResponseEntity<String> responseWrapper = new ResponseEntity<>(tokenExpired, HttpStatus.REQUEST_TIMEOUT);
//            return responseWrapper;
//        }
//        Token token = tokenManager.findByTokenString(tokenString);
//        int couponId = companyService.updateCoupon1(coupon, token.getClientId());
//        if (couponId == 0) {
//            String error = "The title is all ready been used!";
//            ResponseEntity<String> responseWrapper = new ResponseEntity<>(error, HttpStatus.ALREADY_REPORTED);
//            return responseWrapper;
//        }
//        ResponseEntity<Integer> responseWrapper = new ResponseEntity<>(couponId, HttpStatus.OK);
//        return responseWrapper;
//    }
    @PostMapping("/updateCoupon")
    @ResponseBody
    public ResponseEntity<?> updateCoupon1(@RequestBody Coupon coupon, @RequestHeader("couponId") int couponId, @RequestHeader("token") String tokenString) {
        if (!tokenManager.isTokenExists(tokenString)) {
            String tokenExpired = "Token Expired";
            ResponseEntity<String> responseWrapper = new ResponseEntity<>(tokenExpired, HttpStatus.REQUEST_TIMEOUT);
            return responseWrapper;
        }
        Token token = tokenManager.findByTokenString(tokenString);
        Coupon updatedCoupon = companyService.updateCoupon(couponId, coupon, token.getClientId());
        if (updatedCoupon == null) {
            ResponseEntity<String> responseWrapper = new ResponseEntity<>("Wrong details entered", HttpStatus.REQUEST_TIMEOUT);
            return responseWrapper;
        }else return ResponseEntity.ok().body(updatedCoupon);
        }




    @DeleteMapping("/deleteCoupon")
    @ResponseBody
    public ResponseEntity<?> deleteCoupon(@RequestHeader("couponId") int couponId, @RequestHeader("token") String tokenString) {
        if (!tokenManager.isTokenExists(tokenString)) {
            String tokenExpired = "Token Expired";
            ResponseEntity<String> responseWrapper = new ResponseEntity<>(tokenExpired, HttpStatus.REQUEST_TIMEOUT);
            return responseWrapper;
        }
        Token token = tokenManager.findByTokenString(tokenString);
        int deletedCouponId = companyService.deleteCoupon(couponId, token.getClientId());
        if (deletedCouponId == 0) {
            String error = "Wrong coupon ID!";
            ResponseEntity<String> responseWrapper = new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
            return responseWrapper;
        }else return new ResponseEntity<>("Coupon with id: "+couponId+" has been deleted", HttpStatus.OK);
    }
    @GetMapping("/companyCoupons")
    @ResponseBody
    public ResponseEntity<?> getCompanyCoupons(@RequestHeader("token") String tokenString) {
        if (!tokenManager.isTokenExists(tokenString)) {
            String tokenExpired = "Token Expired";
            ResponseEntity<String> responseWrapper = new ResponseEntity<>(tokenExpired, HttpStatus.REQUEST_TIMEOUT);
            return responseWrapper;
        }
        Token token = tokenManager.findByTokenString(tokenString);
        List<Coupon> companyCoupons = companyService.getAllCompanyCoupons(token.getClientId());
        ResponseEntity<List<Coupon>> responseWrapper = new ResponseEntity<>(companyCoupons, HttpStatus.OK);
        return responseWrapper;
    }
    @GetMapping("/companyCouponsCategory")
    @ResponseBody
    public ResponseEntity<?> getCompanyCouponsFromSpecificCategory(@RequestHeader String tokenString, @RequestHeader Category category){
        if (!tokenManager.isTokenExists(tokenString)) {
            String tokenExpired = "Token expired...";
            ResponseEntity<String> responseWrapper = new ResponseEntity<>(tokenExpired, HttpStatus.REQUEST_TIMEOUT);
            return responseWrapper;
        }
        Token token = tokenManager.findByTokenString(tokenString);
        List<Coupon> companyCoupons = companyService.getAllCouponsFromASpecificCategory(category, token.getClientId());
        ResponseEntity<List<Coupon>> responseWrapper = new ResponseEntity<>(companyCoupons, HttpStatus.OK);
        return responseWrapper;
    }

    @GetMapping("/companyCouponsMaxPrice")
    @ResponseBody
    public ResponseEntity<?> getCompanyCouponsTillMaxPrice(@RequestHeader String tokenString, @RequestHeader int maxPrice){
        if (!tokenManager.isTokenExists(tokenString)) {
            String tokenExpired = "Token expired...";
            ResponseEntity<String> responseWrapper = new ResponseEntity<>(tokenExpired, HttpStatus.REQUEST_TIMEOUT);
            return responseWrapper;
        }
        Token token = tokenManager.findByTokenString(tokenString);
        List<Coupon> companyCoupons = companyService.getAllCouponUntilMaxPrice(maxPrice, token.getClientId());
        ResponseEntity<List<Coupon>> responseWrapper = new ResponseEntity<>(companyCoupons, HttpStatus.OK);
        return responseWrapper;
    }
    @GetMapping("/companyDetails")
    @ResponseBody
    public ResponseEntity<?> getCompanyDetails(@RequestHeader String tokenString) {
        if (!tokenManager.isTokenExists(tokenString)) {
            String tokenExpired = "Token expired...";
            ResponseEntity<String> responseWrapper = new ResponseEntity<>(tokenExpired, HttpStatus.REQUEST_TIMEOUT);
            return responseWrapper;
        }
        Token token = tokenManager.findByTokenString(tokenString);
        String company = companyService.companyDetails(token.getClientId()).toString();
        return new ResponseEntity<>(company, HttpStatus.OK);

    }



    @Override
    public ResponseEntity<?> login(Client client) {
        return null;
    }
}
