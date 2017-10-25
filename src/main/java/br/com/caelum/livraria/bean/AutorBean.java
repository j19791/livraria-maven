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

//@ManagedBean --anota��o do bean gerenciado pelo jsf
@Named
// anota��o do bean gerenciado pelo cdi
@ViewScoped
// anota��o do javax.faces.view.ViewScoped e n�o mais do
// javax.faces.bean.ViewScoped
public class AutorBean implements Serializable {// O CDI exige pela
												// especifica��o que qq Bean que
												// esteja em um escopo maior do
												// que a requisi��o (ViewScoped
												// ou SessionScoped) implemente
												// a interface Serializable. o
												// CDI usa passiva��o q � gravar
												// os dados de um objeto no
												// disco quando o Tomcat se
												// reinicializa.

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Autor autor = new Autor();

	@Inject
	// inje��o de depend�ncias: O CDI inverte o controle: resolver a depend�ncia
	// e injet�-la. A classe AutorBean n�o d� mais um new no DAO. O CDI est� no
	// controle, busca e injeta a depend�ncia.
	// quem vai cri�-lo � o CDI. O CDI vai injetar o DAO! Passar uma inst�ncia
	// pronta para usar. Quando o CDI v� anota��o, ele saber� que o bean precisa
	// de um DAO e consequentemente criar� uma inst�ncia dele e a
	// disponibilizar�
	// private DAO<Autor> dao; // todos os metodos dependem de dao: dependencia
	// de
	// classe
	private AutorDao dao;

	// nao funciona construtor passando parametros com CDI - CDI exige
	// construtor padr�o!!
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
		return this.dao.listaTodos();// utilizando o atributo que � atribuido
										// pelo construtor
	}

	@Transacional
	// sinalizam que precisam de uma transa��o controlada pelo CDI (que precisam
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
	// //sinalizam que precisam de uma transa��o controlada pelo CDI (que
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
		// this.autor � o conteudo do formul�rio quando carregada a p�gina
		// this.autor = new DAO<Autor>(Autor.class).buscaPorId(id);
		this.autor = this.dao.buscaPorId(id);
		if (this.autor == null) {
			this.autor = new Autor();
		}
	}
}
