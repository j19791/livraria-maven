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

		// Random recebe um seed p/ sempre gerar um valor não repetido e em uma
		// sequência. Como queremos sempre os mesmos valores randômicos, fixos,
		// vamos fixar o valor do seed em 1234.
		/*
		 * Random random = new Random(seed);
		 * 
		 * for (Livro livro : livros) { Integer quantidade =
		 * random.nextInt(500);// nextInt serve p/ gerar // um no. randômico //
		 * inteiro p/ nós, mas // precisamos dizer qual // será o valor máximo
		 * // desse número: 500 vendas.add(new Venda(livro, quantidade)); // o
		 * que será a // quantidade? }
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

		model.setTitle("Vendas"); // setando o título do gráfico
		model.setLegendPosition("ne"); // nordeste

		// pegando o eixo X do gráfico e setando o título do mesmo
		Axis xAxis = model.getAxis(AxisType.X);
		xAxis.setLabel("Título");

		// pegando o eixo Y do gráfico e setando o título do mesmo
		Axis yAxis = model.getAxis(AxisType.Y);
		yAxis.setLabel("Quantidade");

		return model;
	}
}