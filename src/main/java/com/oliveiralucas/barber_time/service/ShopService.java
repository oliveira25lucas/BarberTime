package com.oliveiralucas.barber_time.service;

import com.oliveiralucas.barber_time.data.dto.ShopDTO;
import com.oliveiralucas.barber_time.data.dto.summary.ShopSummaryDTO;
import com.oliveiralucas.barber_time.enums.StatusEnum;
import com.oliveiralucas.barber_time.exception.NotFoundException;
import com.oliveiralucas.barber_time.mapper.ShopMapper;
import com.oliveiralucas.barber_time.model.Shop;
import com.oliveiralucas.barber_time.repository.ShopRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@Transactional
public class ShopService {

    private final ShopRepository shopRepository;
    private final ShopMapper shopMapper;

    public ShopService(ShopRepository shopRepository, ShopMapper shopMapper) {
        this.shopRepository = shopRepository;
        this.shopMapper = shopMapper;
    }

    @Transactional(readOnly = true)
    public List<ShopSummaryDTO> findAll() {
        Comparator<ShopSummaryDTO> byRatingAverage = Comparator.comparing(
                ShopSummaryDTO::getRatingAverage,
                Comparator.nullsLast(java.math.BigDecimal::compareTo)
        );
        Comparator<ShopSummaryDTO> byRatingCount = Comparator.comparing(
                ShopSummaryDTO::getRatingCount,
                Comparator.nullsLast(Integer::compare)
        );

        return shopMapper.toSummary(shopRepository.findAll())
                .stream()
                .filter(dto -> dto.getStatus() == StatusEnum.ACTIVE)
                .sorted(byRatingAverage.reversed().thenComparing(byRatingCount.reversed()))
                .toList();
    }

    @Transactional(readOnly = true)
    public ShopDTO findById(Long id) {
        Shop entity = shopRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Shop id " + id + " not found"));
        return shopMapper.toDTO(entity);
    }

    public ShopDTO create(ShopDTO dto) {
        Shop entity = shopMapper.toEntity(dto);
        entity.setPassword(dto.getPassword());
        entity = shopRepository.save(entity);
        return shopMapper.toDTO(entity);
    }

    public ShopDTO update(Long id, ShopDTO dto) {
        Shop entity = shopRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Shop id " + id + " not found"));
        shopMapper.updateFromDto(dto, entity);
        entity = shopRepository.save(entity);
        return shopMapper.toDTO(entity);
    }

    public void delete(Long id) {
        if (!shopRepository.existsById(id)) {
            throw new NotFoundException("Shop id " + id + " not found");
        }
        shopRepository.deleteById(id);
    }
}
