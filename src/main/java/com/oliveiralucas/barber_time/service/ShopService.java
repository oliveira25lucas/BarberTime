package com.oliveiralucas.barber_time.service;

import com.oliveiralucas.barber_time.data.dto.ShopDTO;
import com.oliveiralucas.barber_time.exception.NotFoundException;
import com.oliveiralucas.barber_time.mapper.ShopMapper;
import com.oliveiralucas.barber_time.model.Shop;
import com.oliveiralucas.barber_time.repository.ShopRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<ShopDTO> findAll() {
        return shopMapper.toDTO(shopRepository.findAll());
    }

    @Transactional(readOnly = true)
    public ShopDTO findById(Long id) {
        Shop entity = shopRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Shop id " + id + " not found"));
        return shopMapper.toDTO(entity);
    }

    public ShopDTO create(ShopDTO dto) {
        Shop entity = shopMapper.toEntity(dto);
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
