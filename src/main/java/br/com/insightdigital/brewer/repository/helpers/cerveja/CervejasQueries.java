package br.com.insightdigital.brewer.repository.helpers.cerveja;

import java.util.List;

import br.com.insightdigital.brewer.model.Cerveja;
import br.com.insightdigital.brewer.repository.filter.CervejaFilter;

public interface CervejasQueries {

	public List<Cerveja> filtrar(CervejaFilter cervejaFilter);
}
