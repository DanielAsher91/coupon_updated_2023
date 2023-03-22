package portofolio.couponSystemUpdated.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;


import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "companies")
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(of = {"id", "name", "email", "password"})
@Data
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Company extends Client {

    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String email;
    private String password;

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JsonIgnoreProperties("company")
    private List<Coupon> companyCoupons = new ArrayList<>();

    public void addCouponToCompany(Coupon coupon) {
        coupon.setCompany(this);
        this.companyCoupons.add(coupon);
    }

}
