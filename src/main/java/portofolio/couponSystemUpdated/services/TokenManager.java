package portofolio.couponSystemUpdated.services;

import portofolio.couponSystemUpdated.entities.Token;
import portofolio.couponSystemUpdated.repositories.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Random;

@Service
public class TokenManager {

    Random r = new Random();
    @Autowired
    TokenRepository tokenRepository;



    public String randomToken() {
        String token = "TOKEN";
        int indexNum = r.nextInt(9-1)+1;

        int randomNumber = r.nextInt(99999-10000) + 10000;
        return token +"_"+indexNum+"_"+randomNumber;
    }

   public Token createToken(int clientId) {
       Token newToken = Token.builder().token(randomToken()).issueOfToken(LocalTime.now()).clientId(clientId).build();
       tokenRepository.save(newToken);
       System.out.println("TOKEN Created: " + newToken);
       return newToken;

   }

   public boolean isTokenExists(String tokenString) {
        if (!tokenRepository.existsByToken(tokenString)) {
            return false;
        }return true;
   }

   public Token findByTokenString(String tokenString) {
        return tokenRepository.findByToken(tokenString);
   }

   public String deleteToken(Token token) {
        String deletedToken = token.getToken();
        tokenRepository.delete(token);
        return deletedToken;

   }

}
