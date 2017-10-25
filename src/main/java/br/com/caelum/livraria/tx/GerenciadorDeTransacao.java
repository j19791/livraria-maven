package br.com.caelum.livraria.tx;

import java.io.Serializable;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;

//Centralizar o gerenciamento das transações da aplicação
@Transacional
// CDI sabe que, ao encontrar a anotação @Transacional, deve chamar o método
// executaTX do GerenciadorDeTransacao
@Interceptor
// tipos de classe que fazem algo antes (no nosso caso, executar o begin) e
// depois (commit)
// sao configurados no beans.xml
public class GerenciadorDeTransacao implements Serializable {

	/**
	 * 
	 */

	@Inject
	EntityManager manager;

	private static final long serialVersionUID = 1L;

	@Transacional
	// anotação criada - existe para configurar nos métodos dos daos que é
	// preciso ter uma transação
	@AroundInvoke
	// o CDI, ao ver a anotação @Transacional, saberá o método que deve
	// executar. É executado algo antes e depois da chamada do método.
	public Object executaTX(InvocationContext contexto) throws Exception {

		System.out.println("Iniciando transação pelo GerenciadorDeTransacao");
		manager.getTransaction().begin();

		// continua a execução do método dos DAOs anotado com @Transacional
		Object resultado = contexto.proceed(); // O método proceed devolve um
												// objeto que representa o
												// possível retorno dos métodos
												// anotados

		manager.getTransaction().commit();
		System.out.println("Comitando transação pelo GerenciadorDeTransacao");

		return resultado;
	}
}
