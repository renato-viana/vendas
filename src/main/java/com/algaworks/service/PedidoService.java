package com.algaworks.service;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import com.algaworks.exception.NegocioException;
import com.algaworks.model.Pedido;
import com.algaworks.repository.PedidoRepository;
import com.algaworks.util.jpa.Transactional;

public class PedidoService implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject 
	private PedidoRepository pedidoRepository;
	
	@Transactional
	public Pedido salvar(Pedido pedido) throws NegocioException {
		if (pedido.isNovo()) {
			pedido.setDataCriacao(new Date());
		}
		
		pedido.recalcularValorTotal();
		
		if(pedido.isNaoAlteravel()) {
			throw new NegocioException("Pedido não pode ser alterado no status " + pedido.getStatus().getDescricao() + ".");
		}
		
		if (pedido.getItens().isEmpty()) {
			throw new NegocioException("O pedido deve possuir pelo menos um item.");
		}
		
		if (pedido.isValorTotalNegativo()) {
			throw new NegocioException("O valor total do pedido não pode ser negativo.");
		}
		
		pedido = this.pedidoRepository.adicionar(pedido);
		
		return pedido;
		
	}
}
