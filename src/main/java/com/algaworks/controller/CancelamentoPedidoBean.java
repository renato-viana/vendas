package com.algaworks.controller;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.event.PedidoAlteradoEvent;
import com.algaworks.exception.NegocioException;
import com.algaworks.model.Pedido;
import com.algaworks.service.CancelamentoService;
import com.algaworks.util.jsf.FacesUtil;
import com.algaworks.validation.PedidoEdicao;

@Named
@RequestScoped
public class CancelamentoPedidoBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private CancelamentoService cancelamentoService;
	
	@Inject
	private Event<PedidoAlteradoEvent> pedidoAlteradoEvent;
	
	@Inject
	@PedidoEdicao
	private Pedido pedido;
	
	public void cancelarPedido() {
		try {
			this.pedido = this.cancelamentoService.cancelar(this.pedido);
			this.pedidoAlteradoEvent.fire(new PedidoAlteradoEvent(this.pedido));
			
			FacesUtil.addInfoMessage("Pedido cancelado com sucesso.");
		} catch(NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}
}
