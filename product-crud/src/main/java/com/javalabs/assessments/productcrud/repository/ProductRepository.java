package com.javalabs.assessments.productcrud.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javalabs.assessments.productcrud.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	public Optional<Product> findByName(final String name);

	public Optional<Product> findByNameAndIdNot(final String name, final long id);
}
