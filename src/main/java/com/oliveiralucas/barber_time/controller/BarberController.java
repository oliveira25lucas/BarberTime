package com.oliveiralucas.barber_time.controller;

import com.oliveiralucas.barber_time.data.dto.BarberDTO;
import com.oliveiralucas.barber_time.service.BarberService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/barber")
public class BarberController {

    private final BarberService barberService;

    public BarberController(BarberService barberService) {
        this.barberService = barberService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public BarberDTO createBarber(@RequestBody BarberDTO barberDTO) {
        return barberService.createBarber(barberDTO);
    }

    @GetMapping
    public List<BarberDTO> getAllBarbers() {
        return barberService.findAllBarbers();
    }

    @GetMapping("/{id}")
    public BarberDTO getBarberById(@PathVariable Long id) {
        return barberService.findById(id);
    }

    @PutMapping("/{id}")
    public BarberDTO updateBarber(@PathVariable Long id, @RequestBody BarberDTO barberDTO) {
        return barberService.update(id, barberDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBarber(@PathVariable Long id) {
        barberService.delete(id);
    }
}
