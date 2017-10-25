package br.com.caelum.livraria.util;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import br.com.caelum.livraria.modelo.Usuario;

public class Autorizador implements PhaseListener {

	private static final long serialVersionUID = 1L;

	@Override
	public void afterPhase(PhaseEvent event) {
		// recuperar a árvore de componentes: precisamos ter o nome do arquivo
		// .xhtml que estamos acessando para
		// podermos verificar se é a página de login.
		FacesContext context = event.getFacesContext();
		String nomePagina = context.getViewRoot().getViewId();// pegando o nome
																// da pagina
																// acessada

		if ("/login.xhtml".equals(nomePagina)) { // nao faz nada pois o usuario
													// esta acessando a pagina
													// de login que não possui
													// autorização
			return;
		}

		Usuario usuarioLogado = (Usuario) context.getExternalContext()
				.getSessionMap().get("usuarioLogado"); // pega do httpSession a
														// indicação que o
														// usuário logou

		if (usuarioLogado != null) { // usuario logado, nao faz nada
			return;
		}

		// nao logado, redirecionar/navegar para pagina de login -
		// programaticamente
		NavigationHandler handler = context.getApplication()
				.getNavigationHandler();
		handler.handleNavigation(context, null, "/login?faces-redirect=true");
		// pular todas as fases do ciclo de vida, indo direto para
		// RenderResponse
		context.renderResponse();

	}

	@Override
	public void beforePhase(PhaseEvent event) {
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}
}