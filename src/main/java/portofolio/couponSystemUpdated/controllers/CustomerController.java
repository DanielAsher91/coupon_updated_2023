package portofolio.couponSystemUpdated.controllers;

import portofolio.couponSystemUpdated.entities.*;
import portofolio.couponSystemUpdated.services.CustomerService;
import portofolio.couponSystemUpdated.services.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/customer")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CustomerController extends ClientController{

    @Autowired
    CustomerService customerService;

    @Autowired
    TokenManager tokenManager;


    @GetMapping("/login")
    @ResponseBody
    @CrossOrigin(exposedHeaders = "*")
    public ResponseEntity<?> login(@RequestHeader("email") String email, @RequestHeader("password") String password) {
        Customer loginCustomer = customerService.customerLoginForController(email, password);
        if (loginCustomer == null) {
            return new ResponseEntity<>("Wrong Details Entered!", HttpStatus.NOT_FOUND);
        }
        int loginCustomerId = loginCustomer.getId();
        Token token = tokenManager.createToken(loginCustomerId);
        HttpHeaders tokenResponse = new HttpHeaders();
        tokenResponse.set("Customer-Token",token.getToken());
        return ResponseEntity.ok().headers(tokenResponse).body(loginCustomer);


    }
//    public ResponseEntity<?> login(@RequestHeader("email") String email,@RequestHeader("password") String password) {
//        Company loginCompany = companyService.companyLogin(email, password);
//        if (loginCompany == null) {
//            return new ResponseEntity<>(loginCompany, HttpStatus.NOT_FOUND);
//        }
//        int loginCompanyId = loginCompany.getId();
//        System.out.println(loginCompanyId);
//        Token token = tokenManager.createToken(loginCompanyId);
//        HttpHeaders tokenResponse = new HttpHeaders();
//        tokenResponse.set("Company-Token", token.getToken());
//        return ResponseEntity.ok().headers(tokenResponse).body(loginCompany);
//    }

    @PostMapping("/couponPurchase")
    @ResponseBody
    public ResponseEntity<?> couponPurchase(@RequestHeader("couponId") int couponId, @RequestHeader("token") String tokenString) {
        if (!tokenManager.isTokenExists(tokenString)) {
            String tokenExpired = "Token Expired";
            ResponseEntity<String> responseWrapper = new ResponseEntity<>(tokenExpired, HttpStatus.REQUEST_TIMEOUT);
            return responseWrapper;
        }
        Token token = tokenManager.findByTokenString(tokenString);
        String couponPurchaseResponse = customerService.couponPurchaseForController(couponId, token.getClientId());
        if (!couponPurchaseResponse.equals("The Coupon Was Purchased Successfully!")) {
            return new ResponseEntity<>(couponPurchaseResponse, HttpStatus.NOT_FOUND);
        }else return new ResponseEntity<>(couponPurchaseResponse, HttpStatus.OK);
    }

    @GetMapping("/customerCoupons")
    @ResponseBody
    public ResponseEntity<?> getCustomerCoupons(@RequestHeader("token") String tokenString) {
        if (!tokenManager.isTokenExists(tokenString)) {
            String tokenExpired = "Token Expired";
            ResponseEntity<String> responseWrapper = new ResponseEntity<>(tokenExpired, HttpStatus.REQUEST_TIMEOUT);
            return responseWrapper;
        }
        Token token = tokenManager.findByTokenString(tokenString);
        List<Coupon> customerCoupons = customerService.getAllCustomerCoupons(token.getClientId());
        ResponseEntity<List<Coupon>> responseWrapper = new ResponseEntity<>(customerCoupons, HttpStatus.OK);
        return responseWrapper;

    }

    @GetMapping("/customerCouponsCategory")
    @ResponseBody
    public ResponseEntity<?> getCustomerCouponsCategory(@RequestHeader("token") String tokenString,@RequestHeader("category") Category category) {
        if (!tokenManager.isTokenExists(tokenString)) {
            String tokenExpired = "Token Expired";
            ResponseEntity<String> responseWrapper = new ResponseEntity<>(tokenExpired, HttpStatus.REQUEST_TIMEOUT);
            return responseWrapper;
        }
        Token token = tokenManager.findByTokenString(tokenString);
        List<Coupon> customerCouponsFromCategory = customerService.getAllCouponsFromASpecificCategory(token.getClientId(), category);
        ResponseEntity<List<Coupon>> responseWrapper = new ResponseEntity<>(customerCouponsFromCategory, HttpStatus.OK);
        return responseWrapper;
    }

    @GetMapping("/customerCouponsMaxPrice")
    @ResponseBody
    public ResponseEntity<?> getCustomerCouponsMaxPrice(@RequestHeader("token") String tokenString,@RequestHeader("maxPrice") int maxPrice) {
        if (!tokenManager.isTokenExists(tokenString)) {
            String tokenExpired = "Token Expired";
            ResponseEntity<String> responseWrapper = new ResponseEntity<>(tokenExpired, HttpStatus.REQUEST_TIMEOUT);
            return responseWrapper;
        }
        Token token = tokenManager.findByTokenString(tokenString);
        List<Coupon> customerCouponsTillMaxPrice = customerService.getAllCouponsUntilMaxPrice(token.getClientId(), maxPrice);
        ResponseEntity<List<Coupon>> responseWrapper = new ResponseEntity<>(customerCouponsTillMaxPrice, HttpStatus.OK);
        return responseWrapper;
    }
    @GetMapping("/customerDetails")
    @ResponseBody
    public ResponseEntity<?> getCustomerDetails(@RequestHeader String tokenString) {
        if (!tokenManager.isTokenExists(tokenString)) {
            String tokenExpired = "Token expired...";
            ResponseEntity<String> responseWrapper = new ResponseEntity<>(tokenExpired, HttpStatus.REQUEST_TIMEOUT);
            return responseWrapper;
        }
        Token token = tokenManager.findByTokenString(tokenString);
        String customer = customerService.getCustomerDetails(token.getClientId()).toString();
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }
    @GetMapping("/allCoupons")
    @ResponseBody
    public ResponseEntity<?> getAllCoupons(@RequestHeader("token") String tokenString) {
        if (!tokenManager.isTokenExists(tokenString)) {
            String tokenExpired = "Token Expired";
            ResponseEntity<String> responseWrapper = new ResponseEntity<>(tokenExpired, HttpStatus.REQUEST_TIMEOUT);
            return responseWrapper;
        }
//        Token token = tokenManager.findByTokenString(tokenString);
        return new ResponseEntity<>(customerService.getAllCoupons(), HttpStatus.OK);

    }
    @DeleteMapping("/deleteToken")
    @ResponseBody
    public ResponseEntity<?> deleteToken(@RequestHeader("token") String tokenString) {
        if (!tokenManager.isTokenExists(tokenString)) {
            String tokenExpired = "Token Expired";
            ResponseEntity<String> responseWrapper = new ResponseEntity<>(tokenExpired, HttpStatus.REQUEST_TIMEOUT);
            return responseWrapper;
        }
        Token deletedToken = tokenManager.findByTokenString(tokenString);
        if (deletedToken == null) {
            return new ResponseEntity<>("Token all ready expired!", HttpStatus.NOT_FOUND);
        }else return new ResponseEntity<>(tokenManager.deleteToken(deletedToken), HttpStatus.OK);

    }


    @Override
    public ResponseEntity<?> login(Client client) {
        return null;
    }
}
