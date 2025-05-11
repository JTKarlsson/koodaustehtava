package fi.joni.koodaustehtava.koodaustehtava;

import fi.joni.koodaustehtava.koodaustehtava.walletDb.Player;
import fi.joni.koodaustehtava.koodaustehtava.walletDb.PlayerRepository;
import fi.joni.koodaustehtava.koodaustehtava.walletDb.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class KoodaustehtavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(KoodaustehtavaApplication.class, args);
	}

	// Tässä versioissa ei ole tapaa luoda uutta pelaajaa appiksella.
	@Bean
	public CommandLineRunner createPlayersWithMoney(PlayerRepository playerRepository, TransactionRepository transactionRepository) {
		return (args) -> {

			Player player1 = new Player();
			player1.setPlayerId("1");
			player1.setPlayerName("Pekka");
			player1.setBalance("100.00");
			playerRepository.save(player1);

			Player player2 = new Player();
			player2.setPlayerId("2");
			player2.setPlayerName("Maisa");
			player2.setBalance("1000.00");
			playerRepository.save(player2);

			Player player3 = new Player();
			player3.setPlayerId("3");
			player3.setPlayerName("Hermanni");
			player3.setBalance("10.00");
			playerRepository.save(player3);


			System.out.println("Dummy data luotu onnistuneesti");
		};
	}
}
