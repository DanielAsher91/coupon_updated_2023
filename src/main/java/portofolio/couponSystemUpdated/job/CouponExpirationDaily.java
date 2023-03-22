package portofolio.couponSystemUpdated.job;

import portofolio.couponSystemUpdated.entities.Coupon;
import portofolio.couponSystemUpdated.repositories.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class CouponExpirationDaily implements java.lang.Runnable {

    private boolean exit = false;

    @Autowired
    CouponRepository couponRepository;
    @Override
    public void run() {

        while (!exit) {

           List<Coupon> expiredCoupons = couponRepository.findByEndDateBefore(LocalDate.now());

            for (Coupon coupon : expiredCoupons) {
                couponRepository.delete(coupon);
            }
            try {
                Thread.sleep(12*60*60*1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }



    }
    public void setExit() {
        this.exit=true;
        System.out.println("The day job stopped working");
    }
}
