package com.seagalputra.tidder.domain.user;

import com.seagalputra.tidder.api.email.EmailService;
import com.seagalputra.tidder.api.email.request.SendEmailRequest;
import com.seagalputra.tidder.api.exception.SpringTidderException;
import com.seagalputra.tidder.api.user.AuthService;
import com.seagalputra.tidder.api.user.request.LoginRequest;
import com.seagalputra.tidder.api.user.request.RegisterRequest;
import com.seagalputra.tidder.api.user.response.AuthenticationResponse;
import com.seagalputra.tidder.api.user.response.UserResponse;
import com.seagalputra.tidder.domain.token.entity.VerificationToken;
import com.seagalputra.tidder.domain.token.repository.VerificationTokenRepository;
import com.seagalputra.tidder.domain.user.entity.User;
import com.seagalputra.tidder.domain.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    
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
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token)
                .orElseThrow(() -> new SpringTidderException("Invalid Token!"));

        fetchUserAndEnable(verificationToken);
    }

    @Override
    public AuthenticationResponse login(LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());

        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtProvider.generateToken(authenticate);

        return AuthenticationResponse.builder()
                .username(loginRequest.getUsername())
                .token(token)
                .build();
    }

    @Override
    public UserResponse getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        User user = userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getUsername()));
        return UserResponse.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .created(user.getCreated())
                .enabled(user.isEnabled())
                .build();
    }

    private void fetchUserAndEnable(VerificationToken verificationToken) {
        String username = verificationToken
                .getUser()
                .getUsername();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new SpringTidderException("User not found with name - " + username));
        user.setEnabled(true);
        userRepository.save(user);
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
