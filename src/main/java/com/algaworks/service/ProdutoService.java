package com.algaworks.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.algaworks.exception.NegocioException;
import com.algaworks.model.Produto;
import com.algaworks.repository.ProdutoRepository;
import com.algaworks.util.jpa.Transactional;

public class ProdutoService implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private ProdutoRepository produtoRepository;

	@Transactional
	public Produto salvar(Produto produto) throws NegocioException {
		Produto produtoExistente = produtoRepository.buscarPorSku(produto.getSku());
		
		if (produtoExistente != null && !produtoExistente.equals(produto)) {
			throw new NegocioException("Já existe um produto com o SKU informado.");
		}
		
		return produtoRepository.adicionar(produto);
	}
}
