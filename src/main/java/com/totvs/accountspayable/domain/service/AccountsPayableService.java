package com.totvs.accountspayable.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import com.totvs.accountspayable.application.enums.SituacaoConta;
import com.totvs.accountspayable.domain.model.AccountsPayable;
import com.totvs.accountspayable.domain.repository.AccountsPayableRepository;


@Service
public class AccountsPayableService {
    @Autowired
    private AccountsPayableRepository accountRepository;
    
    public Page<AccountsPayable> findAll(Pageable pageable) {
    	return accountRepository.findAll(pageable);
    }
    
    public AccountsPayable findById(Integer id) {
    	return accountRepository.findById(id).orElseThrow();
    }
    
    public Double getTotalPaid(LocalDate start, LocalDate end) {
    	return accountRepository.sumValorByDataPagamentoBetween(start, end).orElse(0.0);
    }

    public AccountsPayable save(AccountsPayable account) {
        return accountRepository.save(account);
    }

    public AccountsPayable update(Integer id, AccountsPayable account) {
    	account.setId(id);
        return accountRepository.save(account);
    }

    public AccountsPayable updateSituacao(Integer id, SituacaoConta situacao) {
        AccountsPayable account = accountRepository.findById(id).orElseThrow();
        account.setSituacao(situacao);
        return accountRepository.save(account);
    }
    
    public void importacaoCSV(MultipartFile file) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            
        	String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                
                AccountsPayable account = new AccountsPayable();
                account.setDatavencimento(LocalDate.parse(data[0]));
                account.setValor(new BigDecimal(data[1]).setScale(2, RoundingMode.HALF_UP));
                account.setDescricao(data[2]);
                account.setFornecedor(data[3]);

                accountRepository.save(account);
            }
        } catch (IOException e) {
            throw new RuntimeException("Falha ao importar CSV, verifique o arquivo!", e);
        }
    }
}