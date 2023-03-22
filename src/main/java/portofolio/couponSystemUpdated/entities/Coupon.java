package portofolio.couponSystemUpdated.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import jakarta.persistence.*;


import java.time.LocalDate;

@Entity
@Table(name = "coupons")
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(of = "id")
@Data
@Builder
@ToString(of = {"id", "category", "title", "description", "startDate", "endDate", "amount", "price"})
public class Coupon  extends Client {

    @Id
    @GeneratedValue
    private int id;
    private Category category;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private int amount;
    private double price;
    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    @JsonIgnoreProperties("companyCoupons")
    private Company company;
    @ManyToOne(fetch = FetchType.LAZY)
    @Transient
    private Customer customer;
}
