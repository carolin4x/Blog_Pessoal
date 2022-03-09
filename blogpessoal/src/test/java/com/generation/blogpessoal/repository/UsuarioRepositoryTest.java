package com.generation.blogpessoal.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.generation.blogpessoal.model.Usuario;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioRepositoryTest {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@BeforeAll //será a 1ª coisa a ser executada dos testes
	void start() {

		usuarioRepository.deleteAll(); // delete sem where do MySQL. Preciso matar mey DB antes de começar a testar

		// aqui criamos o metoo contrutor a moda antiga,
		usuarioRepository.save(
				new Usuario(0L, "João da Silva", "joao@email.com.br", "13465278", "https://i.imgur.com/FETvs2O.jpg"));

		usuarioRepository.save(new Usuario(0L, "Manuela da Silva", "manuela@email.com.br", "13465278",
				"https://i.imgur.com/NtyGneo.jpg"));

		usuarioRepository.save(new Usuario(0L, "Adriana da Silva", "adriana@email.com.br", "13465278",
				"https://i.imgur.com/mB3VM2N.jpg"));

		usuarioRepository.save(
				new Usuario(0L, "Paulo Antunes", "paulo@email.com.br", "13465278", "https://i.imgur.com/JR7kUFU.jpg"));

	}
	
	@Test // método de teste
	@DisplayName("👻Retorna 1 usuario") // 
	public void deveRetornarUmUsuario() { //void só diz se deu certo ou não. O nome deve ser objetivo e assertivo.

		// assertTrue verifica se o usuario é True, se o usuário existe o teste passou. 
		Optional<Usuario> usuario = usuarioRepository.findByUsuario("joao@email.com.br"); //com Optional ele pode retornar um usuario como pode nao retornar nada
		assertTrue(usuario.get().getUsuario().equals("joao@email.com.br"));
	}
	
	@Test
	@DisplayName("Retorna 3 usuarios")
	public void deveRetornarTresUsuarios() {

		//assertEquals veriica se tem exatamente a quantidade que eu passei. 
		//assertTrue verifica se a lista esta retornando na posição solicitada, ex: get(1) é a Manuela
		List<Usuario> listaDeUsuarios = usuarioRepository.findAllByNomeContainingIgnoreCase("Silva");
		assertEquals(3, listaDeUsuarios.size());
		assertTrue(listaDeUsuarios.get(0).getNome().equals("João da Silva"));
		assertTrue(listaDeUsuarios.get(1).getNome().equals("Manuela da Silva"));
		assertTrue(listaDeUsuarios.get(2).getNome().equals("Adriana da Silva"));
		
	}
}
