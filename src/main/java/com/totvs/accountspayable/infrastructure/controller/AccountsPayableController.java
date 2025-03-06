package com.totvs.accountspayable.infrastructure.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.totvs.accountspayable.application.enums.SituacaoConta;
import com.totvs.accountspayable.domain.model.AccountsPayable;
import com.totvs.accountspayable.domain.service.AccountsPayableService;


@RestController
@RequestMapping("/accountspayable")
public class AccountsPayableController {
    @Autowired
    private AccountsPayableService accountService;

    @PostMapping
    public ResponseEntity<AccountsPayable> create(@RequestBody AccountsPayable account) {
        return ResponseEntity.ok(accountService.save(account));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountsPayable> update(@PathVariable Integer id, @RequestBody AccountsPayable account) {
        return ResponseEntity.ok(accountService.update(id, account));
    }

    @PatchMapping("/{id}/situacao")
    public ResponseEntity<AccountsPayable> updateSituacao(@PathVariable Long id, @RequestParam SituacaoConta situacao) {
        return ResponseEntity.ok(accountService.updateSituacao(id, situacao));
    }

    @GetMapping
    public ResponseEntity<Page<AccountsPayable>> findAll(Pageable pageable) {
        return ResponseEntity.ok(accountService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountsPayable> findById(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.findById(id));
    }

    @GetMapping("/totalpaid")
    public ResponseEntity<Double> getTotalPago(@RequestParam LocalDate start, @RequestParam LocalDate end) {
    	    return ResponseEntity.ok(accountService.getTotalPaid(start, end));
    	}
}
