package dev.giuseppe.SpringMvc3.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.giuseppe.SpringMvc3.model.Prodotto;
import dev.giuseppe.SpringMvc3.repository.ProdottoRepository;
import jakarta.transaction.Transactional;

@Service
public class ProdottoService {

	@Autowired
    private ProdottoRepository prodottoRepository;

    public List<Prodotto> getAllProdotti() {
        return prodottoRepository.findAll();
    }

    public Prodotto trovaPerId(Integer id) {
        return prodottoRepository.findById(id).orElse(null);
    }

    public List<Prodotto> cercaProdotti(String nome) {
        return prodottoRepository.findByNomeContainingIgnoreCase(nome);
    }

    public List<Prodotto> findByPrezzoLessThan(Float prezzo) {
        return prodottoRepository.findByPrezzoLessThan(prezzo);
    }

    public List<Prodotto> findByNomeContainingAndPrezzoLessThan(String nome, Float prezzo) {
        return prodottoRepository.findByNomeContainingIgnoreCaseAndPrezzoLessThan(nome, prezzo);
    }

    public void salvaProdotto(Prodotto prodotto) {
        prodottoRepository.save(prodotto);
    }

    public void eliminaProdotto(Integer id) {
    	prodottoRepository.deleteById(id);
    }
    
    @Transactional
    public void deleteByPrezzoBetween(Float minPrezzo, Float maxPrezzo) {
        prodottoRepository.deleteByPrezzoBetween(minPrezzo, maxPrezzo);
    }
}