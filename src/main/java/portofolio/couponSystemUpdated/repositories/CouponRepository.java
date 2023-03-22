package portofolio.couponSystemUpdated.repositories;

import portofolio.couponSystemUpdated.entities.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon, Integer> {

    boolean existsById(int couponId);
    List<Coupon> findByEndDateBefore(LocalDate localDate);

}
