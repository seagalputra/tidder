package com.seagalputra.tidder.web.controller;

import com.seagalputra.tidder.api.user.AuthService;
import com.seagalputra.tidder.api.user.request.RegisterRequest;
import com.seagalputra.tidder.api.web.GenericResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<GenericResponse> signup(@RequestBody RegisterRequest registerRequest) {
        authService.signup(registerRequest);
        return new ResponseEntity<>(GenericResponse.SuccessResponse("User Registration Successful"), HttpStatus.OK);
    }
}
