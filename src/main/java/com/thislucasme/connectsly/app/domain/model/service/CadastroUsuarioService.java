package com.thislucasme.connectsly.app.domain.model.service;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thislucasme.connectsly.app.domain.model.exception.EntidadeNaoEncontradaException;
import com.thislucasme.connectsly.app.domain.model.model.Usuario;
import com.thislucasme.connectsly.app.domain.model.repository.UsuarioRepository;

@Service
public class CadastroUsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	
	public Usuario adcionar(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}
	public Usuario atualizar(String idHash, Usuario usuario) {
		Optional<Usuario> usuarioAtual = usuarioRepository.findByIdHash(idHash);
		
		if(!usuarioAtual.isPresent()) {
			throw new EntidadeNaoEncontradaException(String.format("Usuario com id % nao existe", usuario.getIdHashFromFirebase()));
		}
		BeanUtils.copyProperties(usuario, usuarioAtual.get(), "id", "id_hash_from_firebase");
		return usuarioRepository.save(usuarioAtual.get());
	}
	
	public Usuario delete(String idHash) {
		Optional<Usuario> user = usuarioRepository.findByIdHash(idHash);
		
		if(!user.isPresent()) {
			throw new EntidadeNaoEncontradaException("Usuario com id "+idHash+" nao existe");
		}
		
		return user.get();
	}

	
}
