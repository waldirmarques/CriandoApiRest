package com.br.waldir.dto;

import java.io.Serializable;

import com.br.waldir.domain.Disciplina;

public class DisciplinaDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String nome;
	private Double nota;
	
	public DisciplinaDTO() {
	
	}
	
	public DisciplinaDTO(Disciplina disciplina) {
		super();
		this.id = disciplina.getId();
		this.nome = disciplina.getNome();
		this.nota = disciplina.getNota();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Double getNota() {
		return nota;
	}
	public void setNota(Double nota) {
		this.nota = nota;
	}

}
