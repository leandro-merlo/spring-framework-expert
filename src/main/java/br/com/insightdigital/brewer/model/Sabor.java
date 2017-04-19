package br.com.insightdigital.brewer.model;

public enum Sabor {

	ADOCICADA("Adocicada"), AMARGA("Amarga"), FORTE("Forte"), FRUTADA("Frutada"), SUAVE("Suave"),;

	private String nome;

	private Sabor(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

}
