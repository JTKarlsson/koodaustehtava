package fi.joni.koodaustehtava.koodaustehtava.walletDb;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
}