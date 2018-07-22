package br.com.ntconsulting.leitorarquivo.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Cliente {

	@Id
	@GeneratedValue
	private Long id;
	private String cnpj;
	private String nome;
	private String areaNegocio;

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "arquivo_id", updatable= false, insertable=true)
	private Arquivo arquivo;
	
	public Cliente() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public Cliente(String cnpj, String nome, String areaNegocio) {
		super();		
		this.cnpj = cnpj;
		this.nome = nome;
		this.areaNegocio = areaNegocio;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Arquivo getArquivo() {
		return arquivo;
	}

	public void setArquivo(Arquivo arquivo) {
		this.arquivo = arquivo;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getAreaNegocio() {
		return areaNegocio;
	}

	public void setAreaNegocio(String areaNegocio) {
		this.areaNegocio = areaNegocio;
	}

	@Override
	public String toString() {
		return "Cliente [id=" + id + ", cnpj=" + cnpj + ", nome=" + nome + ", areaNegocio="
				+ areaNegocio + "]";
	}

}
