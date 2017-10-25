package br.com.caelum.livraria.dao;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

	private static EntityManagerFactory emf = Persistence
			.createEntityManagerFactory("livraria-maven");

	@Produces
	// Queremos que o CDI tome conta desse EntityManager. getEntityManager
	// produz um EntityManager (devolve um EntityManager novo), ent�o sabe
	// cri�-lo. O m�todo � uma f�brica, aplicando o padr�o factory method. CDI
	// chama esses m�todos de f�brica de Producer.
	@RequestScoped
	// o CDI desejar� saber quantas vezes queremos criar um EntityManager dentro
	// da aplica��o: a cada requisi��o
	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public void close(@Disposes EntityManager em) {// @Disposes : chamar o
													// m�todo quando a
													// requisi��o acaba. se
													// removermos um autor, n�o
													// conseguiremos gravar um
		// novo na mesma requisi��o, o que n�o pode acontecer! S� devemos fechar
		// o EntityManager depois da requisi��o.
		em.close();
	}
}
