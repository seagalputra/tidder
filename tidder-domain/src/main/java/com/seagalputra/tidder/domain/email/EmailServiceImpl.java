package com.seagalputra.tidder.domain.email;

import com.seagalputra.tidder.api.email.EmailService;
import com.seagalputra.tidder.api.email.request.SendEmailRequest;
import com.seagalputra.tidder.api.exception.SpringTidderException;
import com.seagalputra.tidder.common.MailContentBuilder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final MailContentBuilder mailContentBuilder;

    @Override
    public void sendEmail(SendEmailRequest sendEmailRequest) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("tidder-84c624@inbox.mailtrap.io");
            messageHelper.setTo(sendEmailRequest.getRecipient());
            messageHelper.setSubject(sendEmailRequest.getSubject());
            messageHelper.setText(mailContentBuilder.build(sendEmailRequest.getBody()));
        };

        try {
            mailSender.send(messagePreparator);
            log.info("Activation email sent!");
        } catch (MailException e) {
            throw new SpringTidderException("Exception occured when sending email to " + sendEmailRequest.getRecipient(), e);
        }
    }
}

