package br.com.ofertaveiculos.sistema.repository;

import br.com.ofertaveiculos.sistema.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
   
    List<Veiculo> findByCategoriaId(Long idCategoria);

   
    List<Veiculo> findByModeloContainingIgnoreCase(String modelo);

    
    List<Veiculo> findByAno(int ano);
}