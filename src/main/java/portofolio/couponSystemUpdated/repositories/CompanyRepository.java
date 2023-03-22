package portofolio.couponSystemUpdated.repositories;

import portofolio.couponSystemUpdated.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

    Boolean existsByEmailAndPassword(String email, String password);

    Company findByEmailAndPassword(String email, String password);







}
