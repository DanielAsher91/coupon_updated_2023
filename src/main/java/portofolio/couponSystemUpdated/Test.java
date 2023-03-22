package portofolio.couponSystemUpdated;

import portofolio.couponSystemUpdated.job.CouponExpirationDaily;
import portofolio.couponSystemUpdated.job.TokenExpiration30Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Test {
    @Autowired
    LoginManager loginManager;
    @Autowired
    CouponExpirationDaily couponExpirationDaily;

    @Autowired
    TokenExpiration30Min tokenExpiration30Min;

    public void testAll() {

        Thread couponThread = new Thread(couponExpirationDaily);
        couponThread.start();
        Thread tokenThread = new Thread(tokenExpiration30Min);
        tokenThread.start();



 //       CustomerService customerService = (CustomerService)loginManager.login(ClientType.Customer, "kitty.fisher@yahoo.com", "1");
//        customerService.couponPurchase(2848);
//        System.out.println(customerService.getAllCustomerCoupons());
//        System.out.println(customerService.getAllCouponsFromASpecificCategory(Category.Restaurant));
//        System.out.println(customerService.getAllCouponsUntilMaxPrice(100));
//        System.out.println(customerService.getCustomerDetails());

//        CompanyService companyService = (CompanyService)loginManager.login(ClientType.Company, "cristobal.koch@hotmail.com", "2");
//        companyService.addNewCoupon(Category.Electricity, "iPhone 13", "Brand new iPhone 13", LocalDate.now(), LocalDate.of(2024,1,1), 10, 500,"url///1");
//        companyService.updateCoupon(2940, Category.Electricity, "iPhone 16", "Brand new iPhone 17", LocalDate.now(), LocalDate.of(2024,1,1), 7,150,"drllll");
//        companyService.deleteExistingCoupon(2940);
//        System.out.println(companyService.getAllCompanyCoupons());
//        System.out.println((companyService.getAllCouponsFromASpecificCategory(Category.Electricity)));
//        System.out.println(companyService.getAllCouponUntilMaxPrice(0));
//        System.out.println(companyService.getAllCouponUntilMaxPrice(100));
//        System.out.println(companyService.getCompanyDetails());

//        AdminService adminService = (AdminService)loginManager.login(ClientType.Admin,"admin@admin.com", "admin");
//        adminService.addNewCompany("Stark123", "starkactive123.offical@gmail.com", "5");
//        adminService.updateCompany(2933, "Stop");
//        adminService.deleteExistsCompany(294211);
//        System.out.println(adminService.getAllCompanies());
//        System.out.println(adminService.getCompanyByID(2941));
//        adminService.addNewCustomer("Daniella", "Asher", "daniellabit@gmail.com", "555");
//        adminService.updateCustomer(2740, "Gabriel", "Khalfon", "gabis912@gmail.com");
//        adminService.deleteExistingCustomer(274222);
//        System.out.println(adminService.getAllCustomers());
//        System.out.println(adminService.getCustomerByID(2740));


    }

}
