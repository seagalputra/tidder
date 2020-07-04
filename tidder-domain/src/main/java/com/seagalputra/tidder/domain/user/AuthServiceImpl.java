package com.seagalputra.tidder.domain.user;

import com.seagalputra.tidder.api.email.EmailService;
import com.seagalputra.tidder.api.email.request.SendEmailRequest;
import com.seagalputra.tidder.api.user.AuthService;
import com.seagalputra.tidder.api.user.request.RegisterRequest;
import com.seagalputra.tidder.domain.token.entity.VerificationToken;
import com.seagalputra.tidder.domain.token.repository.VerificationTokenRepository;
import com.seagalputra.tidder.domain.user.entity.User;
import com.seagalputra.tidder.domain.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final EmailService emailService;
    
    @Override
    @Transactional
    public void signup(RegisterRequest registerRequest) {
        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());

        User user = User.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(encodedPassword)
                .created(Instant.now())
                .enabled(false)
                .build();

        userRepository.save(user);

        String token = generateVerificationToken(user);
        SendEmailRequest sendEmailRequest = SendEmailRequest.builder()
                .subject("Please Activate your Account")
                .recipient(user.getEmail())
                .body("Thank you for signing up to Tidder, " +
                        "please click on the below url to activate your account : " +
                        "http://localhost:8080/api/auth/accountVerification/" + token)
                .build();

        emailService.sendEmail(sendEmailRequest);
    }

    @Override
    public void verifyAccount(String token) {

    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = VerificationToken.builder()
                .token(token)
                .user(user)
                .build();

        verificationTokenRepository.save(verificationToken);
        return token;
    }
}
