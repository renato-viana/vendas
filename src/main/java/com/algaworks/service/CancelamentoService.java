package com.algaworks.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.algaworks.exception.NegocioException;
import com.algaworks.model.Pedido;
import com.algaworks.model.StatusPedido;
import com.algaworks.repository.PedidoRepository;
import com.algaworks.util.jpa.Transactional;

public class CancelamentoService implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private PedidoRepository pedidoRepository;
	
	@Inject
	private EstoqueService estoqueService;
	
	@Transactional
	public Pedido cancelar(Pedido pedido) throws NegocioException {
		pedido = this.pedidoRepository.buscarPedidoPorId(pedido.getId());
		
		if(pedido.isNaoCancelavel()) {
			throw new NegocioException("Pedido não pode ser cancelado no status " + pedido.getStatus().getDescricao() + ".");
		}
		
		if(pedido.isEmitido()) {
			this.estoqueService.retornarItensEstoque(pedido);
		}
		
		pedido.setStatus(StatusPedido.CANCELADO);
		
		pedido = this.pedidoRepository.adicionar(pedido);
		
		return pedido;
	}


}
