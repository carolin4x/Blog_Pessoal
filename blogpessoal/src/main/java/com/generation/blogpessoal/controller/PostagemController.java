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
import com.generation.blogpessoal.model.Postagem;
import com.generation.blogpessoal.repository.PostagemRepository;

@RestController
@RequestMapping("/postagens")
@CrossOrigin(origins = "*", allowedHeaders = "*") 
	// isso libera qualquer origem e qlqr cabeçalho entre B-e e F-e. Em produção, no origins coloca o end do front end.
	//Origins -> libera a REQUISICAO Headers -> libera CABECALHO. Usando * libera de qlqr origem, em ambos
public class PostagemController {

	@Autowired
	private PostagemRepository postagemRepository;
	//injeção de dependência: inversão de controle, eu transfiro para o Spring a responsabilidade de criar e instanciar objetos
	
	@GetMapping //criar método
	public ResponseEntity<List<Postagem>> getAll(){ //vai devolver um objeto de resposta, e no corpo vai trazer varios objetos da classe postagem. /Precisamos de uma Colecctiona <List> pra trazer os objetos <Postagem>
		return ResponseEntity.ok(postagemRepository.findAll()); //o ok é para resposta 200: sucesso.
		
		/* este metodo é idem ao select * from tb_postagens; do MySQL */		
	}
	
	@GetMapping("/{id}") //o valor dentro das {} é uma variavel de caminho
	public ResponseEntity<Postagem> getById(@PathVariable Long id){ //ñ pode ter 2 gets iguais. o PathVariable pega a anotação {id} e coloca no Long id
		return postagemRepository.findById(id)
			.map(resposta -> ResponseEntity.ok(resposta)) //lambda é mto mais que um IF mas serve bem aqui, pra enxutar o codigo. 
			.orElse(ResponseEntity.notFound().build()); //lambda = (resposta -> o que retorna)
		/*insere um Optional. O map/orElse: funções do Opt*/	
	}
	
	@GetMapping ("/titulo/{titulo}") /*variavel titulo relacionada lá com a colecction da Interface*/
	public ResponseEntity<List<Postagem>> getByTitulo(@PathVariable String titulo){ 
		return ResponseEntity.ok(postagemRepository.findAllByTituloContainingIgnoreCase(titulo));
	}
	
	@PostMapping
	public ResponseEntity <Postagem> postPostagem(@Valid @RequestBody Postagem postagem) {
		/*estou enviando, nesse caso, um Objeto Postagem*/
		return ResponseEntity.status(HttpStatus.CREATED) /*status*/
				.body(postagemRepository.save(postagem)); /*corpo*/
		/*aqui eu salvo o objeto que inseri com postPostagem.*/ 
	}
	
//	@PutMapping
//	public ResponseEntity <Postagem> putPostagem(@Valid @RequestBody Postagem postagem) {
//		/*estou atualizando, nesse caso, o Objeto Postagem. ele sabe q é atualização com base no ID, que é único*/
//		return ResponseEntity.status(HttpStatus.OK) /*status*/
//				.body(postagemRepository.save(postagem)); /*corpo*/
//		/*aqui eu salvo o objeto que inseri com postPostagem.*/ 
//	} 
	
	@PutMapping //desafio 1
    public ResponseEntity<Postagem> putPostagem (@Valid @RequestBody Postagem postagem) {
        return postagemRepository.findById(postagem.getId()) // get puxa de um ID ja existente
                .map(resposta -> ResponseEntity.status(HttpStatus.OK)
                		.body(postagemRepository.save(postagem))) //realiza se resposta n for nulla
                .orElse(ResponseEntity.notFound().build()); //realiza se a resposta for nulla	
	}
	
	
	
	@DeleteMapping("/{id}") // wdesafio 2
	public ResponseEntity<?> deletePostagem(@PathVariable Long id) {
		
		return postagemRepository.findById(id)
				.map(resposta -> {
					postagemRepository.deleteById(id);
					return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
				})
				.orElse(ResponseEntity.notFound().build());
	}
/*	@DeleteMapping("/{id}")
*	@ResponseStatus(value = HttpStatus.NO_CONTENT)
*	public void deletePostagem(@PathVariable Long id) { 
*		Optional<Postagem> postagem = postagemRepository.findById(id);
*		if (postagem.isEmpty())
*				throw new ResponseStatusException(HttpStatus.NOT_FOUND);
*		
*		postagemRepository.deleteById(id);
*	}
*/	
	
	
//	@DeleteMapping("/{id}") /*ele n retorna nada, é do tipo void*/
//	public void  deletePostagem(@PathVariable Long id) { 
//		postagemRepository.deleteById(id); /*método de apagar tá pronto!*/
//	} 
		
}