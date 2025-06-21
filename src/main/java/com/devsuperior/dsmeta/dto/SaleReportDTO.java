package com.devsuperior.dsmeta.dto;

import java.time.LocalDate;

import com.devsuperior.dsmeta.projections.SaleReportProjection;

public class SaleReportDTO {

	private Long id;
	private String name;
	private Double amount;
	private LocalDate date;
	
	public SaleReportDTO() {
		
	}
	
	public SaleReportDTO(Long id, String name, Double amount, LocalDate date) {
		super();
		this.id = id;
		this.name = name;
		this.amount = amount;
		this.date = date;
	}
	
	public SaleReportDTO(SaleReportProjection projection) {
		id = projection.getId();
		name = projection.getName();
		amount = projection.getAmount();
		date = projection.getDate();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	
}
