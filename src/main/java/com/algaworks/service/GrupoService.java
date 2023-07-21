package com.algaworks.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.algaworks.exception.NegocioException;
import com.algaworks.model.Grupo;
import com.algaworks.repository.GrupoRepository;
import com.algaworks.util.jpa.Transactional;

public class GrupoService implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private GrupoRepository grupoRepository;
	
	@Transactional
	public Grupo salvar(Grupo grupo) throws NegocioException {
		Grupo grupoExistente = grupoRepository.buscarGrupoPeloNome(grupo.getNome());
		
		if (grupoExistente != null && !grupoExistente.equals(grupo)) {
			throw new NegocioException("Já existe um grupo com o nome informado.");
		}
		
		return grupoRepository.adicionar(grupo);
	}
}