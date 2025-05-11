package fi.joni.koodaustehtava.koodaustehtava.walletDb;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, String> {
}
