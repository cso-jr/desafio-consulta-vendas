package com.devsuperior.dsmeta.repositories;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.projections.SaleReportProjection;
import com.devsuperior.dsmeta.projections.SaleSumProjection;

public interface SaleRepository extends JpaRepository<Sale, Long> {

	/*
	@Query("SELECT new com.devsuperior.dsmeta.dto.SalesSumDTO(obj.seller.name, SUM(obj.amount)) "
			+ "FROM Sale obj "
			+ "GROUP BY obj.seller.name")*/
	
	@Query(nativeQuery = true, value = "SELECT TB_SELLER.NAME, SUM(TB_SALES.AMOUNT) AS TOTAL "
			+ "FROM TB_SALES "
			+ "INNER JOIN TB_SELLER ON TB_SELLER.ID = TB_SALES.SELLER_ID "
			+ "WHERE TB_SALES.DATE BETWEEN :initialDate AND :finalDate "
			+ "GROUP BY TB_SELLER.NAME",
			countQuery ="SELECT TB_SELLER.NAME, SUM(TB_SALES.AMOUNT) AS TOTAL "
					+ "FROM TB_SALES "
					+ "INNER JOIN TB_SELLER ON TB_SELLER.ID = TB_SALES.SELLER_ID "
					+ "WHERE TB_SALES.DATE BETWEEN :initialDate AND :finalDate "
					+ "GROUP BY TB_SELLER.NAME" )
	Page<SaleSumProjection> search1(LocalDate initialDate, LocalDate finalDate, Pageable pageable);

	
	@Query(nativeQuery = true, value= "SELECT TB_SALES.ID, TB_SALES.DATE, TB_SALES.AMOUNT, TB_SELLER.NAME AS NAME "
			+ "FROM TB_SALES "
			+ "INNER JOIN TB_SELLER ON TB_SALES.SELLER_ID = TB_SELLER.ID "
			+ "WHERE TB_SALES.DATE BETWEEN :initialDate AND :finalDate AND "
			+ "UPPER(TB_SELLER.NAME) LIKE UPPER(CONCAT('%',:name,'%'))",
			 countQuery = "SELECT TB_SALES.ID, TB_SALES.DATE, TB_SALES.AMOUNT, TB_SELLER.NAME "
			 		+ "FROM TB_SALES "
			 		+ "INNER JOIN TB_SELLER ON TB_SALES.SELLER_ID = TB_SELLER.ID "
			 		+ "WHERE TB_SALES.DATE BETWEEN :initialDate AND :finalDate AND "
			 		+ "UPPER(TB_SELLER.NAME) LIKE UPPER(CONCAT('%',:name,'%'))")
	Page<SaleReportProjection> search2(LocalDate initialDate, LocalDate finalDate, String name, Pageable pageable);
}
