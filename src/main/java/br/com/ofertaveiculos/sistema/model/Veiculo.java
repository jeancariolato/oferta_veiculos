package br.com.ofertaveiculos.sistema.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "veiculos")
@Getter
@Setter
public class Veiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String placa;

    private String cor;
    private String modelo;
    private String marca;
    private int ano; 

    @Column(name = "imagem_url")
    private String imagemUrl; 

    @ManyToOne 
    @JoinColumn(name = "id_categoria", nullable = false)
    private Categoria categoria;
}