package com.SEBN.backend.controllers;

import com.SEBN.backend.models.OffreEmpl;
import com.SEBN.backend.models.User;
import com.SEBN.backend.repository.OffreEmplRepository;
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

@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RestController
@RequestMapping("/api")
@Api(tags = "Job Offer Management API")

public class OffreEmplController {
    @Autowired
    private OffreEmplRepository OffreEmplRepository;
    private Optional<User> user;

    @RequestMapping("/hello")
    public String index() {
        return "Spring Boot Example!!";
    }

    @GetMapping("/offres")
    @ApiOperation(value = "Get all job offers", notes = "Retrieve a list of all job offers")
    @ApiResponse(code = 200, message = "List of job offers retrieved successfully")
    @PreAuthorize("hasRole('ROLE_RESP_RECRU')")

    public List<OffreEmpl> getAllOffres() {
        return  (List<OffreEmpl>) OffreEmplRepository.findAll();
        //return (List<Offre>) OffreRepository.findAll();
    }


    @GetMapping("/offres/{id}")
    @PreAuthorize("hasRole('ROLE_RESP_RECRU')")
    @ApiOperation(value = "Get job offer by ID", notes = "Retrieve a single job offer by its ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Job offer retrieved successfully"),
            @ApiResponse(code = 404, message = "Job offer not found")
    })

    public ResponseEntity<OffreEmpl> getOffreById(@PathVariable(value = "id") Long id) {
        OffreEmpl offres = OffreEmplRepository.findById(id).orElse(null);
        if (offres == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok().body(offres);
    }

    @GetMapping("/offre/{id}")
    @PreAuthorize("hasRole('ROLE_RESP_RECRU')")
    @ApiOperation(value = "Get job offers by user", notes = "Retrieve job offers by user ID")
    @ApiResponse(code = 200, message = "List of job offers retrieved successfully")
    public List<OffreEmpl> getOffreByUser(@PathVariable(value = "id") String titre)
    {
        //Optional<User> users = UserRepository.findById(titre);
        return  (List<OffreEmpl>) OffreEmplRepository.findByUser(titre);
    }

    @PostMapping("/offre")
    @PreAuthorize("hasRole('ROLE_RESP_RECRU')")
    @ApiOperation(value = "Create a new job offer", notes = "Create a new job offer entry")
    @ApiResponse(code = 200, message = "Job offer created successfully")
    public OffreEmpl createOffre(@Valid @RequestBody OffreEmpl offres) {
        return OffreEmplRepository.save(offres);
    }

    @PutMapping("/offres/{id}")
    @PreAuthorize("hasRole('ROLE_RESP_RECRU')")
    @ApiOperation(value = "Update an existing job offer", notes = "Update an existing job offer entry")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Job offer updated successfully"),
            @ApiResponse(code = 404, message = "Job offer not found")
    })
    public ResponseEntity<OffreEmpl> updateOffre(@PathVariable(value = "id") Long id,
                                                 @Valid @RequestBody OffreEmpl offresDetails) {
        Optional<OffreEmpl> optionalOffre = OffreEmplRepository.findById(id);
        if (!optionalOffre.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        OffreEmpl existingOffre = optionalOffre.get();
        existingOffre.setTitre(offresDetails.getTitre());
        existingOffre.setDescription(offresDetails.getDescription());
        existingOffre.setCompetencesRequises(offresDetails.getCompetencesRequises());
        existingOffre.setUser(offresDetails.getUser());

        final OffreEmpl updatedOffre = OffreEmplRepository.save(existingOffre);
        return ResponseEntity.ok(updatedOffre);
    }

    @DeleteMapping("/offres/{id}")
    @PreAuthorize("hasRole('ROLE_RESP_RECRU')")
    @ApiOperation(value = "Delete job offer", notes = "Delete a job offer by its ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Job offer deleted successfully"),
            @ApiResponse(code = 404, message = "Job offer not found")
    })
    public ResponseEntity<Map<String, Boolean>> deleteOffre(@PathVariable(value = "id") Long id) {
        Optional<OffreEmpl> optionalOffre = OffreEmplRepository.findById(id);
        if (!optionalOffre.isPresent()) {
            Map<String, Boolean> response = new HashMap<>();
            response.put("deleted", Boolean.FALSE);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        OffreEmpl offres = optionalOffre.get();
        OffreEmplRepository.delete(offres);

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok().body(response);
    }

}