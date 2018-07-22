package br.com.ntconsulting.leitorarquivo.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "venda_item")
public class VendaItem {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name="id_item")
	private Long idItem;
	private Integer quantidade;
	private BigDecimal preco;

	@ManyToOne
	@JoinColumn(name = "venda_id")
	private Venda venda;

	public VendaItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	public VendaItem(Long idItem, Integer quantidade, BigDecimal preco) {
		super();
		this.idItem = idItem;
		this.quantidade = quantidade;
		this.preco = preco;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdItem() {
		return idItem;
	}

	public void setIdItem(Long idItem) {
		this.idItem = idItem;
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
		return "VendaItem [id=" + id + ", idItem=" + idItem + ", quantidade=" + quantidade + ", preco=" + preco + "]";
	}

}
