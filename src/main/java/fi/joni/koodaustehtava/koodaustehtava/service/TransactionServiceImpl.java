package fi.joni.koodaustehtava.koodaustehtava.service;

import fi.joni.koodaustehtava.koodaustehtava.dto.RequestDto;
import fi.joni.koodaustehtava.koodaustehtava.model.CurrentBalance;
import fi.joni.koodaustehtava.koodaustehtava.model.Player;
import fi.joni.koodaustehtava.koodaustehtava.model.Transaction;
import fi.joni.koodaustehtava.koodaustehtava.repository.PlayerRepository;
import fi.joni.koodaustehtava.koodaustehtava.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static fi.joni.koodaustehtava.koodaustehtava.utils.Consts.PAY;
import static fi.joni.koodaustehtava.koodaustehtava.utils.Consts.WIN;


@Service
public class TransactionServiceImpl implements TransactionService{

    private final PlayerRepository playerRepository;
    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(PlayerRepository playerRepository, TransactionRepository transactionRepository) {
        this.playerRepository = playerRepository;
        this.transactionRepository = transactionRepository;
    }

    public CurrentBalance processTransaction(RequestDto requestDto, String transactionType) {
        Player player = findPlayerById(requestDto.getPlayerId());

        validateTransactionUniqueness(requestDto.gettransactionId());

        if (transactionType.equals(PAY) && player.getBalance().compareTo(requestDto.getAmount()) < 0) {
            throw new RuntimeException("Balance is too low to play");
        }

        BigDecimal updatedBalance = calculateNewBalance(player.getBalance(), requestDto.getAmount(), transactionType);
        player.setBalance(updatedBalance);
        playerRepository.save(player);

        Transaction transaction = createTransaction(requestDto, transactionType, player);
        transactionRepository.save(transaction);

        return new CurrentBalance(updatedBalance);
    }

    private Player findPlayerById(String playerId) {
        return playerRepository.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Player not found"));
    }

    private void validateTransactionUniqueness(String transactionId) {
        if (transactionRepository.findById(transactionId).isPresent()) {
            throw new RuntimeException("Transaction ID is already used");
        }
    }

    private BigDecimal calculateNewBalance(BigDecimal currentBalance, BigDecimal amount, String transactionType) {
        return transactionType.equals(WIN)
                ? currentBalance.add(amount)
                : currentBalance.subtract(amount);
    }


    private Transaction createTransaction(RequestDto requestDto, String transactionType, Player player) {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(requestDto.gettransactionId());
        transaction.setAmount(requestDto.getAmount());
        transaction.setTransactionType(transactionType);
        transaction.setTimestamp(LocalDateTime.now().toString());
        transaction.setPlayer(player);
        return transaction;
    }
}

