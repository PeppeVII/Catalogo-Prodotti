package dev.giuseppe.SpringMvc3.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.giuseppe.SpringMvc3.model.Ruolo;
import dev.giuseppe.SpringMvc3.model.Utente;
import dev.giuseppe.SpringMvc3.repository.RuoloRepository;
import dev.giuseppe.SpringMvc3.repository.UtenteRepository;

@Service("utenteService")
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private RuoloRepository ruoloRepository;

    public Utente salvaUtente(Utente utente) {
        return utenteRepository.save(utente);
    }

    public Utente trovaPerUsername(String username) {
        return utenteRepository.findByUsername(username);
    }

    public Utente trovaPerUsernameEPassword(String username, String password) {
        return utenteRepository.findByUsernameAndPassword(username, password);
    }

    public void createRolesIfNotPresent() {
        Ruolo admin = ruoloRepository.findByNome("admin");
        Ruolo utente = ruoloRepository.findByNome("utente");
        if(admin == null) {
            admin = new Ruolo("admin");
            ruoloRepository.save(admin);
        }
        if (utente == null) {
            utente = new Ruolo("utente");
            ruoloRepository.save(utente);
        }
    }

    public void createUsersIfNotPresent() {
        Utente utenteAdmin = trovaPerUsername("admin");
        Ruolo ruoloAdmin = ruoloRepository.findByNome("admin");
        if(utenteAdmin == null) {
            utenteAdmin = new Utente("admin", "admin_pass");
            utenteAdmin.setRuolo(ruoloAdmin);
            utenteRepository.save(utenteAdmin);
        }
    }
}