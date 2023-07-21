package com.algaworks.service;

import java.io.Serializable;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.algaworks.exception.NegocioException;
import com.algaworks.model.Usuario;
import com.algaworks.repository.UsuarioRepository;
import com.algaworks.util.jpa.Transactional;

public class UsuarioService implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Transactional
	public Usuario salvar(Usuario usuario) throws NegocioException {
		
		Usuario usuarioExistente = usuarioRepository.buscarUsuarioPorEmail(usuario.getEmail());
		
		if (usuarioExistente != null && !usuarioExistente.equals(usuario)) {
			throw new NegocioException("Já existe um usuário cadastrado com o e-Mail informado.");
		}
		
		Usuario novoUsuario = new Usuario();
		novoUsuario.setNome(usuario.getNome());
		novoUsuario.setEmail(usuario.getEmail());
		
		novoUsuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
		
		novoUsuario.setGrupos(usuario.getGrupos());
		
		return usuarioRepository.adicionar(novoUsuario);
	}
}
