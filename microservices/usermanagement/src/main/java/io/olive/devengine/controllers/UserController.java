package io.olive.devengine.controllers;

import io.olive.devengine.models.AppUser;
import io.olive.devengine.services.AppUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private AppUserService appUserService;

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/signup")
    AppUser signup(@RequestBody AppUser user) {
        logger.info(System.currentTimeMillis() + ": saving app user");
        return appUserService.saveAppUser(user);
    }
}
