package br.com.caelum.livraria.tx;

import java.io.Serializable;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;

//Centralizar o gerenciamento das transa��es da aplica��o
@Transacional
// CDI sabe que, ao encontrar a anota��o @Transacional, deve chamar o m�todo
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
	// anota��o criada - existe para configurar nos m�todos dos daos que �
	// preciso ter uma transa��o
	@AroundInvoke
	// o CDI, ao ver a anota��o @Transacional, saber� o m�todo que deve
	// executar. � executado algo antes e depois da chamada do m�todo.
	public Object executaTX(InvocationContext contexto) throws Exception {

		System.out.println("Iniciando transa��o pelo GerenciadorDeTransacao");
		manager.getTransaction().begin();

		// continua a execu��o do m�todo dos DAOs anotado com @Transacional
		Object resultado = contexto.proceed(); // O m�todo proceed devolve um
												// objeto que representa o
												// poss�vel retorno dos m�todos
												// anotados

		manager.getTransaction().commit();
		System.out.println("Comitando transa��o pelo GerenciadorDeTransacao");

		return resultado;
	}
}
