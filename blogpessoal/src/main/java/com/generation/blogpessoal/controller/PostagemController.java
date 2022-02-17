package com.generation.blogpessoal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.blogpessoal.model.Postagem;
import com.generation.blogpessoal.repository.PostagemRepository;

@RestController
@RequestMapping("/postagens")
@CrossOrigin(origins = "*", allowedHeaders = "*") 
	// isso libera qualquer origem e qlqr cabeçalho entre B-e e F-e
	//em produção, no origins coloca o end do front end.
	//Origins -> libera a REQUISICAO Headers -> libera CABECALHO. Usando * libera de qlqr origem, em ambos
public class PostagemController {

	@Autowired
	private PostagemRepository postagemRepository;
	//injeção de dependência: inversão de controle, eu transfiro para o Spring a responsabilidade de criar e instanciar objetos
	
	@GetMapping //criar método
	public ResponseEntity<List<Postagem>> getAll(){ //vai devolver um objeto de resposta, e no corpo vai trazer varios objetos da classe postagem
		return ResponseEntity.ok(postagemRepository.findAll()); //o ok é para resposta 200: sucesso.
		
		/* este metodo é idem ao select * from tb_postagens; do MySQL */		
	}
	
	
}
