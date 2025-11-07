package com.oliveiralucas.barber_time.service;

import com.oliveiralucas.barber_time.data.dto.AppointmentDTO;
import com.oliveiralucas.barber_time.enums.AppointmentStatusEnum;
import com.oliveiralucas.barber_time.exception.NotFoundException;
import com.oliveiralucas.barber_time.mapper.AppointmentMapper;
import com.oliveiralucas.barber_time.model.*;
import com.oliveiralucas.barber_time.model.BarberService;
import com.oliveiralucas.barber_time.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.oliveiralucas.barber_time.enums.AppointmentStatusEnum.SCHEDULED;
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
        validateAssociations(entity);
        bookAppointment(entity, entity.getBarber(), entity.getBarberService());
        totalPrice(entity, entity.getBarberService(), entity.getProduct());
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

    public List<AppointmentDTO> findByShopAndDate(Long shopId, LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = atEndOfDay(date);
        return appointmentMapper.toDTO(
                appointmentRepository.findAllByShopIdAndStartAtBetweenOrderByStartAt(shopId, startOfDay, endOfDay)
        );
    }

    public List<AppointmentDTO> findByShopAndPeriod(Long shopId, LocalDateTime start, LocalDateTime end) {
        validatePeriod(start, end);
        return appointmentMapper.toDTO(
                appointmentRepository.findAllByShopIdAndStartAtBetweenOrderByStartAt(shopId, start, end)
        );
    }

    public List<AppointmentDTO> findByBarberAndDate(Long barberId, LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = atEndOfDay(date);
        return appointmentMapper.toDTO(
                appointmentRepository.findAllByBarberIdAndStartAtBetweenOrderByStartAt(barberId, startOfDay, endOfDay)
        );
    }

    public List<AppointmentDTO> findByBarberAndPeriod(Long barberId, LocalDateTime start, LocalDateTime end) {
        validatePeriod(start, end);
        return appointmentMapper.toDTO(
                appointmentRepository.findAllByBarberIdAndStartAtBetweenOrderByStartAt(barberId, start, end)
        );
    }

    public List<AppointmentDTO> findUpcomingByCustomer(Long customerId, LocalDateTime reference) {
        LocalDateTime start = reference != null ? reference : LocalDateTime.now();
        return appointmentMapper.toDTO(
                appointmentRepository.findAllByCustomerIdAndStartAtGreaterThanEqualOrderByStartAt(customerId, start)
        );
    }

    public List<AppointmentDTO> findByStatus(AppointmentStatusEnum status) {
        return appointmentMapper.toDTO(
                appointmentRepository.findAllByAppointmentStatusOrderByStartAt(status)
        );
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

    private void totalPrice(Appointment appointment, BarberService barberService, Product product) {
        requireNonNull(appointment, "Appointment must not be null");
        requireNonNull(barberService, "BarberService must not be null");

        BigDecimal totalPrice;
        BigDecimal productPrice = product != null ? product.getPrice() : BigDecimal.ZERO;
        BigDecimal barberServicePrice = requireNonNull(barberService.getPrice(), "BarberService price must not be null");

        totalPrice = barberServicePrice.add(productPrice);

        appointment.setTotalPrice(totalPrice);
    }

    private void validateAssociations(Appointment appointment){
        Shop shop = requireNonNull(appointment.getShop(), "Appointment shop must be provided");
        Long shopId = requireNonNull(shop.getId(), "Appointment shop id must be provided");

        Barber barber = requireNonNull(appointment.getBarber(), "Appointment barber must be provided");
        Barber barberFromService = requireNonNull(appointment.getBarberService(), "Appointment barber service must be provided").getBarber();

        Shop barberShop = requireNonNull(barber.getShop(), "Barber must belong to a shop");
        if (!shopId.equals(requireNonNull(barberShop.getId(), "Barber shop id must be provided"))) {
            throw new IllegalArgumentException("Barber does not belong to the selected shop");
        }

        Barber serviceBarber = requireNonNull(barberFromService, "Barber service must be associated to a barber");
        Long serviceBarberId = requireNonNull(serviceBarber.getId(), "Barber service barber id must be provided");
        if (!serviceBarberId.equals(requireNonNull(barber.getId(), "Barber id must be provided"))) {
            throw new IllegalArgumentException("Barber service does not belong to the selected barber");
        }

        Shop serviceBarberShop = requireNonNull(serviceBarber.getShop(), "Barber service barber must belong to a shop");
        if (!shopId.equals(requireNonNull(serviceBarberShop.getId(), "Barber service barber shop id must be provided"))) {
            throw new IllegalArgumentException("Barber service does not belong to the selected shop");
        }

        Product product = appointment.getProduct();
        if (product != null) {
            Shop productShop = requireNonNull(product.getShop(), "Product must belong to a shop");
            if (!shopId.equals(requireNonNull(productShop.getId(), "Product shop id must be provided"))) {
                throw new IllegalArgumentException("Product does not belong to the selected shop");
            }
        }
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

        List<AppointmentStatusEnum> ignoredStatuses = List.of(AppointmentStatusEnum.CANCELLED,
                AppointmentStatusEnum.NO_SHOW);

        boolean barberBusy = appointmentRepository
                .existsByBarberIdAndAppointmentStatusNotInAndStartAtLessThanAndEndAtGreaterThan(barber.getId(),
                        ignoredStatuses, end, start);

        if (barberBusy) {
            throw new IllegalArgumentException("Barber already has an appointment for the selected time");
        }
        appointment.setAppointmentStatus(SCHEDULED);
        appointment.setEndAt(end);
    }

    private static LocalDateTime atEndOfDay(LocalDate date) {
        return date.atTime(LocalTime.MAX);
    }

    private static void validatePeriod(LocalDateTime start, LocalDateTime end) {
        requireNonNull(start, "Start datetime must be provided");
        requireNonNull(end, "End datetime must be provided");
        if (!end.isAfter(start)) {
            throw new IllegalArgumentException("End datetime must be after start datetime");
        }
    }

    private static boolean overlaps(LocalDateTime aStart, LocalDateTime aEnd,
                                    LocalDateTime bStart, LocalDateTime bEnd) {

        return aStart.isBefore(bEnd) && bStart.isBefore(aEnd);
    }
}
