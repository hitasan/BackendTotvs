package com.totvs.accountspayable.domain.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.totvs.accountspayable.domain.model.AccountsPayable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface AccountsPayableRepository extends JpaRepository<AccountsPayable, Integer> {
	@Query("SELECT SUM(c.valor) FROM AccountsPayable c WHERE c.datapagamento BETWEEN :start AND :end")
	Optional<Double> sumValorByDataPagamentoBetween(@Param("start") LocalDate start, @Param("end") LocalDate end);
}