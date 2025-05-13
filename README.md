# Koodaustehtävä

Tässä on toteutettu yksinkertainen pelimoottorin ja pelitilin (wallet) välinen integraatio,
jonka avulla voi ostaa pelejä ja maksaa voittoja.
Pelitilillä tarkoitetaan tässä yhteydessä palvelinta, joka tarjoaa HTTP API:n pelimoottoreille ja
hallinnoi asiakkaiden pelivaroja.

## Teknologia

- Spring boot sovellus
- Tietokanta muistinvarainen h2
- Dokumentaatiossa käytetty OpenApi speksiä
- "Pelimoottorina" toimii curl

## Ajaminen

- Tarvitset Java ympäristön asennetuksi
- Sitten ajat kansiossa "koodaustehtava" komennon ``mvn install`` ja tämä asentaa tarvittavat riippuvuudet
- Kun riippuvuudet on asennettu voit käynnistää sovelluksen komennolla ``mvn spring-boot:run``
- Sovellus löytyy tämän jälkeen osoitteesta https://localhost:8443
- Tietokannan konsoliin pääsee osoitteesta https://localhost:8443/h2-console/ (Huom sertti on itsetehty, joten siihen voi luottaa :D)
- Kun sovellus käynnistyy, se luo samalla "dummy dataa" 3kpl pelaajia eri rahasummilla. Näitä vasten voi kokeilla rajapintaa
- Rajapintaa voi kokeilla seuraavilla curl scripteillä: 

### Curlit

api/pay endpointille:
```bash

#!/bin/bash

curl -X POST https://localhost:8443/api/pay \
  -k -H "Content-Type: application/json" \
  -d '{
        "playerId": "2",
        "amount": "299",
        "transactionId": "txn-123"
      }'
```

api/pay endpointille:
```bash

#!/bin/bash

curl -X POST https://localhost:8443/api/win \
  -k -H "Content-Type: application/json" \
  -d '{
        "playerId": "1",
        "amount": "15",
        "transactionId": "txn-111"
      }'
```

## Pelitili

- Pelitili on kyseisessä sovelluksessa tietokanta, johon tallentaan tapahtumia maksuista ja voitoista TransactionService luokan kautta.
- TransictionService tekee ensin validointeja: Tarkistaa onko kyseinen pelaaja olemassa, onko pelitilillä varaa peliin ja onko tapahtuman id uniikki.
- Jos validoinnit menee läpi, TransactionService tallentaa uuden 'balancen' pelaajan tilille.

### Tietokanta

Pelaajataulu:

| Sarake       | Tyyppi           | Selite                                | Pakollinen (`NOT NULL`) |
| ------------ |------------------|---------------------------------------|-------------------------|
| `player_id`   | `VARCHAR(255)`   | Pelaajan yksilöllinen ID, PRIMARY_KEY | NOT NULL                |
| `player_name` | `VARCHAR(255)`   | Pelaajan nimi                         | NOT NULL                |
| `balance`    | `DECIMAL(19, 2)` | Pelaajan rahasaldo                    | NOT NULL                |

```sql
CREATE TABLE player (
    player_id VARCHAR(255) PRIMARY KEY NOT NULL,
    player_name VARCHAR(255) NOT NULL,
    balance DECIMAL(19, 2) NOT NULL
);
```

Tapahtumataulu:

| Sarake             | Tyyppi           | Selite                                  | Pakollinen (`NOT NULL`) |
|--------------------|------------------|-----------------------------------------| ----------------------- |
| `transaction_id`   | `VARCHAR(255)`   | Tapahtuman yksilöllinen ID, PRIMARY_KEY | NOT NULL                |
| `player_id`        | `VARCHAR(255)`   | Pelaajan yksilöllinen ID, FOREIGN_KEY  | NOT NULL                |
| `amount`           | `DECIMAL(19, 2)` | Tapahtuman summa                        | NOT NULL                |
| `transaction_type` | `VARCHAR(255)`   | PAY / WIN                               | NOT NULL                |
| `timestamp`        | `VARCHAR(255)`   | Aikaleima                               | NOT NULL                |

