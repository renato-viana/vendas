package com.algaworks.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.exception.NegocioException;
import com.algaworks.model.Usuario;
import com.algaworks.repository.UsuarioRepository;
import com.algaworks.repository.filter.UsuarioFilter;
import com.algaworks.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaUsuariosBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioRepository usuarioRepository;

	private UsuarioFilter filtro;
	private List<Usuario> usuariosFiltrados;
	private Usuario usuarioSelecionado;

	public PesquisaUsuariosBean() {
		limpar();
	}

	//realiza a pesquisa de usuários com filtro
	public void pesquisar() {
		usuariosFiltrados = usuarioRepository.usuariosFiltrados(filtro);
	}
	
	//remove um usuário
	public void excluir() {
		try {
			usuarioRepository.remover(usuarioSelecionado);
			usuariosFiltrados.remove(usuarioSelecionado);
			limpar();
	
			FacesUtil.addInfoMessage("Usuário " + usuarioSelecionado.getNome() + " excluído com sucesso.");
		} catch(NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}
	
	//limpa os dados da tela após excluir um usuário que foi buscado
	private void limpar() {
		filtro = new UsuarioFilter();
	}
	
	public UsuarioFilter getFiltro() {
		return filtro;
	}
	
	public List<Usuario> getUsuariosFiltrados() {
		return usuariosFiltrados;
	}
	
	public Usuario getUsuarioSelecionado() {
		return usuarioSelecionado;
	}

	public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}
}
