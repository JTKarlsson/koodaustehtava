package fi.joni.koodaustehtava.koodaustehtava.repository;

import fi.joni.koodaustehtava.koodaustehtava.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, String> {
}
