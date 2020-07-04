package com.seagalputra.tidder.api.user;

import com.seagalputra.tidder.api.user.request.RegisterRequest;

public interface AuthService {
    void signup(RegisterRequest registerRequest);
    void verifyAccount(String token);
}
