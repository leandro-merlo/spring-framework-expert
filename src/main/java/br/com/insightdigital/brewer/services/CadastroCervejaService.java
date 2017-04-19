package br.com.insightdigital.brewer.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.insightdigital.brewer.model.Cerveja;
import br.com.insightdigital.brewer.repository.Cervejas;

@Service
public class CadastroCervejaService {

	@Autowired
	private Cervejas cervejas;
	
	@Transactional
	public void salvar(Cerveja cerveja){
		cervejas.save(cerveja);
	}
}
