package br.com.insightdigital.brewer.services.event.cerveja;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import br.com.insightdigital.brewer.storage.FotoStorage;

@Component
public class CervejaListener {

	@Autowired
	private FotoStorage fotoStorage;
	
	@EventListener(condition = "#event.temFoto()")
	public void cervejaSalva(CervejaSalvaEvent event){
		System.out.println("Nova Cerveja salva - " + event.getCerveja().getNome());
		fotoStorage.salvar(event.getCerveja().getFoto());
	}
}
