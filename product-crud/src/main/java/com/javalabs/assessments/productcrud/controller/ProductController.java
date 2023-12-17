package com.javalabs.assessments.productcrud.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.javalabs.assessments.productcrud.dto.ProductDTO;
import com.javalabs.assessments.productcrud.entity.Product;
import com.javalabs.assessments.productcrud.service.ProductService;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ProductController {
	@Autowired
	private ProductService productService;

	@GetMapping(path = "/{productId}")
	public ResponseEntity<?> findBook(@NotNull @PathVariable final Long productId) {
		ResponseEntity<?> response;
		log.info("Request to find product by id : {}", productId);

		Optional<Product> product = this.productService.getProduct(productId);

		if (product.isPresent()) {
			response = this.success(product.get());
		} else {
			response = this.failure(ProductService.PRODUCT_DOES_NOT_EXIST_MESSAGE);
		}

		return response;
	}

	@GetMapping(path = "/all")
	public ResponseEntity<List<ProductDTO>> getAllProducts() {
		List<ProductDTO> productList = this.productService.getAllProducts();

		return success(productList);
	}

	@PostMapping
	public ResponseEntity<?> addBook(@Validated @RequestBody final ProductDTO productDTO) {
		ResponseEntity<?> response;
		log.info("Request to add new product : {}", productDTO);

		try {
			Product newProduct = this.productService.addProduct(productDTO);
			response = this.success(newProduct);
		} catch (final Exception exception) {
			response = this.failure(exception.getMessage());
		}

		return response;
	}

	@PutMapping
	public ResponseEntity<?> updateBook(
			@Validated(value = ProductDTO.UpdateExistingProduct.class) @RequestBody final ProductDTO productDTO) {
		ResponseEntity<?> response;
		log.info("Request to update product : {}", productDTO);

		try {
			Product updatedProduct = this.productService.updateProduct(productDTO);
			response = this.success(updatedProduct);
		} catch (final Exception exception) {
			response = this.failure(exception.getMessage());
		}

		return response;
	}

	@DeleteMapping(path = "/{productId}")
	public ResponseEntity<?> deleteProduct(@NotNull @PathVariable final Long productId) {
		ResponseEntity<?> response;
		log.info("Request to delete product by id : {}", productId);

		try {
			this.productService.deleteProduct(productId);
			response = this.success("Product deleted.");
		} catch (final Exception exception) {
			response = this.failure(exception.getMessage());
		}

		return response;
	}

	private <T> ResponseEntity<T> success(final T body) {
		return ResponseEntity.<T>ok(body);
	}

	private ResponseEntity<String> failure(final String message) {
		return ResponseEntity.badRequest().body(message);
	}
}
