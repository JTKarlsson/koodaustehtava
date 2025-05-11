package fi.joni.koodaustehtava.koodaustehtava.repository;

import fi.joni.koodaustehtava.koodaustehtava.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
}