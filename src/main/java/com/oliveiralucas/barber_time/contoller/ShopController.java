package com.oliveiralucas.barber_time.contoller;

import com.oliveiralucas.barber_time.model.Shop;
import com.oliveiralucas.barber_time.service.ShopService;
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
    public Shop createShop(@RequestBody Shop shop) {
        return shopService.create(shop);
    }

    @GetMapping
    public List<Shop> getAllShops() {
        return shopService.findAll();
    }

    @GetMapping("{id}")
    public Shop getShopById(@PathVariable Long id) {
        return shopService.findById(id);
    }

    @PutMapping("{id}")
    public Shop updateShop(@PathVariable Long id,@RequestBody Shop shop) {
        return shopService.update(id, shop);

    }

    @DeleteMapping("{id}")
    public void deleteShopById(@PathVariable Long id) {
        shopService.delete(id);
    }
}
