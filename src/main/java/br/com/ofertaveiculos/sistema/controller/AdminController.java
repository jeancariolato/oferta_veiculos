package br.com.ofertaveiculos.sistema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import br.com.ofertaveiculos.sistema.model.Veiculo; // e outros
import ch.qos.logback.core.model.Model;

@Controller
@RequestMapping("/admin") // Todas as URLs aqui começarão com /admin
public class AdminController {

    // ... Injetar seus services/repositories

    // ----- GERENCIAMENTO DE VEÍCULOS -----
    @GetMapping("/veiculos")
    public String listarVeiculos(Model model) { /* ... */ return "admin/lista-veiculos"; }

    @GetMapping("/veiculos/novo")
    public String formNovoVeiculo(Model model) { /* ... */ return "admin/form-veiculo"; }

    @PostMapping("/veiculos/salvar")
    public String salvarVeiculo(@ModelAttribute Veiculo veiculo, @RequestParam("imagemFile") MultipartFile file) {
        // Lógica para salvar o veículo e a imagem (veja o Passo 8)
        return "redirect:/admin/veiculos";
    }

    // ... Métodos para editar e excluir veículos

    // ----- GERENCIAMENTO DE CATEGORIAS -----
    // ... Métodos GET e POST para listar, criar, editar e excluir categorias.
}