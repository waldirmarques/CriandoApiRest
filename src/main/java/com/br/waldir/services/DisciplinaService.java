package com.br.waldir.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.br.waldir.domain.Disciplina;
import com.br.waldir.dto.DisciplinaDTO;
import com.br.waldir.repository.DisciplinaRepository;
import com.br.waldir.servives.exceptions.DataIntegrityException;
import com.br.waldir.servives.exceptions.ObjectNotFoundException;

@Service
public class DisciplinaService {
	
	@Autowired
	private DisciplinaRepository repo;
	
	public Disciplina find(Integer id) {
		Optional <Disciplina> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Disciplina.class.getName()));
	}

	@Transactional
	public Disciplina insert(Disciplina obj) {
		obj.setId(null);
		return repo.save(obj);
	}

	public Disciplina update(Disciplina obj) {
		find(obj.getId());
		return repo.save(obj);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivevel excluir uma Disciplina que possui Disciplinas");
		}
	}

	public List<Disciplina> findAll() {
		return repo.findAll();
	}

	public Page<Disciplina> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage , Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Disciplina fromDTO(@Valid DisciplinaDTO objDto) {
		return new Disciplina(objDto.getId(),objDto.getNome(),objDto.getNota());
	}
}
