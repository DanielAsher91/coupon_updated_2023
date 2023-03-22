package portofolio.couponSystemUpdated.entities;

import lombok.*;


import java.time.LocalTime;
import jakarta.persistence.*;

@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(of = "id")
@Data
@Builder
public class Token {

    @Id
    @GeneratedValue
    private int id;
    private String token;
    private LocalTime issueOfToken;

    private int clientId;


}
