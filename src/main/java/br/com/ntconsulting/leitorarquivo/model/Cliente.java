package br.com.ntconsulting.leitorarquivo.model;

public class Cliente {

	private String arquivo;
	private String cnpj;
	private String nome;
	private String areaNegocio;

	public Cliente() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Cliente(String arquivo, String cnpj, String nome, String areaNegocio) {
		super();
		this.arquivo = arquivo;
		this.cnpj = cnpj;
		this.nome = nome;
		this.areaNegocio = areaNegocio;
	}

	public String getArquivo() {
		return arquivo;
	}

	public void setArquivo(String arquivo) {
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
		return "Cliente [arquivo=" + arquivo + ", cnpj=" + cnpj + ", nome=" + nome + ", areaNegocio=" + areaNegocio
				+ "]";
	}

}
