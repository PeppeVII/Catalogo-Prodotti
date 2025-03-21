package dev.giuseppe.SpringMvc3.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import dev.giuseppe.SpringMvc3.model.Ruolo;

public interface RuoloRepository extends JpaRepository<Ruolo, Long> {
    Ruolo findByNome(String nome);
}