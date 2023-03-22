package portofolio.couponSystemUpdated.controllers;

import portofolio.couponSystemUpdated.entities.Client;
import org.springframework.http.ResponseEntity;


public abstract class ClientController {

   public abstract ResponseEntity<?> login(Client client);

}





