package com.algaworks.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.algaworks.model.Categoria;

public class CategoriaRepository implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public List<Categoria> buscarCategoriasRaizes() {
		return manager.createQuery("from Categoria where categoriaPai is null", Categoria.class).getResultList();
	}
	
	public Categoria buscarCategoriaPorId(Long id) {
		return manager.find(Categoria.class, id);
	}
	
	public List<Categoria> carregarSubcategoriasDe(Categoria categoriaPai) {
		return manager.createQuery("from Categoria where categoriaPai = :pai", Categoria.class)
				.setParameter("pai", categoriaPai)
				.getResultList();
	}
}
