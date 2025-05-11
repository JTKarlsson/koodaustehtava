package fi.joni.koodaustehtava.koodaustehtava.service;

import fi.joni.koodaustehtava.koodaustehtava.dto.RequestDto;
import fi.joni.koodaustehtava.koodaustehtava.model.Player;
import fi.joni.koodaustehtava.koodaustehtava.model.Transaction;
import fi.joni.koodaustehtava.koodaustehtava.repository.PlayerRepository;
import fi.joni.koodaustehtava.koodaustehtava.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransactionService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public void addTransactionToPlayer(RequestDto requestDto) {
        Player player = playerRepository.findById(requestDto.getPlayerId())
                .orElseThrow(() -> new RuntimeException("Player not found"));

        if (player.getBalance().compareTo(requestDto.getAmount()) < 0)  {
            throw new RuntimeException("balance is too low for play");
        }

        Transaction transaction = new Transaction();
        transaction.setTransactionId(requestDto.gettransactionId());
        transaction.setAmount(requestDto.getAmount());
        transaction.setTransactionType("WIN");
        transaction.setTimestamp(String.valueOf(LocalDateTime.now()));
        transaction.setPlayer(player);

        transactionRepository.save(transaction);
    }
}
