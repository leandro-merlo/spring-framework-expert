package br.com.insightdigital.brewer.model;

public enum Origem {
	
	NACIONAL("Nacional"),
	INTERNACIONAL("Internacional");
	
	private String nome;
	
	private Origem(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}
	
	
}
