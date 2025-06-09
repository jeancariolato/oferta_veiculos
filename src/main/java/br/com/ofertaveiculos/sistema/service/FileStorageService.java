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

    public FileStorageService() {
       
        this.fileStorageLocation = Paths.get("uploads").toAbsolutePath().normalize();

        try {
            
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Não foi possível criar o diretório para armazenar os arquivos.", ex);
        }
    }

    /**
     *
     * @param file
     * @return
     */
    public String storeFile(MultipartFile file) {
        // Pega o nome original do arquivo
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            
            if (originalFileName.contains("..")) {
                throw new RuntimeException("Desculpe! O nome do arquivo contém uma sequência de caminho inválida " + originalFileName);
            }

            
            String fileExtension = "";
            try {
                fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            } catch (Exception e) {
                fileExtension = "";
            }
            String newFileName = UUID.randomUUID().toString() + fileExtension;


           
            Path targetLocation = this.fileStorageLocation.resolve(newFileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return newFileName;
        } catch (IOException ex) {
            throw new RuntimeException("Não foi possível armazenar o arquivo " + originalFileName + ". Por favor, tente novamente!", ex);
        }
    }
}