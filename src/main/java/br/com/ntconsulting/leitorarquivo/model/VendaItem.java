package br.com.ntconsulting.leitorarquivo.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class VendaItem {

	@Id
	private Long id;
	private Integer quantidade;
	private BigDecimal preco;

	@ManyToOne
	@JoinColumn(name = "venda_id")
	private Venda venda;

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

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

	@Override
	public String toString() {
		return "VendaItem [id=" + id + ", quantidade=" + quantidade + ", preco=" + preco + ", venda=" + venda + "]";
	}

}
