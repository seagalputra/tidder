package com.seagalputra.tidder.api.email;

import com.seagalputra.tidder.api.email.request.SendEmailRequest;

public interface EmailService {
    void sendEmail(SendEmailRequest sendEmailRequest);
}
