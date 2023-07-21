package com.algaworks.controller;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.model.Cliente;
import com.algaworks.util.jsf.FacesUtil;
import com.algaworks.util.mail.Mailer;
import com.algaworks.validation.ClienteEdicao;
import com.outjected.email.api.MailMessage;
import com.outjected.email.impl.templating.velocity.VelocityTemplate;

@Named
@RequestScoped
public class EnvioClienteEmailBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private Mailer mailer;
	
	@Inject
	@ClienteEdicao
	private Cliente cliente;
	
	public void enviarDados() throws IOException {
		MailMessage message = mailer.novaMensagem();
		
		String path = getClass().getClassLoader().getResource("emails/cliente.template").getFile();
		
		message.to(this.cliente.getEmail())
			.subject("Cliente " + this.cliente.getNome())
			.bodyHtml(new VelocityTemplate(new File(path)))
			.put("cliente", this.cliente)
			.send();
	
		FacesUtil.addInfoMessage("Dados do cliente enviados com sucesso.");
	}
}
