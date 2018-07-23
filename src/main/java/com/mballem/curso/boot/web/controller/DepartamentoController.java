package com.mballem.curso.boot.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mballem.curso.boot.domein.Departamento;
import com.mballem.curso.boot.service.DepartamentoService;

@Controller
@RequestMapping("/departamentos")
public class DepartamentoController {
	
	@Autowired
	private DepartamentoService service;
	
	@GetMapping("/cadastrar")
	public String cadastrar(Departamento departamento) {
		
		return "/departamento/cadastro";
	}
	
	@GetMapping("/listar")
	public String listar(ModelMap model) {
		model.addAttribute("departamentos", this.service.buscarTodos());
		return "/departamento/lista";
	}
	
	@PostMapping("/salvar")
	public String salvar(Departamento departamento, RedirectAttributes redirectAttributes) {
		service.salvar(departamento);
		redirectAttributes.addFlashAttribute("success", "Registro inserido com sucesso!");
		return "redirect:/departamentos/cadastrar";
	}
	
	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("departamento", this.service.buscarPorId(id));
		return "/departamento/cadastro";
	}
	
	@PostMapping("/editar")
	public String editar(Departamento departamento, RedirectAttributes redirectAttributes) {
		this.service.editar(departamento);
		redirectAttributes.addFlashAttribute("success", "Registro alterado com sucesso!");
		return "redirect:/departamentos/cadastrar";
	}
	
	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, ModelMap model) {
		if(this.service.departamentoTemCargos(id)) {
			model.addAttribute("fail", "Registro não removido. Posui cargo(s) vinculado(s)!");			
		}else {
			this.service.excluir(id);
			model.addAttribute("success", "Registro removido com sucesso!");
		}
		return listar(model);
	}
}
