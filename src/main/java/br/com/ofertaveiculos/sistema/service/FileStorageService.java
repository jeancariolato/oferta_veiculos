package br.com.ofertaveiculos.sistema.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    // Construtor que define o diretório de upload.
    // O ideal é configurar este caminho no 'application.properties'.
    public FileStorageService() {
        // Define o diretório de uploads. Cuidado com o caminho em diferentes SO.
        // Este caminho é relativo à raiz do projeto.
        this.fileStorageLocation = Paths.get("uploads").toAbsolutePath().normalize();

        try {
            // Cria o diretório se ele não existir
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Não foi possível criar o diretório para armazenar os arquivos.", ex);
        }
    }

    /**
     * Armazena o arquivo no disco.
     * @param file O arquivo enviado na requisição (MultipartFile).
     * @return O nome do arquivo salvo.
     */
    public String storeFile(MultipartFile file) {
        // Pega o nome original do arquivo
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Validações básicas do nome do arquivo
            if (originalFileName.contains("..")) {
                throw new RuntimeException("Desculpe! O nome do arquivo contém uma sequência de caminho inválida " + originalFileName);
            }

            // Gera um nome de arquivo único para evitar conflitos
            String fileExtension = "";
            try {
                fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            } catch (Exception e) {
                fileExtension = "";
            }
            String newFileName = UUID.randomUUID().toString() + fileExtension;


            // Copia o arquivo para o local de destino (sobrescrevendo se já existir)
            Path targetLocation = this.fileStorageLocation.resolve(newFileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return newFileName;
        } catch (IOException ex) {
            throw new RuntimeException("Não foi possível armazenar o arquivo " + originalFileName + ". Por favor, tente novamente!", ex);
        }
    }
}