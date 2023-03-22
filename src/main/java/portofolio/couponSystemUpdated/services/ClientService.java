package portofolio.couponSystemUpdated.services;

import portofolio.couponSystemUpdated.entities.Company;
import portofolio.couponSystemUpdated.entities.Coupon;
import portofolio.couponSystemUpdated.entities.Customer;
import portofolio.couponSystemUpdated.repositories.CompanyRepository;
import portofolio.couponSystemUpdated.repositories.CouponRepository;
import portofolio.couponSystemUpdated.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;


public abstract class ClientService {

    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CouponRepository couponRepository;

    public List getDates(LocalDate localDate) {
        return couponRepository.findByEndDateBefore(localDate);
    }


    public void deleteAll() {
        couponRepository.deleteAll();
        customerRepository.deleteAll();
        companyRepository.deleteAll();
    }

    public boolean isCompanyExists(String email, String password) {
        return companyRepository.existsByEmailAndPassword(email, password);
    }
    public void addCompany(Company company) {
        companyRepository.save(company);
    }
    public void deleteCompany(int companyId) {
        companyRepository.deleteById(companyId);
    }
//    public List<Company> getAllCompanies() {
//        return companyRepository.findAll();
//    }
//    public Optional<Company> getOneCompany(int id) {
//        return companyRepository.findById(id);
//    }


    public boolean isCustomerExists(String email, String password) {
        return customerRepository.existsByEmailAndPassword(email, password);
    }
    public void addCustomer(Customer customer) {

        customerRepository.save(customer);
    }
    public void deleteCustomer(int customerId) {
        customerRepository.deleteById(customerId);
    }

    public Optional<Customer> getOneCustomer(int customerId) {
        return customerRepository.findById(customerId);
    }


    public void addCoupon(Coupon coupon) {
        couponRepository.save(coupon);
    }

    public Coupon addCoupon1(Coupon coupon) {
        Coupon couponFromDB = couponRepository.save(coupon);
        return couponFromDB;
    }
    public void deleteCoupon(int couponId) {
        couponRepository.deleteById(couponId);
    }

    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }
    public Optional<Coupon> getOneCoupon(int couponId) {
        return couponRepository.findById(couponId);
    }

    public void addCouponPurchase(int customerId, int couponId) {
        if (getOneCustomer(customerId).isPresent() && getOneCoupon(couponId).isPresent()) {
            Customer customer = getOneCustomer(customerId).get();
            Coupon coupon = getOneCoupon(couponId).get();
            customer.addCouponToCustomer(coupon);
            coupon.setCustomer(customer);
            addCustomer(customer);
            addCoupon(coupon);
        }
    }

    public void deleteCouponPurchase(int customerId, int couponId) {
        if (getOneCustomer(customerId).isPresent() && getOneCoupon(couponId).isPresent()) {
            Customer customer = getOneCustomer(customerId).get();
            Coupon coupon = getOneCoupon(couponId).get();
            coupon.setCustomer(null);
            addCoupon(coupon);
            Iterator<Coupon> iter = customer.getCustomerCoupons().iterator();

            while (iter.hasNext()) {
                Coupon coupon1 = iter.next();
                if (coupon1.getId() == couponId) {
                    iter.remove();
                }
            }addCustomer(customer);
        }
    }

}
