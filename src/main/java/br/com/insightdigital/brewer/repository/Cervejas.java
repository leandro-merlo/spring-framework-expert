package br.com.insightdigital.brewer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.insightdigital.brewer.model.Cerveja;

@Repository
public interface Cervejas extends JpaRepository<Cerveja, Long>{

}
