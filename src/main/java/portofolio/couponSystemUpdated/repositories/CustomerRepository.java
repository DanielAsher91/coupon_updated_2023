package portofolio.couponSystemUpdated.repositories;

import portofolio.couponSystemUpdated.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    List<Customer> findByFirstName(String firstName);

    Boolean existsByEmailAndPassword(String email, String password);

    Customer findByEmailAndPassword(String email, String password);

}
