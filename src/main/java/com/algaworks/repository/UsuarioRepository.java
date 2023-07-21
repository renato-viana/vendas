package com.algaworks.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import com.algaworks.exception.NegocioException;
import com.algaworks.model.Usuario;
import com.algaworks.repository.filter.UsuarioFilter;
import com.algaworks.util.jpa.Transactional;

public class UsuarioRepository implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public Usuario adicionar(Usuario usuario) {
		return manager.merge(usuario);
	}

	public Usuario buscarUsuarioPorEmail(String email) {
		try {
			return manager.createQuery("from Usuario where lower(email) = :email", Usuario.class)
					.setParameter("email", email.toLowerCase())
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public List<Usuario> vendedores() {
		return this.manager.createQuery("from Usuario", Usuario.class).getResultList();
	}
	
	@Transactional
	public void remover(Usuario usuario) throws NegocioException {
		try {
			usuario = buscarUsuarioPorId(usuario.getId());
			manager.remove(usuario);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Usuário não pode ser excluído");
		}
	}

	public Usuario buscarUsuarioPorId(Long id) {
		return manager.find(Usuario.class, id);
	}
	
	public List<Usuario> usuariosFiltrados(UsuarioFilter filtro) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Usuario> criteriaQuery = builder.createQuery(Usuario.class);
		List<Predicate> predicates = new ArrayList<>();
		
		Root<Usuario> usuarioRoot = criteriaQuery.from(Usuario.class);
		
		if (StringUtils.isNotBlank(filtro.getNome())) {
			predicates.add(builder.like(builder.lower(usuarioRoot.get("nome")), "%" + filtro.getNome().toLowerCase() + "%"));
		}
		
		criteriaQuery.select(usuarioRoot);
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		criteriaQuery.orderBy(builder.asc(usuarioRoot.get("nome")));
		
		TypedQuery<Usuario> query = manager.createQuery(criteriaQuery);
		
		return query.getResultList();
	}
}
