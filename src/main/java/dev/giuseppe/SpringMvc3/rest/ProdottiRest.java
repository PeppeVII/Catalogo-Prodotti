package dev.giuseppe.SpringMvc3.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import dev.giuseppe.SpringMvc3.model.Prodotto;
import dev.giuseppe.SpringMvc3.model.Utente;
import dev.giuseppe.SpringMvc3.service.ProdottoService;
import dev.giuseppe.SpringMvc3.service.UtenteService;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/rest/prodotti", produces = "application/json")
@CrossOrigin(origins = "*")
public class ProdottiRest {

    @Autowired
    private ProdottoService prodottoService;

    @Autowired
    private UtenteService utenteService;

    private static final Logger LOGGER = Logger.getLogger(ProdottiRest.class.getName());

    // Metodo per verificare le credenziali
    private void checkUtente(HttpHeaders httpHeaders, boolean admin) throws Exception {
        String username = httpHeaders.getFirst("username");
        String password = httpHeaders.getFirst("password");
        LOGGER.info("Verifica credenziali: username=" + username + ", password=" + password);

        // 400 Bad Request: Credenziali mancanti
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            throw new Exception("Credenziali mancanti negli header (username/password)");
        }

        // Cerca l'utente nel database
        Utente utente = utenteService.trovaPerUsernameEPassword(username, password);
        LOGGER.info("Utente trovato: " + (utente != null));

        // 401 Unauthorized: Utente non presente nel DB
        if (utente == null) {
            throw new Exception("Utente non trovato nel database");
        }

