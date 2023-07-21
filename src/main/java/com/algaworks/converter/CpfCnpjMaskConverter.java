package com.algaworks.converter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("cpfCnpjMask")
public class CpfCnpjMaskConverter implements Converter {

	Pattern CPF_PATTTERN = Pattern.compile("(\\d{3})(\\d{3})(\\d{3})");
	String CPF_REPLACEMENT = "$1.$2.$3-";
	
	Pattern CNPJ_PATTTERN = Pattern.compile("(\\d{2})(\\d{3})(\\d{3})(\\d{4})");
	String CNPJ_REPLACEMENT = "$1.$2.$3/$4-";
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null) {
			value = value.replaceAll("\\.|-|/", "");
		}
		
		return value;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		String resultado = value.toString();
		
		Matcher cpfMatcher = CPF_PATTTERN.matcher(resultado);
		Matcher cnpjMatcher = CNPJ_PATTTERN.matcher(resultado);
		
		if (cpfMatcher.matches()) {
			resultado = cpfMatcher.replaceAll(CPF_REPLACEMENT);
		} else if(cnpjMatcher.matches()) {
			resultado = cnpjMatcher.replaceAll(CNPJ_REPLACEMENT);
		}
		
		return resultado;
	}

}
