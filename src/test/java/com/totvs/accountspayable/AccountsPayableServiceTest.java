package com.totvs.accountspayable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.totvs.accountspayable.application.enums.SituacaoConta;
import com.totvs.accountspayable.domain.model.AccountsPayable;
import com.totvs.accountspayable.domain.repository.AccountsPayableRepository;
import com.totvs.accountspayable.domain.service.AccountsPayableService;

public class AccountsPayableServiceTest {

    @Mock
    private AccountsPayableRepository accountRepository;

    @InjectMocks
    private AccountsPayableService accountService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<AccountsPayable> expectedPage = new PageImpl<>(List.of(new AccountsPayable()));
        when(accountRepository.findAll(pageable)).thenReturn(expectedPage);

        Page<AccountsPayable> result = accountService.findAll(pageable);

        assertNotNull(result);
        assertEquals(expectedPage, result);
        verify(accountRepository, times(1)).findAll(pageable);
    }

    @Test
    public void testFindById() {
        Integer id = 1;
        AccountsPayable expectedAccount = new AccountsPayable();
        expectedAccount.setId(id);
        when(accountRepository.findById(id)).thenReturn(Optional.of(expectedAccount));

        AccountsPayable result = accountService.findById(id);

        assertNotNull(result);
        assertEquals(expectedAccount, result);
        verify(accountRepository, times(1)).findById(id);
    }

    @Test
    public void testGetTotalPaid() {
        LocalDate start = LocalDate.now().minusDays(30);
        LocalDate end = LocalDate.now();
        Double expectedTotal = 1000.0;
        when(accountRepository.sumValorByDataPagamentoBetween(start, end)).thenReturn(Optional.of(expectedTotal));

        Double result = accountService.getTotalPaid(start, end);

        assertNotNull(result);
        assertEquals(expectedTotal, result);
        verify(accountRepository, times(1)).sumValorByDataPagamentoBetween(start, end);
    }

    @Test
    public void testSave() {
        AccountsPayable account = new AccountsPayable();
        account.setId(1);
        when(accountRepository.save(account)).thenReturn(account);

        AccountsPayable result = accountService.save(account);

        assertNotNull(result);
        assertEquals(account, result);
        verify(accountRepository, times(1)).save(account);
    }

    @Test
    public void testUpdate() {
        Integer id = 1;
        AccountsPayable account = new AccountsPayable();
        account.setId(id);
        when(accountRepository.save(account)).thenReturn(account);

        AccountsPayable result = accountService.update(id, account);

        assertNotNull(result);
        assertEquals(account, result);
        verify(accountRepository, times(1)).save(account);
    }

    @Test
    public void testUpdateSituacao() {
        Integer id = 1;
        SituacaoConta situacao = SituacaoConta.BAIXADO;
        AccountsPayable account = new AccountsPayable();
        account.setId(id);
        when(accountRepository.findById(id)).thenReturn(Optional.of(account));
        when(accountRepository.save(account)).thenReturn(account);

        AccountsPayable result = accountService.updateSituacao(id, situacao);

        assertNotNull(result);
        assertEquals(situacao, result.getSituacao());
        verify(accountRepository, times(1)).findById(id);
        verify(accountRepository, times(1)).save(account);
    }

    @Test
    public void testImportacaoCSV() throws IOException {
        MultipartFile file = mock(MultipartFile.class);
        when(file.getInputStream()).thenReturn(new ByteArrayInputStream("2023-10-01,100.00,Descricao,Fornecedor".getBytes()));

        accountService.importacaoCSV(file);

        verify(accountRepository, times(1)).save(any(AccountsPayable.class));
    }
}