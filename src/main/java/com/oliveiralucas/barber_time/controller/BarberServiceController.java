package com.oliveiralucas.barber_time.controller;

import com.oliveiralucas.barber_time.data.dto.BarberServiceDTO;
import com.oliveiralucas.barber_time.data.dto.summary.BarberServiceSummaryDTO;
import com.oliveiralucas.barber_time.service.BarberServiceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/barberservice/v1")
@Tag(name = "Barber Service", description = "Endpoints for Barber Service")
public class BarberServiceController{

    public final BarberServiceService barberServiceService;

    public BarberServiceController(BarberServiceService barberServiceService) {
        this.barberServiceService = barberServiceService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a Barber Service",
            tags = {"Barber Service"})
    public BarberServiceDTO create(@RequestBody BarberServiceDTO dto) {
        return barberServiceService.createBarberService(dto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Find all Barber Services",
            tags = {"Barber Service"})
    public List<BarberServiceSummaryDTO> list() {
        return barberServiceService.findAllBarberServices();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Find a Barber Services",
            tags = {"Barber Service"})
    public BarberServiceDTO getBarberServiceById(@PathVariable Long id) {
        return barberServiceService.findById(id);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update a Barber Service",
            tags = {"Barber Service"})
    public BarberServiceDTO updateBarberService(@PathVariable Long id, @RequestBody BarberServiceDTO barberService) {
        return barberServiceService.update(id, barberService);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Barber Service",
            tags = {"Barber Service"})
    public void deleteBarberServiceById(@PathVariable Long id) {
        barberServiceService.delete(id);
    }
}
