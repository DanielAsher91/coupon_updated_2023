package portofolio.couponSystemUpdated.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import jakarta.persistence.*;


import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers")
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(of = "id")
@Data
@ToString(of = {"id", "firstName", "lastName", "email", "password"})
@Builder
public class Customer  extends Client  {

    @Id
    @GeneratedValue
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    @OneToMany( fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JsonIgnoreProperties("customer")
    private List<Coupon> customerCoupons = new ArrayList<>();

    public void addCouponToCustomer(Coupon coupon) {
        coupon.setCustomer(this);
        this.customerCoupons.add(coupon);


    }
}
