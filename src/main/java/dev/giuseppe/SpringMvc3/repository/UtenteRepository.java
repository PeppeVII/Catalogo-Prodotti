package dev.giuseppe.SpringMvc3.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.giuseppe.SpringMvc3.model.Utente;

public interface UtenteRepository extends JpaRepository<Utente, Integer> {
	Utente findByUsername(String username);
	Utente findByUsernameAndPassword(String username, String password);

}
