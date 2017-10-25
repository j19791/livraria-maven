package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
//import javax.faces.bean.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import br.com.caelum.livraria.modelo.Venda;

//@ManagedBean
@Named
@ViewScoped
public class VendasBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// @Inject
	// LivroDao livroDao;

	@Inject
	EntityManager manager;

	// public List<Venda> getVendas(long seed) {
	public List<Venda> getVendas() {

		// List<Livro> livros = new DAO<Livro>(Livro.class).listaTodos();
		// List<Livro> livros = this.livroDao.listaTodos();
		// List<Venda> vendas = new ArrayList<Venda>();

		// Random recebe um seed p/ sempre gerar um valor n�o repetido e em uma
		// sequ�ncia. Como queremos sempre os mesmos valores rand�micos, fixos,
		// vamos fixar o valor do seed em 1234.
		/*
		 * Random random = new Random(seed);
		 * 
		 * for (Livro livro : livros) { Integer quantidade =
		 * random.nextInt(500);// nextInt serve p/ gerar // um no. rand�mico //
		 * inteiro p/ n�s, mas // precisamos dizer qual // ser� o valor m�ximo
		 * // desse n�mero: 500 vendas.add(new Venda(livro, quantidade)); // o
		 * que ser� a // quantidade? }
		 */

		List<Venda> vendas = this.manager.createQuery("select v from Venda v",
				Venda.class).getResultList();

		return vendas;

	}

	// model do chart
	public BarChartModel getVendasModel() {

		BarChartModel model = new BarChartModel();
		model.setAnimate(true);

		ChartSeries vendaSerie = new ChartSeries();
		vendaSerie.setLabel("Vendas 2016");

		// List<Venda> vendas = getVendas(1234);
		List<Venda> vendas = getVendas();

		for (Venda venda : vendas) {
			vendaSerie.set(venda.getLivro().getTitulo(), venda.getQuantidade());
		}

		model.addSeries(vendaSerie);

		model.setTitle("Vendas"); // setando o t�tulo do gr�fico
		model.setLegendPosition("ne"); // nordeste

		// pegando o eixo X do gr�fico e setando o t�tulo do mesmo
		Axis xAxis = model.getAxis(AxisType.X);
		xAxis.setLabel("T�tulo");

		// pegando o eixo Y do gr�fico e setando o t�tulo do mesmo
		Axis yAxis = model.getAxis(AxisType.Y);
		yAxis.setLabel("Quantidade");

		return model;
	}
}