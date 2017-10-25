package br.com.caelum.livraria.bean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
//import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.livraria.dao.UsuarioDao;
import br.com.caelum.livraria.modelo.Usuario;

//@ManagedBean
@Named
@ViewScoped
public class LoginBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Usuario usuario = new Usuario();

	public Usuario getUsuario() {
		return usuario;
	}

	@Inject
	UsuarioDao dao;

	@Inject
	FacesContext context; // quem precisa criar o FacesContext � o JSF, ent�o o
							// CDI n�o pode criar esse FacesContext. Vamos criar
							// um Producer.

	public String efetuaLogin() {
		System.out.println("Fazendo login do usu�rio "
				+ this.usuario.getEmail());

		// FacesContext context = FacesContext.getCurrentInstance(); injetado
		// boolean existe = new UsuarioDao().existe(this.usuario);
		boolean existe = this.dao.existe(this.usuario);

		System.out.println(existe);

		if (existe) {
			context.getExternalContext().getSessionMap()
					.put("usuarioLogado", this.usuario); // grava na httpsession
															// que o usuario
															// esta logado para
															// ser usado no
															// PhaseListener
															// Autorizador e o
															// nome do usuario
															// passado pelo form
															// de login
			return "livro?faces-redirect=true";
		}

		context.getExternalContext().getFlash().setKeepMessages(true); // escopo
																		// flash:
																		// dura
																		// duas
																		// requisi��es:
																		// mensagens
																		// de
																		// valida��o
																		// e
																		// redirecionamento
																		// p/
																		// limpar
																		// os
																		// dados
																		// do
																		// form
		context.addMessage(null, new FacesMessage("Usu�rio n�o encontrado"));// O
																				// primeiro
																				// par�metro
																				// �
																				// o
																				// nome
																				// do
																				// componente.
																				// Criando
																				// uma
																				// mensagem
																				// e
																				// associ�-la
																				// a
																				// um
																				// componente
																				// espec�fico,
																				// ele
																				// precisa
																				// ficar
																				// explicitado
																				// aqui.
																				// Queremos
																				// uma
																				// mensagem
																				// global,
																				// relacionada
																				// ao
																				// formul�rio,
																				// ent�o
																				// deixaremos
																				// o
																				// valor
																				// null

		return "login?faces-redirect=true"; // onde o usu�rio deve ser
											// redirecionado caso o login d�
		// falha. boa pr�tica: fazer um redirecionamento ap�s submeter um
		// formul�rio, para limpar os dados da requisi��o
	}

	public String deslogar() {

		// FacesContext context = FacesContext.getCurrentInstance();injetado
		context.getExternalContext().getSessionMap().remove("usuarioLogado");// sem
																				// a
																				// chave
																				// na
																				// sess�o,
																				// o
																				// nosso
																				// autorizador
																				// n�o
																				// permitir�
																				// o
																				// acesso
																				// �s
																				// p�ginas
																				// do
																				// sistema
		return "login?faces-redirect=true"; // qdo deslogar, ir para pagina de
											// login
	}
}