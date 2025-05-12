package fi.joni.koodaustehtava.koodaustehtava.controller;

import fi.joni.koodaustehtava.koodaustehtava.dto.RequestDto;
import fi.joni.koodaustehtava.koodaustehtava.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static fi.joni.koodaustehtava.koodaustehtava.utils.Consts.PAY;
import static fi.joni.koodaustehtava.koodaustehtava.utils.Consts.WIN;


@RestController
@RequestMapping("/api")
public class ApiController {

    private final TransactionService transactionService;

    public ApiController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/pay")
    public ResponseEntity<?> handlePay(@RequestBody RequestDto request) {
        try {
            var response = transactionService.processTransaction(request, PAY);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }

    }

    @PostMapping("/win")
    public ResponseEntity<?> handleWin(@RequestBody RequestDto request) {
        try {
            var response = transactionService.processTransaction(request, WIN);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }

    }
}