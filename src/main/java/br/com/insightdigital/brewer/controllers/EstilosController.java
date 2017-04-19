package br.com.insightdigital.brewer.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.insightdigital.brewer.model.Estilo;
import br.com.insightdigital.brewer.services.CadastroEstiloService;
import br.com.insightdigital.brewer.services.exception.NomeEstiloJaCadastradoException;

@Controller
public class EstilosController {

	@Autowired
	private CadastroEstiloService estiloService;
	
	@RequestMapping("/estilos/novo")
	public ModelAndView novo(Estilo estilo){
		ModelAndView mv = new ModelAndView("estilo/CadastroEstilo");
		return mv;
	}
	
	@RequestMapping(value="/estilos/novo", method={RequestMethod.POST})
	public ModelAndView salvar(@Valid Estilo estilo, BindingResult result, RedirectAttributes attributes){
		if (result.hasErrors()){
			return novo(estilo);
		}
		try{
			estiloService.salvar(estilo);						
		} catch (NomeEstiloJaCadastradoException e) {
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			return novo(estilo);
		}
		attributes.addFlashAttribute("mensagem", "Estilo adicionado com sucesso");		
		ModelAndView mv = new ModelAndView("redirect:/estilos/novo");
		return mv;
	}
	
	@RequestMapping(value="/estilos", 
		method={
			RequestMethod.POST
		}, 
		consumes = { 
			MediaType.APPLICATION_JSON_VALUE 
		}
	)
	public @ResponseBody ResponseEntity<?> salvar(@RequestBody @Valid Estilo estilo, BindingResult result){
		if (result.hasErrors()){
			return ResponseEntity.badRequest().body(result.getFieldError("nome").getDefaultMessage());
		}
		
		try{
			estilo = estiloService.salvar(estilo);						
		} catch (NomeEstiloJaCadastradoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
		return ResponseEntity.ok(estilo);
	}
}
