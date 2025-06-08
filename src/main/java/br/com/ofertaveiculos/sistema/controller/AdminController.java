package br.com.ofertaveiculos.sistema.controller;

import br.com.ofertaveiculos.sistema.model.Categoria;
import br.com.ofertaveiculos.sistema.model.Veiculo;
import br.com.ofertaveiculos.sistema.service.CategoriaService;
import br.com.ofertaveiculos.sistema.service.VeiculoService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final VeiculoService veiculoService;
    private final CategoriaService categoriaService;

    public AdminController(VeiculoService veiculoService, CategoriaService categoriaService) {
        this.veiculoService = veiculoService;
        this.categoriaService = categoriaService;
    }

    // ========== GERENCIAMENTO DE VEÍCULOS ==========

    @GetMapping("/veiculos")
    public String listarVeiculos(Model model) {
        model.addAttribute("veiculos", veiculoService.findAll());
        return "admin/lista-veiculos";
    }

    // ESTE É O MÉTODO QUE FALTAVA!
    @GetMapping("/veiculos/novo")
    public String formNovoVeiculo(Model model) {
        // Prepara um objeto Veiculo vazio para o formulário
        model.addAttribute("veiculo", new Veiculo());
        // Carrega a lista de categorias para o <select>
        model.addAttribute("categorias", categoriaService.findAll());
        return "admin/form-veiculo";
    }

    @GetMapping("/veiculos/editar/{id}")
    public String formEditarVeiculo(@PathVariable Long id, Model model) {
        veiculoService.findById(id).ifPresent(veiculo -> {
            model.addAttribute("veiculo", veiculo);
            model.addAttribute("categorias", categoriaService.findAll());
        });
        return "admin/form-veiculo";
    }

    @PostMapping("/veiculos/salvar")
    public String salvarVeiculo(@ModelAttribute Veiculo veiculo, @RequestParam("imagemFile") MultipartFile imagemFile, RedirectAttributes redirectAttributes) {
        try {
            veiculoService.save(veiculo, imagemFile);
            redirectAttributes.addFlashAttribute("successMessage", "Veículo salvo com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao salvar veículo: " + e.getMessage());
        }
        return "redirect:/admin/veiculos";
    }

    @PostMapping("/veiculos/excluir/{id}")
    public String excluirVeiculo(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            veiculoService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Veículo excluído com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao excluir veículo.");
        }
        return "redirect:/admin/veiculos";
    }

    // ========== GERENCIAMENTO DE CATEGORIAS ==========

  @GetMapping("/categorias")
    public String listarCategorias(Model model) {
        // O nome da variável aqui ("categoriasAdmin") deve ser o mesmo usado no th:each
        model.addAttribute("categoriasAdmin", categoriaService.findAll());
        return "admin/lista-categorias";
    }

    @GetMapping("/categorias/nova")
    public String formNovaCategoria(Model model) {
        model.addAttribute("categoria", new Categoria());
        return "admin/form-categoria";
    }

    @GetMapping("/categorias/editar/{id}")
    public String formEditarCategoria(@PathVariable Long id, Model model) {
        categoriaService.findById(id).ifPresent(categoria -> model.addAttribute("categoria", categoria));
        return "admin/form-categoria";
    }

    @PostMapping("/categorias/salvar")
    public String salvarCategoria(@ModelAttribute Categoria categoria, RedirectAttributes redirectAttributes) {
        categoriaService.save(categoria);
        redirectAttributes.addFlashAttribute("successMessage", "Categoria salva com sucesso!");
        return "redirect:/admin/categorias";
    }
}