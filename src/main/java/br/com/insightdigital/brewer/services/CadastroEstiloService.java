package br.com.insightdigital.brewer.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.insightdigital.brewer.model.Estilo;
import br.com.insightdigital.brewer.repository.Estilos;
import br.com.insightdigital.brewer.services.exception.NomeEstiloJaCadastradoException;

@Service
public class CadastroEstiloService {
	
	@Autowired
	private Estilos estilos;
	
	@Transactional
	public Estilo salvar(Estilo estilo) throws NomeEstiloJaCadastradoException {
		Optional<Estilo> optional = estilos.findByNomeIgnoreCase(estilo.getNome());
		if (optional.isPresent()){
			throw new NomeEstiloJaCadastradoException("Nome do estilo já cadastrado");
		}
		return estilos.saveAndFlush(estilo);			
	}

}
