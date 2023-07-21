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
import com.algaworks.model.Cliente;
import com.algaworks.repository.filter.ClienteFilter;
import com.algaworks.util.jpa.Transactional;

public class ClienteRepository implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public Cliente adicionar(Cliente cliente) {
		return manager.merge(cliente);
	}

	public Cliente buscarClientePeloEmail(String email) {
		try {
			return manager.createQuery("from Cliente where upper(email) = :email", Cliente.class)
					.setParameter("email", email.toUpperCase())
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public List<Cliente> buscarPorNome(String nome) {
		return this.manager.createQuery("from Cliente where upper(nome) like :nome", Cliente.class)
				.setParameter("nome", nome.toUpperCase() + "%")
				.getResultList();
	}
	
	@Transactional
	public void remover(Cliente cliente) throws NegocioException {
		try {
			cliente = buscarClientePorId(cliente.getId());
			manager.remove(cliente);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Cliente não pode ser excluído");
		}
	}
	
	public Cliente buscarClientePorId(Long id) {
		return manager.find(Cliente.class, id);
	}
	
	public List<Cliente> clientesFiltrados(ClienteFilter filtro) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Cliente> criteriaQuery = builder.createQuery(Cliente.class);
		List<Predicate> predicates = new ArrayList<>();

		Root<Cliente> clienteRoot = criteriaQuery.from(Cliente.class);

		if (StringUtils.isNotBlank(filtro.getNome())) {
			predicates.add(builder.like(builder.lower(clienteRoot.get("nome")), "%" + filtro.getNome().toLowerCase() + "%"));
		}

		if (StringUtils.isNotBlank(filtro.getDocumentoReceitaFederal())) {
			predicates.add(builder.equal(clienteRoot.get("documentoReceitaFederal"), filtro.getDocumentoReceitaFederal()));
		}

		criteriaQuery.select(clienteRoot);
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		criteriaQuery.orderBy(builder.asc(clienteRoot.get("nome")));

		TypedQuery<Cliente> query = manager.createQuery(criteriaQuery);

		return query.getResultList();
	}
	 
}