        // Per le operazioni di scrittura (POST, PUT, PATCH, DELETE), controlla il ruolo admin
        if (admin) {
            if (!"admin".equals(utente.getRuolo().getNome())) {
                LOGGER.warning("Accesso negato: utente non admin");
                throw new Exception("Accesso negato: solo gli admin possono eseguire operazioni di scrittura");
            }
        }
    }

    // Visualizzazione di tutti i prodotti
    @GetMapping("/getall")
    public ResponseEntity<?> getAllProdotti(@RequestHeader HttpHeaders httpHeaders) {
        try {
            checkUtente(httpHeaders, false);
            List<Prodotto> prodotti = prodottoService.getAllProdotti();
            HttpStatus httpStatus = prodotti != null && !prodotti.isEmpty() ? HttpStatus.OK : HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(prodotti, httpStatus);
        } catch (Exception e) {
            String message = e.getMessage();
            if (message.contains("Credenziali mancanti")) {
                return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
            } else if (message.contains("Utente non trovato")) {
                return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
            } else if (message.contains("Accesso negato")) {
                return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
            }
            LOGGER.severe("Errore interno: " + e.getMessage());
            return new ResponseEntity<>("Errore interno del server", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Visualizzazione di un prodotto per ID
    @GetMapping("/getById/{id}")
    public ResponseEntity<?> getProdottoById(@PathVariable Integer id, @RequestHeader HttpHeaders httpHeaders) {
        try {
            checkUtente(httpHeaders, false);
            Prodotto prodotto = prodottoService.trovaPerId(id);
            HttpStatus httpStatus = prodotto != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(prodotto, httpStatus);
        } catch (Exception e) {
            String message = e.getMessage();
            if (message.contains("Credenziali mancanti")) {
                return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
            } else if (message.contains("Utente non trovato")) {
                return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
            } else if (message.contains("Accesso negato")) {
                return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
            }
            return new ResponseEntity<>("Errore interno del server", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Visualizzazione di prodotti per nome (contiene stringa)
    @GetMapping("/cerca/{nome}")
    public ResponseEntity<?> getProdottiByNome(@PathVariable String nome, @RequestHeader HttpHeaders httpHeaders) {
        try {
            checkUtente(httpHeaders, false);
            List<Prodotto> prodotti = prodottoService.cercaProdotti(nome);
            HttpStatus httpStatus = prodotti != null && !prodotti.isEmpty() ? HttpStatus.OK : HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(prodotti, httpStatus);
        } catch (Exception e) {
            String message = e.getMessage();
            if (message.contains("Credenziali mancanti")) {
                return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
            } else if (message.contains("Utente non trovato")) {
                return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
            } else if (message.contains("Accesso negato")) {
                return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
            }
            return new ResponseEntity<>("Errore interno del server", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Visualizzazione di prodotti con prezzo minore di un valore
    @GetMapping("/cerca/prezzo/minore/{prezzo}")
    public ResponseEntity<?> getProdottiByPrezzoMinore(@PathVariable Float prezzo, @RequestHeader HttpHeaders httpHeaders) {
        try {
            checkUtente(httpHeaders, false);
            List<Prodotto> prodotti = prodottoService.findByPrezzoLessThan(prezzo);
            HttpStatus httpStatus = prodotti != null && !prodotti.isEmpty() ? HttpStatus.OK : HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(prodotti, httpStatus);
        } catch (Exception e) {
            String message = e.getMessage();
            if (message.contains("Credenziali mancanti")) {
                return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
            } else if (message.contains("Utente non trovato")) {
                return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
            } else if (message.contains("Accesso negato")) {
                return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
            }
            return new ResponseEntity<>("Errore interno del server", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Visualizzazione di prodotti per nome e prezzo minore
    @GetMapping("/cerca/{nome}/{prezzo}")
    public ResponseEntity<?> getProdottiByNomeAndPrezzoMinore(
            @PathVariable String nome, @PathVariable Float prezzo, @RequestHeader HttpHeaders httpHeaders) {
        try {
            checkUtente(httpHeaders, false);
            List<Prodotto> prodotti = prodottoService.findByNomeContainingAndPrezzoLessThan(nome, prezzo);
            HttpStatus httpStatus = prodotti != null && !prodotti.isEmpty() ? HttpStatus.OK : HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(prodotti, httpStatus);
        } catch (Exception e) {
            String message = e.getMessage();
            if (message.contains("Credenziali mancanti")) {
                return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
            } else if (message.contains("Utente non trovato")) {
                return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
            } else if (message.contains("Accesso negato")) {
                return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
            }
            return new ResponseEntity<>("Errore interno del server", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Salvataggio di un nuovo prodotto
    @PostMapping(path = "/inserisci", consumes ="application/json")
    public ResponseEntity<?> creaProdotto(@RequestBody Prodotto prodotto, @RequestHeader HttpHeaders httpHeaders) {
        try {
            checkUtente(httpHeaders, true);
            prodottoService.salvaProdotto(prodotto);
            return new ResponseEntity<>(prodotto, HttpStatus.CREATED);
        } catch (Exception e) {
            String message = e.getMessage();
            if (message.contains("Credenziali mancanti")) {
                return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
            } else if (message.contains("Utente non trovato")) {
                return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
            } else if (message.contains("Accesso negato")) {
                return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
            }
            return new ResponseEntity<>("Errore interno del server", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Aggiornamento completo di un prodotto
    @PutMapping(path = "/aggiornamentoTotale", consumes ="application/json")
    public ResponseEntity<?> aggiornamentoTotale(
            @RequestBody Prodotto prodotto, @RequestHeader HttpHeaders httpHeaders) {
        try {
            checkUtente(httpHeaders, true);
            Prodotto esistente = prodottoService.trovaPerId(prodotto.getId());
            if (esistente == null) {
                return ResponseEntity.notFound().build();
            }
            esistente.setNome(prodotto.getNome());
            esistente.setPrezzo(prodotto.getPrezzo());
            prodottoService.salvaProdotto(esistente);
            return new ResponseEntity<>(esistente, HttpStatus.OK);
        } catch (Exception e) {
            String message = e.getMessage();
            if (message.contains("Credenziali mancanti")) {
                return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
            } else if (message.contains("Utente non trovato")) {
                return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
            } else if (message.contains("Accesso negato")) {
                return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
            }
            return new ResponseEntity<>("Errore interno del server", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Aggiornamento parziale di un prodotto
    @PatchMapping(path = "/aggiornamentoParziale", consumes ="application/json")
    public ResponseEntity<?> aggiornamentoParziale(
            @RequestBody Prodotto prodotto, @RequestHeader HttpHeaders httpHeaders) {
        try {
            checkUtente(httpHeaders, true);
            Prodotto esistente = prodottoService.trovaPerId(prodotto.getId());
            if (esistente == null) {
                return ResponseEntity.notFound().build();
            }
            if (prodotto.getNome() != null && !prodotto.getNome().trim().isEmpty()) {
                esistente.setNome(prodotto.getNome());
            }
            if (prodotto.getPrezzo() != null) {
                esistente.setPrezzo(prodotto.getPrezzo());
            }
            prodottoService.salvaProdotto(esistente);
            return new ResponseEntity<>(esistente, HttpStatus.OK);
        } catch (Exception e) {
            String message = e.getMessage();
            if (message.contains("Credenziali mancanti")) {
                return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
            } else if (message.contains("Utente non trovato")) {
                return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
            } else if (message.contains("Accesso negato")) {
                return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
            }
            return new ResponseEntity<>("Errore interno del server", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Cancellazione di un prodotto per ID
    @DeleteMapping("/cancella/{id}")
    public ResponseEntity<?> deleteProdotto(@PathVariable Integer id, @RequestHeader HttpHeaders httpHeaders) {
        try {
            checkUtente(httpHeaders, true);
            Prodotto esistente = prodottoService.trovaPerId(id);
            if (esistente == null) {
                return ResponseEntity.notFound().build();
            }
            prodottoService.eliminaProdotto(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            String message = e.getMessage();
            if (message.contains("Credenziali mancanti")) {
                return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
            } else if (message.contains("Utente non trovato")) {
                return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
            } else if (message.contains("Accesso negato")) {
                return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
            }
            return new ResponseEntity<>("Errore interno del server", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Cancellazione di prodotti con prezzo compreso tra due valori
    @DeleteMapping("/prezzo/range/{minPrezzo}/{maxPrezzo}")
    public ResponseEntity<?> deleteProdottiByPrezzoRange(
            @PathVariable Float minPrezzo, @PathVariable Float maxPrezzo, @RequestHeader HttpHeaders httpHeaders) {
        try {
            checkUtente(httpHeaders, true);
            prodottoService.deleteByPrezzoBetween(minPrezzo, maxPrezzo);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
        	e.printStackTrace();
            String message = e.getMessage();
            if (message.contains("Credenziali mancanti")) {
                return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
            } else if (message.contains("Utente non trovato")) {
                return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
            } else if (message.contains("Accesso negato")) {
                return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
            }
            return new ResponseEntity<>("Errore interno del server", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}