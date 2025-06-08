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
        // 1. Verifica se um arquivo de imagem foi enviado
        if (imagemFile != null && !imagemFile.getOriginalFilename().isEmpty()) {
            // 2. Salva o arquivo no disco usando o FileStorageService
            String fileName = fileStorageService.storeFile(imagemFile);

            // 3. Cria a URL para acessar a imagem.
            // Precisaremos de um controlador para servir esses arquivos (ver nota abaixo).
            String fileDownloadUri = "/uploads/" + fileName;

            // 4. Atribui a URL da imagem ao veículo
            veiculo.setImagemUrl(fileDownloadUri);
        } else if (veiculo.getId() != null) {
            // Se for uma edição e nenhuma imagem nova foi enviada, mantém a imagem antiga.
            veiculoRepository.findById(veiculo.getId()).ifPresent(veiculoExistente -> {
                veiculo.setImagemUrl(veiculoExistente.getImagemUrl());
            });
        }

        // 5. Salva o veículo no banco de dados
        return veiculoRepository.save(veiculo);
    }

    public void deleteById(Long id) {
        // Opcional: Adicionar lógica para deletar o arquivo de imagem do disco
        veiculoRepository.deleteById(id);
    }
    
    // Você pode adicionar os outros métodos de busca aqui se quiser encapsular a lógica
    public List<Veiculo> findByCategoriaId(Long id) { return veiculoRepository.findByCategoriaId(id); }
    public List<Veiculo> findByModeloContainingIgnoreCase(String modelo) { return veiculoRepository.findByModeloContainingIgnoreCase(modelo); }
    public List<Veiculo> findByAno(int ano) { return veiculoRepository.findByAno(ano); }
}