package com.algaworks.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import com.algaworks.model.Usuario;
import com.algaworks.repository.UsuarioRepository;

@Named
public class UsuarioConverter implements Converter {

	@Inject
	private UsuarioRepository usuarioRepository;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Usuario retorno = null;
		
		if (StringUtils.isNotEmpty(value)) {
			Long id = new Long(value);
			retorno = usuarioRepository.buscarUsuarioPorId(id);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Usuario usuario = (Usuario) value;
			
			return usuario.getId() == null ? null : usuario.getId().toString();
		}
		
		return "";
	}

}
