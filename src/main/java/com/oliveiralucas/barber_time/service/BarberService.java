package com.oliveiralucas.barber_time.service;

import com.oliveiralucas.barber_time.data.dto.BarberDTO;
import com.oliveiralucas.barber_time.data.dto.ProductDTO;
import com.oliveiralucas.barber_time.data.dto.summary.BarberSummaryDTO;
import com.oliveiralucas.barber_time.enums.StatusEnum;
import com.oliveiralucas.barber_time.exception.NotFoundException;
import com.oliveiralucas.barber_time.mapper.BarberMapper;
import com.oliveiralucas.barber_time.model.Barber;
import com.oliveiralucas.barber_time.model.Shop;
import com.oliveiralucas.barber_time.repository.BarberRepository;
import com.oliveiralucas.barber_time.repository.ShopRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

@Service
public class BarberService {

    private final BarberRepository barberRepository;
    private final ShopRepository shopRepository;
    private final BarberMapper barberMapper;

    public BarberService(BarberRepository barberRepository, ShopRepository shopRepository, BarberMapper barberMapper) {
        this.barberRepository = barberRepository;
        this.shopRepository = shopRepository;
        this.barberMapper = barberMapper;
    }

    @Transactional(readOnly = true)
    public List<BarberSummaryDTO> findAllBarbers() {
        Comparator<BarberSummaryDTO> byRatingAverage = Comparator.comparing(
                BarberSummaryDTO::getRatingAverage,
                Comparator.nullsLast(java.math.BigDecimal::compareTo)
        );
        Comparator<BarberSummaryDTO> byRatingCount = Comparator.comparing(
                BarberSummaryDTO::getRatingCount,
                Comparator.nullsLast(Integer::compare)
        );

        return barberMapper.toSummary(barberRepository.findAll())
                .stream()
                .filter(dto -> dto.getStatus() == StatusEnum.ACTIVE)
                .sorted(byRatingAverage.reversed().thenComparing(byRatingCount.reversed()))
                .toList();
    }

    @Transactional(readOnly = true)
    public BarberDTO findById(Long id) {
        Barber entity = barberRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Barber id " + id + " not found"));
        return barberMapper.toDTO(entity);
    }

    public BarberDTO createBarber(BarberDTO dto) {
        Barber entity = barberMapper.toEntity(dto);
        entity.setShop(resolveShop(dto));
        entity.setPassword(dto.getPassword());
        validateWorkingHours(entity, entity.getShop());
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

    private Shop resolveShop(BarberDTO dto) {
        if (dto.getShop() == null || dto.getShop().getId() == null) {
            throw new NotFoundException("Shop id must be provided to associate a barber");
        }

        Long shopId = dto.getShop().getId();
        return shopRepository.findById(shopId)
                .orElseThrow(() -> new NotFoundException("Shop id " + shopId + " not found"));
    }

    private void validateWorkingHours(Barber barber, Shop shop) {
        requireNonNull(barber, "Barber must not be null");
        requireNonNull(shop, "Shop must not be null");
        LocalTime shopOpen = requireNonNull(shop.getOpenTime(), "Shop open time must be provided");
        LocalTime shopClose = requireNonNull(shop.getCloseTime(), "Shop close time must be provided");
        LocalTime startShift = requireNonNull(barber.getStartShift(), "Barber start shift must be provided");
        LocalTime endShift = requireNonNull(barber.getEndShift(), "Barber end shift must be provided");
        LocalTime startLunch = requireNonNull(barber.getStartLunch(), "Barber lunch start must be provided");
        LocalTime endLunch = requireNonNull(barber.getEndLunch(), "Barber lunch end must be provided");

        if (!startShift.isBefore(endShift)) {
            throw new IllegalArgumentException("Barber shift start time must be before end time");
        }
        if (!startLunch.isBefore(endLunch)) {
            throw new IllegalArgumentException("Barber lunch start time must be before end time");
        }
        if (startShift.isBefore(shopOpen) || endShift.isAfter(shopClose)) {
            throw new IllegalArgumentException("Barber shift must be within shop working hours");
        }
        if (startLunch.isBefore(shopOpen) || endLunch.isAfter(shopClose)) {
            throw new IllegalArgumentException("Barber lunch must be within shop working hours");
        }
        if (startLunch.isBefore(startShift) || endLunch.isAfter(endShift)) {
            throw new IllegalArgumentException("Barber lunch must occur during the barber's shift");
        }
    }
}
