package com.algaworks.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import com.algaworks.model.Cliente;
import com.algaworks.repository.ClienteRepository;

@Named
public class ClienteConverter implements Converter {

	@Inject
	private ClienteRepository clienteRepository;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Cliente retorno = null;
		
		if (StringUtils.isNotEmpty(value)) {
			Long id = new Long(value);
			retorno = clienteRepository.buscarClientePorId(id);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Cliente cliente = (Cliente) value;
			
			return cliente.getId() == null ? null : cliente.getId().toString();
		}
		
		return "";
	}

}
