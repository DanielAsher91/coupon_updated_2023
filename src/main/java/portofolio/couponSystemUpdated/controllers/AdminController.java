package portofolio.couponSystemUpdated.controllers;

import portofolio.couponSystemUpdated.entities.Client;
import portofolio.couponSystemUpdated.entities.Company;
import portofolio.couponSystemUpdated.entities.Customer;
import portofolio.couponSystemUpdated.entities.Token;
import portofolio.couponSystemUpdated.services.AdminService;
import portofolio.couponSystemUpdated.services.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("admin")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AdminController extends ClientController{

    @Autowired
    AdminService adminService;

    @Autowired
    TokenManager tokenManager;

    @GetMapping("/login")
    @ResponseBody
    public ResponseEntity<?> login(@RequestHeader("email") String email,@RequestHeader("password") String password) {
        if (email.equals("admin@admin.com") && password.equals("admin")) {
            int adminId = 1;
            Token token = tokenManager.createToken(adminId);
            ResponseEntity<String> responseWrapper = new ResponseEntity<>(token.getToken(), HttpStatus.OK);
            return responseWrapper;
        }String badLoginResponse = "Wrong details entered";
        return new ResponseEntity<>(badLoginResponse, HttpStatus.NOT_FOUND);

    }
    @PostMapping("/addCompany")
    @ResponseBody
    public ResponseEntity<?> addCompany(@RequestBody Company company, @RequestHeader("token") String tokenString) {

        if (!tokenManager.isTokenExists(tokenString)) {
            String tokenExpired = "Token Expired";
            return new ResponseEntity<>(tokenExpired, HttpStatus.REQUEST_TIMEOUT);
        }
        if (adminService.addCompany(company.getName(), company.getEmail(), company.getPassword()) == null) {
            return new ResponseEntity<>("Wrong details entered", HttpStatus.NOT_FOUND);
        }
        Company addedCompany = adminService.addCompany1(company);
        System.out.println(company.toString());
        return new ResponseEntity<>(company, HttpStatus.OK);

    }

//    @PostMapping("/addCustomer")
//    @ResponseBody
//    public ResponseEntity<?> addCustomer(@RequestBody Customer customer, @RequestHeader("token") String tokenString) {
//        if (!tokenManager.isTokenExists(tokenString)) {
//            String tokenExpired = "Token expired";
//            System.out.println("Token expired...");
//            return new ResponseEntity<>(tokenExpired, HttpStatus.REQUEST_TIMEOUT);
//
//        }
//        System.out.println("IM here");
//        if (adminService.addNewCustomer(customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getPassword()) == null){
//            return new ResponseEntity<>("Email is all ready in use", HttpStatus.NOT_FOUND);
//        }
//        Customer addedCustomer = adminService.addCustomerController(customer);
//        System.out.println(addedCustomer.toString());
//        return new ResponseEntity<>(addedCustomer, HttpStatus.OK);
//    }

    @PostMapping("/updateCompany")
    @ResponseBody
    public ResponseEntity<?> updateCompany(@RequestBody Company company, @RequestHeader("companyId") int companyId, @RequestHeader("token") String tokenString) {
        if (!tokenManager.isTokenExists(tokenString)) {
            String tokenExpired = "Token expired";
            return new ResponseEntity<>(tokenExpired, HttpStatus.REQUEST_TIMEOUT);}
        Company updatedCompany = adminService.updateCompany(companyId, company.getEmail());
        if (updatedCompany == null) {
            return new ResponseEntity<>("Wrong details entered", HttpStatus.NOT_FOUND);
        }return new ResponseEntity<>(updatedCompany, HttpStatus.OK);
    }
    @DeleteMapping("/deleteCompany")
    @ResponseBody
    public ResponseEntity<?> deleteCompany(@RequestHeader("companyId") int companyId, @RequestHeader("token") String tokenString) {
        if (!tokenManager.isTokenExists(tokenString)) {
            String tokenExpired = "Token expired";
            return new ResponseEntity<>(tokenExpired, HttpStatus.REQUEST_TIMEOUT);
        }
       if (adminService.deleteExistsCompany(companyId) == 0) {
           return new ResponseEntity<>("Wrong ID Entered!", HttpStatus.NOT_FOUND);
       }return new ResponseEntity<>("Compnay with id " + companyId + " has been deleted!", HttpStatus.OK);
    }
    @GetMapping("allCompanies")
    @ResponseBody
    public ResponseEntity<?> getAllCompanies(@RequestHeader("token") String tokenString) {
        if (!tokenManager.isTokenExists(tokenString)) {
            String tokenExpired = "Token Expired";
            return new ResponseEntity<>(tokenExpired, HttpStatus.NOT_FOUND);
        }
        List<Company> allCompanies = adminService.getAllCompanies();
        return new ResponseEntity<>(allCompanies,HttpStatus.OK);
    }
    @GetMapping("/oneCompany")
    @ResponseBody
    public ResponseEntity<?> getOneCompany(@RequestHeader("companyId") int companyId, @RequestHeader("token") String tokenString) {

        if (!tokenManager.isTokenExists(tokenString)) {
            String tokenExpired = "Token Expired";
            System.out.println("tokennn");
            return new ResponseEntity<>(tokenExpired, HttpStatus.REQUEST_TIMEOUT);
        }
        Optional<Company> company = adminService.getOneCompany(companyId);
        System.out.println(company.get().getId());
        return new ResponseEntity<>(company, HttpStatus.OK);
    }

    @PostMapping("/addCustomer")
    @ResponseBody
    public ResponseEntity<?> addCustomer(@RequestBody Customer customer, @RequestHeader("token") String tokenString) {
        if (!tokenManager.isTokenExists(tokenString)) {
            String tokenExpired = "Token Expired";
            System.out.println("Token expired...");
            return new ResponseEntity<>(tokenExpired, HttpStatus.REQUEST_TIMEOUT);

        }
        System.out.println("IM here");
        System.out.println(customer);
//        if (adminService.addNewCustomer(customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getPassword()) == null){
//            return new ResponseEntity<>("Email is all ready in use", HttpStatus.NOT_FOUND);
//        }
        Customer addedCustomer = adminService.addCustomerController(customer);

        System.out.println(addedCustomer.toString());
        return new ResponseEntity<>(addedCustomer, HttpStatus.OK);
    }

    public ResponseEntity<?> updateCustomer(@RequestBody Customer customer, @RequestHeader("customerId") int customerId, @RequestHeader("token") String tokenString) {
        System.out.println(customerId);
        System.out.println(customer.toString());
        if (!tokenManager.isTokenExists(tokenString)) {
            String tokenExpired = "Token Expired";
            return new ResponseEntity<>(tokenExpired, HttpStatus.REQUEST_TIMEOUT);
        }
       int updateResponse = adminService.updateCustomerController(customerId, customer.getFirstName(), customer.getLastName(), customer.getEmail());
        if (updateResponse == 0) {
            System.out.println(updateResponse);
            return new ResponseEntity<>("Email : " + customer.getEmail() + " is all ready in use!", HttpStatus.NOT_ACCEPTABLE);
        }if(updateResponse == 1) {
            System.out.println(updateResponse);
            return new ResponseEntity<>("The Customer has been updated!", HttpStatus.OK);
        }else return new ResponseEntity<>("No Customer with this ID :" + customer.getId(), HttpStatus.NOT_FOUND);
    }
    @PostMapping("/updateCustomer")
    @ResponseBody
    public ResponseEntity<?> updateCustomer1(@RequestBody Customer customer, @RequestHeader("customerId") int customerId, @RequestHeader("token") String tokenString) {
        if (!tokenManager.isTokenExists(tokenString)) {
            String tokenExpired = "Token expired";
            return new ResponseEntity<>(tokenExpired, HttpStatus.REQUEST_TIMEOUT);}
        Customer updatedCustomer = adminService.updateCustomer1(customerId, customer.getFirstName(), customer.getLastName(), customer.getEmail());
        if (updatedCustomer == null) {
            return new ResponseEntity<>("Wrong details entered", HttpStatus.NOT_FOUND);
        }return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
    }

//    @PostMapping("/updateCompany")
//    @ResponseBody
//    public ResponseEntity<?> updateCompany(@RequestBody Company company, @RequestHeader("companyId") int companyId, @RequestHeader("token") String tokenString) {
//        if (!tokenManager.isTokenExists(tokenString)) {
//            String tokenExpired = "Token expired";
//            return new ResponseEntity<>(tokenExpired, HttpStatus.REQUEST_TIMEOUT);}
//        Company updatedCompany = adminService.updateCompany(companyId, company.getEmail());
//        if (updatedCompany == null) {
//            return new ResponseEntity<>("Wrong details entered", HttpStatus.NOT_FOUND);
//        }return new ResponseEntity<>(updatedCompany, HttpStatus.OK);
//    }

    @DeleteMapping("/deleteCustomer")
    @ResponseBody
    public ResponseEntity<?> deleteCustomer(@RequestHeader("customerId") int customerId, @RequestHeader("token") String tokenString) {

        if (!tokenManager.isTokenExists(tokenString)) {
            String tokenExpired = "Token expired";
            return new ResponseEntity<>(tokenExpired, HttpStatus.REQUEST_TIMEOUT);
        }
        int deleteResponse = adminService.deleteExistingCustomer(customerId);
        if (deleteResponse==1) {
        return new ResponseEntity<>("No Customer with this ID : " + customerId, HttpStatus.NOT_FOUND);
        }else return new ResponseEntity<>("The customer with ID : " + customerId + " has been deleted!", HttpStatus.OK);
    }
    @GetMapping("/allCustomers")
    @ResponseBody
    public ResponseEntity<?> getAllCustomers(@RequestHeader("token") String tokenString){
        if (!tokenManager.isTokenExists(tokenString)) {
            String tokenExpired = "Token Expired";
            return new ResponseEntity<>(tokenExpired, HttpStatus.REQUEST_TIMEOUT);
        }
        List<Customer> allCustomer = adminService.getAllCustomers();
        return new ResponseEntity<>(allCustomer,HttpStatus.OK);
    }
    @GetMapping("/oneCustomer")
    @ResponseBody
    public ResponseEntity<?> getOneCustomer(@RequestHeader("customerId") int customerId, @RequestHeader("token") String tokenString){

        if (!tokenManager.isTokenExists(tokenString)) {
            String tokenExpired = "Token Expired";
            return new ResponseEntity<>(tokenExpired, HttpStatus.REQUEST_TIMEOUT);
        }
        Customer customer = adminService.getCustomerByID(customerId);
        System.out.println(customer);
        if (customer == null) {
            System.out.println("Wrong ID entered!");
            return new ResponseEntity<>("Wrong ID entered!", HttpStatus.NOT_FOUND); }
        else return new ResponseEntity<>(customer, HttpStatus.OK);


    }

    @Override
    public ResponseEntity<?> login(Client client) {
        return null;
    }
}
