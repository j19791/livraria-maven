package br.com.caelum.livraria.util;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;

public class JsfUtil {

	@Produces
	// precisamos mostrar para o CDI que esse método é um produtor
	@RequestScoped
	// queremos criar o FacesContext uma vez por requisição
	public FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}

}

// Mas quem precisa criar o FacesContext é o JSF, então o CDI não pode criar
// esse FacesContext. Então o que iremos fazer? Algo parecido com o que fizemos
// com o EntityManager, vamos criar um Producer.