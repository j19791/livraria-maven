package br.com.caelum.livraria.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.caelum.livraria.log.Log;
import br.com.caelum.livraria.modelo.Livro;

public class LivroDao implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	EntityManager em;// dependencia. @Inject = new EntityManager : mas
						// EntityManager é interface (abstract) não da para
						// instanciar
	private DAO<Livro> dao; // os métodos já foram implementados no DAO
							// genérico: utilizá-los aqui através da delegação

	@PostConstruct
	// o CDI deve chamar o método init automaticamente quando instancia a classe
	// LivroDao
	void init() {
		this.dao = new DAO<Livro>(em, Livro.class);
	}

	@Log
	public void adiciona(Livro t) {
		dao.adiciona(t);
	}

	@Log
	public void remove(Livro t) {
		dao.remove(t);
	}

	@Log
	public void atualiza(Livro t) {
		dao.atualiza(t);
	}

	@Log
	public List<Livro> listaTodos() {
		return dao.listaTodos();
	}

	public Livro buscaPorId(Integer id) {
		return dao.buscaPorId(id);
	}

	public int contaTodos() {
		return dao.contaTodos();
	}

	public List<Livro> listaTodosPaginada(int firstResult, int maxResults) {
		return dao.listaTodosPaginada(firstResult, maxResults);
	}

	public int quantidadeDeElementos() {
		return dao.quantidadeDeElementos();
	}

}
