package com.algaworks.controller;

import java.io.Serializable;

import javax.enterprise.inject.Produces;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.exception.NegocioException;
import com.algaworks.model.Cliente;
import com.algaworks.model.Endereco;
import com.algaworks.model.TipoPessoa;
import com.algaworks.service.ClienteService;
import com.algaworks.util.jsf.FacesUtil;
import com.algaworks.validation.ClienteEdicao;

@Named
@ViewScoped
public class CadastroClienteBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private ClienteService clienteService; 
	
	@Produces
	@ClienteEdicao
	private Cliente cliente;
	
	private Endereco endereco;
	private Endereco enderecoSelecionado;
	private boolean editandoEndereco;
	
	//inicializa uma instância nova de cliente no carregamento da página se o cliente for nulo
	public void inicializar() {
		if (cliente == null) {
			limpar();
		}
	}
	
	//limpa os dados da tela após cadastrar um novo cliente
	private void limpar() {
		cliente = new Cliente();
		cliente.setTipo(TipoPessoa.FISICA);
	}
	
	//limpa os dados da tela de endereços
	public void limparEndereco() {
		endereco = new Endereco();
		this.editandoEndereco = false;
	}
	
	//salva um novo cliente 
	public void salvar(){
		try {
			this.cliente = clienteService.salvar(this.cliente);
			limpar();
			FacesUtil.addInfoMessage("Cliente salvo com sucesso.");
		} catch(NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}
	
	//abre o dialog de adição de novos endereços
	public void adicionarEndereco() {
		if(!cliente.getEnderecos().contains(endereco)) {
			this.cliente.getEnderecos().add(endereco);
			this.endereco.setCliente(this.cliente);
			limparEndereco();
		}
	}
	
	//abre o dialog para editar um endereços
	public void editarEndereco(boolean editar) {
		this.editandoEndereco = editar;
	}
	
	//remove o endereço 
	public void removerEndereco() {
		this.cliente.getEnderecos().remove(enderecoSelecionado);
		limparEndereco();
		FacesUtil.addInfoMessage("Endereço excluído com sucesso.");		
	}
  
	//verifica a existência do id do objeto cliente para saber se ele é novo ou não
	public boolean isEditando() {
		return this.cliente != null && this.cliente.getId() != null;
	}	

	//método que verifica a edição de um endereço
	public boolean isEditandoEndereco() {
		return editandoEndereco;
	}	
	
	//retorno dos tipos de pessoa na tela
	public TipoPessoa[] getTipos() {
		return TipoPessoa.values();
	}
	
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Endereco getEnderecoSelecionado() {
		return enderecoSelecionado;
	}

	public void setEnderecoSelecionado(Endereco enderecoSelecionado) {
		this.enderecoSelecionado = enderecoSelecionado;
	}
}
