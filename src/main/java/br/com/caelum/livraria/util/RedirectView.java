package br.com.caelum.livraria.util;

//criar um tipo mais expressivo para indicar a View para o JSF.
public class RedirectView {

	private String viewName;

	public RedirectView(String viewName) {
		this.viewName = viewName;
	}

	// O controlador do JSF, ao encontrar um tipo diferente de String, chamará o
	// método toString() em busca do nome da View
	@Override
	public String toString() {
		return viewName + "?faces-redirect=true";
	}
}
