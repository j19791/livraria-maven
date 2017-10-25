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
	// produz um EntityManager (devolve um EntityManager novo), então sabe
	// criá-lo. O método é uma fábrica, aplicando o padrão factory method. CDI
	// chama esses métodos de fábrica de Producer.
	@RequestScoped
	// o CDI desejará saber quantas vezes queremos criar um EntityManager dentro
	// da aplicação: a cada requisição
	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public void close(@Disposes EntityManager em) {// @Disposes : chamar o
													// método quando a
													// requisição acaba. se
													// removermos um autor, não
													// conseguiremos gravar um
		// novo na mesma requisição, o que não pode acontecer! Só devemos fechar
		// o EntityManager depois da requisição.
		em.close();
	}
}
