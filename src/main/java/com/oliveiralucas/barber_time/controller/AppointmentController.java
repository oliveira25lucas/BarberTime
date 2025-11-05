package com.oliveiralucas.barber_time.controller;

import com.oliveiralucas.barber_time.data.dto.AppointmentDTO;
import com.oliveiralucas.barber_time.service.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/appointment/v1")
@Tag(name="Appointment", description = "Endpoints for Appointment")
public class AppointmentController {
    
    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a Appointment", 
            tags = {"Appointment"})
    public AppointmentDTO createAppointment(@RequestBody AppointmentDTO appointmentDTO) {
        return appointmentService.createAppointment(appointmentDTO);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Find All Appointment",
            tags = {"Appointment"})
    public List<AppointmentDTO> getAllAppointments() {
        return appointmentService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Find one Appointment by ID",
            tags = {"Appointment"})
    public AppointmentDTO getAppointmentsById(@PathVariable Long id) {
        return appointmentService.findByID(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update a Appointment",
            tags = {"Appointment"})
    public AppointmentDTO updateAppointment(@PathVariable Long id, @RequestBody AppointmentDTO appointmentDTO) {
        return appointmentService.updateAppointment(id, appointmentDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Appointment",
            tags = {"Appointment"})
    public void deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
    }
}
