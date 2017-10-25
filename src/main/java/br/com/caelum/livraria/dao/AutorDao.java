package br.com.caelum.livraria.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.caelum.livraria.log.Log;
import br.com.caelum.livraria.modelo.Autor;

public class AutorDao implements Serializable {// o AutorBean sobrevive por mais
												// de uma requisi��o, ele e seus
												// atributos precisam
												// implementar a interface
												// Serializable

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	EntityManager em;// dependencia. @Inject = new EntityManager : mas
						// EntityManager � interface (abstract) n�o da para
						// instanciar
	private DAO<Autor> dao; // os m�todos j� foram implementados no DAO
							// gen�rico: utiliz�-los aqui atrav�s da delega��o

	@PostConstruct
	// o CDI deve chamar o m�todo init automaticamente quando instancia a classe
	// AutorDao
	void init() {
		this.dao = new DAO<Autor>(em, Autor.class);
	}

	@Log
	public List<Autor> listaTodos() {
		return this.dao.listaTodos();
	}

	@Log
	public void adiciona(Autor autor) {
		this.dao.adiciona(autor);

	}

	@Log
	public void atualiza(Autor autor) {
		this.dao.atualiza(autor);

	}

	@Log
	public void remove(Autor autor) {
		this.dao.remove(autor);

	}

	public Autor buscaPorId(Integer id) {

		return this.dao.buscaPorId(id);
	}

}
