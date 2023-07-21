package com.algaworks.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.exception.NegocioException;
import com.algaworks.model.Cliente;
import com.algaworks.repository.ClienteRepository;
import com.algaworks.repository.filter.ClienteFilter;
import com.algaworks.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaClientesBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private ClienteRepository clienteRepository;

	private ClienteFilter filtro;
	private List<Cliente> clientesFiltrados;
	private Cliente clienteSelecionado;
	
	public PesquisaClientesBean() {
		limpar();
	}

	//realiza a pesquisa de clientes com filtro
	public void pesquisar() {
		clientesFiltrados = clienteRepository.clientesFiltrados(filtro);
	}
	
	//remove um usuário
	public void excluir() {
		try {
			clienteRepository.remover(clienteSelecionado);
			clientesFiltrados.remove(clienteSelecionado);
			limpar();
	
			FacesUtil.addInfoMessage("Cliente " + clienteSelecionado.getNome() + " excluído com sucesso.");
		} catch(NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}
	
	//limpa os dados da tela após excluir um cliente que foi buscado
	private void limpar() {
		filtro = new ClienteFilter();
	}

	public List<Cliente> getClientesFiltrados() {
		return clientesFiltrados;
	}

	public ClienteFilter getFiltro() {
		return filtro;
	}

	public Cliente getClienteSelecionado() {
		return clienteSelecionado;
	}

	public void setClienteSelecionado(Cliente clienteSelecionado) {
		this.clienteSelecionado = clienteSelecionado;
	}
}
