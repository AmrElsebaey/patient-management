package com.pm.patientservice.controller;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public ResponseEntity<List<PatientResponseDTO>> getAllPatients() {
        return ResponseEntity.ok(patientService.getAllPatients());
    }

    @PostMapping
    public ResponseEntity<PatientResponseDTO> createPatient(
            @RequestBody @Valid PatientRequestDTO patientRequestDTO) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(patientService.createPatient(patientRequestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> updatePatient(
            @PathVariable UUID id,
            @RequestBody @Valid PatientRequestDTO patientRequestDTO) {

        return ResponseEntity.ok(patientService.updatePatient(id, patientRequestDTO));
            }


}
