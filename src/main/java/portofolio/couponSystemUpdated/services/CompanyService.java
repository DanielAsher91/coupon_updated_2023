package portofolio.couponSystemUpdated.services;

import portofolio.couponSystemUpdated.entities.Category;
import portofolio.couponSystemUpdated.entities.Company;
import portofolio.couponSystemUpdated.entities.Coupon;
import portofolio.couponSystemUpdated.repositories.CompanyRepository;
import portofolio.couponSystemUpdated.repositories.CouponRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Data
public class CompanyService extends ClientService {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    CouponRepository couponRepository;
    private String email;
    private String password;
    private int companyId;

    public void companyLoginForLoginManager(String email, String password) {
        if (companyRepository.existsByEmailAndPassword(email, password)) {
            this.email = email;
            this.password = password;
            this.companyId = companyRepository.findByEmailAndPassword(email, password).getId();
            System.out.println("Login complete!");
            return;
        }
        System.out.println("Wrong details entered!");
    }

    public boolean companyLogin1(String email, String password) {
        if (companyRepository.existsByEmailAndPassword(email, password)) {
            this.email = email;
            this.password = password;
            this.companyId = companyRepository.findByEmailAndPassword(email, password).getId();
            System.out.println("Login complete!");
            return true;
        }
        System.out.println("Wrong details entered!");
        return false;
    }

    public Company companyLogin(String email, String password) {
        if(!companyRepository.existsByEmailAndPassword(email, password)) {
            return null;
        }return companyRepository.findByEmailAndPassword(email, password);
    }

    public int addCoupon1(Coupon coupon, int companyIdFromToken) {
        Company couponsCompany = companyRepository.findById(companyIdFromToken).get();
        List<Coupon> companyCoupons = couponsCompany.getCompanyCoupons();
        for (Coupon coupon1 : companyCoupons) {
            if (coupon1.getTitle().equals(coupon.getTitle())) {
                System.out.println("The title is all ready been used!");
                return 0;
            }

        }
        Coupon newCoupon = Coupon.builder().category(coupon.getCategory()).title(coupon.getTitle()).description(coupon.getDescription()).startDate(coupon.getStartDate())
                .endDate(coupon.getEndDate()).amount(coupon.getAmount()).price(coupon.getPrice()).image(coupon.getImage()).company(couponsCompany).build();
        couponRepository.save(newCoupon);
        return newCoupon.getId();
    }


    public void addNewCoupon(Category category, String title, String description, LocalDate startDate
            , LocalDate endDate, int amount, double price, String image) {
        if (companyRepository.findById(companyId).isEmpty()) {
            System.out.println("Wrong company ID : " + companyId);
            return;
        }
        Company couponsCompany = companyRepository.findById(companyId).get();
        List<Coupon> companyCoupons = couponsCompany.getCompanyCoupons();
        for (Coupon coupon : companyCoupons) {
            if (coupon.getTitle().equals(title)) {
                System.out.println("The title is all ready been used!");
                return;
            }
        }
        Coupon newCoupon = Coupon.builder().category(category).title(title).description(description).startDate(startDate)
                .endDate(endDate).amount(amount).price(price).image(image).company(couponsCompany).build();
        Coupon coupon = addCoupon1(newCoupon);
        System.out.println("Coupon With ID : " + coupon.getId() + " has been added successfully!");


    }

//    public int updateCoupon1(Coupon coupon, int companyIdFromToken) {
//        Company couponsCompany = companyRepository.findById(companyIdFromToken).get();
//        List<Coupon> companyCoupons = couponsCompany.getCompanyCoupons();
//        for (Coupon coupon1 : companyCoupons) {
//            if (coupon1.getTitle().equals(coupon.getTitle())) {
//                System.out.println("The title is all ready been used!");
//                return 0;
//            }
//        }
//        Coupon updatedCoupon = couponRepository.findById(coupon.getId()).get();
//        updatedCoupon.setCategory(coupon.getCategory());
//        updatedCoupon.setTitle(coupon.getTitle());
//        updatedCoupon.setDescription(coupon.getDescription());
//        updatedCoupon.setStartDate(coupon.getStartDate());
//        updatedCoupon.setEndDate(coupon.getEndDate());
//        updatedCoupon.setAmount(coupon.getAmount());
//        updatedCoupon.setPrice(coupon.getPrice());
//        updatedCoupon.setImage(coupon.getImage());
//
//        couponRepository.save(updatedCoupon);
//        return updatedCoupon.getId();
//    }
//////////////////////////////////////
    public Coupon updateCoupon(int couponId, Coupon updatedCoupon, int companyIdFromToken) {
        if (companyRepository.findById(companyIdFromToken).isEmpty()) {
            System.out.println("Wrong company ID : " + companyIdFromToken);
            return null;
        }
        if (couponRepository.findById(couponId).isEmpty()) {
            System.out.println("Wrong coupon ID : " + couponId);
            return null;
        }
        Company couponsCompany = companyRepository.findById(companyIdFromToken).get();

        List<Coupon> companyCoupons = couponsCompany.getCompanyCoupons();
        for (Coupon coupon : companyCoupons) {

            if (coupon.getTitle().equals(updatedCoupon.getTitle())) {
                System.out.println("The title is all ready been used!");
                return null;
            }
        }
        Coupon newUpdatedCoupon = couponRepository.findById(couponId).get();
        newUpdatedCoupon.setCategory(updatedCoupon.getCategory());
        newUpdatedCoupon.setTitle(updatedCoupon.getTitle());
        newUpdatedCoupon.setDescription(updatedCoupon.getDescription());
        newUpdatedCoupon.setStartDate(updatedCoupon.getStartDate());
        newUpdatedCoupon.setEndDate(updatedCoupon.getEndDate());
        newUpdatedCoupon.setAmount(updatedCoupon.getAmount());
        newUpdatedCoupon.setPrice(updatedCoupon.getPrice());
        newUpdatedCoupon.setImage(updatedCoupon.getImage());

        addCoupon1(newUpdatedCoupon);

        System.out.println("Coupon : " + newUpdatedCoupon.getId() + " has been updated successfully");
        return newUpdatedCoupon;
    }

