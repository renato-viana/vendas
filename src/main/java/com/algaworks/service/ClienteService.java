package com.algaworks.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.algaworks.exception.NegocioException;
import com.algaworks.model.Cliente;
import com.algaworks.repository.ClienteRepository;
import com.algaworks.util.jpa.Transactional;

public class ClienteService implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private ClienteRepository clienteRepository;
	
	@Transactional
	public Cliente salvar(Cliente cliente) throws NegocioException {
		Cliente clienteExistente = clienteRepository.buscarClientePeloEmail(cliente.getEmail());
		
		if (clienteExistente != null && !clienteExistente.equals(cliente)) {
			throw new NegocioException("Já existe um cliente cadastrado com o e-Mail informado.");
		}
		
		return clienteRepository.adicionar(cliente);
	}

}
