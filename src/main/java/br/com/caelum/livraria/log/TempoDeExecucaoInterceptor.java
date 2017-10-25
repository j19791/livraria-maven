package br.com.caelum.livraria.log;

import java.io.Serializable;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Interceptor
@Log
public class TempoDeExecucaoInterceptor implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Log
	@AroundInvoke
	public Object logarTempo(InvocationContext contexto) throws Exception {

		long antes = System.currentTimeMillis();

		Object resultado = contexto.proceed();

		long depois = System.currentTimeMillis();

		long tempo = depois - antes;

		System.out.println(contexto.getMethod().getName() + ": " + tempo);

		return resultado;

	}
}
