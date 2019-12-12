package com.br.waldir.resources;

import java.net.URI;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.br.waldir.domain.Disciplina;
import com.br.waldir.dto.DisciplinaDTO;
import com.br.waldir.services.DisciplinaService;

import javassist.tools.rmi.ObjectNotFoundException;

@RestController
@RequestMapping(value="/disciplinas")
public class DisciplinaResources {

	@Autowired
	private DisciplinaService service;
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<Disciplina> find(@PathVariable Integer id){
		Disciplina obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}
	
	
	@RequestMapping(method=RequestMethod.POST) //add a new admin
	public ResponseEntity<Void> insert(@Valid @RequestBody DisciplinaDTO objDto){
		Disciplina obj = service.fromDTO(objDto);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").
				buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.PUT) //update an entire ADM
	public ResponseEntity<Void> update(@Valid @RequestBody DisciplinaDTO objDto,@PathVariable Integer id) throws ObjectNotFoundException{
		Disciplina obj = service.fromDTO(objDto);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE) //Delete ADM
	public ResponseEntity<Void> delete(@PathVariable Integer id) throws ObjectNotFoundException {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method = RequestMethod.GET) //list all ADM
	public ResponseEntity<List<DisciplinaDTO>> findPage() {
		List<Disciplina> list = service.findAll();
		List<DisciplinaDTO> listDTO = list.stream().map(obj -> new DisciplinaDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}
	
	@RequestMapping(value="/ranking", method = RequestMethod.GET) //list all ADM
	public ResponseEntity<List<DisciplinaDTO>> findPageRanking() {
		List<Disciplina> list = service.findAll();
		
		list.sort(Comparator.comparing(Disciplina::getNota)); //Retorna a lista ordenada seguindo um parametro
		
		List<DisciplinaDTO> listDTO = list.stream().map(obj -> new DisciplinaDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}
	
	@RequestMapping(value = "/page", method = RequestMethod.GET) //lists all paged ADMs
	public ResponseEntity<Page<DisciplinaDTO>> findAll(
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC") String direction){
		Page<Disciplina> list = service.findPage(page, linesPerPage, orderBy, direction);
		Page<DisciplinaDTO> listDTO = list.map(obj -> new DisciplinaDTO(obj));
		return ResponseEntity.ok().body(listDTO);
	}


}
