package com.javalabs.assessments.productcrud.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javalabs.assessments.productcrud.dto.ProductDTO;
import com.javalabs.assessments.productcrud.entity.Product;
import com.javalabs.assessments.productcrud.repository.ProductRepository;

@Service
public class ProductService {
	public static final String DUPLICATE_PRODUCT_MESSAGE = "Another product with same name already exists.";
	public static final String PRODUCT_DOES_NOT_EXIST_MESSAGE = "Product with given id does not exist.";

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ObjectMapper objectMapper;

	public List<ProductDTO> getAllProducts() {
		return this.productRepository.findAll().stream()
				.map(entity -> this.objectMapper.convertValue(entity, ProductDTO.class)).toList();
	}

	@Transactional
	public Product addProduct(final ProductDTO productDTO) throws Exception {
		Product newProduct;
		Optional<Product> product = this.productRepository.findByName(productDTO.getName());

		if (product.isEmpty()) {
			newProduct = this.objectMapper.convertValue(productDTO, Product.class);
			newProduct.setId(null);

			newProduct = this.productRepository.save(newProduct);
		} else {
			throw new Exception(DUPLICATE_PRODUCT_MESSAGE);
		}

		return newProduct;
	}

	public Optional<Product> getProduct(final long productId) {
		Optional<Product> product = this.productRepository.findById(productId);

		return product;
	}

	@Transactional
	public Product updateProduct(final ProductDTO productDTO) throws Exception {
		Product updatedProduct;

		Optional<Product> product = this.productRepository.findById(productDTO.getId());
		if (product.isPresent()) {
			Optional<Product> updateProduct = this.productRepository.findByNameAndIdNot(productDTO.getName(),
					productDTO.getId());

			if (updateProduct.isPresent()) {
				throw new Exception(DUPLICATE_PRODUCT_MESSAGE);
			} else {
				Product productToUpdate = this.objectMapper.convertValue(productDTO, Product.class);
				updatedProduct = this.productRepository.save(productToUpdate);
			}
		} else {
			throw new Exception(PRODUCT_DOES_NOT_EXIST_MESSAGE);
		}

		return updatedProduct;
	}

	@Transactional
	public void deleteProduct(final long productId) throws Exception {
		Product existingProduct = this.productRepository.findById(productId)
				.orElseThrow(() -> new Exception(PRODUCT_DOES_NOT_EXIST_MESSAGE));

		this.productRepository.delete(existingProduct);
	}
}
