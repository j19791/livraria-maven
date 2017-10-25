package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import javax.faces.application.FacesMessage;
//import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.livraria.dao.AutorDao;
import br.com.caelum.livraria.dao.LivroDao;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.modelo.Livro;
import br.com.caelum.livraria.tx.Transacional;

//escopo de requisi��o.
//cada request causa a cria��o de um novo LivroBean ,a vida do Managed Bean dura apenas um request.
//Queremos que o LivroBean exista enquanto a tela existir. ViewScoped sobrevive a v�rios requests.
//@ManagedBean
@Named
@ViewScoped
public class LivroBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Livro livro = new Livro();

	// private LivroDataModel livroDataModel = new LivroDataModel();

	private List<Livro> livros; // para utilizar no cache (ordena��o da tabela
								// do primefaces)

	public Livro getLivro() {
		return livro;
	}

	/* para gravar o autor vindo do combobox */
	private Integer autorId;

	@Inject
	private AutorDao autorDao;

	@Inject
	private LivroDao livroDao;

	@Inject
	FacesContext context;

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}

	public Integer getAutorId() {
		return autorId;
	}

	public void gravarAutor() {
		// Autor autor = new DAO<Autor>(Autor.class).buscaPorId(this.autorId);
		Autor autor = this.autorDao.buscaPorId(this.autorId);
		this.livro.adicionaAutor(autor);
	}

	public List<Autor> getAutores() {
		System.out.println("teste");
		// return new DAO<Autor>(Autor.class).listaTodos();
		return this.autorDao.listaTodos();
	}

	public List<Autor> getAutoresDoLivro() {
		return this.livro.getAutores();
	}

	// m�todo getLivros � chamado cada vez que clicamos em alguma coluna para
	// ordena��o => usar cache
	public List<Livro> getLivros() {
		// DAO<Livro> dao = new DAO<Livro>(Livro.class);

		if (this.livros == null) {
			this.livros = this.livroDao.listaTodos();// se o cache estiver vazio
														// (abertura
			// da pagina), popula cache a partir
			// do banco
		}
		// sen�o, utiliza lista ja carregada do cache (somente para ordena��o da
		// tabela de livros)

		return livros;
	}

	@Transacional
	public void gravar() {// o metodo gravar serve para adicionar e atualizar
		System.out.println("Gravando livro " + this.livro.getTitulo());

		if (livro.getAutores().isEmpty()) {
			context.addMessage("autor", new FacesMessage(
					"Livro deve ter pelo menos um Autor"));
			return;
		} else {
			// new DAO<Livro>(Livro.class).adiciona(this.livro);
			// DAO<Livro> dao = new DAO<Livro>(Livro.class);
			if (this.livro.getId() == null) {
				this.livroDao.adiciona(this.livro); // livro sem
				// id (novo
				// livro
				// preenchido
				// no form
				// =>
				// adiciona
				this.livros = this.livroDao.listaTodos();// atualiza cache (para
															// ordena��o
				// da tabela de livros) ao
				// inserir novo
				// livro

			} else {
				this.livroDao.atualiza(this.livro); // livro com
				// id (livro
				// carregado
				// no form
				// p/
				// atualiza��o
			}
			this.livro = new Livro();// limpa o formulario de cadastro de livros
		}
	}

	@Transacional
	public void remover(Livro livro) {
		System.out.println("Removendo livro " + livro.getTitulo());
		// DAO<Livro> dao = new DAO<Livro>(Livro.class);
		this.livroDao.remove(livro);
		this.livros = this.livroDao.listaTodos();
	}

	public void carregar(Livro livro) {
		System.out.println("Carregando livro " + livro.getTitulo());
		this.livro = this.livroDao.buscaPorId(livro.getId());
		// colocamos o livro da tabela no nosso formul�rio, e � esse livro da
		// tabela n�o ter� os autores carregados (JPA lazy). Por isso vamos
		// carregar
		// explicitamente o livro da tabela, atrav�s de uma nova conex�o,
		// utilizando o LivroDao. Nessa nova conex�o, carregando o livro
		// novamente, o JPA conseguir� carregar os autore
		// this.livro = livro; // carrega o formulario com os dados do livro que
		// quero atualizar
	}

	public void removerAutorDoLivro(Autor autor) {
		this.livro.removeAutor(autor);
	}

	// validadores personalizados: testar se o valor digitado come�a com um,
	// caso contr�rio, ser� lan�ada a exce��o
	public void comecaComDigitoUm(FacesContext fc, // um objeto que permite
													// obter informa��es da view
													// processada no momento
			UIComponent component,// componente da view que est� sendo validado
			Object value) // valor digitado pelo usu�rio
			throws ValidatorException {

		String valor = value.toString();
		if (!valor.startsWith("1")) {
			throw new ValidatorException(new FacesMessage(
					"Deveria come�ar com 1"));
		}
	}

	// utilizando navega��o via comandlink e action com expression language
	public String formAutor() {
		System.out.println("Chamanda o formulario do Autor");
		return "autor?faces-redirect=true"; // pagina que desejamos acessar e
											// redirecionamento pelo navegador,
											// no lado do cliente
	}

	public void carregaPeloId() {
		Integer id = this.livro.getId();
		// this.livro � o conteudo do formul�rio quando carregada a p�gina
		// this.livro = new DAO<Livro>(Livro.class).buscaPorId(id);
		this.livro = this.livroDao.buscaPorId(id);
		if (this.livro == null) {
			this.livro = new Livro();
		}
	}

	public boolean precoEhMenor(Object valorColuna, Object filtroDigitado,
			Locale locale) { // java.util.Locale

		// tirando espa�os do filtro
		String textoDigitado = (filtroDigitado == null) ? null : filtroDigitado
				.toString().trim();

		System.out.println("Filtrando pelo " + textoDigitado
				+ ", Valor do elemento: " + valorColuna);

		// o filtro � nulo ou vazio?
		if (textoDigitado == null || textoDigitado.equals("")) {
			return true;
		}

		// elemento da tabela � nulo?
		if (valorColuna == null) {
			return false;
		}

		try {
			// fazendo o parsing do filtro para converter para Double
			Double precoDigitado = Double.valueOf(textoDigitado);
			Double precoColuna = (Double) valorColuna;

			// comparando os valores, compareTo devolve um valor negativo se o
			// value � menor do que o filtro
			return precoColuna.compareTo(precoDigitado) < 0;

		} catch (NumberFormatException e) {

			// usuario nao digitou um numero
			return false;
		}
	}

	/*
	 * public LivroDataModel getLivroDataModel() { return livroDataModel; }
	 */

}
