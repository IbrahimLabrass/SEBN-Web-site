package com.SEBN.backend.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")

public class TestController {
    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }
    @GetMapping("/user")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String userAccess() {
        return "user Content.";
    }


    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String respoAccess() {
        return "Admin  Board.";
    }
    @GetMapping("/stage")
    @PreAuthorize("hasRole('ROLE_RESP_STAGE')")
    public String stageAccess() {
        return "Respo stage Board.";
    }

    @GetMapping("/recru")
    @PreAuthorize("hasRole('ROLE_RESP_RECRUT')")
    public String recruAccess() {
        return "Respo Recru Board.";
    }

    @GetMapping("/even")
    @PreAuthorize("hasRole('ROLE_RESP_EVEN')")
    public String evenAccess() {
        return "Respo Even Board.";
    }
}