package com.oliveiralucas.barber_time.service;

import com.oliveiralucas.barber_time.model.Barber;
import com.oliveiralucas.barber_time.model.Barber;
import com.oliveiralucas.barber_time.repository.BarberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BarberService {

    private final BarberRepository barberRepository;

    public BarberService(BarberRepository barberRepository) {
        this.barberRepository = barberRepository;
    }

    public Barber createBarber(Barber barber) {
        barber.setId(null);
        return barberRepository.save(barber);
    }

    public List<Barber> findAllBarbers() {
        return barberRepository.findAll();
    }

    public Barber findById(Long id) {
        return barberRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Barber " + id + " not found"));
    }

    public Barber update(Long id, Barber barber) {
        Barber existingBarber = findById(id);
        existingBarber.setEmail(barber.getEmail());
        existingBarber.setPhone(barber.getPhone());
        existingBarber.setAddress(barber.getAddress());
        existingBarber.setStatus(barber.getStatus());
        return barberRepository.save(existingBarber);
    }

    public void delete(Long id) {
        barberRepository.findById(id);
        barberRepository.deleteById(id);
    }
}
