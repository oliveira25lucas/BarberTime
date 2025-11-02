package com.oliveiralucas.barber_time.controller;

import com.oliveiralucas.barber_time.data.dto.BarberServiceDTO;
import com.oliveiralucas.barber_time.service.BarberServiceService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/barberservice")
public class BarberServiceController{

    public final BarberServiceService barberServiceService;

    public BarberServiceController(BarberServiceService barberServiceService) {
        this.barberServiceService = barberServiceService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BarberServiceDTO create(@RequestBody BarberServiceDTO dto) {
        return barberServiceService.createBarberService(dto);
    }

    @GetMapping
    public List<BarberServiceDTO> list() {
        return barberServiceService.findAllBarberServices();
    }

    @GetMapping("/{id}")
    public BarberServiceDTO getBarberServiceById(@PathVariable Long id) {
        return barberServiceService.findById(id);
    }

    @PutMapping("/update/{id}")
    public BarberServiceDTO updateBarberService(@PathVariable Long id, @RequestBody BarberServiceDTO barberService) {
        return barberServiceService.update(id, barberService);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBarberServiceById(@PathVariable Long id) {
        barberServiceService.delete(id);
    }
}
