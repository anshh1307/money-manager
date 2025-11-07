package com.ansh.moneymanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import sendinblue.ApiClient;
import sendinblue.Configuration;
import sibApi.TransactionalEmailsApi;
import sibModel.SendSmtpEmail;
import sibModel.SendSmtpEmailSender;
import sibModel.SendSmtpEmailTo;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Value("${BREVO_API_KEY}")
    private String apiKey;

    @Value("${BREVO_FROM_EMAIL}")
    private String fromEmail;

    @Async
    public void sendEmail(String to, String subject, String body){
        try {
            ApiClient client = Configuration.getDefaultApiClient();
            client.setApiKey(apiKey);

            TransactionalEmailsApi apiInstance = new TransactionalEmailsApi();
            SendSmtpEmailSender sender = new SendSmtpEmailSender().email(fromEmail);
            SendSmtpEmailTo receiver = new SendSmtpEmailTo().email(to);

            SendSmtpEmail email = new SendSmtpEmail()
                    .sender(sender)
                    .addToItem(receiver)
                    .subject(subject)
                    .htmlContent(body);

            apiInstance.sendTransacEmail(email);

        } catch (Exception e) {
            throw new RuntimeException("Email sending failed: " + e.getMessage());
        }
    }
}
