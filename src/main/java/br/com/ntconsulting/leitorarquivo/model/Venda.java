package br.com.ntconsulting.leitorarquivo.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Venda {

	@Id
	@GeneratedValue
	private Long id;

	private Long idVenda;
	private String nomeVendedor;

	@OneToMany(mappedBy = "venda", cascade = CascadeType.ALL)
	private List<VendaItem> itens;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "arquivo_id", updatable = false, insertable = true)
	private Arquivo arquivo;

	public Venda() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Venda(Long idVenda, String nomeVendedor) {
		super();
		this.idVenda = idVenda;
		this.nomeVendedor = nomeVendedor;
	}

	public Long getIdVenda() {
		return idVenda;
	}

	public void setIdVenda(Long idVenda) {
		this.idVenda = idVenda;
	}

	public Arquivo getArquivo() {
		return arquivo;
	}

	public void setArquivo(Arquivo arquivo) {
		this.arquivo = arquivo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeVendedor() {
		return nomeVendedor;
	}

	public void setNomeVendedor(String nomeVendedor) {
		this.nomeVendedor = nomeVendedor;
	}

	public List<VendaItem> getItens() {
		return itens;
	}

	public void setItens(List<VendaItem> itens) {
		this.itens = itens;
	}

	@Override
	public String toString() {
		return "Venda [id=" + id + ", idVenda=" + idVenda + ", nomeVendedor=" + nomeVendedor + "]";
	}

}
