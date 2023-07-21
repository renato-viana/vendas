package com.algaworks.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.hibernate.Session;
import org.jboss.weld.interceptor.util.proxy.TargetInstanceProxy;

import com.algaworks.util.jsf.FacesUtil;
import com.algaworks.util.report.ExecutorRelatorio;

@Named
@RequestScoped
public class RelatorioPedidosEmitidosBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@NotNull
	private Date dataInicio;
	@NotNull
	private Date dataFim;
	
	@Inject
	private FacesContext facesContext;

	@Inject
	private HttpServletResponse response;
	
	@Inject
	private EntityManager manager;
	
	public void emitir() {
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("data_inicio", this.dataInicio);
		parametros.put("data_fim", this.dataFim);
		
		ExecutorRelatorio executor = new ExecutorRelatorio("/relatorios/pedidos_emitidos.jasper", this.response, parametros, "Pedidos Emitidos.pdf");
		
		TargetInstanceProxy<?> proxy = (TargetInstanceProxy<?>) manager.unwrap(Session.class);
	    Session session = (Session) proxy.getTargetInstance();
	    
		session.doWork(executor);
		
		if(executor.isRelatorioGerado()) {
			facesContext.responseComplete();
		} else {
			FacesUtil.addWarnMessage("A execução do relatório não retornou dados.");
		}
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
}
