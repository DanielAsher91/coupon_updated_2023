package portofolio.couponSystemUpdated.services;

import portofolio.couponSystemUpdated.entities.Category;
import portofolio.couponSystemUpdated.entities.Coupon;
import portofolio.couponSystemUpdated.entities.Customer;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Data
public class CustomerService extends ClientService {


    private String email;

    private String password;

    private int customerId;

    public void CustomerLoginForLoginManager(String email, String password) {
        if (customerRepository.existsByEmailAndPassword(email, password)) {
            this.email = email;
            this.password = password;
            int customerId = customerRepository.findByEmailAndPassword(email, password).getId();
            System.out.println(customerId);
            this.customerId = customerId;
            return;

        }
        System.out.println("No customer with that details found!");

    }

    public Customer customerLoginForController(String email, String password) {
        if(!customerRepository.existsByEmailAndPassword(email, password)) {
            return null;
        }return customerRepository.findByEmailAndPassword(email, password);
    }

    public boolean customerLogin(String email, String password) {
        if (customerRepository.existsByEmailAndPassword(email, password)) {
            this.email = email;
            this.password = password;
            int customerId = customerRepository.findByEmailAndPassword(email, password).getId();
            System.out.println(customerId);
            this.customerId = customerId;
            return true;

        }
        System.out.println("No customer with that details found!");
        return false;

    }

    public Optional<Customer> getCustomer(int customerId) {
        return customerRepository.findById(customerId);
    }
    public Optional<Coupon> getCoupon1(int couponId) {
        return couponRepository.findById(couponId);
    }





    public void couponPurchase2(int couponId, int customerIdFromFront) {
        if (couponRepository.findById(couponId).isEmpty()) {
            System.out.println("Wrong coupon ID : " + couponId);
            return;
        }if (customerRepository.findById(customerId).isEmpty()) {
            System.out.println("Wrong customer ID : " + customerId);
            return;
        }
        Customer customerP = customerRepository.findById(customerId).get();
        Coupon couponP = couponRepository.findById(couponId).get();
        List<Coupon> customerCoupons = customerP.getCustomerCoupons();
        for (Coupon coupon : customerCoupons) {
            if (coupon.getId() == couponP.getId()) {
                System.out.println("Cant buy same ticket twice!");
                return;
            } if (couponP.getAmount() == 0) {
                System.out.println("Coupon amount finished");
                return;
            } if (couponP.getEndDate().isBefore(LocalDate.now())) {
                System.out.println("Coupon has been expired!");
                return;
            }
        }
        couponP.setAmount(couponP.getAmount()-1);

        addCoupon(couponP);
        customerP.addCouponToCustomer(couponP);

        customerRepository.save(customerP);
    }

    public String couponPurchaseForController(int couponId, int customerIdFromFront) {
        if (couponRepository.findById(couponId).isEmpty()) {
            return "Wrong coupon ID : " + couponId;
        }if (customerRepository.findById(customerIdFromFront).isEmpty()) {
            return "Wrong customer ID : " + customerIdFromFront;
        }
        Customer purchaseCustomer = customerRepository.findById(customerId).get();
        Coupon purchasedCoupon = couponRepository.findById(couponId).get();
        List<Coupon> customerCoupons = purchaseCustomer.getCustomerCoupons();
        for (Coupon coupon : customerCoupons) {
            if (coupon.getId() == couponId) {
                return "Cant Purchase same ticket twice!";
            }if (purchasedCoupon.getAmount() == 0) {
                return "Coupon amount finished";
            }if (purchasedCoupon.getEndDate().isBefore(LocalDate.now())) {
                return "Coupon has been expired!";
            }
        }
        purchasedCoupon.setAmount(purchasedCoupon.getAmount() - 1);
        addCoupon(purchasedCoupon);
        purchaseCustomer.addCouponToCustomer(purchasedCoupon);
        customerRepository.save(purchaseCustomer);
        return "The Coupon Was Purchased Successfully!";

    }

    public Coupon couponPurchase(Coupon coupon) {
        if (couponRepository.findById(coupon.getId()).isEmpty()) {
            System.out.println("Wrong coupon ID : " + coupon.getId());
            return null;
        }if (customerRepository.findById(customerId).isEmpty()) {
            System.out.println("Wrong customer ID : " + customerId);
            return null;
        }
        Customer customerP = customerRepository.findById(customerId).get();
        Coupon couponP = couponRepository.findById(coupon.getId()).get();
        List<Coupon> customerCoupons = customerP.getCustomerCoupons();
        for (Coupon coupon1 : customerCoupons) {
            if (coupon1.getId() == couponP.getId()) {
                System.out.println("Cant buy same ticket twice!");
                return null;
            } if (couponP.getAmount() == 0) {
                System.out.println("Coupon amount finished");
                return null;
            } if (couponP.getEndDate().isBefore(LocalDate.now())) {
                System.out.println("Coupon has been expired!");
                return null;
            }
        }
        couponP.setAmount(couponP.getAmount()-1);

        addCoupon(couponP);
        customerP.addCouponToCustomer(couponP);

        customerRepository.save(customerP);
        return couponP;
    }

    public List<Coupon> getAllCustomerCoupons(int customerIdFromToken) {
       try {
          Customer customer = customerRepository.findById(customerIdFromToken).get();
          return customer.getCustomerCoupons();
       }catch (Exception e) {
           System.out.println("Wrong ID entered!");
           return null;
       }
    }

    public List<Coupon> getAllCouponsFromASpecificCategory(int customerIdFromToken, Category category) {
        try {
            Customer customer = customerRepository.findById(customerIdFromToken).get();
            List<Coupon> customerCoupons = customer.getCustomerCoupons();
            List<Coupon> customersCouponsFromSpecificCategory = new ArrayList<>();
            for (Coupon coupon : customerCoupons) {
                if (coupon.getCategory() == category) {
                    customersCouponsFromSpecificCategory.add(coupon);
                }
            }return customersCouponsFromSpecificCategory;
        }catch (Exception e) {
            System.out.println("Wrong ID entered!");
            return null;
        }
    }

    public List<Coupon> getAllCouponsUntilMaxPrice(int customerIdFromToken, double maxPrice) {
        try {
            Customer customer = customerRepository.findById(customerIdFromToken).get();
            List<Coupon> customerCoupons = customer.getCustomerCoupons();
            List<Coupon> customersCouponsUntilMaxPrice = new ArrayList<>();
            for (Coupon coupon : customerCoupons) {
                if (coupon.getPrice() < maxPrice) {
                    customersCouponsUntilMaxPrice.add(coupon);
                }
            }return customersCouponsUntilMaxPrice;

        }catch (Exception e) {
            System.out.println("Wrong ID entered!");
            return null;
        }
    }

    public Customer getCustomerDetails(int customerIdFromToken) {
        try {
            return customerRepository.findById(customerIdFromToken).get();
        }catch (Exception e) {
            System.out.println("Wrong ID entered!");
            return null;
        }
    }

    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }












}
