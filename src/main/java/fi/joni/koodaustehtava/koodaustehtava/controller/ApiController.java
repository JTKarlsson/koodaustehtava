package fi.joni.koodaustehtava.koodaustehtava.controller;

import fi.joni.koodaustehtava.koodaustehtava.dto.RequestDto;
import fi.joni.koodaustehtava.koodaustehtava.model.CurrentBalance;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {

    @PostMapping("/pay")
    public ResponseEntity<CurrentBalance> handlePay(@RequestBody RequestDto request) {

        var response = new CurrentBalance("100.00");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/win")
    public ResponseEntity<CurrentBalance> handleWin(@RequestBody RequestDto request) {

        var response = new CurrentBalance("110.00");

        return ResponseEntity.ok(response);
    }

}
