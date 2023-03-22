package portofolio.couponSystemUpdated.repositories;

import portofolio.couponSystemUpdated.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    Boolean existsByToken (String tokenString);

    Token findByToken(String token);

}
