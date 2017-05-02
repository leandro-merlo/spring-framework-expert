package br.com.insightdigital.brewer.repository.helpers.cerveja;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.com.insightdigital.brewer.model.Cerveja;
import br.com.insightdigital.brewer.model.Estilo;
import br.com.insightdigital.brewer.repository.filter.CervejaFilter;

public class CervejasImpl implements CervejasQueries {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional(readOnly = true)
	@Override
	public List<Cerveja> filtrar(CervejaFilter cervejaFilter) {
		/*
		 * Obtém a sessão do Hibernate
		 * Cria o Construtor de Critérios e o Critério em si.
		 * O objeto root é a representação dos objetos a serem pesquisados.
		 * Assim, é utilizado nos Predicates (cláusulas da pesquisa).
		 * Também, no root é configurado o parâmetro fetch para o nome de um atributo,
		 * tornando-o EAGER
		 */
		Session s = em.unwrap(Session.class);
		CriteriaBuilder builder = s.getCriteriaBuilder();
		CriteriaQuery<Cerveja> criteria = builder.createQuery(Cerveja.class);
		Root<Cerveja> root = criteria.from(Cerveja.class);
		root.fetch("estilo");
		ArrayList<Predicate> restrictions = new ArrayList<>();
		if (cervejaFilter != null){
			if (!StringUtils.isEmpty(cervejaFilter.getSku())){
				restrictions.add(builder.equal(root.get("sku"), cervejaFilter.getSku()));
			}
			if (!StringUtils.isEmpty(cervejaFilter.getNome())){
				restrictions.add(builder.like(builder.lower(root.get("nome")), "%" + cervejaFilter.getNome().toLowerCase() + "%"));
			}
			if (isEstiloPresent(cervejaFilter)){
				restrictions.add(builder.equal(root.get("estilo"), cervejaFilter.getEstilo()));
			}
			if (cervejaFilter.getSabor() != null){
				restrictions.add(builder.equal(root.get("sabor"), cervejaFilter.getSabor()));
			}
			if (cervejaFilter.getOrigem() != null){
				restrictions.add(builder.equal(root.get("origem"), cervejaFilter.getOrigem()));
			}
			if (cervejaFilter.getPrecoDe() != null){
				restrictions.add(builder.ge(root.get("valor"), cervejaFilter.getPrecoDe()));
			}
			if (cervejaFilter.getPrecoAte() != null){
				restrictions.add(builder.le(root.get("valor"), cervejaFilter.getPrecoAte()));
			}
			if (!restrictions.isEmpty()){
				Predicate[] temp = new Predicate[restrictions.size()];
				restrictions.toArray(temp);
				criteria.where(temp);				
			}
		}
		criteria.select(root);
		return s.createQuery(criteria).getResultList();
	}
	
	private boolean isEstiloPresent(CervejaFilter filter){
		Optional<Estilo> optional = Optional.ofNullable(filter.getEstilo());
		return optional.isPresent() && optional.get().getId() != null;
	}

}
