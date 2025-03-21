package dev.giuseppe.SpringMvc3.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.giuseppe.SpringMvc3.model.Ruolo;
import dev.giuseppe.SpringMvc3.repository.RuoloRepository;

@Service
public class RuoloService {
	
	 	@Autowired
	    private RuoloRepository ruoloRepository;

	    public Ruolo salvaRuolo(Ruolo ruolo) {
	        return ruoloRepository.save(ruolo);
	    }

	    public Ruolo trovaPerNome(String nome) {
	        return ruoloRepository.findByNome(nome);
	    }
}
