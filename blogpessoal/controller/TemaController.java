package com.generation.blogpessoal.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.blogpessoal.model.Tema;
import com.generation.blogpessoal.repository.TemaRepository;

@RestController //sinaliza que essa é uma classe controller
@RequestMapping("/temas") //dá o endereço na URL, ex: /temas
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TemaController {
	
	@Autowired
	private TemaRepository temaRepository;
	//injeção de dependência!!
	
	@GetMapping
	public ResponseEntity<List<Tema>> getAll() { 
		return ResponseEntity.ok(temaRepository.findAll());
	}
	
	@PostMapping
	public ResponseEntity<Tema> postTema(@Valid @RequestBody Tema tema) { 
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(temaRepository.save(tema)); 
	}
	
	@GetMapping("/{id}") 											//buscar os temas pelo ID
	public ResponseEntity<Tema>getById(@PathVariable Long id) {
		return temaRepository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta)) 
				.orElse(ResponseEntity.notFound().build());
		
	}
	
	@GetMapping ("/descricao/{descricao}") 						/*variavel descricao relacionada lá com a colecction da Interface*/
	public ResponseEntity<List<Tema>> getByTitulo(@PathVariable String descricao){ 
		return ResponseEntity.ok(temaRepository
				.findAllByDescricaoContainingIgnoreCase(descricao));
	}

	@PutMapping //desafio 1
    public ResponseEntity<Tema> putTema (@Valid @RequestBody Tema tema) {
       return temaRepository.findById(tema.getId()) // get puxa de um ID ja existente
              .map(resposta -> ResponseEntity.status(HttpStatus.OK)
                	.body(temaRepository.save(tema))) //realiza se resposta NÃO for nulla
                .orElse(ResponseEntity.notFound().build()); //realiza se a resposta for nulla	
	} 
	
	
/*	@PutMapping //desafio 1
    public boolean putTema1 (@Valid @RequestBody Tema tema) {
        return temaRepository.existsById(tema.getId())  // get puxa de um ID ja existente
                .map(resposta -> ResponseEntity.status(HttpStatus.OK)
               	.body(temaRepository.save(tema))) //realiza se resposta NÃO for nulla
               .orElse(ResponseEntity.notFound().build()); //realiza se a resposta for nulla	
	} */
	
		 
	@DeleteMapping("/{id}") // desafio 2
	public ResponseEntity<?> deleteTema(@PathVariable Long id) {
		return temaRepository.findById(id)
				.map(resposta -> {
					temaRepository.deleteById(id);
					return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
				})
				.orElse(ResponseEntity.notFound().build());
	}
}
