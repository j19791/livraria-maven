package br.com.caelum.livraria.bean;

import java.util.HashMap;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;

import org.primefaces.context.RequestContext;

@ManagedBean
public class AuditoriaBean {

	public void visualizarAuditoria() {
		System.out.println("entrou1");

		Map<String, Object> options = new HashMap<String, Object>();
		options.put("resizable", false);
		RequestContext.getCurrentInstance().openDialog("auditoriaInformacaoResolucaoPopUp", options, null);

		System.out.println("entrou2");
		System.out.println("entrou3");
	}

	public void showMessage() {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "What we do in life",
				"Echoes in eternity.");

		RequestContext.getCurrentInstance().showMessageInDialog(message);
	}

}
