package com.SEBN.backend.controllers;

import com.SEBN.backend.models.FeedbackEvent;
import com.SEBN.backend.repository.FeedbackRepository;
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

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class FeedbackController {

    @Autowired
    private FeedbackRepository feedbackRepository;

    // Endpoint pour récupérer tous les feedbacks
    @GetMapping("/feedbacks")
    @PreAuthorize("hasRole('ROLE_ADMIN')")

    public List<FeedbackEvent> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }

    // Endpoint pour créer un nouveau feedback
    @PostMapping("/feedbacks")

    public ResponseEntity<FeedbackEvent> createFeedback(@Valid @RequestBody FeedbackEvent feedback) {
        try {
            FeedbackEvent createdFeedback = feedbackRepository.save(feedback);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdFeedback);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Endpoint pour récupérer un feedback par ID
    @GetMapping("/feedbacks/{id}")

    public ResponseEntity<FeedbackEvent> getFeedbackById(@PathVariable Long id) {
        Optional<FeedbackEvent> optionalFeedback = feedbackRepository.findById(id);
        return optionalFeedback.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
