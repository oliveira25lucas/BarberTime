package com.oliveiralucas.barber_time.controller;

import com.oliveiralucas.barber_time.data.dto.ShopDTO;
import com.oliveiralucas.barber_time.service.ShopService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/shop")
public class ShopController {

    private final ShopService shopService;


    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ShopDTO create(@RequestBody ShopDTO dto) {
        return shopService.create(dto);
    }

    @GetMapping
    public List<ShopDTO> list() {
        return shopService.findAll();
    }

    @GetMapping("/{id}")
    public ShopDTO getShopById(@PathVariable Long id) {
        return shopService.findById(id);
    }

    @PutMapping("/update/{id}")
    public ShopDTO updateShop(@PathVariable Long id, @RequestBody ShopDTO shop) {
        return shopService.update(id, shop);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteShopById(@PathVariable Long id) {
        shopService.delete(id);
    }
}
