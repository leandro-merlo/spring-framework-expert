package br.com.insightdigital.brewer.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import br.com.insightdigital.brewer.validation.SKU;

@Entity
@Table(name = "cerveja")
public class Cerveja implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1976158840214897864L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@SKU
	@NotBlank(message = "SKU é obrigatório")
	private String sku;

	@NotBlank(message = "Nome é obrigatório")
	private String nome;

	@NotNull
	@Size(min = 5, max = 50, message= "A descrição deve ter entre 5 e 50 caractéres")
	private String descricao;

	@NotNull(message="O valor é obrigatório")
	@DecimalMin(value="0.5", message="Não é permitido vender cervejas a menos de R$ 0,50")
	@DecimalMax(value="9999.99", inclusive=true, message="O valor máximo permitido para cervejas é de R$ 9.999,99")
	private BigDecimal valor;

	@NotNull(message="O teor alcoólico é obrigatório")
	@DecimalMin(value="0", message="Não é permitido ter teor alcoólico negativo")
	@DecimalMax(value="100", message="Teor Alcoólico não deve ultrapassar os 100%")
	@Column(name = "teor_alcoolico")
	private BigDecimal teorAlcoolico;

	@NotNull(message="A comissão em estoque é obrigatória")
	@DecimalMin(value="0", message="Não é permitido ter comissões negativas")
	@DecimalMax(value="100", message="As comissões não deve ultrapassar os 100%")
	@Column(name = "comissao")
	private BigDecimal comissao;

	@NotNull(message="A quantidade em estoque é obrigatória")
	@Max(value=9999, message="Valor máximo permitido para quantidade em estoque é de 9.999 itens")
	@Min(value=0, message="A quantidade em estoque não deve ser negativa")
	@Column(name = "quantidade_estoque")
	private Integer quantidadeEstoque;

	@NotNull(message="A origem é obrigatória")
	@Enumerated(EnumType.STRING)
	private Origem origem;

	@NotNull(message="O sabor é obrigatório")
	@Enumerated(EnumType.STRING)
	private Sabor sabor;

	@NotNull(message="O estilo é obrigatória")
	@ManyToOne
	@JoinColumn(name = "id_estilo")
	private Estilo estilo;

	@PrePersist
	@PreUpdate
	private void prePersistUpdate(){
		this.sku = this.sku.toUpperCase();
	}
	
	
	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public BigDecimal getTeorAlcoolico() {
		return teorAlcoolico;
	}

	public void setTeorAlcoolico(BigDecimal teorAlcoolico) {
		this.teorAlcoolico = teorAlcoolico;
	}

	public BigDecimal getComissao() {
		return comissao;
	}

	public void setComissao(BigDecimal comissao) {
		this.comissao = comissao;
	}

	public Integer getQuantidadeEstoque() {
		return quantidadeEstoque;
	}

	public void setQuantidadeEstoque(Integer quantidadeEstoque) {
		this.quantidadeEstoque = quantidadeEstoque;
	}

	public Origem getOrigem() {
		return origem;
	}

	public void setOrigem(Origem origem) {
		this.origem = origem;
	}

	public Sabor getSabor() {
		return sabor;
	}

	public void setSabor(Sabor sabor) {
		this.sabor = sabor;
	}

	public Estilo getEstilo() {
		return estilo;
	}

	public void setEstilo(Estilo estilo) {
		this.estilo = estilo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cerveja other = (Cerveja) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	

}
