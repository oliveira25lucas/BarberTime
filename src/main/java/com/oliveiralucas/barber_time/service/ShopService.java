package com.oliveiralucas.barber_time.service;

import com.oliveiralucas.barber_time.model.Shop;
import com.oliveiralucas.barber_time.repository.ShopRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopService {

    private final ShopRepository shopRepository;

    public ShopService(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    public Shop create(Shop shop) {
        shop.setId(null);
        return shopRepository.save(shop);
    }

    public Shop findById(Long id) {
        return shopRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Shop " + id + " not found"));}

    public List<Shop> findAll() {
        return shopRepository.findAll();
    }

    public Shop update(Long id, Shop shop) {
        Shop existingShop = findById(id);
        existingShop.setEmail(shop.getEmail());
        existingShop.setPhone(shop.getPhone());
        existingShop.setAddress(shop.getAddress());
        return shopRepository.save(existingShop);
    }

    public void delete(Long id) {
        Shop shop = findById(id);
        shopRepository.deleteById(id);
    }
}
