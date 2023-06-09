package nl.tudelft.sem.template.controllers;

import java.util.List;
import java.util.Optional;
import nl.tudelft.sem.template.services.GatewayService;
import nl.tudelft.sem.template.shared.domain.Request;
import nl.tudelft.sem.template.shared.entities.Event;
import nl.tudelft.sem.template.shared.entities.EventModel;
import nl.tudelft.sem.template.shared.enums.PositionName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping(path = "/api/event")
public class GatewayEventController {

    @Autowired
    private transient GatewayService gatewayService;

    /**
     * Gets all events.
     */
    @GetMapping("/all")
    public ResponseEntity<List<Event>> getEvents(@RequestParam(required = false) Optional<Long> owner,
                                                 @RequestParam(required = false) Optional<Long> match) {
        try {
            return ResponseEntity.ok(gatewayService.getAllEvents(owner, match));
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Register a new event.
     */
    @PostMapping("/register")
    public ResponseEntity<Event> registerNewEvent(@RequestBody EventModel eventModel) throws Exception {
        try {
            return ResponseEntity.ok(gatewayService.addNewEvent(eventModel));
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Delete an event.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity deleteEvent(@PathVariable("id") Long id) {
        try {
            System.out.println("deleteEvent");
            return gatewayService.deleteEvent(id);
        } catch (ResponseStatusException e) {
            System.out.println("deleteEvent exception " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.out.println("deleteEvent exception " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Update an event.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEvent(@PathVariable("id") Long id,
                                         @RequestBody EventModel eventModel) {
        try {
            return ResponseEntity.ok(gatewayService.updateEvent(eventModel, id));
        } catch (ResponseStatusException e) {
            System.out.println("updateEvent exception " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.out.println("updateEvent exception " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Enquque to an event.
     */
    @PostMapping("{eventId}/enqueue/{userId}")
    public ResponseEntity<String> enqueue(@PathVariable("eventId") Long eventId,
                                          @PathVariable("userId") Long userId,
                                          @RequestParam PositionName position) {
        try {
            return ResponseEntity.ok(gatewayService.enqueueToEvent(eventId, userId, position));
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Accept an event.
     */
    @PostMapping("{id}/accept")
    public ResponseEntity<String> accept(@PathVariable("id") Long id,
                                         @RequestBody Request request,
                                         @RequestParam() boolean outcome) {
        try {
            return ResponseEntity.ok(gatewayService.acceptToEvent(id, request, outcome));
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
