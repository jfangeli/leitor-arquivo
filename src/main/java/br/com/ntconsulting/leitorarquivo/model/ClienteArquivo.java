package br.com.ntconsulting.leitorarquivo.model;

public class ClienteArquivo {

	private String identificador;
	private String cnpj;
	private String nome;
	private String areaNegocio;

	public ClienteArquivo(String identificador, String cnpj, String nome, String areaNegocio) {
		super();
		this.identificador = identificador;
		this.cnpj = cnpj;
		this.nome = nome;
		this.areaNegocio = areaNegocio;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
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
		return "ClienteArquivo [identificador=" + identificador + ", cnpj=" + cnpj + ", nome=" + nome + ", areaNegocio="
				+ areaNegocio + "]";
	}

}
