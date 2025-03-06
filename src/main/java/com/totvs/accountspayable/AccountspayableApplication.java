package com.totvs.accountspayable;

import com.totvs.accountspayable.domain.model.AccountsPayable;
import com.totvs.accountspayable.domain.service.AccountsPayableService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@SpringBootApplication
public class AccountspayableApplication {
	public static void main(String[] args) {
		SpringApplication.run(AccountspayableApplication.class, args);

		LocalDate dataBase = LocalDate.now();
		
		AccountsPayable c01 = new AccountsPayable();
		c01.setDatavencimento(dataBase.plusDays(7));
		c01.setValor(new BigDecimal(1000.0));
		c01.setFornecedor("FornecedorA");
		c01.setDescricao("Titulo ref. NF 000000001/1");
		
		AccountsPayable c02 = new AccountsPayable();
		c02.setDatavencimento(dataBase.plusMonths(1));
		c02.setValor(new BigDecimal(200.00));
		c02.setFornecedor("FornecedorA");
		c02.setDescricao("Titulo ref. NF 000000002/1");

		AccountsPayable c03 = new AccountsPayable();
		c03.setDatavencimento(dataBase.plusDays(7));
		c03.setValor(new BigDecimal(200.00));
		c03.setFornecedor("FornecedorB");
		c03.setDescricao("Titulo ref. NF 000000001/1");

		AccountsPayable c04 = new AccountsPayable();
		c04.setDatavencimento(dataBase.plusDays(7));
		c04.setValor(new BigDecimal(100.00));
		c04.setFornecedor("FornecedorC");
		c04.setDescricao("Titulo ref. NF 000000004/1");
		
		AccountsPayable c05 = new AccountsPayable();
		c05.setDatavencimento(dataBase.plusMonths(1));
		c05.setValor(new BigDecimal(300.00));
		c05.setFornecedor("FornecedorA");
		c05.setDescricao("Titulo ref. NF 000000002/1");
		
		AccountsPayableService accountService = new AccountsPayableService();
        accountService.save(c01);
        System.out.println(c01);
        
        accountService.save(c02);
        System.out.println(c02);
        
        accountService.save(c03);
        System.out.println(c03);
        
        accountService.save(c04);
        System.out.println(c04);
        
        accountService.save(c05);
        System.out.println(c05);    
	}
}
