package dev.giuseppe.SpringMvc3.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.giuseppe.SpringMvc3.model.Prodotto;
import dev.giuseppe.SpringMvc3.repository.ProdottoRepository;

@Service
public class ProdottoService {

	@Autowired
    private ProdottoRepository prodottoRepository;

    public Prodotto salvaProdotto(Prodotto prodotto) {
        return prodottoRepository.save(prodotto);
    }

    public List<Prodotto> cercaProdotti(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return prodottoRepository.findAll();
        }
        return prodottoRepository.findByNomeContainingIgnoreCase(nome);
    }

    public Prodotto trovaPerId(Integer id) {
        return prodottoRepository.findById(id).orElse(null);
    }

    public void eliminaProdotto(Integer id) {
        prodottoRepository.deleteById(id);
    }
}