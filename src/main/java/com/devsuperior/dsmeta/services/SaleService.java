package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
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
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}
	//variáveis precisaram ser inicializadas fora do método dateHandling()
	LocalDate initialDate = null;
	LocalDate finalDate = null;

	//Recebe Strings e Pageable para tratamento antes de chamar o repository
	public Page<SaleReportDTO> saleReport(String minDate, String maxDate, String name, Pageable pageable) {

		// método com try-catch sem customização
		try {
			//converte as datas String minDate e maxDate em LocalDate initialDate e finalDate de acordo com as regras fornecidas
			dateHandling(minDate, maxDate);

		} catch (DateTimeParseException e) {
			System.err.println(e.getMessage());
		}
		// service recebe os parâmetros e chama o repository, que vai devolver um projection para construção do DTO
		Page<SaleReportProjection> result = repository.saleReport(initialDate, finalDate, name, pageable);
		return result.map(x -> new SaleReportDTO(x));
	}

	
	public Page<SaleSumDTO> saleSum(String minDate, String maxDate, Pageable pageable) {
		//método sem o try-catch
		dateHandling(minDate, maxDate);

		Page<SaleSumDTO> result = repository.saleSum(initialDate, finalDate, pageable);
		
		return result.map(x -> new SaleSumDTO(x));

	}

	// tratamento da data inicial e final com mesmo comportamento para os dois métodos saleReport() e saleSum()
	private void dateHandling(String minDate, String maxDate) throws DateTimeParseException {

		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());

		if (maxDate != null) {
			finalDate = LocalDate.parse(maxDate);
		} else {

			finalDate = today;
		}

		if (minDate != null) {

			initialDate = LocalDate.parse(minDate);

		} else {
			initialDate = finalDate.minusYears(1L);
		}
	}
}
