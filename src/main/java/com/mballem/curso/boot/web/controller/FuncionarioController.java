package com.mballem.curso.boot.web.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mballem.curso.boot.domein.Cargo;
import com.mballem.curso.boot.domein.Funcionario;
import com.mballem.curso.boot.domein.UF;
import com.mballem.curso.boot.service.CargoService;
import com.mballem.curso.boot.service.FuncionarioService;

@Controller
@RequestMapping("/funcionarios")
public class FuncionarioController {
	
	@Autowired
	private FuncionarioService funcionarioService; 
	
	@Autowired
	private CargoService cargoService; 
	
	@GetMapping("/cadastrar")
	public String cadastrar(Funcionario funcionario) {
		
		return "/funcionario/cadastro";
	}
	
	@GetMapping("/listar")
	public String listar(ModelMap model) {
		model.addAttribute("funcionarios", this.funcionarioService.buscarTodos());		
		return "/funcionario/lista";
	}
	
	@PostMapping("/salvar")
	public String salvar(Funcionario funcionario, RedirectAttributes redirectAttributes) {
		this.funcionarioService.salvar(funcionario);		
		redirectAttributes.addFlashAttribute("success", "Registro inserido com sucesso!");
		return "redirect:/funcionarios/cadastrar";
	}
	
	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("funcionario", this.funcionarioService.buscarPorId(id));
		return "/funcionario/cadastro";
	}
	
	@PostMapping("/editar")
	public String editar(Funcionario funcionario, RedirectAttributes redirectAttributes) {
		this.funcionarioService.editar(funcionario);
		redirectAttributes.addFlashAttribute("success", "Registro alterado com sucesso!");
		return "redirect:/funcionarios/cadastrar";
	}
	
	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		this.funcionarioService.excluir(id);
		redirectAttributes.addFlashAttribute("success", "Registro excluido com sucesso!");
		return "redirect:/funcionarios/listar";
	}
	
	@GetMapping("/buscar/nome")
	public String buscarPorNome(@RequestParam("nome") String nome,  ModelMap model) {
		model.addAttribute("funcionarios", this.funcionarioService.buscarPorNome(nome));
		return "/funcionario/lista";
	}
	
	@GetMapping("/buscar/cargo")
	public String buscarPorCargo(@RequestParam("id") Long id,  ModelMap model) {
		model.addAttribute("funcionarios", this.funcionarioService.buscarPorCargo(id));
		return "/funcionario/lista";
	}
	
	@GetMapping("/buscar/data")
	public String buscarPorDatas(@RequestParam("entrada")
													 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
													 LocalDate entrada,
													 
													 @RequestParam("saida")
													 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
													 LocalDate saida, ModelMap model) {
		
		model.addAttribute("funcionarios", this.funcionarioService.buscarPorDatas(entrada, saida));
		return "/funcionario/lista";
	}
	
	@ModelAttribute("cargos")
	public List<Cargo> listaDeCargos(){
		return cargoService.buscarTodos();
	}
	
	@ModelAttribute("ufs")
	public UF[] getUfs() {
		return UF.values();
	}
	
}
