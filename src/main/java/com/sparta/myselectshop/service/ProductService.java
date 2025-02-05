package com.sparta.myselectshop.service;

import com.sparta.myselectshop.dto.ProductMypriceRequestDto;
import com.sparta.myselectshop.dto.ProductRequestDto;
import com.sparta.myselectshop.dto.ProductResponseDto;
import com.sparta.myselectshop.entity.Product;
import com.sparta.myselectshop.entity.User;
import com.sparta.myselectshop.naver.dto.ItemDto;
import com.sparta.myselectshop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponseDto createProduct(ProductRequestDto requestDto, User user) {
        Product product = productRepository.save(new Product(requestDto, user));
        return new ProductResponseDto(product);
    }

    @Transactional
    public ProductResponseDto updateProduct(long id, ProductMypriceRequestDto requestDto) {
        Product product = productRepository
                .findById(id)
                .orElseThrow(() -> new NullPointerException("해당 상품을 찾을 수 없습니다."));

        product.update(requestDto);
        return new ProductResponseDto(product);
    }

    public List<ProductResponseDto> getProducts(User user) {
        return productRepository.findAllByUser(user)
                .stream()
                .map(ProductResponseDto::new)
                .toList();
    }

    @Transactional
    public void updateBySearch(Long id, ItemDto itemDto) {
        Product product = productRepository
                .findById(id)
                .orElseThrow(() -> new NullPointerException("해당 상품을 찾을 수 없습니다."));
        product.updateByItemDto(itemDto);
    }

    public List<ProductResponseDto> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(ProductResponseDto::new)
                .toList();
    }
}
