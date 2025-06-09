package br.com.ofertaveiculos.sistema.service;

import br.com.ofertaveiculos.sistema.model.Veiculo;
import br.com.ofertaveiculos.sistema.repository.VeiculoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Optional;

@Service
public class VeiculoService {

    private final VeiculoRepository veiculoRepository;
    private final FileStorageService fileStorageService;

    public VeiculoService(VeiculoRepository veiculoRepository, FileStorageService fileStorageService) {
        this.veiculoRepository = veiculoRepository;
        this.fileStorageService = fileStorageService;
    }

    public List<Veiculo> findAll() {
        return veiculoRepository.findAll();
    }

    public Optional<Veiculo> findById(Long id) {
        return veiculoRepository.findById(id);
    }

    public Veiculo save(Veiculo veiculo, MultipartFile imagemFile) {
    
        if (imagemFile != null && !imagemFile.getOriginalFilename().isEmpty()) {
           
            String fileName = fileStorageService.storeFile(imagemFile);

        
            String fileDownloadUri = "/uploads/" + fileName;

            
            veiculo.setImagemUrl(fileDownloadUri);
        } else if (veiculo.getId() != null) {
           
            veiculoRepository.findById(veiculo.getId()).ifPresent(veiculoExistente -> {
                veiculo.setImagemUrl(veiculoExistente.getImagemUrl());
            });
        }

   
        return veiculoRepository.save(veiculo);
    }

    public void deleteById(Long id) {
       
        veiculoRepository.deleteById(id);
    }
    
   
    public List<Veiculo> findByCategoriaId(Long id) { return veiculoRepository.findByCategoriaId(id); }
    public List<Veiculo> findByModeloContainingIgnoreCase(String modelo) { return veiculoRepository.findByModeloContainingIgnoreCase(modelo); }
    public List<Veiculo> findByAno(int ano) { return veiculoRepository.findByAno(ano); }
}