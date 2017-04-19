package br.com.insightdigital.brewer.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.insightdigital.brewer.model.Cerveja;
import br.com.insightdigital.brewer.model.Origem;
import br.com.insightdigital.brewer.model.Sabor;
import br.com.insightdigital.brewer.repository.Estilos;
import br.com.insightdigital.brewer.services.CadastroCervejaService;

@Controller
public class CervejasController {


	@Autowired
	private Estilos estilos;
	
	@Autowired
	private CadastroCervejaService cadastroCervejaService;
	
	@RequestMapping("/cervejas/novo")
	public ModelAndView novo(Cerveja cerveja){	
		ModelAndView model = new ModelAndView("cerveja/CadastroCerveja");
		model.addObject("sabores", Sabor.values());
		model.addObject("estilos", estilos.findAll());
		model.addObject("origens", Origem.values());
		return model;
	}
	
	@RequestMapping(value = "/cervejas/novo", method = { RequestMethod.POST })	
	public ModelAndView cadastrar(@Valid Cerveja cerveja, BindingResult result, RedirectAttributes attributes){
		
		if (result.hasErrors()){
			return novo(cerveja);
		}
		
		cadastroCervejaService.salvar(cerveja);
		attributes.addFlashAttribute("mensagem", "Cerveja adicionada com sucesso");		
		return new ModelAndView("redirect:/cervejas/novo");		
	}

}
