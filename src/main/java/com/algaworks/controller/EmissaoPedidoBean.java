package com.algaworks.controller;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.event.PedidoAlteradoEvent;
import com.algaworks.exception.NegocioException;
import com.algaworks.model.Pedido;
import com.algaworks.service.EmissaoService;
import com.algaworks.util.jsf.FacesUtil;
import com.algaworks.validation.PedidoEdicao;

@Named
@RequestScoped
public class EmissaoPedidoBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private EmissaoService emissaoService;
	
	@Inject
	@PedidoEdicao
	private Pedido pedido;
	
	@Inject
	private Event<PedidoAlteradoEvent> pedidoAlteradoEvent;
	
	public void emitirPedido() {
		this.pedido.removerItemVazio();
		
		try {
			this.pedido = this.emissaoService.emitir(this.pedido);
			this.pedidoAlteradoEvent.fire(new PedidoAlteradoEvent(this.pedido));
			
			FacesUtil.addInfoMessage("Pedido emitido com sucesso.");
		} catch(NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		} finally {
			this.pedido.adicionarItemVazio();
		}
	}
}
