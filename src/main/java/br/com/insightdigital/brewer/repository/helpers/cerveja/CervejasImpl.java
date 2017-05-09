package br.com.insightdigital.brewer.repository.helpers.cerveja;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
	public Page<Cerveja> filtrar(CervejaFilter cervejaFilter) {		
		return filtrar(cervejaFilter, null);
	}
	
	@Transactional(readOnly = true)
	@Override
	public Page<Cerveja> filtrar(CervejaFilter cervejaFilter, Pageable pageable) {
		/*
		 * Obtém a sessão do Hibernate
		 * Cria o Construtor de Critérios e o Critério em si.
		 * O objeto root é a representação dos objetos a serem pesquisados.
		 * Assim, é utilizado nos Predicates (cláusulas da pesquisa).
		 * Também, no root é configurado o parâmetro fetch para o nome de um atributo,
		 * tornando-o EAGER
		 */
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Cerveja> criteria = builder.createQuery(Cerveja.class);
		Root<Cerveja> root = criteria.from(Cerveja.class);
		root.fetch("estilo");
		
		adicionarFiltro(root, cervejaFilter, builder, criteria);
		
		criteria.select(root);
		TypedQuery<Cerveja> realQuery = em.createQuery(criteria);
		
		int paginaAtual = pageable.getPageNumber();
		int totalPorPagina = pageable.getPageSize();
		int primeiroRegistro = paginaAtual * totalPorPagina;
		
		realQuery.setFirstResult(primeiroRegistro);
		realQuery.setMaxResults(totalPorPagina);
		List<Cerveja> list = realQuery.getResultList();
		Page<Cerveja> result = new PageImpl<Cerveja>(list, pageable, calcularTotal(cervejaFilter));
		return result;
	}

	private void adicionarFiltro(Root<?> root, CervejaFilter cervejaFilter, CriteriaBuilder builder,
			CriteriaQuery<?> criteria) {
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
	}
	
	private long calcularTotal(CervejaFilter cervejaFilter) {
		
		CriteriaBuilder builder = em.getCriteriaBuilder();
//		CriteriaQuery<Cerveja> criteria = builder.createQuery(Cerveja.class);
//		Root<Cerveja> root = criteria.from(Cerveja.class);
//		root.fetch("estilo");
//		Root<Long> countRoot = criteria.from(Long.class);
//		adicionarFiltro(root, cervejaFilter, builder, criteria);
		
		
		CriteriaQuery<Long> criteriaCount = builder.createQuery(Long.class);
		Root<?> entityRoot = criteriaCount.from(Cerveja.class);
		entityRoot.join("estilo");
		adicionarFiltro(entityRoot, cervejaFilter, builder, criteriaCount);
		criteriaCount.select(builder.count(entityRoot));
		
		Long l = em.createQuery(criteriaCount).getSingleResult();
		return l.longValue();
	}

	private boolean isEstiloPresent(CervejaFilter filter){
		Optional<Estilo> optional = Optional.ofNullable(filter.getEstilo());
		return optional.isPresent() && optional.get().getId() != null;
	}

}
