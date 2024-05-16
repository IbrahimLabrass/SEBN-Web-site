package com.SEBN.backend.controllers;

import com.SEBN.backend.models.Event;
import com.SEBN.backend.models.InscrEvent;
import com.SEBN.backend.models.User;
import com.SEBN.backend.repository.EventRepository;
import com.SEBN.backend.repository.InscrEventRepository;
import com.SEBN.backend.repository.UserRepository;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

    @CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
    @RestController
    @RequestMapping("/api")

    public class EventController {

        @Autowired
        private EventRepository EventRepository;

        @Autowired
        private UserRepository userRepository;
        @Autowired
        private InscrEventRepository inscrEventRepository;
        // Endpoint to create a new event
        @PostMapping("/events")

        @PreAuthorize("hasRole('ROLE_RESP_EVENEMENT')")
        public ResponseEntity<Event> createEvent(@Valid @RequestBody Event event, @RequestParam Long userId) {
            Optional<User> optionalUser = userRepository.findById(userId);
            if (!optionalUser.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            event.setUser(optionalUser.get());
            Event createdEvent = EventRepository.save(event);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEvent);
        }

        // Endpoint to retrieve all events
        @GetMapping("/events")

        @PreAuthorize("hasRole('ROLE_RESP_EVENEMENT')")

        public List<Event> getAllEvents() {
            return EventRepository.findAll();
        }



        // Endpoint to update an existing event
        @PutMapping("/events/{id}")

        @PreAuthorize("hasRole('ROLE_RESP_EVENEMENT')")

        public ResponseEntity<Event> updateEvent(@PathVariable Long id, @Valid @RequestBody Event event) {
            Optional<Event> optionalEvent = EventRepository.findById(id);
            if (!optionalEvent.isPresent()) {
                return ResponseEntity.notFound().build();
            }
            Event existingEvent = optionalEvent.get();
            existingEvent.setTitre(event.getTitre());
            existingEvent.setDescription(event.getDescription());
            existingEvent.setDate(event.getDate());
            Event updatedEvent = EventRepository.save(existingEvent);
            return ResponseEntity.ok(updatedEvent);
        }

        // Endpoint to delete an event by ID
        @DeleteMapping("/events/{id}")

        @PreAuthorize("hasRole('ROLE_RESP_EVENEMENT')")
        public ResponseEntity<?> deleteEvent(@PathVariable Long id) {
            Optional<Event> optionalEvent = EventRepository.findById(id);
            if (!optionalEvent.isPresent()) {
                return ResponseEntity.notFound().build();
            }
            EventRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }

        // Endpoint pour récupérer un événement par son ID
        @GetMapping("/events/{id}")

        public ResponseEntity<Event> getEventById(@PathVariable Long id) {
            Optional<Event> optionalEvent = EventRepository.findById(id);
            return optionalEvent.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        }

        // Endpoint pour permettre aux visiteurs de s'inscrire à un événement
        @PostMapping("/events/{id}/register")

        public ResponseEntity<?> registerForEvent(@PathVariable Long id, @Valid @RequestBody InscrEvent inscrEvent) {
            try {
                Optional<Event> optionalEvent = EventRepository.findById(id);
                if (!optionalEvent.isPresent()) {
                    return ResponseEntity.notFound().build();
                }
                Event event = optionalEvent.get();
                inscrEvent.setEvent(event);
                InscrEvent createdRegistration = inscrEventRepository.save(inscrEvent);
                return ResponseEntity.status(HttpStatus.CREATED).body(createdRegistration);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to register for the event.");
            }
        }

    }