```sql
CREATE TABLE transaction (
    transaction_id VARCHAR(255) PRIMARY KEY NOT NULL,
    player_id VARCHAR(255) NOT NULL,
    amount DECIMAL(19, 2) NOT NULL,
    transaction_type VARCHAR(255) NOT NULL,
    timestamp VARCHAR(255) NOT NULL,
    FOREIGN KEY (playerId) REFERENCES player(playerId)
);
```

## Rajapinta
### Vaatimukset:

- Veloittaessasi peliä pelimoottori välittää pelitilille ostotapahtuman yksilöivän tunnisteen,
  pelaajan yksilöivän tunnisteen ja summan. Vastauksessa pelitili välittää pelimoottorille pelaajan
  pelitilin jäljellä olevan saldon.
- Mikäli peliin tulee voitto pelimoottori välittää pelitilille voittotapahtuman yksilöivän tunnisteen,
  pelaajan yksilöivän tunnisteen ja voittosumman. Vastauksessa pelitili välittää pelimoottorille
  pelaajan pelitilin uuden saldon.

### Toteutus:

Kopioi alla oleva .yaml Swagger editoriin:  [https://editor.swagger.io/](https://editor.swagger.io/), jos haluat nähdä kyseisen .yamlin editorissa

```yaml
openapi: 3.0.3
info:
  title: Koodaustehtävä API
  version: 1.0.0
  description: API maksujen ja voittojen käsittelyyn.

paths:
  /api/pay:
    post:
      summary: Käsittelee maksutapahtuman
      requestBody:
        required: true
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/RequestDto'
      responses:
        '200':
          description: Maksutapahtuma käsitelty onnistuneesti
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/CurrentBalance'
        '500':
          description: palauttaa virheen selitteen, aina 500 error codella..
          content:
            text/plain:
              schema:
                type: string

  /api/win:
    post:
      summary: Käsittelee voitonmaksun
      requestBody:
        required: true
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/RequestDto'
      responses:
        '200':
          description: Voitonmaksu käsitelty onnistuneesti
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/CurrentBalance'
        '500':
          description: palauttaa virheen selitteen, aina 500 error codella..
          content:
            text/plain:
              schema:
                type: string

components:
  schemas:
    RequestDto:
      type: object
      properties:
        transactionId:
          type: string
          example: "123"
        playerId:
          type: string
          example: "1"
        amount:
          type: number
          format: decimal
          example: 10.50
      required:
        - transactionId
        - playerId
        - amount

    CurrentBalance:
      type: object
      properties:
        currentBalance:
          type: number
          format: decimal
          example: 270.75
      required:
        - currentBalance


```

## Huomioitavaa

- Pelitili paluttaa vain, jos transaktio onnistuu. Error tilanteessa palautuu viesti rajapinnan kautta.
- p12 sertifikaatti on itsetehty generoimalla, virallinen sertifikaatti tulisi muualta ja säilyttäisin muualla.
- Virheenhallintaa voisi kehittää lisää, nyt alkeellinen. Eri exeptioneja eri tilanteisiin ja rajapinta voisi palauttaa muutakin kuin aina 500.
- Onko bigDecimal sopiva rahan käsittelyyn? En ole tästä varma.
- Testejä on nyt vain muutama liittyen TransactionServicen business logiikkaan liittyen.
- Robotilla olisi ollut hyvä pommittaa rajapintaa ja testata kestääkö se. Aikaa vain olisi mennyt itseltäni liikaa
- Suunnittelin myös dokkerointia ja tätä kautta Postgres SQL kannan käyttöönottoa.
- OpenApi speksin mukainen generaattori oli myös suunnitelmissa. :D
- Tähän olisi voinut käyttää paljon enemmänkin aikaa. Hyvä, että oli rajattu aika.