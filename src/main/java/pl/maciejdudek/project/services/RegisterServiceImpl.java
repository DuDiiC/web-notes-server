package pl.maciejdudek.project.services;

import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.maciejdudek.project.exceptions.UserAlreadyExistException;
import pl.maciejdudek.project.model.RegisterVerificationToken;
import pl.maciejdudek.project.model.User;
import pl.maciejdudek.project.repositories.RegisterTokenRepository;
import pl.maciejdudek.project.repositories.UserRepository;
import pl.maciejdudek.project.services.register.OnRegistrationCompleteEvent;

import javax.transaction.Transactional;
import java.util.Calendar;

@Service
@RequiredArgsConstructor
@Transactional
public class RegisterServiceImpl implements RegisterService {

    private final UserRepository userRepository;
    private final RegisterTokenRepository registerTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public User register(User user) {
        if(userRepository.findByUsernameIgnoringCase(user.getUsername()).isPresent()) {
            throw new UserAlreadyExistException();
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        user.setEnabled(false);
        User registeredUser = userRepository.save(user);

        applicationEventPublisher.publishEvent(new OnRegistrationCompleteEvent(registeredUser));

        return registeredUser;
    }

    @Override
    public String confirmRegistration(String token) {
        RegisterVerificationToken registerVerificationToken = getRegisterVerificationToken(token);
        Calendar calendar = Calendar.getInstance();
        if((registerVerificationToken.getExpiryDate().getTime() - calendar.getTime().getTime()) <= 0) {
            return "EXPIRED";
        }

        User user = registerVerificationToken.getUser();
        user.setEnabled(true);
        userRepository.save(user);
        return "SUCCESS";
    }

    @Override
    public void createRegisterVerificationToken(User user, String token) {
        RegisterVerificationToken registerVerificationToken = new RegisterVerificationToken();
        registerVerificationToken.setToken(token);
        registerVerificationToken.setUser(user);
        registerTokenRepository.save(registerVerificationToken);
    }

    @Override
    public RegisterVerificationToken getRegisterVerificationToken(String token) {
        return registerTokenRepository.findByToken(token)
                .orElseThrow(() -> new ObjectNotFoundException(token, "RegisterVerificationToken"));
    }
}
