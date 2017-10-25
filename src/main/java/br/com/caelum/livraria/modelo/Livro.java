package br.com.caelum.livraria.modelo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Livro {

	@Id
	@GeneratedValue
	private Integer id;

	private String titulo;
	private String isbn;
	private double preco;
	// private String dataLancamento;
	@Temporal(TemporalType.TIMESTAMP)
	// para armazenar também o horário além da data
	private Calendar dataLancamento = Calendar.getInstance(); // Iniciaremos o
																// atributo com
																// a data atual

	@ManyToMany
	// (fetch = FetchType.EAGER)//EAGER - má pratica - nem sempre qd carregamos
	// um livro, queremos carregar os autores, e isso pode gerar um custo de
	// memória muito grande, em um projeto maior
	// voltando ao padrão LAZY , não precisa declarar. ao carregar o livro, não
	// serão carregados automaticamente os autores. O problema que isso gerará
	// para nós é que, ao tentar carregar um livro para alterá-lo, seus autores
	// não estarão carregados, ou seja, não conseguiremos exibi-los, por isso
	// receberemos um LazyInitializationException
	// toMany é preguiçoso (lazy): carrega livros mas não carrega autores.
	// Utilizando Eager, tbm carregará os autores e evitará a exceção
	private List<Autor> autores = new ArrayList<Autor>();

	public List<Autor> getAutores() {
		return autores;
	}

	public void adicionaAutor(Autor autor) {
		this.autores.add(autor);
	}

	public void removeAutor(Autor autor) {
		this.autores.remove(autor);
	}

	public Livro() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public Calendar getDataLancamento() {
		return dataLancamento;
	}

	public void setDataLancamento(Calendar dataLancamento) {
		this.dataLancamento = dataLancamento;
	}

}