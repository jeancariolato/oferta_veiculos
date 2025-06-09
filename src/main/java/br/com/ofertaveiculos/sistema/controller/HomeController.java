package br.com.ofertaveiculos.sistema.controller;

import br.com.ofertaveiculos.sistema.repository.CategoriaRepository;
import br.com.ofertaveiculos.sistema.repository.VeiculoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {

    private final VeiculoRepository veiculoRepository;
    private final CategoriaRepository categoriaRepository;

    // Injeção de dependências
    public HomeController(VeiculoRepository veiculoRepository, CategoriaRepository categoriaRepository) {
        this.veiculoRepository = veiculoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    // Método para adicionar categorias em todas as páginas
    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("categorias", categoriaRepository.findAll());
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("veiculos", veiculoRepository.findAll());
        return "index"; // -> Retorna /resources/templates/index.html
    }

    @GetMapping("/categoria/{id}")
    public String veiculosPorCategoria(@PathVariable Long id, Model model) {
        model.addAttribute("veiculos", veiculoRepository.findByCategoriaId(id));
        return "index"; // Reutiliza a mesma página para mostrar a lista filtrada
    }

    @GetMapping("/veiculo/{id}")
    public String detalheVeiculo(@PathVariable Long id, Model model) {
        model.addAttribute("veiculo", veiculoRepository.findById(id).orElse(null));
        return "detalhe-veiculo"; // -> Retorna /resources/templates/detalhe-veiculo.html
    }

    @GetMapping("/pesquisar")
    public String pesquisar(@RequestParam(required = false) String modelo, @RequestParam(required = false) Integer ano, Model model) {
        if (modelo != null && !modelo.isEmpty()) {
            model.addAttribute("veiculos", veiculoRepository.findByModeloContainingIgnoreCase(modelo));
        } else if (ano != null) {
            model.addAttribute("veiculos", veiculoRepository.findByAno(ano));
        } else {
            return "redirect:/";
        }
        return "index";
    }



     @GetMapping("/login")
    public String login() {
        // Este método apenas retorna o nome do arquivo HTML da página de login
        // que vamos criar a seguir (sem o .html)
        return "login";
    }
}