package br.com.ntconsulting.leitorarquivo.model;

import java.util.List;

public class Venda {

	private String arquivo;
	private Long id;
	private Vendedor vendedor;
	private List<VendaItem> itens;

	public Venda() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Venda(String arquivo, Long id, Vendedor vendedor, List<VendaItem> itens) {
		super();
		this.arquivo = arquivo;
		this.id = id;
		this.vendedor = vendedor;
		this.itens = itens;
	}

	public String getArquivo() {
		return arquivo;
	}

	public void setArquivo(String arquivo) {
		this.arquivo = arquivo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Vendedor getVendedor() {
		return vendedor;
	}

	public void setVendedor(Vendedor vendedor) {
		this.vendedor = vendedor;
	}

	public List<VendaItem> getItens() {
		return itens;
	}

	public void setItens(List<VendaItem> itens) {
		this.itens = itens;
	}

	@Override
	public String toString() {
		return "Venda [arquivo=" + arquivo + ", id=" + id + ", vendedor=" + vendedor + ", itens=" + itens + "]";
	}

}
