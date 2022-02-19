package com.thislucasme.connectsly.app.domain.model.repository;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.thislucasme.connectsly.app.domain.model.model.Usuario;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	@Query(value = "SELECT * FROM usuario where id_hash_from_firebase not in (select id_hash_from_firebase from usuario where id_hash_from_firebase = :idHash) and nome_pais_en in (select nome_pais_en from usuario where nome_pais_en = :nomePaisEn) and sexo in (select sexo from usuario where sexo = :sexo) ORDER BY RAND() LIMIT :quantidade", nativeQuery = true)
	java.util.List<Usuario> getRamdomUsersFilter(String idHash, String nomePaisEn, String sexo, int quantidade);
	
	@Query(value = "SELECT * FROM usuario where id_hash_from_firebase not in (select id_hash_from_firebase from usuario where id_hash_from_firebase = :idHash)  ORDER BY RAND() LIMIT :quantidade", nativeQuery = true)
	java.util.List<Usuario> getRandomUsers(String idHash, int quantidade);
	
	@Query(value = "select * from usuario where id_hash_from_firebase = :idHash limit 1", nativeQuery = true)
	Optional<Usuario> findByIdHash(String idHash);
	
//	@Query(value = "SELECT * FROM usuario where id_hash_from_firebase not in (select id_hash_from_firebase from usuario where id_hash_from_firebase = :idHash) and nome_pais_en in (select nome_pais_en from usuario where nome_pais_en = :nomePaisEn) and sexo in (select sexo from usuario where sexo = :sexo)  and data between(now() - interval 3 day) and now() ORDER BY RAND() LIMIT :quantidade", nativeQuery = true)
//	java.util.List<Usuario> getRamdomUsersFilterRecent(String idHash, String nomePaisEn, String sexo, int quantidade, boolean recent);
//	
	
	@Transactional
	@Modifying
	@Query(value = "delete from usuario where id_hash_from_firebase = :idHash", nativeQuery = true)
	void deleteByIdHash(String idHash);
	
}
