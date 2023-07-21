package com.algaworks.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import com.algaworks.model.Grupo;

public class GrupoRepository implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public Grupo adicionar(Grupo grupo) {
		return manager.merge(grupo);
	}
	
	public Grupo buscarGrupoPeloNome(String nome) {
		try {
			return manager.createQuery("from Grupo where upper(nome) = :nome", Grupo.class)
					.setParameter("nome", nome.toUpperCase())
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public Grupo buscarGrupoPorId(Long id) {
		return manager.find(Grupo.class, id);
	}
	
	public List<Grupo> listarTodosOsGrupos() {
		return manager.createQuery("from Grupo", Grupo.class).getResultList();
	}
}
