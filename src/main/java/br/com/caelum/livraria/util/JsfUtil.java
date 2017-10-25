package br.com.caelum.livraria.util;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;

public class JsfUtil {

	@Produces
	// precisamos mostrar para o CDI que esse m�todo � um produtor
	@RequestScoped
	// queremos criar o FacesContext uma vez por requisi��o
	public FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}

}

// Mas quem precisa criar o FacesContext � o JSF, ent�o o CDI n�o pode criar
// esse FacesContext. Ent�o o que iremos fazer? Algo parecido com o que fizemos
// com o EntityManager, vamos criar um Producer.