package br.com.ntconsulting.leitorarquivo.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Arquivo {

	@Id
	private Long id;

	private String nome;

	@Temporal(TemporalType.TIMESTAMP)
	private Date data;

	private Integer qtdQcliente;
	private Integer qtdVendedores;
	private Long idMaiorVenda;
	private String nomePiorVendedor;

	@OneToMany(mappedBy = "arquivo")
	private List<Vendedor> vendedores;

	@OneToMany(mappedBy = "arquivo")
	private List<Cliente> clientes;

	@OneToMany(mappedBy = "arquivo")
	private List<Venda> vendas;

	public Arquivo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void addVendedor(Vendedor vendedor) {
		if (getVendedores() == null) {
			setVendedores(new ArrayList<Vendedor>());
		}
		getVendedores().add(vendedor);
	}

	public void addCliente(Cliente cliente) {
		if (getClientes() == null) {
			setClientes(new ArrayList<Cliente>());
		}
		getClientes().add(cliente);
	}

	public void addVenda(Venda venda) {
		if (getVendas() == null) {
			setVendas(new ArrayList<Venda>());
		}
		getVendas().add(venda);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Vendedor> getVendedores() {
		return vendedores;
	}

	public void setVendedores(List<Vendedor> vendedores) {
		this.vendedores = vendedores;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public List<Venda> getVendas() {
		return vendas;
	}

	public void setVendas(List<Venda> vendas) {
		this.vendas = vendas;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Integer getQtdQcliente() {
		return qtdQcliente;
	}

	public void setQtdQcliente(Integer qtdQcliente) {
		this.qtdQcliente = qtdQcliente;
	}

	public Integer getQtdVendedores() {
		return qtdVendedores;
	}

	public void setQtdVendedores(Integer qtdVendedores) {
		this.qtdVendedores = qtdVendedores;
	}

	public Long getIdMaiorVenda() {
		return idMaiorVenda;
	}

	public void setIdMaiorVenda(Long idMaiorVenda) {
		this.idMaiorVenda = idMaiorVenda;
	}

	public String getNomePiorVendedor() {
		return nomePiorVendedor;
	}

	public void setNomePiorVendedor(String nomePiorVendedor) {
		this.nomePiorVendedor = nomePiorVendedor;
	}

	@Override
	public String toString() {
		return "Arquivo [id=" + id + ", nome=" + nome + ", data=" + data + ", qtdQcliente=" + qtdQcliente
				+ ", qtdVendedores=" + qtdVendedores + ", idMaiorVenda=" + idMaiorVenda + ", nomePiorVendedor="
				+ nomePiorVendedor + "]";
	}

}
