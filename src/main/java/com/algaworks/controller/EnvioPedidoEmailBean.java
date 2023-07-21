package com.algaworks.controller;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Locale;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.velocity.tools.generic.NumberTool;

import com.algaworks.model.Pedido;
import com.algaworks.util.jsf.FacesUtil;
import com.algaworks.util.mail.Mailer;
import com.algaworks.validation.PedidoEdicao;
import com.outjected.email.api.MailMessage;
import com.outjected.email.impl.templating.velocity.VelocityTemplate;

@Named
@RequestScoped
public class EnvioPedidoEmailBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private Mailer mailer;
	
	@Inject
	@PedidoEdicao
	private Pedido pedido;
	
	public void enviarPedido() throws IOException {
		MailMessage message = mailer.novaMensagem();
		
		String path = getClass().getClassLoader().getResource("emails/pedido.template").getFile();
		
		message.to(this.pedido.getCliente().getEmail())
			.subject("Pedido " + this.pedido.getId())
			.bodyHtml(new VelocityTemplate(new File(path)))
			.put("pedido", this.pedido)
			.put("numberTool", new NumberTool())
			.put("locale", new Locale("pt", "BR"))
			.send();
	
		FacesUtil.addInfoMessage("Pedido enviado por e-mail com sucesso.");
	}
}
