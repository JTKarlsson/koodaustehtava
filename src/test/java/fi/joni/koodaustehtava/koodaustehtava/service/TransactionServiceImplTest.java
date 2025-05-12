package fi.joni.koodaustehtava.koodaustehtava.service;

import fi.joni.koodaustehtava.koodaustehtava.dto.RequestDto;
import fi.joni.koodaustehtava.koodaustehtava.model.CurrentBalance;
import fi.joni.koodaustehtava.koodaustehtava.model.Player;
import fi.joni.koodaustehtava.koodaustehtava.repository.PlayerRepository;
import fi.joni.koodaustehtava.koodaustehtava.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static fi.joni.koodaustehtava.koodaustehtava.utils.Consts.PAY;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionServiceImplTest {

    private PlayerRepository playerRepository;
    private TransactionRepository transactionRepository;
    private TransactionServiceImpl transactionService;

    @BeforeEach
    public void setUp() {
        // Luo mockit PlayerRepository ja TransactionRepository
        playerRepository = mock(PlayerRepository.class);
        transactionRepository = mock(TransactionRepository.class);

        // Luo TransactionServiceImpl -instanssi käyttäen mock-repositories
        transactionService = new TransactionServiceImpl(playerRepository, transactionRepository);
    }

    @Test
    public void processTransaction_win_success() {
        // Arrange
        Player player = new Player();
        player.setBalance(new BigDecimal("100.00"));
        RequestDto requestDto = new RequestDto("tööt-1", "1", new BigDecimal("10"));

        // Validointi menee tässä läpi, pelaaja löytyy ja "tööt-1" on uniikki
        when(playerRepository.findById("1")).thenReturn(Optional.of(player));
        when(transactionRepository.findById("tööt-1")).thenReturn(Optional.empty());

        //Act
        CurrentBalance currentBalance = transactionService.processTransaction(requestDto, "WIN");

        //Assert
        assertEquals(new BigDecimal("110.00"), currentBalance.getCurrentBalance());
        verify(playerRepository).save(player);
    }

    @Test
    public void processTransaction_Pay_success() {
        // Arrange
        Player player = new Player();
        player.setBalance(new BigDecimal("100.00"));
        RequestDto requestDto = new RequestDto("tööt-1", "1", new BigDecimal("10"));

        // Validointi menee tässä läpi, pelaaja löytyy ja "tööt-1" on uniikki
        when(playerRepository.findById("1")).thenReturn(Optional.of(player));
        when(transactionRepository.findById("tööt-1")).thenReturn(Optional.empty());

        //Act
        CurrentBalance currentBalance = transactionService.processTransaction(requestDto, PAY);

        //Assert
        assertEquals(new BigDecimal("90.00"), currentBalance.getCurrentBalance());
        verify(playerRepository).save(player);
    }

    @Test
    public void processTransaction_Pay_fail() {
        // Arrange
        Player player = new Player();
        player.setBalance(new BigDecimal("100.00"));
        RequestDto requestDto = new RequestDto("tööt-1", "1", new BigDecimal("1000.00"));

        // Validointi menee tässä läpi, pelaaja löytyy ja "tööt-1" on uniikki
        when(playerRepository.findById("1")).thenReturn(Optional.of(player));
        when(transactionRepository.findById("tööt-1")).thenReturn(Optional.empty());

        //Act
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            transactionService.processTransaction(requestDto, PAY);
        });

        //Assert
        assertEquals("Balance is too low to play", exception.getMessage());
    }
}