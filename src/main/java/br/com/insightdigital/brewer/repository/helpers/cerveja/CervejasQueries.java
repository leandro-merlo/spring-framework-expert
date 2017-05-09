package br.com.insightdigital.brewer.repository.helpers.cerveja;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.insightdigital.brewer.model.Cerveja;
import br.com.insightdigital.brewer.repository.filter.CervejaFilter;

public interface CervejasQueries {

	public Page<Cerveja> filtrar(CervejaFilter cervejaFilter);
	public Page<Cerveja> filtrar(CervejaFilter cervejaFilter, Pageable pageable);
}
