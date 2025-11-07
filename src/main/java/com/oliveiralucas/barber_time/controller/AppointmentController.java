package com.oliveiralucas.barber_time.controller;

import com.oliveiralucas.barber_time.data.dto.AppointmentDTO;
import com.oliveiralucas.barber_time.enums.AppointmentStatusEnum;
import com.oliveiralucas.barber_time.service.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @GetMapping("/shop/{shopId}/day/{date}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Find appointments for a shop on a specific day",
            tags = {"Appointment"})
    public List<AppointmentDTO> getAppointmentsByShopAndDate(@PathVariable Long shopId,
                                                             @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return appointmentService.findByShopAndDate(shopId, date);
    }

    @GetMapping("/shop/{shopId}/period")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Find appointments for a shop in a period",
            tags = {"Appointment"})
    public List<AppointmentDTO> getAppointmentsByShopAndPeriod(@PathVariable Long shopId,
                                                               @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
                                                               @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return appointmentService.findByShopAndPeriod(shopId, start, end);
    }

    @GetMapping("/barber/{barberId}/day/{date}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Find appointments for a barber on a specific day",
            tags = {"Appointment"})
    public List<AppointmentDTO> getAppointmentsByBarberAndDate(@PathVariable Long barberId,
                                                               @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return appointmentService.findByBarberAndDate(barberId, date);
    }

    @GetMapping("/barber/{barberId}/period")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Find appointments for a barber in a period",
            tags = {"Appointment"})
    public List<AppointmentDTO> getAppointmentsByBarberAndPeriod(@PathVariable Long barberId,
                                                                 @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
                                                                 @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return appointmentService.findByBarberAndPeriod(barberId, start, end);
    }

    @GetMapping("/customer/{customerId}/upcoming")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Find upcoming appointments for a customer",
            tags = {"Appointment"})
    public List<AppointmentDTO> getUpcomingAppointmentsByCustomer(@PathVariable Long customerId,
                                                                  @RequestParam(value = "from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from) {
        return appointmentService.findUpcomingByCustomer(customerId, from);
    }

    @GetMapping("/status/{status}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Find appointments by status",
            tags = {"Appointment"})
    public List<AppointmentDTO> getAppointmentsByStatus(@PathVariable AppointmentStatusEnum status) {
        return appointmentService.findByStatus(status);
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
