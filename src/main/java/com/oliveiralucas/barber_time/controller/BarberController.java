package com.oliveiralucas.barber_time.controller;

import com.oliveiralucas.barber_time.data.dto.BarberDTO;
import com.oliveiralucas.barber_time.data.dto.summary.BarberSummaryDTO;
import com.oliveiralucas.barber_time.service.BarberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/barber/v1")
@Tag(name = "Barber", description = "Endpoints for Barber")
public class BarberController {

    private final BarberService barberService;

    public BarberController(BarberService barberService) {
        this.barberService = barberService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a Barber",
            tags = {"Barber"})
    public BarberDTO createBarber(@RequestBody BarberDTO barberDTO) {
        return barberService.createBarber(barberDTO);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Find All Barber",
            tags = {"Barber"})
    public List<BarberSummaryDTO> getAllBarbers() {
        return barberService.findAllBarbers();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Find one Barber by ID",
            tags = {"Barber"})
    public BarberDTO getBarberById(@PathVariable Long id) {
        return barberService.findById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update a Barber",
            tags = {"Barber"})
    public BarberDTO updateBarber(@PathVariable Long id, @RequestBody BarberDTO barberDTO) {
        return barberService.update(id, barberDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Barber",
            tags = {"Barber"})
    public void deleteBarber(@PathVariable Long id) {
        barberService.delete(id);
    }
}
