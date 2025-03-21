package dev.giuseppe.SpringMvc3.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.giuseppe.SpringMvc3.model.Prodotto;

public interface ProdottoRepository extends JpaRepository<Prodotto, Integer>{
	List<Prodotto> findByNomeContainingIgnoreCase(String nome);
}
