package com.javalabs.assessments.productcrud.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
	@NotNull(groups = UpdateExistingProduct.class)
	private Long id;

	private String name;
	private String description;
	private Integer price;

	public static interface UpdateExistingProduct {

	}
}
