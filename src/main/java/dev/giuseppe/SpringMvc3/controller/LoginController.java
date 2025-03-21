package dev.giuseppe.SpringMvc3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import dev.giuseppe.SpringMvc3.model.Utente;
import dev.giuseppe.SpringMvc3.service.RuoloService;
import dev.giuseppe.SpringMvc3.service.UtenteService;
import java.util.logging.Logger;

@Controller
@SessionAttributes("utenteLoggato")
public class LoginController {

    private static final Logger LOGGER = Logger.getLogger(LoginController.class.getName());

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private RuoloService ruoloService;

    @GetMapping("/login")
    public String mostraPaginaLogin(Model model) {
        model.addAttribute("utente", new Utente());
        return "paginaLogin";
    }

    @GetMapping("/registrazione")
    public String mostraPaginaRegistrazione(Model model) {
        model.addAttribute("utente", new Utente());
        return "paginaRegistrazione";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("utente") Utente utente, Model model) {
        LOGGER.info("Tentativo di login con username: " + utente.getUsername() + ", password: " + utente.getPassword());
        Utente utenteTrovato = utenteService.trovaPerUsernameEPassword(utente.getUsername(), utente.getPassword());
        if (utenteTrovato != null) {
            LOGGER.info("Utente trovato: " + utenteTrovato.getUsername());
            utenteTrovato.setLogged(true);
            utenteService.salvaUtente(utenteTrovato);
            model.addAttribute("utenteLoggato", utenteTrovato);
            LOGGER.info("Utente salvato nella sessione: " + utenteTrovato.getUsername());
            return "homeInterna";
        } else {
            LOGGER.warning("Login fallito: username o password errati per " + utente.getUsername());
            model.addAttribute("messaggio", "Login fallito: username o password errati.");
            return "paginaLogin";
        }
    }

    @GetMapping("/logout")
    public String logout(Model model, SessionStatus sessionStatus) {
        Utente utenteLoggato = (Utente) model.getAttribute("utenteLoggato");
        if (utenteLoggato != null) {
            utenteLoggato.setLogged(false);
            utenteService.salvaUtente(utenteLoggato);
            LOGGER.info("Logout eseguito per: " + utenteLoggato.getUsername());
        }
        sessionStatus.setComplete();
        return "redirect:/login";
    }

    @PostMapping("/registra")
    public String registra(@ModelAttribute("utente") Utente utente, Model model) {
        Utente esistente = utenteService.trovaPerUsername(utente.getUsername());
        if (esistente != null) {
            model.addAttribute("messaggio", "Errore: username già in uso.");
            return "paginaRegistrazione";
        }
        try {
            utente.setLogged(false);
            utente.setRuolo(ruoloService.trovaPerNome("utente"));
            utenteService.salvaUtente(utente);
            model.addAttribute("messaggio", "Registrazione avvenuta con successo! Effettua il login.");
            return "paginaLogin";
        } catch (Exception e) {
            model.addAttribute("messaggio", "Errore durante la registrazione: " + e.getMessage());
            return "paginaRegistrazione";
        }
    }

    @ModelAttribute("utenteLoggato")
    public Utente initUtenteLoggato() {
        return null;
    }
}