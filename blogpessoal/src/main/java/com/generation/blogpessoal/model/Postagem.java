package com.generation.blogpessoal.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tb_postagens") // minha tabela lá no DB recebe este nome entre "", caso ela não seja declarada, o Banco criará a tabela com o mesmo nome da classe.//
public class Postagem { 

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "O atributo título é obrigatório!")
	@Size(min = 5, max = 100, message = "O atributo deve conter no mínimo 5 e no máximo 100 caract")
	private String titulo;

	@NotBlank(message = "O atributo texto é obrigatório!")
	@Size(min = 3, max = 1000, message = "O atributo texto deve conter no mínimo 5 e no máximo 1000 caract")
	private String texto;

	@UpdateTimestamp
	private LocalDateTime data;

	@ManyToOne // muitas postagens para UM tema
	@JsonIgnoreProperties("postagem") // desabilita a recursividade infinita durante a exibição dos dados no formato JSON (Desserialização).
	private Tema tema; // Atuará como a "chave estrangeira" da Classe Postagem na relação com a Classe Tema, além de exibir o tema da postagem

	
	/**
	 * Relacionamento com a classe Usuario
	 * Não esqueça de criar os métodos getters e setters para o atributo usuario.
	 */
	@ManyToOne
	@JsonIgnoreProperties("postagem")
	private Usuario usuario;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

	public Tema getTema() {
		return tema;
	}

	public void setTema(Tema tema) {
		this.tema = tema;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	

}
