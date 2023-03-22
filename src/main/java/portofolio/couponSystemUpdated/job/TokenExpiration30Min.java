package portofolio.couponSystemUpdated.job;

import portofolio.couponSystemUpdated.entities.Token;
import portofolio.couponSystemUpdated.repositories.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.List;
@Component
public class TokenExpiration30Min implements Runnable{

    @Autowired
    TokenRepository tokenRepository;

    private boolean exit;

    @Override
    public void run() {

        while(!exit) {

        List<Token> allTokens = tokenRepository.findAll();
        for (Token token : allTokens) {

          if(token.getIssueOfToken().plusMinutes(30).isBefore(LocalTime.now())) {
              tokenRepository.delete(token);
          }
        }
            try {
                Thread.sleep(30*60*30);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


        }
}}
