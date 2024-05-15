package com.SEBN.backend.controllers;

import com.SEBN.backend.models.DemandeStage;
import com.SEBN.backend.models.Stage;
import com.SEBN.backend.models.User;
import com.SEBN.backend.repository.DemandeStageRepository;
import com.SEBN.backend.repository.StageRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RestController
@RequestMapping("/api")
@Api(tags = "Internship Management API")

public class StageController {

    @Autowired
    private StageRepository stageRepository;
    private DemandeStageRepository demandeStageRepository;

    private Optional<User> user;



    @PostMapping("/stage-request")
    @ApiOperation(value = "request internship", notes = "request an  internship ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Internship updated successfully"),
            @ApiResponse(code = 404, message = "not done")
    })
    public ResponseEntity<?> createStageRequest(@Valid @RequestBody DemandeStage demandeStage) {
        try {
            // Enregistrez la demande de stage dans la base de données
            DemandeStage createdRequest = demandeStageRepository.save(demandeStage);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdRequest);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create stage request.");
        }
    }
    @GetMapping("/stages")
    @ApiOperation(value = "Get all internships", notes = "Retrieve a list of all internships")
    @ApiResponse(code = 200, message = "List of internships retrieved successfully")
    public List<Stage> getAllStages() {
        return stageRepository.findAll();
    }

    @GetMapping("/stages/{id}")
    @ApiOperation(value = "Get internship by ID", notes = "Retrieve a single internship by its ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Internship retrieved successfully"),
            @ApiResponse(code = 404, message = "Internship not found")
    })
    public ResponseEntity<Stage> getStageById(@PathVariable(value = "id") Long id) {
        Optional<Stage> optionalStage = stageRepository.findById(id);
        return optionalStage.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/stages")
    @PreAuthorize("hasRole('ROLE_RESP_STAGE')")

    @ApiOperation(value = "Create a new internship", notes = "Create a new internship entry")
    @ApiResponse(code = 201, message = "Internship created successfully")
    public ResponseEntity<Stage> createStage(@Valid @RequestBody Stage stage) {
        Stage createdStage = stageRepository.save(stage);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStage);
    }

    @PutMapping("/stages/{id}")
    @PreAuthorize("hasRole('ROLE_RESP_STAGE')")

    @ApiOperation(value = "Update internship", notes = "Update an existing internship by its ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Internship updated successfully"),
            @ApiResponse(code = 404, message = "Internship not found")
    })
    public ResponseEntity<Stage> updateStage(@PathVariable(value = "id") Long id,
                                             @Valid @RequestBody Stage stageDetails) {
        Optional<Stage> optionalStage = stageRepository.findById(id);
        if (!optionalStage.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Stage existingStage = optionalStage.get();
        existingStage.setNom_stagaire(stageDetails.getNom_stagaire());
        existingStage.setSujet(stageDetails.getSujet());
        existingStage.setDuré(stageDetails.getDuré());
        existingStage.setEncadrant(stageDetails.getEncadrant());

        final Stage updatedStage = stageRepository.save(existingStage);
        return ResponseEntity.ok(updatedStage);
    }

    @DeleteMapping("/stages/{id}")
    @PreAuthorize("hasRole('ROLE_RESP_STAGE')")

    @ApiOperation(value = "Delete internship", notes = "Delete an internship by its ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Internship deleted successfully"),
            @ApiResponse(code = 404, message = "Internship not found")
    })
    public ResponseEntity<?> deleteStage(@PathVariable(value = "id") Long id) {
        Optional<Stage> optionalStage = stageRepository.findById(id);
        if (!optionalStage.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        stageRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    // Endpoint for preselecting interns, accessible only to ROLE_RESP_RECRUTEMENT
    @PostMapping("/stages/preselection")
    @PreAuthorize("hasRole('ROLE_RESP_STAGE')")
    @ApiOperation(value = "Preselect interns", notes = "Preselect interns for internship")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Interns preselected successfully"),
            @ApiResponse(code = 500, message = "Error occurred while preselecting interns")
    })
    public ResponseEntity<?> preselectInterns(@RequestBody List<Long> internIds) {
        try {
            for (Long internId : internIds) {
                Optional<Stage> optionalStage = stageRepository.findById(internId);
                if (optionalStage.isPresent()) {
                    Stage stage = optionalStage.get();
                    // Perform preselection action on intern
                    stage.setPreselected(true); // Set a boolean flag indicating preselection

                    stageRepository.save(stage);
                }
            }
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while preselecting interns.");
        }
    }

    @PutMapping("/stages/{id}/affecter-encadrant")
    @PreAuthorize("hasRole('ROLE_RESP_STAGE')")
    @ApiOperation(value = "Assign supervisor to internship", notes = "Assign a supervisor to an internship")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Supervisor assigned successfully"),
            @ApiResponse(code = 404, message = "Internship not found")
    })
    public ResponseEntity<?> affecterEncadrant(@PathVariable Long id, Map<String, String> requestBody) {
        Optional<Stage> optionalStage = stageRepository.findById(id);
        if (!optionalStage.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Stage stage = optionalStage.get();
        String supervisorName = requestBody.get("encadrantName");

        stage.setEncadrant(supervisorName);
        stageRepository.save(stage);

        return ResponseEntity.ok().build();
    }
}