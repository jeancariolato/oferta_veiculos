package br.com.ofertaveiculos.sistema.repository;

import br.com.ofertaveiculos.sistema.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
    // Busca veículos por ID da categoria
    List<Veiculo> findByCategoriaId(Long idCategoria);

    // Busca por modelo (ignorando maiúsculas/minúsculas)
    List<Veiculo> findByModeloContainingIgnoreCase(String modelo);

    // Busca por ano
    List<Veiculo> findByAno(int ano);
}