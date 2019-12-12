package com.br.waldir.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.waldir.domain.Disciplina;
import com.br.waldir.repository.DisciplinaRepository;

@Service
public class DBService {
		
	@Autowired
	private DisciplinaRepository disRep;

	public boolean instantiateDatabase(){
		
		Disciplina disciplina = new Disciplina(null,"Projeto Avançado de Software",9.8);
		
		
		disRep.saveAll(Arrays.asList(disciplina));
		
		return true;
		
	}
}

