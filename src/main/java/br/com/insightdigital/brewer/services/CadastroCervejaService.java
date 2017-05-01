package br.com.insightdigital.brewer.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

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
}
