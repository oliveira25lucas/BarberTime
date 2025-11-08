package com.oliveiralucas.barber_time.service;

import com.oliveiralucas.barber_time.data.dto.ProductDTO;
import com.oliveiralucas.barber_time.data.dto.ProductDTO;
import com.oliveiralucas.barber_time.data.dto.summary.ProductSummaryDTO;
import com.oliveiralucas.barber_time.enums.StatusEnum;
import com.oliveiralucas.barber_time.exception.NotFoundException;
import com.oliveiralucas.barber_time.mapper.ProductMapper;
import com.oliveiralucas.barber_time.model.Product;
import com.oliveiralucas.barber_time.model.Product;
import com.oliveiralucas.barber_time.model.Shop;
import com.oliveiralucas.barber_time.repository.ProductRepository;
import com.oliveiralucas.barber_time.repository.ShopRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ShopRepository shopRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ShopRepository shopRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.shopRepository = shopRepository;
        this.productMapper = productMapper;
    }


    @Transactional(readOnly = true)
    public List<ProductSummaryDTO> findAllProducts() {
        Comparator<ProductSummaryDTO> byPrice = Comparator.comparing(
                ProductSummaryDTO::getPrice,
                Comparator.nullsLast(java.math.BigDecimal::compareTo)
        );
        Comparator<ProductSummaryDTO> byQuantity = Comparator.comparing(
                ProductSummaryDTO::getQuantity,
                Comparator.nullsLast(Integer::compare)
        ).reversed();

        return productMapper.toSummary(productRepository.findAll())
                .stream()
                .filter(dto -> dto.getStatus() == StatusEnum.ACTIVE)
                .filter(dto -> dto.getQuantity() == null || dto.getQuantity() > 0)
                .sorted(byPrice.thenComparing(byQuantity))
                .toList();
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Product entity = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product id " + id + " not found"));
        return productMapper.toDTO(entity);
    }

    public ProductDTO createProduct(ProductDTO dto) {
        Product entity = productMapper.toEntity(dto);
        entity.setShop(resolveShop(dto));
        entity = productRepository.save(entity);
        return productMapper.toDTO(entity);
    }

    public ProductDTO update(Long id, ProductDTO dto) {
        Product entity = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product id " + id + " not found"));
        productMapper.updateFromDto(dto, entity);
        entity = productRepository.save(entity);
        return productMapper.toDTO(entity);
    }

    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new NotFoundException("Product id " + id + " not found");
        }
        productRepository.deleteById(id);
    }

    private Shop resolveShop(ProductDTO dto) {
        if (dto.getShop() == null || dto.getShop().getId() == null) {
            throw new NotFoundException("Shop id must be provided to associate a product");
        }

        Long shopId = dto.getShop().getId();
        return shopRepository.findById(shopId)
                .orElseThrow(() -> new NotFoundException("Shop id " + shopId + " not found"));
    }
}
