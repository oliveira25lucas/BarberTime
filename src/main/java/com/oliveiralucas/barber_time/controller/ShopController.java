package com.oliveiralucas.barber_time.controller;

import com.oliveiralucas.barber_time.data.dto.ShopDTO;
import com.oliveiralucas.barber_time.service.ShopService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/shop/v1")
@Tag(name = "Shop", description = "Endpoints for Shop")
public class ShopController {

    private final ShopService shopService;


    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a Shop",
            tags = {"Shop"})
    public ShopDTO create(@RequestBody ShopDTO dto) {
        return shopService.create(dto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Find all Shop",
            tags = {"Shop"})
    public List<ShopDTO> list() {
        return shopService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Find a Shop",
            tags = {"Shop"})
    public ShopDTO getShopById(@PathVariable Long id) {
        return shopService.findById(id);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update a Shop",
            tags = {"Shop"})
    public ShopDTO updateShop(@PathVariable Long id, @RequestBody ShopDTO shop) {
        return shopService.update(id, shop);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Shop",
            tags = {"Shop"})
    public void deleteShopById(@PathVariable Long id) {
        shopService.delete(id);
    }
}
