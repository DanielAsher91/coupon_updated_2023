package portofolio.couponSystemUpdated.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public abstract class Client {

 private String email;
 private String password;


}
