package portofolio.couponSystemUpdated;

import portofolio.couponSystemUpdated.services.AdminService;
import portofolio.couponSystemUpdated.services.ClientService;
import portofolio.couponSystemUpdated.services.CompanyService;
import portofolio.couponSystemUpdated.services.CustomerService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Data
public class LoginManager {

    @Autowired
    AdminService adminService;

    @Autowired
    CompanyService companyService;

    @Autowired
    CustomerService customerService;





    public ClientService login(ClientType clientType, String email, String password) {


       if (clientType == ClientType.Admin) {
       return adminService;
       }
       if (clientType == ClientType.Company && companyService.isCompanyExists(email, password)) {
           companyService.companyLoginForLoginManager(email, password);
           return companyService;
       }if (clientType == ClientType.Customer && customerService.isCustomerExists(email, password)) {
           customerService.CustomerLoginForLoginManager(email, password);
           return customerService;
        }

       else {
            System.out.println("Wrong details entered!");
           return null;}
    }


}
