package com.thislucasme.connectsly.app.controller;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thislucasme.connectsly.app.domain.model.exception.EntidadeNaoEncontradaException;
import com.thislucasme.connectsly.app.domain.model.model.Usuario;
import com.thislucasme.connectsly.app.domain.model.repository.UsuarioRepository;
import com.thislucasme.connectsly.app.domain.model.service.CadastroUsuarioService;
import com.thislucasme.connectsly.app.util.Data;
import com.thislucasme.connectsly.app.util.Data.DataListener;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import nonapi.io.github.classgraph.utils.ReflectionUtils;


@RestController
@RequestMapping(value = "/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "API REST Connectsly")
@CrossOrigin(origins = "*")
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private CadastroUsuarioService usuarioService;
	
	@PostMapping
	@ApiOperation(value = "Salva um usuario")
	public ResponseEntity<?> salvarUser(@RequestBody Usuario usuario) {
		
		try {
			usuarioService.adcionar(usuario);
			return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	@ApiOperation(value = "Retorna todos os usuarios")
	@GetMapping
	public List<Usuario> listar(){
		return usuarioRepository.findAll();
	}
	@ApiOperation(value = "Filtra um usuario passando como parametro, id, nome do pais, genero e quantidade")
	@GetMapping("/random-users-by-filter")
	public ResponseEntity<List<Usuario>> ramdomUsersByFilter(
			@RequestParam String idHash,
			@RequestParam String nomePaisEn,
			@RequestParam String sexo,
			@RequestParam int quantidade){
//		if(recent) {
//			return ResponseEntity.status(HttpStatus.OK).body(usuarioRepository.getRamdomUsersFilterRecent(idHash, nomePaisEn, sexo, quantidade, recent));
//		}
		return ResponseEntity.status(HttpStatus.OK).body(usuarioRepository.getRamdomUsersFilter(idHash, nomePaisEn, sexo, quantidade));
	}
	
	@DeleteMapping
	public ResponseEntity<Usuario> deleteUser(@RequestParam String idHash){
		
		usuarioRepository.deleteByIdHash(idHash);
		
		try {
			
			Usuario user = usuarioService.delete(idHash);
			usuarioRepository.delete(user);
			return ResponseEntity.status(HttpStatus.CREATED).body(user);
			
		}catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Usuario());
		}
		

	}
	
	@GetMapping("/random-users")
	public ResponseEntity<List<Usuario>> randomUsers(
			@RequestParam String idHash, @RequestParam int quantidade){
		return ResponseEntity.status(HttpStatus.OK).body(usuarioRepository.getRandomUsers(idHash, quantidade));
	}
	@ApiOperation(value = "Atualizar um usuario")
	@PutMapping("/{idHash}")
	public ResponseEntity<Usuario> atualizar(@PathVariable String idHash, @RequestBody Usuario usuario){
		
		try {
			Usuario usuarioAtual = usuarioService.atualizar(idHash, usuario);
			return ResponseEntity.status(HttpStatus.CREATED).body(usuarioAtual);
		}catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	@PatchMapping("/{idHash}")
	public ResponseEntity<?> atualizarParcial(@PathVariable String idHash,
			@RequestBody Map<String, Object> campos){
		
		Optional<Usuario> usuarioAtual = usuarioRepository.findByIdHash(idHash);
		
		if(!usuarioAtual.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		
		merge(campos, usuarioAtual.get());
 
		
		return atualizar(idHash, usuarioAtual.get());
	}
	
	private void merge(Map<String, Object> campos, Usuario usuario) {
		
		ObjectMapper objectMapper = new ObjectMapper();
		Usuario usuarioOrigem = objectMapper.convertValue(campos, Usuario.class);
		
		campos.forEach((nomePropiedade, valorPropiedade) -> {
			Field field = org.springframework.util.ReflectionUtils.findField(Usuario.class, nomePropiedade);
			field.setAccessible(true);
			
			Object novoValor = org.springframework.util.ReflectionUtils.getField(field, usuarioOrigem);
			org.springframework.util.ReflectionUtils.setField(field, usuario, novoValor);
		});
	}
	@GetMapping("/data")
	public String getData() {
		
		   String DATE_FORMAT_NOW = "yyyy/MM/dd HH:mm:ss";
	       SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
	    
		   Calendar cal = Calendar.getInstance();
		   Calendar data2 = Calendar.getInstance();
		   data2.set(2020, 9, 18);
	       System.out.println(sdf.format(cal.getTime()));
	       new Data().parse(new DataListener() {
			
			@Override
			public void onDataLoaded(String diferenca) {
				System.out.println(diferenca);
			}
			
			@Override
			public void onDataFailure(Exception e) {
				System.out.println(e.getMessage());
				
			}
		});
	       return "oi";
	}
}
