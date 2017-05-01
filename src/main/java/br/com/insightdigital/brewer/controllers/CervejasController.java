package br.com.insightdigital.brewer.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.insightdigital.brewer.model.Cerveja;
import br.com.insightdigital.brewer.model.Origem;
import br.com.insightdigital.brewer.model.Sabor;
import br.com.insightdigital.brewer.repository.Cervejas;
import br.com.insightdigital.brewer.repository.Estilos;
import br.com.insightdigital.brewer.services.CadastroCervejaService;

@Controller
@RequestMapping("/cervejas")
public class CervejasController {


	@Autowired
	private Estilos estilos;
	
	@Autowired
	private Cervejas cervejas;
	
	@Autowired
	private CadastroCervejaService cadastroCervejaService;
	
	@RequestMapping("/novo")
	public ModelAndView novo(Cerveja cerveja){	
		ModelAndView model = new ModelAndView("cerveja/CadastroCerveja");
		model.addObject("sabores", Sabor.values());
		model.addObject("estilos", estilos.findAll());
		model.addObject("origens", Origem.values());
		return model;
	}
	
	@RequestMapping(value = "/novo", method = { RequestMethod.POST })	
	public ModelAndView cadastrar(@Valid Cerveja cerveja, BindingResult result, RedirectAttributes attributes){
		
		if (result.hasErrors()){
			return novo(cerveja);
		}
		
		cadastroCervejaService.salvar(cerveja);
		attributes.addFlashAttribute("mensagem", "Cerveja adicionada com sucesso");		
		return new ModelAndView("redirect:/cervejas/novo");		
	}
	
	@GetMapping
	public ModelAndView pesquisar(){
		ModelAndView model = new ModelAndView("cerveja/PesquisaCervejas");
		model.addObject("sabores", Sabor.values());
		model.addObject("estilos", estilos.findAll());
		model.addObject("origens", Origem.values());
		model.addObject("cervejas", cervejas.findAll());
		return model;
	}

}
