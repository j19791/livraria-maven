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
	// para armazenar tamb�m o hor�rio al�m da data
	private Calendar dataLancamento = Calendar.getInstance(); // Iniciaremos o
																// atributo com
																// a data atual

	@ManyToMany
	// (fetch = FetchType.EAGER)//EAGER - m� pratica - nem sempre qd carregamos
	// um livro, queremos carregar os autores, e isso pode gerar um custo de
	// mem�ria muito grande, em um projeto maior
	// voltando ao padr�o LAZY , n�o precisa declarar. ao carregar o livro, n�o
	// ser�o carregados automaticamente os autores. O problema que isso gerar�
	// para n�s � que, ao tentar carregar um livro para alter�-lo, seus autores
	// n�o estar�o carregados, ou seja, n�o conseguiremos exibi-los, por isso
	// receberemos um LazyInitializationException
	// toMany � pregui�oso (lazy): carrega livros mas n�o carrega autores.
	// Utilizando Eager, tbm carregar� os autores e evitar� a exce��o
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