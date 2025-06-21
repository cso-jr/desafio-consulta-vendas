package com.devsuperior.dsmeta.projections;

import java.time.LocalDate;

public interface SaleReportProjection {

	Long getId();
	String getName();
	Double getAmount();
	LocalDate getDate();
	
}
