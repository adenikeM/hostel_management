package com.springboot.hostelmanagement.controller;

import com.springboot.hostelmanagement.model.Hostel;
import com.springboot.hostelmanagement.service.HostelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/hostel")
public class HostelController {
    private final HostelService hostelService;

    public HostelController(HostelService hostelService) {
        this.hostelService = hostelService;
    }

    @GetMapping
    public ResponseEntity<List<Hostel>> getAllHostel() {
        return ResponseEntity.ok().body(hostelService.getAllHostel());
    }

    @PostMapping
    public ResponseEntity<Hostel> createHostel(@RequestBody Hostel hostel) {
        log.info("Request to create hostel ==> {}", hostel);

        if (hostel.getId() != null) {
            log.info("Hostel ==> {}", hostel);
            throw new IllegalArgumentException("Id should be null");
        }
        return ResponseEntity.ok().body(hostelService.createHostel(hostel));

    }

    @GetMapping("/{id}")
    public ResponseEntity<Hostel> getHostel(@PathVariable Integer id) {
        log.info("Get a hostel with id" + id);
        if (id < 1) {
            throw new IllegalArgumentException("Id can't be less than 1");
        }
        return hostelService.getHostel(id)
                            .map(hostel -> {
                                log.info("Retrieved hostel");
                                return ResponseEntity.ok().body(hostel);
                            }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping
    public ResponseEntity<?> updateHostel(@RequestBody Hostel hostel) {
        if (hostel.getId() == null) {
            throw new IllegalArgumentException();
        }
        Optional<Hostel> updatedStudent = hostelService.updateHostel(hostel);

        if (updatedStudent.isPresent()) {
            return ResponseEntity.ok(updatedStudent);
        } else {
            return ResponseEntity.badRequest().body(
                    ("Hostel with Id " + hostel.getId() + " does not exist")
            )
                    ;
        }

    }

    @DeleteMapping("/{id}")
    public void deleteHostel(@PathVariable Integer id) {
        hostelService.deleteHostel(id);
        ResponseEntity.noContent().build();
    }
}


