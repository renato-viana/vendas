package com.algaworks.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import com.algaworks.exception.NegocioException;
import com.algaworks.model.Categoria;
import com.algaworks.model.Produto;
import com.algaworks.repository.CategoriaRepository;
import com.algaworks.service.ProdutoService;
import com.algaworks.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroProdutoBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private CategoriaRepository categoriaRepository;

	@Inject
	private ProdutoService produtoService;
	
	private Produto produto;
	
	@NotNull(message = "Categoria deve ser informada.")
	private Categoria categoriaPai;
	
	private List<Categoria> categoriasRaizes;
	private List<Categoria> subcategorias;
	
	public CadastroProdutoBean() {
		limpar();
	}
	
	//carrega a lista de categorias na inicialização da página
	public void inicializar() {
		if(this.produto == null) {
			limpar();
		}
		
		categoriasRaizes = categoriaRepository.buscarCategoriasRaizes();
		
		//busca as subcategorias da categoria pai no momento da edição
		if (this.categoriaPai != null) {
			carregarSubcategorias();
		}
	}	
	
	//carrega as subcategorias através da categoria selecionada
	public void carregarSubcategorias() {
		subcategorias = categoriaRepository.carregarSubcategoriasDe(categoriaPai);
	}
	
	//limpa os dados da tela após cadastrar um novo produto
	private void limpar() {
		produto = new Produto();
		categoriaPai = null;
		subcategorias = new ArrayList<>();
	}	
	
	//salva um novo produto
	public void salvar(){
		try {
			this.produto = produtoService.salvar(this.produto);
			limpar();
			FacesUtil.addInfoMessage("Produto salvo com sucesso.");
		} catch(NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public Produto getProduto() {
		return produto;
	}
	
	//seta o valor da categoria pai no select no momento da edição do produto
	public void setProduto(Produto produto) {
		this.produto = produto;
		
		if (this.produto != null) {
			this.categoriaPai = this.produto.getCategoria().getCategoriaPai();
		}
	}
	
	//verifica a existência do id do objeto produto para saber se ele é novo ou não
	public boolean isEditando() {
		return this.produto.getId() != null;
	}

	public List<Categoria> getCategoriasRaizes() {
		return categoriasRaizes;
	}
	
	public List<Categoria> getSubcategorias() {
		return subcategorias;
	}

	public Categoria getCategoriaPai() {
		return categoriaPai;
	}

	public void setCategoriaPai(Categoria categoriaPai) {
		this.categoriaPai = categoriaPai;
	}
}
