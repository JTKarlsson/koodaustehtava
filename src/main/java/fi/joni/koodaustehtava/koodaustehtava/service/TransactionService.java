package fi.joni.koodaustehtava.koodaustehtava.service;

import fi.joni.koodaustehtava.koodaustehtava.dto.RequestDto;
import fi.joni.koodaustehtava.koodaustehtava.model.CurrentBalance;

public interface TransactionService {

    /**
     * Metodi, joka tallentaa kantaan pelaajan tilin tapahtumat ja päivittää balancea,
     * Tekee myös validointia: Tarkistaa, onko maksuun varaa, löytyykö pelaaja sekä
     * onko transaktionId uniikki.
     *
     * @param requestDto Tässä on pyynnön tiedot
     *
     * @param transactionType Kertoo onko kyseessä WIN vai PAY ja päättelee tämän perusteella
     *                        lisätäänkö balancea vai vähennetäänkö balancea
     * @return Palauttaa päivitetyn balancen {@link CurrentBalance}.
     */
    public CurrentBalance processTransaction(RequestDto requestDto, String transactionType);
}