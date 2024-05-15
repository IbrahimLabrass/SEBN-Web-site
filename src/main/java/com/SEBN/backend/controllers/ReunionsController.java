package com.SEBN.backend.controllers;

import com.SEBN.backend.models.Reunions;
import com.SEBN.backend.models.User;
import com.SEBN.backend.repository.ReunionsRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@Api(tags = "Meeting Management API")

public class ReunionsController {

    @Autowired
    private ReunionsRepository reunionsRepository;
    private Optional<User> user;

    @GetMapping("/reunions")
    @PreAuthorize("hasRole('ROLE_RESP_STAGE')")
    @ApiOperation(value = "Get all meetings", notes = "Retrieve a list of all meetings")
    @ApiResponse(code = 200, message = "List of meetings retrieved successfully")
    public List<Reunions> getAllReunions() {
        return reunionsRepository.findAll();
    }

    @GetMapping("/reunions/{id}")
    @PreAuthorize("hasRole('ROLE_RESP_STAGE')")
    @ApiOperation(value = "Get meeting by ID", notes = "Retrieve a single meeting by its ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Meeting retrieved successfully"),
            @ApiResponse(code = 404, message = "Meeting not found")
    })
    public ResponseEntity<Reunions> getReunionById(@PathVariable(value = "id") Long id) {
        Optional<Reunions> optionalReunion = reunionsRepository.findById(id);
        if (!optionalReunion.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Reunions reunion = optionalReunion.get();
        return ResponseEntity.ok(reunion);
    }

    @PostMapping("/reunions")
    @PreAuthorize("hasRole('ROLE_RESP_STAGE')")
    @ApiOperation(value = "Create a new meeting", notes = "Create a new meeting entry")
    @ApiResponse(code = 201, message = "Meeting created successfully")
    public ResponseEntity<Reunions> createReunion(@Valid @RequestBody Reunions reunion) {
        Reunions createdReunion = reunionsRepository.save(reunion);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReunion);
    }

    @DeleteMapping("/reunions/{id}")
    @PreAuthorize("hasRole('ROLE_RESP_STAGE')")
    @ApiOperation(value = "Delete meeting", notes = "Delete a meeting by its ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Meeting deleted successfully"),
            @ApiResponse(code = 404, message = "Meeting not found")
    })
    public ResponseEntity<Map<String, Boolean>> deleteReunion(@PathVariable(value = "id") Long id) {
        Optional<Reunions> optionalReunion = reunionsRepository.findById(id);
        if (!optionalReunion.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        reunionsRepository.deleteById(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}