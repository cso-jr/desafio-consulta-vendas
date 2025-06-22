package com.devsuperior.dsmeta.dto;

import com.devsuperior.dsmeta.projections.SaleSumProjection;

public class SaleSumDTO {

	private String sellerName;
	private Double total;
	
	public SaleSumDTO() {
		
	}
	
	public SaleSumDTO(String name, Double total) {
		super();
		this.sellerName = name;
		this.total = total;
	}
	
	public SaleSumDTO(SaleSumProjection projection) {
		sellerName = projection.getName();
		total = projection.getTotal();
	}
	
	public SaleSumDTO(SaleSumDTO entity) {
		sellerName = entity.getSellerName();
		total = entity.getTotal();
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String name) {
		this.sellerName = name;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "SalesSumDTO [name=" + sellerName + ", total=" + total + "]";
	}

		
}
