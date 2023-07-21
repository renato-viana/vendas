package com.algaworks.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.algaworks.exception.NegocioException;
import com.algaworks.model.Pedido;
import com.algaworks.model.StatusPedido;
import com.algaworks.repository.PedidoRepository;
import com.algaworks.util.jpa.Transactional;

public class EmissaoService implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private PedidoService pedidoService;
	
	@Inject
	private PedidoRepository pedidoRepository;
	
	@Inject
	private EstoqueService estoqueService;
	
	@Transactional
	public Pedido emitir(Pedido pedido) throws NegocioException {
		pedido = this.pedidoService.salvar(pedido);
		
		if (pedido.isNaoEmissivel()) {
			throw new NegocioException("Pedido não pode ser emitido com status " + pedido.getStatus().getDescricao() + ".");
		}
		
		this.estoqueService.baixarItensEstoque(pedido);
		
		pedido.setStatus(StatusPedido.EMITIDO);
		
		pedido = this.pedidoRepository.adicionar(pedido);
		
		return pedido;
	}
}
