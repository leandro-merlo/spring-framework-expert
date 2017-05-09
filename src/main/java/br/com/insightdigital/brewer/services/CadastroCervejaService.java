package br.com.insightdigital.brewer.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.insightdigital.brewer.model.Cerveja;
import br.com.insightdigital.brewer.repository.Cervejas;
import br.com.insightdigital.brewer.services.event.cerveja.CervejaSalvaEvent;

@Service
public class CadastroCervejaService {

	@Autowired
	private Cervejas cervejas;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Transactional
	public void salvar(Cerveja cerveja){
		cervejas.save(cerveja);
		publisher.publishEvent(new CervejaSalvaEvent(cerveja));
	}
	
	@Transactional(readOnly = true)
	public Cerveja encontrar(Cerveja cerveja){
		Example<Cerveja> ex = Example.of(cerveja);
		return cervejas.findOne(ex);
	}
}
