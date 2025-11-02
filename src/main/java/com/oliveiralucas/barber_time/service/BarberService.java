package com.oliveiralucas.barber_time.service;

import com.oliveiralucas.barber_time.data.dto.BarberDTO;
import com.oliveiralucas.barber_time.exception.NotFoundException;
import com.oliveiralucas.barber_time.mapper.BarberMapper;
import com.oliveiralucas.barber_time.model.Barber;
import com.oliveiralucas.barber_time.repository.BarberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BarberService {

    private final BarberRepository barberRepository;
    private final BarberMapper barberMapper;

    public BarberService(BarberRepository barberRepository, BarberMapper barberMapper) {
        this.barberRepository = barberRepository;
        this.barberMapper = barberMapper;
    }

    @Transactional(readOnly = true)
    public List<BarberDTO> findAllBarbers() {
        return barberMapper.toDTO(barberRepository.findAll());
    }

    @Transactional(readOnly = true)
    public BarberDTO findById(Long id) {
        Barber entity = barberRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Barber id " + id + " not found"));
        return barberMapper.toDTO(entity);
    }

    public BarberDTO createBarber(BarberDTO dto) {
        Barber entity = barberMapper.toEntity(dto);
        entity = barberRepository.save(entity);
        return barberMapper.toDTO(entity);
    }

    public BarberDTO update(Long id, BarberDTO dto) {
        Barber entity = barberRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Barber id " + id + " not found"));
        barberMapper.updateFromDto(dto, entity);
        entity = barberRepository.save(entity);
        return barberMapper.toDTO(entity);
    }

    public void delete(Long id) {
        if (!barberRepository.existsById(id)) {
            throw new NotFoundException("Barber id " + id + " not found");
        }
        barberRepository.deleteById(id);
    }
}
