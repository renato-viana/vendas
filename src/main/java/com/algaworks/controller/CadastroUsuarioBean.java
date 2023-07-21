package com.algaworks.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import com.algaworks.exception.NegocioException;
import com.algaworks.model.Grupo;
import com.algaworks.model.Usuario;
import com.algaworks.repository.GrupoRepository;
import com.algaworks.service.UsuarioService;
import com.algaworks.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroUsuarioBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private UsuarioService usuarioService;
	
	@Inject
	private GrupoRepository grupoRepository;
	
	private Usuario usuario;
	
	@NotNull
	private List<Grupo> grupos;
	private List<Grupo> gruposSelecionados;
	
	public CadastroUsuarioBean() {
		limpar();
	}
	
	//limpa os dados da tela após cadastrar um novo usuário
	private void limpar() {
		usuario = new Usuario();
		grupos = new ArrayList<>();
	}
	
	//carrega a lista de grupos na inicialização da página
	public void inicializar() {
		grupos = grupoRepository.listarTodosOsGrupos();
		
		if (isEditando()) {
			gruposSelecionados = usuario.getGrupos();
		}
	}
	
	//salva um novo usuário
	public void salvar(){
		try {
			this.usuario.setGrupos(gruposSelecionados);
			this.usuario = usuarioService.salvar(this.usuario);
			limpar();
			FacesUtil.addInfoMessage("Usuário salvo com sucesso.");
		} catch(NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}
	
	//verifica a existência do id do objeto usuário para saber se ele é novo ou não
	public boolean isEditando() {
		return this.usuario.getId() != null;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Grupo> getGrupos() {
		return grupos;
	}
	
	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}
	
	public List<Grupo> getGruposSelecionados() {
		return gruposSelecionados;
	}

	public void setGruposSelecionados(List<Grupo> gruposSelecionados) {
		this.gruposSelecionados = gruposSelecionados;
	}
}
