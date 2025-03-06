package com.totvs.accountspayable;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.totvs.accountspayable.domain.model.AccountsPayable;
import com.totvs.accountspayable.domain.repository.AccountsPayableRepository;
import com.totvs.accountspayable.domain.service.AccountsPayableService;

import java.time.LocalDate;
import java.math.BigDecimal;

@SpringBootTest
public class AccountsPayableServiceTest {
	@Mock
	private AccountsPayableRepository accountRepository;
	
	@InjectMocks
	private AccountsPayableService accountService;
	
	@Test
	public void testExample() {
		assertTrue(true, "Este teste deve passar.");
	}
	
	@Test
	public void testSave() {
		
		LocalDate dataBase = LocalDate.now();
		
		AccountsPayable c01 = new AccountsPayable();
		c01.setDatavencimento(dataBase.plusDays(7));
		c01.setValor(new BigDecimal(1000.00));
		c01.setFornecedor("FornecedorA");
		c01.setDescricao("Titulo ref. NF 000000001/1");
		
		when(accountRepository.save(any(AccountsPayable.class))).thenReturn(c01);
		
		AccountsPayable savedAccount = accountService.save(c01);
		assertEquals(c01.getDescricao(), savedAccount.getDescricao());
	}
}
