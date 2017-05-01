package br.com.insightdigital.brewer.services.event.cerveja;

import org.springframework.util.StringUtils;

import br.com.insightdigital.brewer.model.Cerveja;

public class CervejaSalvaEvent {

	private Cerveja cerveja;

	public CervejaSalvaEvent(Cerveja cerveja) {
		super();
		this.cerveja = cerveja;
	}

	public Cerveja getCerveja() {
		return cerveja;
	}

	public void setCerveja(Cerveja cerveja) {
		this.cerveja = cerveja;
	}
	
	public boolean temFoto(){
		return !StringUtils.isEmpty(this.cerveja.getFoto());
	}
	
	
}
