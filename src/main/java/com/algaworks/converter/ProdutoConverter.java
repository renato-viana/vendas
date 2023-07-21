package com.algaworks.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import com.algaworks.model.Produto;
import com.algaworks.repository.ProdutoRepository;

@Named
public class ProdutoConverter implements Converter {

	@Inject
	private ProdutoRepository produtoRepository;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Produto retorno = null;
		
		if (StringUtils.isNotEmpty(value)) {
			Long id = new Long(value);
			retorno = produtoRepository.buscarProdutoPorId(id);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Produto produto = (Produto) value;
			
			return produto.getId() == null ? null : produto.getId().toString();
		}
		
		return "";
	}
}
