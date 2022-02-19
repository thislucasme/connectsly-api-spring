package com.thislucasme.connectsly.app.domain.model.model;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;

@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Usuario {
	
	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	public Long id;
	public String idHashFromFirebase;
	public String nome;
	
	private String nome_pais_en;
	private String sexo;
	private String url_Foto;
	private String data;
	private Date dataSql;
	public Long getId() {
		return id;
	}
	
	public String getData() {
		return data;
	}
	

	public Date getDataSql() {
		return dataSql;
	}

	public void setDataSql(Date dataSql) {
		this.dataSql = dataSql;
	}

	public void setData(String data) {
		this.data = data;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public String getIdHashFromFirebase() {
		return idHashFromFirebase;
	}
	public void setIdHashFromFirebase(String idHashFromFirebase) {
		this.idHashFromFirebase = idHashFromFirebase;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getNome_pais_en() {
		return nome_pais_en;
	}
	public void setNome_pais_en(String nome_pais_en) {
		this.nome_pais_en = nome_pais_en;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public String getUrl_Foto() {
		return url_Foto;
	}
	public void setUrl_Foto(String url_Foto) {
		this.url_Foto = url_Foto;
	}

	
	
	
	
	
}
