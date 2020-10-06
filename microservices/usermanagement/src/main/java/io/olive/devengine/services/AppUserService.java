package io.olive.devengine.services;

import io.olive.devengine.models.AppUser;
import io.olive.devengine.repo.AppUserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AppUserService {

    private AppUserRepo appUserRepo;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AppUserService(AppUserRepo appUserRepo, PasswordEncoder passwordEncoder) {
        this.appUserRepo = appUserRepo;
        this.passwordEncoder = passwordEncoder;
    }

    private Logger logger = LoggerFactory.getLogger(AppUserService.class);

    public AppUser saveAppUser(AppUser user) {
        user.setCreatedAt(System.currentTimeMillis());
        user.setUpdatedAt(System.currentTimeMillis());
        user.setPassword(hashPassword(user.getPassword()));
        try {
            appUserRepo.save(user);
            logger.info(user.getCreatedAt() + ": user account created");
        } catch (DataIntegrityViolationException e) {
            logger.info(user.getCreatedAt() + ": error: " + e.getMessage());
            System.out.println("Message : " + e);
            throw new ResponseStatusException(HttpStatus.CONFLICT, "account already exists", e);
        }

        return user;
     }

     private String hashPassword(String password) {
       return passwordEncoder.encode(password);
     }
}

