package com.sparta.myselectshop.controller;

import com.sparta.myselectshop.dto.ProductMypriceRequestDto;
import com.sparta.myselectshop.dto.ProductRequestDto;
import com.sparta.myselectshop.dto.ProductResponseDto;
import com.sparta.myselectshop.entity.UserRoleEnum;
import com.sparta.myselectshop.security.UserDetailsImpl;
import com.sparta.myselectshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class ProductController {

    private final ProductService productService;

    public static final int MIN_MY_PRICE = 100;

    @PostMapping("/products")
    public ProductResponseDto createProduct(@RequestBody ProductRequestDto requestDto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return productService.createProduct(requestDto, userDetails.getUser());
    }

    @PutMapping("/products/{id}")
    public ProductResponseDto updateProduct(@PathVariable long id, @RequestBody ProductMypriceRequestDto requestDto) {

        int myprice = requestDto.getMyprice();
        if (myprice < MIN_MY_PRICE) {
            throw new IllegalArgumentException("유효하지 않은 관심 가격입니다. 최소 " + MIN_MY_PRICE + "원 이상으로 설정해주세요." );
        }

        return productService.updateProduct(id, requestDto);
    }

    @GetMapping("/products")
    public List<ProductResponseDto> getProducts(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return productService.getProducts(userDetails.getUser());
    }

    // Admin 계정은 모든 계정의 상품을 조회할 수 있도록 추가 구성
    @GetMapping("/admin/products")
    public List<ProductResponseDto> getAllProducts(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        UserRoleEnum role = userDetails.getUser().getRole();
        log.info("role = {}", role);

        if (!UserRoleEnum.ADMIN.equals(role)) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }

        return productService.getAllProducts();
    }

}
