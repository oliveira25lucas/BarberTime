package com.oliveiralucas.barber_time.service;

import com.oliveiralucas.barber_time.data.dto.AppointmentDTO;
import com.oliveiralucas.barber_time.exception.NotFoundException;
import com.oliveiralucas.barber_time.mapper.AppointmentMapper;
import com.oliveiralucas.barber_time.model.*;
import com.oliveiralucas.barber_time.model.BarberService;
import com.oliveiralucas.barber_time.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
                .orElseThrow(() -> new NotFoundException("Shop id " + barberId + " not found"));
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
}
