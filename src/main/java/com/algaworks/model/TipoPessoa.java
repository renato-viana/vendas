package com.algaworks.model;

import com.algaworks.model.validation.CnpjGroup;
import com.algaworks.model.validation.CpfGroup;

public enum TipoPessoa {

	FISICA("Física", "CPF", "999.999.999-99", CpfGroup.class), 
	JURIDICA("Jurídica", "CNPJ", "99.999.999/9999-99", CnpjGroup.class);
	
	private String descricao;
	private String rotulo;
	private String mascara;
	private Class<?> grupo;
	
	private TipoPessoa(String descricao, String rotulo, String mascara, Class<?> grupo) {
		this.descricao = descricao;
		this.rotulo = rotulo;
		this.mascara = mascara;
		this.grupo = grupo;
	}

	public String getDescricao() {
		return descricao;
	}

	public String getRotulo() {
		return rotulo;
	}

	public String getMascara() {
		return mascara;
	}
	
	public String getGrupo() {
		return grupo.getCanonicalName();
	}
}
