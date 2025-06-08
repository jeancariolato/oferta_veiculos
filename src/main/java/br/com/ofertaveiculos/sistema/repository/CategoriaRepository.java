package br.com.ofertaveiculos.sistema.repository;

import br.com.ofertaveiculos.sistema.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}