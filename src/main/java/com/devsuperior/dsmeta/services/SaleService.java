package com.devsuperior.dsmeta.services;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SaleSumDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.projections.SaleReportProjection;
import com.devsuperior.dsmeta.projections.SaleSumProjection;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

public Page<SaleReportDTO> search2(String minDate, String maxDate, String name, Pageable pageable){
		
		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		LocalDate initialDate;
		LocalDate finalDate;
		
		if (maxDate != null) {
			finalDate = LocalDate.parse(maxDate);
		}else {
			
			finalDate = today;
		}
		
		if (minDate != null) {
		
			initialDate = LocalDate.parse(minDate);
			
		} else {
			initialDate = finalDate.minusYears(1L);
		}
		
		Page<SaleReportProjection> result = repository.search2(initialDate, finalDate, name, pageable); 
		return result.map(x -> new SaleReportDTO(x));
}
	
	
	public Page<SaleSumDTO> search1(String minDate, String maxDate, Pageable pageable){
		
		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		LocalDate initialDate;
		LocalDate finalDate;
		
		if (maxDate != null) {
			finalDate = LocalDate.parse(maxDate);
		}else {
			
			finalDate = today;
		}
		
		if (minDate != null) {
		
			initialDate = LocalDate.parse(minDate);
			
		} else {
			initialDate = finalDate.minusYears(1L);
		}

		
		Page<SaleSumProjection> result = repository.search1(initialDate, finalDate, pageable); 
		return result.map(x -> new SaleSumDTO(x));
		
	}
}
