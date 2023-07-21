package com.algaworks.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.exception.NegocioException;
import com.algaworks.model.Produto;
import com.algaworks.repository.ProdutoRepository;
import com.algaworks.repository.filter.ProdutoFilter;
import com.algaworks.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaProdutosBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private ProdutoRepository produtoRepository;
	
	private ProdutoFilter filtro;
	private List<Produto> produtosFiltrados;
	private Produto produtoSelecionado;
	
	public PesquisaProdutosBean() {
		limpar();
	}
	
	//realiza a pesquisa de produtos com filtro
	public void pesquisar() {
		produtosFiltrados = produtoRepository.produtosFiltrados(filtro);
	}
	
	//remove um produto
	public void excluir() {
		try {
			produtoRepository.remover(produtoSelecionado);
			produtosFiltrados.remove(produtoSelecionado);
			limpar();
			
			FacesUtil.addInfoMessage("Produto " + produtoSelecionado.getSku() + " excluído com sucesso.");
		} catch(NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}
	
	//limpa os dados da tela após excluir um produto que foi buscado
	private void limpar() {
		filtro = new ProdutoFilter();
	}	
	
	public List<Produto> getProdutosFiltrados() {
		return produtosFiltrados;
	}

	public ProdutoFilter getFiltro() {
		return filtro;
	}

	public Produto getProdutoSelecionado() {
		return produtoSelecionado;
	}

	public void setProdutoSelecionado(Produto produtoSelecionado) {
		this.produtoSelecionado = produtoSelecionado;
	}
}
