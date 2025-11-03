package com.oliveiralucas.barber_time.service;

import com.oliveiralucas.barber_time.data.dto.BarberServiceDTO;
import com.oliveiralucas.barber_time.exception.NotFoundException;
import com.oliveiralucas.barber_time.mapper.BarberServiceMapper;
import com.oliveiralucas.barber_time.model.Barber;
import com.oliveiralucas.barber_time.model.BarberService;
import com.oliveiralucas.barber_time.repository.BarberRepository;
import com.oliveiralucas.barber_time.repository.BarberServiceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BarberServiceService {

    private final BarberServiceRepository barberServiceRepository;
    private final BarberRepository barberRepository;
    private final BarberServiceMapper barberServiceMapper;

    public BarberServiceService(BarberServiceRepository barberServiceRepository, BarberRepository barberRepository, BarberServiceMapper barberServiceMapper) {
        this.barberServiceRepository = barberServiceRepository;
        this.barberRepository = barberRepository;
        this.barberServiceMapper = barberServiceMapper;
    }

    @Transactional(readOnly = true)
    public List<BarberServiceDTO> findAllBarberServices() {
        return barberServiceMapper.toDTO(barberServiceRepository.findAll());
    }

    @Transactional(readOnly = true)
    public BarberServiceDTO findById(Long id) {
        BarberService entity = barberServiceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Barber Service id " + id + " not found"));
        return barberServiceMapper.toDTO(entity);
    }

    public BarberServiceDTO createBarberService(BarberServiceDTO dto) {
        BarberService entity = barberServiceMapper.toEntity(dto);
        entity.setBarber(resolveBarber(dto));
        entity = barberServiceRepository.save(entity);
        return barberServiceMapper.toDTO(entity);
    }

    public BarberServiceDTO update(Long id, BarberServiceDTO dto) {
        BarberService entity = barberServiceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Barber Service id " + id + " not found"));
        barberServiceMapper.updateFromDto(dto, entity);
        entity = barberServiceRepository.save(entity);
        return barberServiceMapper.toDTO(entity);
    }

    public void delete(Long id) {
        if (!barberServiceRepository.existsById(id)) {
            throw new NotFoundException("Barber Service id " + id + " not found");
        }
        barberServiceRepository.deleteById(id);
    }

    private Barber resolveBarber(BarberServiceDTO dto) {
        if (dto.getBarber() == null || dto.getBarber().getId() == null) {
            throw new NotFoundException("Barber id must be provided to associate a service");
        }

        Long barberId = dto.getBarber().getId();
        return barberRepository.findById(barberId)
                .orElseThrow(() -> new NotFoundException("Barber id " + barberId + " not found"));
    }
}
