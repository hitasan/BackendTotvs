package com.totvs.accountspayable;

//import java.io.File;
//import java.io.FileInputStream;
//import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.mock.web.MockMultipartFile;

import com.totvs.accountspayable.application.enums.SituacaoConta;
import com.totvs.accountspayable.domain.model.AccountsPayable;
import com.totvs.accountspayable.infrastructure.controller.AccountsPayableController;

@SpringBootApplication
public class AccountspayableApplication {
	public static void main(String[] args) {
		SpringApplication.run(AccountspayableApplication.class, args);
	}
	
	@Bean
    public CommandLineRunner init(AccountsPayableController accountsPayableController) {
        return args -> {
            LocalDate dataBase = LocalDate.now();

            // --------------------------------------------------------------------------------------------------------------
            // Realizando teste de inclusão de contas a pagar
            // --------------------------------------------------------------------------------------------------------------
            AccountsPayable c01 = new AccountsPayable();
            c01.setDataemissao(dataBase);
            c01.setDatavencimento(dataBase.plusDays(7));
            c01.setValor(new BigDecimal("1000.00"));
            c01.setFornecedor("FornecedorA");
            c01.setDescricao("Título ref. NF 000000001/1");
            c01.setSituacao(SituacaoConta.ABERTO);
            
            AccountsPayable c02 = new AccountsPayable();
            c02.setDataemissao(dataBase);
            c02.setDatavencimento(dataBase.plusDays(7));
            c02.setValor(new BigDecimal("500.00"));
            c02.setFornecedor("FornecedorB");
            c02.setDescricao("Título ref. NF 000000001/1");
            c02.setSituacao(SituacaoConta.ABERTO);
            
            AccountsPayable c03 = new AccountsPayable();
            c03.setDataemissao(dataBase);
            c03.setDatavencimento(dataBase.plusDays(7));
            c03.setValor(new BigDecimal("200.00"));
            c03.setFornecedor("FornecedorC");
            c03.setDescricao("Título ref. NF 000000001/1");
            c03.setSituacao(SituacaoConta.ABERTO);

            AccountsPayable c04 = new AccountsPayable();
            c04.setDataemissao(dataBase);
            c04.setDatavencimento(dataBase.plusDays(7));
            c04.setValor(new BigDecimal("150.00"));
            c04.setFornecedor("FornecedorA");
            c04.setDescricao("Título ref. NF 000000002/1");
            c04.setSituacao(SituacaoConta.ABERTO);

            AccountsPayable savedAcc01 = accountsPayableController.create(c01).getBody();
            System.out.println("Titulo criado: " + savedAcc01);
            AccountsPayable savedAcc02 = accountsPayableController.create(c02).getBody();
            System.out.println("Titulo criado: " + savedAcc02);
            AccountsPayable savedAcc03 = accountsPayableController.create(c03).getBody();
            System.out.println("Titulo criado: " + savedAcc03);
            AccountsPayable savedAcc04 = accountsPayableController.create(c04).getBody();
            System.out.println("Titulo criado: " + savedAcc04);


            // --------------------------------------------------------------------------------------------------------------
            // Realizando teste de alteração dos titulos contas a pagar
            // --------------------------------------------------------------------------------------------------------------
            if (savedAcc01 != null) {
            	savedAcc01.setDescricao("Título atualizado ref. NF 000000001/1 - ALTERADO");
            	savedAcc01.setDatapagamento(dataBase);
            	AccountsPayable updAcc01 = accountsPayableController.update(savedAcc01.getId(), savedAcc01).getBody();
            	System.out.println("Titulo atualizado: " + updAcc01);
            }
            if (savedAcc02 != null) {
            	savedAcc02.setDescricao("Título atualizado ref. NF 000000001/1 - ALTERADO");
            	AccountsPayable updAcc02 = accountsPayableController.update(savedAcc02.getId(), savedAcc02).getBody();
            	System.out.println("Titulo atualizado: " + updAcc02);
            }
            if (savedAcc03 != null) {
            	savedAcc03.setDescricao("Título atualizado ref. NF 000000001/1 - ALTERADO");
            	AccountsPayable updAcc03 = accountsPayableController.update(savedAcc03.getId(), savedAcc03).getBody();
            	System.out.println("Titulo atualizado: " + updAcc03);
            }
            if (savedAcc04 != null) {
            	savedAcc04.setDatapagamento(dataBase);
            	savedAcc04.setDescricao("Título atualizado ref. NF 000000002/1 - ALTERADO");
                AccountsPayable updAcc04 = accountsPayableController.update(savedAcc04.getId(), savedAcc04).getBody();
                System.out.println("Titulo atualizado: " + updAcc04);
            }


            // --------------------------------------------------------------------------------------------------------------
            // Realizando teste de alteração da situação dos titulos de contas a pagar
            // --------------------------------------------------------------------------------------------------------------
            if (savedAcc01 != null) {
            	AccountsPayable updAcc01 = accountsPayableController.updateSituacao(savedAcc01.getId(), SituacaoConta.BAIXA_PARCIAL).getBody();
            	System.out.println("Situação atualizada: " + updAcc01);
            }
            if (savedAcc04 != null) {
            	AccountsPayable updAcc04 = accountsPayableController.updateSituacao(savedAcc04.getId(), SituacaoConta.BAIXADO).getBody();
                System.out.println("Situação atualizada: " + updAcc04);
            }


            // --------------------------------------------------------------------------------------------------------------        
            // Obtendo o total pago em um intervalo de datas        
            // --------------------------------------------------------------------------------------------------------------        
            LocalDate startDate = dataBase.minusDays(30);        
            LocalDate endDate = dataBase;        
            Double totalPago = accountsPayableController.getTotalPago(startDate, endDate).getBody();        
            System.out.println();
            System.out.println("-----------------------------------------------------------------");
            System.out.println("Total pago entre " + startDate + " e " + endDate + ": " + totalPago);
            System.out.println("-----------------------------------------------------------------");
            System.out.println();
            
            
            // --------------------------------------------------------------------------------------------------------------        
            // Localizando o titulo pelo ID        
            // -------------------------------------------------------------------------------------------------------------- 
            AccountsPayable foundAccount = accountsPayableController.findById( savedAcc01.getId() ).getBody();        
            System.out.println();
            System.out.println("-----------------------------------------------------------------");
            System.out.println("Titulo encontrado: " + foundAccount);      
            System.out.println("-----------------------------------------------------------------");
            System.out.println();


            // --------------------------------------------------------------------------------------------------------------        
            // Listando todos titulos criados        
            // -------------------------------------------------------------------------------------------------------------- 
            Pageable pageable = PageRequest.of(0, 10);
            ResponseEntity<Page<AccountsPayable>> response = accountsPayableController.findAll(pageable);
            Page<AccountsPayable> accountsPage = response.getBody();

            System.out.println();
            System.out.println("-----------------------------------------------------------------");
            if (accountsPage != null) {
                System.out.println("Contas a pagar encontradas:");
                accountsPage.forEach(account -> System.out.println(account));
            } else {
                System.out.println("Nenhuma conta a pagar encontrada.");
            }
            System.out.println("-----------------------------------------------------------------");
            System.out.println();
            
            
            // --------------------------------------------------------------------------------------------------------------        
            // Testando importação        
            // --------------------------------------------------------------------------------------------------------------
            //File csvFile = new File("contas.csv");

            //InputStream inputStream = new FileInputStream(csvFile);
            //MultipartFile file = new MockMultipartFile( "file", csvFile.getName(), "text/csv", inputStream );

            //ResponseEntity<String> resp = accountsPayableController.importCSV(file);
            //System.out.println(resp.getBody());

        };
    }
}
