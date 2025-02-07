package com.sparta.myselectshop.controller;

import com.sparta.myselectshop.dto.ProductMypriceRequestDto;
import com.sparta.myselectshop.dto.ProductRequestDto;
import com.sparta.myselectshop.dto.ProductResponseDto;
import com.sparta.myselectshop.security.UserDetailsImpl;
import com.sparta.myselectshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class ProductController {

    private final ProductService productService;

    @PostMapping("/products")
    public ProductResponseDto createProduct(@RequestBody ProductRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return productService.createProduct(requestDto, userDetails.getUser());
    }

    @PutMapping("/products/{id}")
    public ProductResponseDto updateProduct(@PathVariable long id, @RequestBody ProductMypriceRequestDto requestDto) {

        return productService.updateProduct(id, requestDto);
    }

    @GetMapping("/products")
    public Page<ProductResponseDto> getProducts(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("isAsc") boolean isAsc,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return productService.getProducts(
                userDetails.getUser(),
                page - 1,
                size,
                sortBy,
                isAsc
        );
    }

    @PostMapping("/products/{productId}/folder")
    public void addFolder(@AuthenticationPrincipal UserDetailsImpl userDetails,
                          @PathVariable Long productId,
                          @RequestParam("folderId") Long folderId) {
        productService.addFolder(productId, folderId, userDetails.getUser());
    }

    @GetMapping("/folders/{folderId}/products")
    public Page<ProductResponseDto> getProductsInFolder(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                        @PathVariable Long folderId,
                                                        @RequestParam("page") int page,
                                                        @RequestParam("size") int size,
                                                        @RequestParam("sortBy") String sortBy,
                                                        @RequestParam("isAsc") boolean isAsc
    ) {
        return productService.getProductsInFolder(
                folderId,
                page - 1,
                size,
                sortBy,
                isAsc,
                userDetails.getUser()
        );
    }
}
