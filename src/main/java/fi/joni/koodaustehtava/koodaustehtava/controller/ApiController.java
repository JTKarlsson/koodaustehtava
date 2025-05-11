package fi.joni.koodaustehtava.koodaustehtava.controller;

import fi.joni.koodaustehtava.koodaustehtava.dto.RequestDto;
import fi.joni.koodaustehtava.koodaustehtava.model.CurrentBalance;
import fi.joni.koodaustehtava.koodaustehtava.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final TransactionService transactionService;

    public ApiController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/pay")
    public ResponseEntity<CurrentBalance> handlePay(@RequestBody RequestDto request) {

        transactionService.addTransactionToPlayer(request);
        var response = new CurrentBalance(new BigDecimal("100.00"));

        return ResponseEntity.ok(response);
    }

    @PostMapping("/win")
    public ResponseEntity<CurrentBalance> handleWin(@RequestBody RequestDto request) {

        transactionService.addTransactionToPlayer(request);
        var response = new CurrentBalance(new BigDecimal("100.00"));

        return ResponseEntity.ok(response);
    }

}
