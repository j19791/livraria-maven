package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.List;

//import javax.faces.bean.ViewScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.livraria.dao.AutorDao;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.tx.Transacional;
import br.com.caelum.livraria.util.RedirectView;

//@ManagedBean --anotação do bean gerenciado pelo jsf
@Named
// anotação do bean gerenciado pelo cdi
@ViewScoped
// anotação do javax.faces.view.ViewScoped e não mais do
// javax.faces.bean.ViewScoped
public class AutorBean implements Serializable {// O CDI exige pela
												// especificação que qq Bean que
												// esteja em um escopo maior do
												// que a requisição (ViewScoped
												// ou SessionScoped) implemente
												// a interface Serializable. o
												// CDI usa passivação q é gravar
												// os dados de um objeto no
												// disco quando o Tomcat se
												// reinicializa.

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Autor autor = new Autor();

	@Inject
	// injeção de dependências: O CDI inverte o controle: resolver a dependência
	// e injetá-la. A classe AutorBean não dá mais um new no DAO. O CDI está no
	// controle, busca e injeta a dependência.
	// quem vai criá-lo é o CDI. O CDI vai injetar o DAO! Passar uma instância
	// pronta para usar. Quando o CDI vê anotação, ele saberá que o bean precisa
	// de um DAO e consequentemente criará uma instância dele e a
	// disponibilizará
	// private DAO<Autor> dao; // todos os metodos dependem de dao: dependencia
	// de
	// classe
	private AutorDao dao;

	// nao funciona construtor passando parametros com CDI - CDI exige
	// construtor padrão!!
	/*
	 * public AutorBean(DAO<Autor> dao) { this.dao = dao; }
	 */

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	public List<Autor> getAutores() {
		// return new DAO<Autor>(Autor.class).listaTodos();
		return this.dao.listaTodos();// utilizando o atributo que é atribuido
										// pelo construtor
	}

	@Transacional
	// sinalizam que precisam de uma transação controlada pelo CDI (que precisam
	// da nossa classe GerenciadorDeTransacao
	// public String gravar() {
	public RedirectView gravar() {
		System.out.println("Gravando autor " + this.autor.getNome());
		if (this.autor.getId() == null) {
			// new DAO<Autor>(Autor.class).adiciona(this.autor);
			this.dao.adiciona(this.autor);
		} else
			// new DAO<Autor>(Autor.class).atualiza(this.autor);
			this.dao.atualiza(this.autor);

		// return "livro?faces-redirect=true";
		return new RedirectView("livro");
	}

	@Transacional
	// //sinalizam que precisam de uma transação controlada pelo CDI (que
	// precisam da nossa classe GerenciadorDeTransacao
	public void remover(Autor autor) {
		// new DAO<Autor>(Autor.class).remove(autor);
		this.dao.remove(autor);
	}

	public void carregar(Autor autor) {

		this.autor = autor; // carrega o formulario com os dados do livro que
							// quero atualizar
	}

	public void carregaPelaId() {
		Integer id = this.autor.getId();
		// this.autor é o conteudo do formulário quando carregada a página
		// this.autor = new DAO<Autor>(Autor.class).buscaPorId(id);
		this.autor = this.dao.buscaPorId(id);
		if (this.autor == null) {
			this.autor = new Autor();
		}
	}
}
