package br.com.insightdigital.brewer.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.insightdigital.brewer.controllers.page.PageWrapper;
import br.com.insightdigital.brewer.model.Cerveja;
import br.com.insightdigital.brewer.model.Origem;
import br.com.insightdigital.brewer.model.Sabor;
import br.com.insightdigital.brewer.repository.Cervejas;
import br.com.insightdigital.brewer.repository.Estilos;
import br.com.insightdigital.brewer.repository.filter.CervejaFilter;
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
	
	@GetMapping("/editar/{id}")
	public ModelAndView editar(Cerveja cerveja, BindingResult result){
		if (result == null || !result.hasErrors()){
			cerveja = cadastroCervejaService.encontrar(cerveja);
		}
		ModelAndView model = new ModelAndView("cerveja/CadastroCerveja");
		model.addObject("cerveja", cerveja);
		buildCombos(model);
		return model;
	}
	
	@PostMapping(value = "/editar/{id}")	
	public ModelAndView alterar(@Valid Cerveja cerveja, BindingResult result, RedirectAttributes attributes){
		
		if (result.hasErrors()){
			return editar(cerveja, result);
		}
		
		cadastroCervejaService.salvar(cerveja);
		attributes.addFlashAttribute("mensagem", "Cerveja adicionada com sucesso");		
		return new ModelAndView("redirect:/cervejas/novo");		
	}
	
	
	@GetMapping("/novo")
	public ModelAndView novo(Cerveja cerveja){	
		ModelAndView model = new ModelAndView("cerveja/CadastroCerveja");
		buildCombos(model);		
		return model;
	}
	
	@PostMapping(value = "/novo")	
	public ModelAndView cadastrar(@Valid Cerveja cerveja, BindingResult result, RedirectAttributes attributes){
		
		if (result.hasErrors()){
			return novo(cerveja);
		}
		
		cadastroCervejaService.salvar(cerveja);
		attributes.addFlashAttribute("mensagem", "Cerveja adicionada com sucesso");		
		return new ModelAndView("redirect:/cervejas/novo");		
	}
	
	@GetMapping
	public ModelAndView pesquisar(CervejaFilter cervejaFilter, BindingResult result, 
			@PageableDefault(size = 2) Pageable pageable, HttpServletRequest request){
		ModelAndView model = new ModelAndView("cerveja/PesquisaCervejas");
		buildCombos(model);
		PageWrapper<Cerveja> pageWrapper = new PageWrapper<>(cervejas.filtrar(cervejaFilter, pageable), request);
		model.addObject("pagina", pageWrapper);
		return model;
	}
	
	private void buildCombos(ModelAndView model) {
		model.addObject("sabores", Sabor.values());
		model.addObject("estilos", estilos.findAll());
		model.addObject("origens", Origem.values());		
	}

}
