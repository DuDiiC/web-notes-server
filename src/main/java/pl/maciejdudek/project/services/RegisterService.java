package pl.maciejdudek.project.services;

import pl.maciejdudek.project.model.RegisterVerificationToken;
import pl.maciejdudek.project.model.User;

public interface RegisterService {

    User register(User user);

    void createRegisterVerificationToken(User user, String token);

    RegisterVerificationToken getRegisterVerificationToken(String token);

    String confirmRegistration(String token);
}
