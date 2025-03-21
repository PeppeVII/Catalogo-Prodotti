package dev.giuseppe.SpringMvc3.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import dev.giuseppe.SpringMvc3.model.Prodotto;
import dev.giuseppe.SpringMvc3.model.Utente;
import dev.giuseppe.SpringMvc3.service.ProdottoService;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@SessionAttributes("utenteLoggato")
	public class ProdottoController {

	@Autowired
    private ProdottoService prodottoService;

	private static final Logger LOGGER = Logger.getLogger(ProdottoController.class.getName());

	@GetMapping("/ricercaProdotti")
	public String mostraRicercaProdotti(Model model) {
	    Utente utenteLoggato = (Utente) model.getAttribute("utenteLoggato");
	    LOGGER.info("Utente loggato in /ricercaProdotti: " + (utenteLoggato != null ? utenteLoggato.getUsername() : "null"));
	    if (utenteLoggato == null || !utenteLoggato.isLogged()) {
	        LOGGER.warning("Reindirizzamento a /login: utente non loggato.");
	        return "redirect:/login";
	    }
	    model.addAttribute("prodottoRicerca", new Prodotto());
	    return "ricercaProdotti";
	}
    @GetMapping("/creaProdotto")
    public String mostraCreaProdotto(Model model) {
        Utente utenteLoggato = (Utente) model.getAttribute("utenteLoggato");
        if (utenteLoggato == null || !utenteLoggato.isLogged()) {
            return "redirect:/login";
        }
        if (!"admin".equals(utenteLoggato.getRuolo().getNome())) {
            model.addAttribute("messaggio", "Accesso negato: solo gli admin possono creare prodotti.");
            return "esito";
        }
        model.addAttribute("nuovoProdotto", new Prodotto());
        return "creaProdotto";
    }

    @PostMapping("/creaProdotto")
    public String creaProdotto(@ModelAttribute("nuovoProdotto") Prodotto prodotto, Model model) {
        Utente utenteLoggato = (Utente) model.getAttribute("utenteLoggato");
        if (utenteLoggato == null || !utenteLoggato.isLogged() || !"admin".equals(utenteLoggato.getRuolo().getNome())) {
            model.addAttribute("messaggio", "Accesso negato: solo gli admin possono creare prodotti.");
            return "esito";
        }
        try {
            prodottoService.salvaProdotto(prodotto);
            model.addAttribute("messaggio", "Prodotto creato con successo!");
        } catch (Exception e) {
            model.addAttribute("messaggio", "Errore durante la creazione del prodotto: " + e.getMessage());
        }
        return "esito";
    }

    @PostMapping("/ricercaProdotti")
    public String ricercaProdotti(@ModelAttribute("prodottoRicerca") Prodotto prodotto, Model model) {
        Utente utenteLoggato = (Utente) model.getAttribute("utenteLoggato");
        if (utenteLoggato == null || !utenteLoggato.isLogged()) {
            return "redirect:/login";
        }
        try {
            model.addAttribute("risultati", prodottoService.cercaProdotti(prodotto.getNome()));
            return "risultatiRicercaProdotti";
        } catch (Exception e) {
            model.addAttribute("messaggio", "Errore durante la ricerca dei prodotti: " + e.getMessage());
            return "esito";
        }
    }

    // Modifica completa
    @PostMapping("/totalUpdate")
    public String aggiornamentoTotale(HttpServletRequest request, Model model) {
        Utente utenteLoggato = (Utente) model.getAttribute("utenteLoggato");
        if (utenteLoggato == null || !utenteLoggato.isLogged() || !"admin".equals(utenteLoggato.getRuolo().getNome())) {
            model.addAttribute("messaggio", "Accesso negato: solo gli admin possono modificare prodotti.");
            return "esito";
        }
        try {
            Integer id = Integer.parseInt(request.getParameter("id"));
            String nome = request.getParameter("nome");
            String prezzoStr = request.getParameter("prezzo");

            // Validazione (adattata dal Validator del professore)
            if (nome == null || nome.trim().isEmpty() || prezzoStr == null || prezzoStr.trim().isEmpty()) {
                throw new IllegalArgumentException("Nome e prezzo sono obbligatori per la modifica completa.");
            }
            Float prezzo = Float.parseFloat(prezzoStr);

            Prodotto prodotto = prodottoService.trovaPerId(id);
            if (prodotto != null) {
                prodotto.setNome(nome);
                prodotto.setPrezzo(prezzo);
                prodottoService.salvaProdotto(prodotto);
                model.addAttribute("messaggio", "Prodotto modificato completamente con successo!");
            } else {
                model.addAttribute("messaggio", "Prodotto non trovato.");
            }
        } catch (IllegalArgumentException e) {
            model.addAttribute("messaggio", "Errore: " + e.getMessage());
        } catch (Exception e) {
            model.addAttribute("messaggio", "Errore interno durante la modifica completa: " + e.getMessage());
        }
        return "esito";
    }

    // Modifica parziale
    @PostMapping("/partialUpdate")
    public String aggiornamentoParziale(HttpServletRequest request, Model model) {
        Utente utenteLoggato = (Utente) model.getAttribute("utenteLoggato");
        if (utenteLoggato == null || !utenteLoggato.isLogged() || !"admin".equals(utenteLoggato.getRuolo().getNome())) {
            model.addAttribute("messaggio", "Accesso negato: solo gli admin possono modificare prodotti.");
            return "esito";
        }
        try {
            Integer id = Integer.parseInt(request.getParameter("id"));
            String nome = request.getParameter("nome");
            String prezzoStr = request.getParameter("prezzo");

            Prodotto prodotto = prodottoService.trovaPerId(id);
            if (prodotto != null) {
                if (nome != null && !nome.trim().isEmpty()) {
                    prodotto.setNome(nome);
                }
                if (prezzoStr != null && !prezzoStr.trim().isEmpty()) {
                    prodotto.setPrezzo(Float.parseFloat(prezzoStr));
                }
                prodottoService.salvaProdotto(prodotto);
                model.addAttribute("messaggio", "Prodotto modificato parzialmente con successo!");
            } else {
                model.addAttribute("messaggio", "Prodotto non trovato.");
            }
        } catch (Exception e) {
            model.addAttribute("messaggio", "Errore durante la modifica parziale: " + e.getMessage());
        }
        return "esito";
    }

    // Cancellazione
    @PostMapping("/delete")
    public String deleteProdotto(@RequestParam("id") Integer id, Model model) {
        Utente utenteLoggato = (Utente) model.getAttribute("utenteLoggato");
        LOGGER.info("Utente loggato in /delete: " + (utenteLoggato != null ? utenteLoggato.getUsername() : "null"));
        if (utenteLoggato == null || !utenteLoggato.isLogged() || !"admin".equals(utenteLoggato.getRuolo().getNome())) {
            LOGGER.warning("Accesso negato o utente non loggato in /delete.");
            model.addAttribute("messaggio", "Accesso negato: solo gli admin possono cancellare prodotti.");
            return "esito";
        }
        try {
            Prodotto prodotto = prodottoService.trovaPerId(id);
            if (prodotto != null) {
                prodottoService.eliminaProdotto(id);
                model.addAttribute("messaggio", "Prodotto cancellato con successo!");
            } else {
                model.addAttribute("messaggio", "Prodotto non trovato.");
            }
        } catch (Exception e) {
            model.addAttribute("messaggio", "Errore durante la cancellazione: " + e.getMessage());
        }
        return "esito";
    }
}