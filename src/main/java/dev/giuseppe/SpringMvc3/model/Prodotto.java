package dev.giuseppe.SpringMvc3.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Prodotto {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;
    private Float prezzo;

    // Costruttore vuoto
    public Prodotto() {
    }

    // Costruttore con parametri
    public Prodotto(String nome, Float prezzo) {
        this.nome = nome;
        this.prezzo = prezzo;
    }

    // Getter e Setter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Float getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(Float prezzo) {
        this.prezzo = prezzo;
    }

    @Override
    public String toString() {
        return "Prodotto [id=" + id + ", nome=" + nome + ", prezzo=" + prezzo + "]";
    }
}

