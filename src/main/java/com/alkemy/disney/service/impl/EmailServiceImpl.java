package com.alkemy.disney.service.impl;

import com.alkemy.disney.service.EmailService;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;

import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private Environment env;

    @Value("${alkemy.disney.email.sender}")
    private String emailSender;
    @Value("${alkemy.disney.email.enable}")
    private boolean enable;

    public void sendWelcomeEmailTo(String to){
        if(!enable){return;}
        String apiKey = env.getProperty("EMAIL_API_KEY");
        SendGrid sg = new SendGrid(apiKey);

        Mail mail = setEmail(to);
        Request request =   setRequest(mail);

        try {
            Response response = sg.api(request);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Mail setEmail(String to){
        Email fromEmail = new Email(emailSender);
        Email toEmail = new Email(to);
        Content content = new Content("text/plain", "Bienvenido/a a Alkemy Disney");
        String subject = "Alkemy Icons";
        Mail mail = new Mail(fromEmail, subject, toEmail, content);

        return mail;
    }

    public Request setRequest(Mail mail) {
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        try {
            request.setBody(mail.build());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return request;
    }
}





