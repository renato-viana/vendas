package com.algaworks.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import com.algaworks.model.Pedido;
import com.algaworks.repository.PedidoRepository;

@Named
public class PedidoConverter implements Converter {

	@Inject
	private PedidoRepository pedidoRepository;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Pedido retorno = null;
		
		if (StringUtils.isNotEmpty(value)) {
			Long id = new Long(value);
			retorno = pedidoRepository.buscarPedidoPorId(id);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Pedido pedido = (Pedido) value;
			
			return pedido.getId() == null ? null : pedido.getId().toString();
		}
		
		return "";
	}
}
