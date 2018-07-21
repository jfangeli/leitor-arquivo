package br.com.ntconsulting.leitorarquivo.model;

import java.math.BigDecimal;

public class VendaItem {

	private Long id;
	private Integer quantidade;
	private BigDecimal preco;

	public VendaItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	public VendaItem(Long id, Integer quantidade, BigDecimal preco) {
		super();
		this.id = id;
		this.quantidade = quantidade;
		this.preco = preco;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	@Override
	public String toString() {
		return "VendaItem [id=" + id + ", quantidade=" + quantidade + ", preco=" + preco + "]";
	}

}
