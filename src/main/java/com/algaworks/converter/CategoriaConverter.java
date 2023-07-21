package com.algaworks.converter;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.convert.ClientConverter;

import com.algaworks.model.Categoria;
import com.algaworks.repository.CategoriaRepository;

@Named
public class CategoriaConverter implements Converter, ClientConverter {

	@Inject
	private CategoriaRepository categoriaRepository;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Categoria retorno = null;
		
		if (StringUtils.isNotEmpty(value)) {
			Long id = new Long(value);
			retorno = categoriaRepository.buscarCategoriaPorId(id);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Categoria categoria = (Categoria) value;
			
			return categoria.getId() == null ? null : categoria.getId().toString();
		}
		
		return "";
	}

	@Override
	public Map<String, Object> getMetadata() {
		return null;
	}

	@Override
	public String getConverterId() {
		return "com.algaworks.Categoria";
	}

}
