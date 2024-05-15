package com.SEBN.backend.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
@Api(tags = "Test API")

public class TestController {
    @GetMapping("/all")
    @ApiOperation(value = "Public access", notes = "Endpoint accessible to all users")

    public String allAccess() {
        return "Public Content.";
    }
    @GetMapping("/user")
    @PreAuthorize("hasRole('ROLE_USER')")
    @ApiOperation(value = "User access", notes = "Endpoint accessible to users with 'ROLE_USER'")

    public String userAccess() {
        return "user Content.";
    }


    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "Admin access", notes = "Endpoint accessible to users with 'ROLE_ADMIN'")

    public String respoAccess() {
        return "Admin  Board.";
    }


    @GetMapping("/stage")
    @PreAuthorize("hasRole('ROLE_RESP_STAGE')")
    @ApiOperation(value = "Stage access", notes = "Endpoint accessible to users with 'ROLE_RESP_STAGE'")

    public String stageAccess() {
        return "Respo stage Board.";
    }

    @GetMapping("/recru")
    @PreAuthorize("hasRole('ROLE_RESP_RECRUT')")
    @ApiOperation(value = "Recruitment access", notes = "Endpoint accessible to users with 'ROLE_RESP_RECRUT'")

    public String recruAccess() {
        return "Respo Recru Board.";
    }

    @GetMapping("/even")
    @PreAuthorize("hasRole('ROLE_RESP_EVEN')")
    @ApiOperation(value = "Event access", notes = "Endpoint accessible to users with 'ROLE_RESP_EVEN'")

    public String evenAccess() {
        return "Respo Even Board.";
    }
}