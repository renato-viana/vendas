package com.algaworks.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import com.algaworks.model.Cliente;
import com.algaworks.repository.ClienteRepository;

@Named
@ViewScoped
public class SelecaoClienteBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private ClienteRepository clienteRepository;
	
	private String nome;
	
	private List<Cliente> clientesFiltrados;
	
	public void pesquisar() {
		clientesFiltrados = clienteRepository.buscarPorNome(nome);
	}

	//seleciona o cliente e exibe no campo
	public void selecionar(Cliente cliente) {
		PrimeFaces.current().dialog().closeDynamic(cliente);
	}
	
	public void abrirDialogo() {
		Map<String,Object> opcoes = new HashMap<String, Object>();
		opcoes.put("modal", true);
		opcoes.put("resizable", false);
		opcoes.put("contentHeight", 470);
		
		PrimeFaces.current().dialog().openDynamic("/dialogos/selecaoCliente", opcoes, null);
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Cliente> getClientesFiltrados() {
		return clientesFiltrados;
	}
}
