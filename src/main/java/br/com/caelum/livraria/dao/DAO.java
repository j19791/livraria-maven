package br.com.caelum.livraria.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

public class DAO<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final Class<T> classe;
	private EntityManager em; // EntityManager é dependência de todos os
								// métodos: dependência da classe

	public DAO(EntityManager em, Class<T> classe) {
		this.classe = classe;
		this.em = em;
	}

	public void adiciona(T t) {

		// consegue a entity manager
		// EntityManager em = new JPAUtil().getEntityManager(); utilizando cdi -
		// diminuir acoplamento do JPAUtil

		// abre transacao
		// em.getTransaction().begin(); passando o controle para o CDI no
		// GerenciadorDeTransacao, br.com.caelum.livraria.tx

		// persiste o objeto
		em.persist(t);

		// commita a transacao
		// em.getTransaction().commit(); invertendo o controle para o CDI

		// fecha a entity manager
		// em.close();
	}

	public void remove(T t) {
		// EntityManager em = new JPAUtil().getEntityManager(); utilizando cdi -
		// diminuir acoplamento do JPAUtil
		// em.getTransaction().begin();invertendo o controle para o CDI

		em.remove(em.merge(t));

		// em.getTransaction().commit(); invertendo o controle para o CDI
		// em.close(); //se removermos um autor, não conseguiremos gravar um
		// novo na mesma requisição, o que não pode acontecer! Só devemos fechar
		// o EntityManager depois da requisição.
	}

	public void atualiza(T t) {
		// EntityManager em = new JPAUtil().getEntityManager(); utilizando cdi -
		// diminuir acoplamento do JPAUtil
		// em.getTransaction().begin(); invertendo o controle para o CDI

		em.merge(t);

		// em.getTransaction().commit();invertendo o controle para o CDI
		// em.close();
	}

	public List<T> listaTodos() {
		// EntityManager em = new JPAUtil().getEntityManager(); utilizando cdi -
		// diminuir acoplamento do JPAUtil
		CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(classe);
		query.select(query.from(classe));

		List<T> lista = em.createQuery(query).getResultList();

		// em.close();
		return lista;
	}

	public T buscaPorId(Integer id) {
		// EntityManager em = new JPAUtil().getEntityManager(); utilizando cdi -
		// diminuir acoplamento do JPAUtil
		T instancia = em.find(classe, id);
		// em.close();
		return instancia;
	}

	public int contaTodos() {
		// EntityManager em = new JPAUtil().getEntityManager(); utilizando cdi -
		// diminuir acoplamento do JPAUtil
		long result = (Long) em.createQuery("select count(n) from livro n")
				.getSingleResult();
		// em.close();

		return (int) result;
	}

	public List<T> listaTodosPaginada(int firstResult, int maxResults) {
		// EntityManager em = new JPAUtil().getEntityManager(); utilizando cdi -
		// diminuir acoplamento do JPAUtil
		CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(classe);
		query.select(query.from(classe));

		List<T> lista = em.createQuery(query).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();

		// em.close();
		return lista;
	}

	public int quantidadeDeElementos() {
		// EntityManager em = new JPAUtil().getEntityManager(); utilizando cdi -
		// diminuir acoplamento do JPAUtil
		long result = (Long) em.createQuery(
				"select count(n) from " + classe.getSimpleName() + " n")
				.getSingleResult();
		// em.close();

		return (int) result;
	}

}
