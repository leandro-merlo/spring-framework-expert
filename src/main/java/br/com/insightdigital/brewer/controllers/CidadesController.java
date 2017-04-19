package br.com.insightdigital.brewer.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.insightdigital.brewer.model.EstadoEnum;

@Controller
public class CidadesController {

	@RequestMapping("/cidades/novo")
	public String novo(Model model){
		model.addAttribute("estados", EstadoEnum.values());		
		return "cidades/CadastroCidade";
	}
}
