package com.algaworks.repository.filter;

import java.io.Serializable;

import com.algaworks.validation.SKU;

public class ProdutoFilter implements Serializable {
	private static final long serialVersionUID = 1L;

	private String nome;
	
	@SKU
	private String sku;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku == null ? null : sku.toUpperCase();
	}
}
