package com.SEBN.backend.controllers;

import com.SEBN.backend.models.Event;
import com.SEBN.backend.models.User;
import com.SEBN.backend.repository.EventRepository;
import com.SEBN.backend.repository.UserRepository;
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

    @CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
    @RestController
    @RequestMapping("/api")
    @Api(tags = "Event Management API")

    public class EventController {

        @Autowired
        private EventRepository EventRepository;

        @Autowired
        private UserRepository userRepository;

        // Endpoint to create a new event
        @PostMapping("/events")
        @ApiOperation(value = "Create a new event", notes = "Create a new event entry")
        @ApiResponses(value = {
                @ApiResponse(code = 201, message = "Event created successfully"),
                @ApiResponse(code = 400, message = "Bad request")
        })
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
        @ApiOperation(value = "Get all events", notes = "Retrieve a list of all events")
        @ApiResponse(code = 200, message = "List of events retrieved successfully")
        @PreAuthorize("hasRole('ROLE_RESP_EVENEMENT')")

        public List<Event> getAllEvents() {
            return EventRepository.findAll();
        }

        // Endpoint to retrieve a specific event by ID
        @GetMapping("/events/{id}")
        @ApiOperation(value = "Get event by ID", notes = "Retrieve a single event by its ID")
        @ApiResponses(value = {
                @ApiResponse(code = 200, message = "Event retrieved successfully"),
                @ApiResponse(code = 404, message = "Event not found")
        })
        @PreAuthorize("hasRole('ROLE_RESP_EVENEMENT')")

        public ResponseEntity<Event> getEventById(@PathVariable Long id) {
            Optional<Event> optionalEvent = EventRepository.findById(id);
            return optionalEvent.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        }

        // Endpoint to update an existing event
        @PutMapping("/events/{id}")
        @ApiOperation(value = "Update an existing event", notes = "Update an existing event entry")
        @ApiResponses(value = {
                @ApiResponse(code = 200, message = "Event updated successfully"),
                @ApiResponse(code = 404, message = "Event not found")
        })
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
        @ApiOperation(value = "Delete event", notes = "Delete an event by its ID")
        @ApiResponses(value = {
                @ApiResponse(code = 200, message = "Event deleted successfully"),
                @ApiResponse(code = 404, message = "Event not found")
        })
        @PreAuthorize("hasRole('ROLE_RESP_EVENEMENT')")
        public ResponseEntity<?> deleteEvent(@PathVariable Long id) {
            Optional<Event> optionalEvent = EventRepository.findById(id);
            if (!optionalEvent.isPresent()) {
                return ResponseEntity.notFound().build();
            }
            EventRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }

    }
