package com.oliveiralucas.barber_time.controller;

import com.oliveiralucas.barber_time.data.dto.ProductDTO;
import com.oliveiralucas.barber_time.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/product/v1")
@Tag(name = "Product", description = "Endpoints for Product")
public class ProductController {
    
    public final ProductService productService;
    
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a Product", 
            tags = {"Product"})
    public ProductDTO createProduct(@RequestBody ProductDTO product) {
        return productService.createProduct(product);
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Find all Products",
            tags = {"Product"})
    public List<ProductDTO> list() {
        return productService.findAllProducts();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Find a Product",
            tags = {"Product"})
    public ProductDTO getProductServiceById(@PathVariable Long id) {
        return productService.findById(id);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update a Product",
            tags = {"Product"})
    public ProductDTO updateProductService(@PathVariable Long id, @RequestBody ProductDTO product) {
        return productService.update(id, product);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Product",
            tags = {"Product"})
    public void deleteProductServiceById(@PathVariable Long id) {
        productService.delete(id);
    }
}
