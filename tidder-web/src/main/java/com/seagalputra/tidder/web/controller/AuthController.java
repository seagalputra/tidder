package com.seagalputra.tidder.web.controller;

import com.seagalputra.tidder.api.user.AuthService;
import com.seagalputra.tidder.api.user.request.LoginRequest;
import com.seagalputra.tidder.api.user.request.RegisterRequest;
import com.seagalputra.tidder.api.web.GenericResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<GenericResponse> signup(@RequestBody RegisterRequest registerRequest) {
        authService.signup(registerRequest);
        return new ResponseEntity<>(GenericResponse.SuccessResponse("User Registration Successful"), OK);
    }

    @GetMapping("/accountVerification/{token}")
    public ResponseEntity<GenericResponse> verifyAccount(@PathVariable String token) {
        authService.verifyAccount(token);
        return new ResponseEntity<>(GenericResponse.SuccessResponse("Account Activated Successfully"), OK);
    }

    @PostMapping("/login")
    public ResponseEntity<GenericResponse> login(@RequestBody LoginRequest loginRequest) {
        return new ResponseEntity<>(GenericResponse.SuccessResponse(authService.login(loginRequest)), OK);
    }
}
