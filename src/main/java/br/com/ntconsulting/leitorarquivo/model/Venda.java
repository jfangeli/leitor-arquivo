package br.com.ntconsulting.leitorarquivo.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Venda {

	private String arquivo;
	@Id
	private Long id;
	private String nomeVendedor;
	
	@OneToMany(mappedBy="venda")
	private List<VendaItem> itens;

	public Venda() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Venda(String arquivo, Long id, String nomeVendedor) {
		super();
		this.arquivo = arquivo;
		this.id = id;
		this.nomeVendedor = nomeVendedor;		
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
		return "Venda [arquivo=" + arquivo + ", id=" + id + ", nomeVendedor=" + nomeVendedor + ", itens=" + itens + "]";
	}

}
