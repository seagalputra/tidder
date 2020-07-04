package com.seagalputra.tidder.api.user;

import com.seagalputra.tidder.api.user.request.LoginRequest;
import com.seagalputra.tidder.api.user.request.RegisterRequest;
import com.seagalputra.tidder.api.user.response.AuthenticationResponse;

public interface AuthService {
    void signup(RegisterRequest registerRequest);
    void verifyAccount(String token);
    AuthenticationResponse login(LoginRequest loginRequest);
}