    public int deleteCoupon(int couponId, int companyIdFromToken) {

        Company company = companyRepository.findById(companyIdFromToken).get();
        List<Coupon> companyCoupons = company.getCompanyCoupons();

        for (Coupon coupon : companyCoupons) {
            if (coupon.getId() == couponId) {
                couponRepository.deleteById(coupon.getId());
                return 1;
            }
        }
        return 0;
    }

    public void deleteExistingCoupon(int couponId) {
        if (companyRepository.findById(companyId).isEmpty()) {
            System.out.println("Wrong Id Entered!");
            return;
        }
        Company company = companyRepository.findById(companyId).get();
        List<Coupon> companyCoupons = company.getCompanyCoupons();
        for (Coupon coupon : companyCoupons) {
            if (coupon.getId() == couponId) {
                couponRepository.deleteById(coupon.getId());
                System.out.println("Coupon has been deleted!");
            }
        }

    }

    public List<Coupon> getAllCompanyCoupons(int companyIdFromToken) {
        if (companyRepository.findById(companyIdFromToken).isEmpty()) {
            System.out.println("Wrong Id Entered!");
            return null;
        }
        Company company = companyRepository.findById(companyIdFromToken).get();
        return company.getCompanyCoupons();
    }

    public List<Coupon> getAllCouponsFromASpecificCategory(Category category, int companyIdFromToken) {
        if (companyRepository.findById(companyIdFromToken).isEmpty()) {
            System.out.println("Wrong Id Entered!");
            return null;
        }
        Company company = companyRepository.findById(companyIdFromToken).get();
        List<Coupon> companyCoupons = company.getCompanyCoupons();
        List<Coupon> companyCouponsFromASpecificCategory = new ArrayList<>();
        for (Coupon coupon : companyCoupons) {
            if (coupon.getCategory() == category) {
                companyCouponsFromASpecificCategory.add(coupon);
            }
        }
        return companyCouponsFromASpecificCategory;
    }

    public List<Coupon> getAllCouponUntilMaxPrice(double maxPrice, int companyIdFromToken) {
        if (companyRepository.findById(companyIdFromToken).isEmpty()) {
            System.out.println("Wrong Id Entered!");
            return null;
        }
        Company company = companyRepository.findById(companyIdFromToken).get();
        List<Coupon> companyCoupons = company.getCompanyCoupons();
        List<Coupon> companyCouponsUntilMaxPrice = new ArrayList<>();
        for (Coupon coupon : companyCoupons) {
            if (coupon.getPrice() < maxPrice) {
                companyCouponsUntilMaxPrice.add(coupon);
            }
        }
        return companyCouponsUntilMaxPrice;
    }

    public String getCompanyDetails() {
        if (companyRepository.findById(companyId).isEmpty()) {

            return "Wrong Id Entered!";
        }
        Company company = companyRepository.findById(companyId).get();
        return company.toString();
    }
    public Company companyDetails(int companyIdFromToken) {
        if (companyRepository.findById(companyIdFromToken).isEmpty()) {
            return null;
        }Optional<Company> optionalCompany  = companyRepository.findById(companyIdFromToken);
        Company company = optionalCompany.get();
        return company;


    }

}
