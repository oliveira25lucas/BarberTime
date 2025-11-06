package com.oliveiralucas.barber_time.service;

import com.oliveiralucas.barber_time.data.dto.AppointmentDTO;
import com.oliveiralucas.barber_time.exception.NotFoundException;
import com.oliveiralucas.barber_time.mapper.AppointmentMapper;
import com.oliveiralucas.barber_time.model.*;
import com.oliveiralucas.barber_time.model.BarberService;
import com.oliveiralucas.barber_time.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static java.util.Objects.requireNonNull;

@Service
@Transactional(readOnly = true)
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final ShopRepository shopRepository;
    private final CustomerRepository customerRepository;
    private final BarberRepository barberRepository;
    private final BarberServiceRepository barberServiceRepository;
    private final ProductRepository productRepository;
    private final AppointmentMapper appointmentMapper;

    public AppointmentService(AppointmentRepository appointmentRepository, ShopRepository shopRepository, CustomerRepository customerRepository, BarberRepository barberRepository, BarberServiceRepository barberServiceRepository, ProductRepository productRepository, AppointmentMapper appointmentMapper) {
        this.appointmentRepository = appointmentRepository;
        this.shopRepository = shopRepository;
        this.customerRepository = customerRepository;
        this.barberRepository = barberRepository;
        this.barberServiceRepository = barberServiceRepository;
        this.productRepository = productRepository;
        this.appointmentMapper = appointmentMapper;
    }

    @Transactional
    public List<AppointmentDTO> findAll() {
        return appointmentMapper.toDTO(appointmentRepository.findAll());
    }

    @Transactional
    public AppointmentDTO findByID(Long id) {
        Appointment entity = appointmentRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Appointment id " + id + " not found"));
        return appointmentMapper.toDTO(entity);
    }

    @Transactional
    public AppointmentDTO createAppointment(AppointmentDTO appointmentDTO) {
        Appointment entity = appointmentMapper.toEntity(appointmentDTO);
        entity.setShop(resolveShop(appointmentDTO));
        entity.setCustomer(resolveCustomer(appointmentDTO));
        entity.setBarber(resolveBarber(appointmentDTO));
        entity.setBarberService(resolveBarberService(appointmentDTO));
        entity.setProduct(resolveProduct(appointmentDTO));
        bookAppointment(entity, entity.getBarber(), entity.getBarberService());
        entity = appointmentRepository.save(entity);
        return appointmentMapper.toDTO(entity);
    }

    @Transactional
    public AppointmentDTO updateAppointment(Long id, AppointmentDTO appointmentDTO) {
        Appointment entity = appointmentRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Appointment id " + id + " not found"));
        appointmentMapper.updateFromDto(appointmentDTO, entity);
        entity = appointmentRepository.save(entity);
        return appointmentMapper.toDTO(entity);
    }

    @Transactional
    public void deleteAppointment(Long id) {
        if (!appointmentRepository.existsById(id)) {
            throw new NotFoundException("Appointment id " + id + " not found");
        }
        appointmentRepository.deleteById(id);
    }

    private Shop resolveShop(AppointmentDTO dto) {
        if (dto.getShop() == null || dto.getShop().getId() == null) {
            throw new NotFoundException("Shop id must be provided to associate a product");
        }
        Long shopId = dto.getShop().getId();
        return shopRepository.findById(shopId)
                .orElseThrow(() -> new NotFoundException("Shop id " + shopId + " not found"));
    }

    private Customer resolveCustomer(AppointmentDTO dto) {
        if (dto.getCustomer() == null || dto.getCustomer().getId() == null) {
            throw new NotFoundException("Customer id must be provided to associate a product");
        }
        Long customerId = dto.getCustomer().getId();
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("Customer id " + customerId + " not found"));
    }

    private Barber resolveBarber(AppointmentDTO dto) {
        if (dto.getBarber() == null || dto.getBarber().getId() == null) {
            throw new NotFoundException("Barber id must be provided to associate a product");
        }
        Long barberId = dto.getBarber().getId();
        return barberRepository.findById(barberId)
                .orElseThrow(() -> new NotFoundException("Barber id " + barberId + " not found"));
    }

    private BarberService resolveBarberService(AppointmentDTO dto) {
        if (dto.getBarberService() == null || dto.getBarberService().getId() == null) {
            throw new NotFoundException("BarberService id must be provided to associate a product");
        }
        Long barberServiceId = dto.getBarberService().getId();
        return barberServiceRepository.findById(barberServiceId)
                .orElseThrow(() -> new NotFoundException("Barber Service id " + barberServiceId + " not found"));
    }

    private Product resolveProduct(AppointmentDTO dto) {
        if (dto.getProduct() == null || dto.getProduct().getId() == null) {
            return null;
        }
        Long productId = dto.getProduct().getId();
        return productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product id " + productId + " not found"));
    }

    private void bookAppointment(Appointment appointment, Barber barber, BarberService barberService) {

        requireNonNull(appointment, "Appointment must not be null");
        requireNonNull(barber, "Barber must not be null");
        requireNonNull(barberService, "BarberService must not be null");

        LocalTime startShift = requireNonNull(barber.getStartShift(), "Barber start shift must be provided");
        LocalTime endShift   = requireNonNull(barber.getEndShift(),   "Barber end shift must be provided");
        LocalTime startLunch = requireNonNull(barber.getStartLunch(), "Barber lunch start must be provided");
        LocalTime endLunch   = requireNonNull(barber.getEndLunch(),   "Barber lunch end must be provided");

        Integer durationMin = requireNonNull(barberService.getDurationMin(), "Barber Service duration minutes must be provided");

        LocalDateTime start = requireNonNull(appointment.getStartAt(), "Appointment start must be provided");
        LocalDateTime end   = appointment.getEndAt() != null ? appointment.getEndAt() : start.plusMinutes(durationMin);
        if (!end.isAfter(start)) {
            throw new IllegalArgumentException("Appointment end must be after start");
        }

        LocalDate date = start.toLocalDate();
        LocalDateTime shiftStart = date.atTime(startShift);
        LocalDateTime shiftEnd   = date.atTime(endShift);
        LocalDateTime lunchStart = date.atTime(startLunch);
        LocalDateTime lunchEnd   = date.atTime(endLunch);

        if (start.isBefore(shiftStart) || end.isAfter(shiftEnd)) {
            throw new IllegalArgumentException("Barber is not on shift");
        }

        if (overlaps(start, end, lunchStart, lunchEnd)) {
            throw new IllegalArgumentException("Appointment overlaps barber lunch break");
        }

        appointment.setEndAt(end);
    }

    private static boolean overlaps(LocalDateTime aStart, LocalDateTime aEnd,
                                    LocalDateTime bStart, LocalDateTime bEnd) {

        return aStart.isBefore(bEnd) && bStart.isBefore(aEnd);
    }
}
