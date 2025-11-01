package com.oliveiralucas.barber_time.contoller;

import com.oliveiralucas.barber_time.model.Barber;
import com.oliveiralucas.barber_time.service.BarberService;
import org.springframework.stereotype.Controller;
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
    public Barber createBarber(@RequestBody Barber barber) {
        return barberService.createBarber(barber);
    }

    @GetMapping
    public List<Barber> getAllBarbers() {
        return barberService.findAllBarbers();
    }

    @GetMapping("/{id}")
    public Barber getBarberById(@PathVariable Long id) {
        return barberService.findById(id);
    }

    @PutMapping("/{id}")
    public Barber updateBarber(@PathVariable Long id, @RequestBody Barber barber) {
        return barberService.update(id, barber);
    }

    @DeleteMapping("/{id}")
    public void deleteBarber(@PathVariable Long id) {
        barberService.delete(id);
    }
}
