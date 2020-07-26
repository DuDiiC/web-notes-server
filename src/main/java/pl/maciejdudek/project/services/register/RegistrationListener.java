package pl.maciejdudek.project.services.register;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import pl.maciejdudek.project.model.User;
import pl.maciejdudek.project.services.RegisterService;

import java.util.UUID;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    private final JavaMailSender javaMailSender;
    private final RegisterService registerService;
    private final String appUrl;

    @Autowired
    public RegistrationListener(JavaMailSender javaMailSender,
                                RegisterService registerService,
                                @Value("${app.url.heroku}") String appUrl) {
        this.javaMailSender = javaMailSender;
        this.registerService = registerService;
        this.appUrl = appUrl;
    }

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent onRegistrationCompleteEvent) {
        User user = onRegistrationCompleteEvent.getUser();
        String token = UUID.randomUUID().toString();
        registerService.createRegisterVerificationToken(user, token);

        String confirmationUrl = appUrl + "/registrationConfirm?token=" + token;
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(user.getEmail());
        email.setSubject("Registration Confirmation - Web Notes");
        email.setText("Please, confirm your registration using link: " + confirmationUrl);
        javaMailSender.send(email);
    }
}
