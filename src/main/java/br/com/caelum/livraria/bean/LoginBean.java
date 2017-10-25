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
	FacesContext context; // quem precisa criar o FacesContext é o JSF, então o
							// CDI não pode criar esse FacesContext. Vamos criar
							// um Producer.

	public String efetuaLogin() {
		System.out.println("Fazendo login do usuário "
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
																		// requisições:
																		// mensagens
																		// de
																		// validação
																		// e
																		// redirecionamento
																		// p/
																		// limpar
																		// os
																		// dados
																		// do
																		// form
		context.addMessage(null, new FacesMessage("Usuário não encontrado"));// O
																				// primeiro
																				// parâmetro
																				// é
																				// o
																				// nome
																				// do
																				// componente.
																				// Criando
																				// uma
																				// mensagem
																				// e
																				// associá-la
																				// a
																				// um
																				// componente
																				// específico,
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
																				// formulário,
																				// então
																				// deixaremos
																				// o
																				// valor
																				// null

		return "login?faces-redirect=true"; // onde o usuário deve ser
											// redirecionado caso o login dê
		// falha. boa prática: fazer um redirecionamento após submeter um
		// formulário, para limpar os dados da requisição
	}

	public String deslogar() {

		// FacesContext context = FacesContext.getCurrentInstance();injetado
		context.getExternalContext().getSessionMap().remove("usuarioLogado");// sem
																				// a
																				// chave
																				// na
																				// sessão,
																				// o
																				// nosso
																				// autorizador
																				// não
																				// permitirá
																				// o
																				// acesso
																				// às
																				// páginas
																				// do
																				// sistema
		return "login?faces-redirect=true"; // qdo deslogar, ir para pagina de
											// login
	}
}