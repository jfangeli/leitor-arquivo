package br.com.ntconsulting.leitorarquivo.model;

public class VendaArquivo {

	private String identificador;
	private Long id;
	private String vendaItens;
	private String nomeVendedor;

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getVendaItens() {
		return vendaItens;
	}

	public void setVendaItens(String vendaItens) {
		this.vendaItens = vendaItens;
	}

	public String getNomeVendedor() {
		return nomeVendedor;
	}

	public void setNomeVendedor(String nomeVendedor) {
		this.nomeVendedor = nomeVendedor;
	}

	@Override
	public String toString() {
		return "VendaArquivo [identificador=" + identificador + ", id=" + id + ", vendaItens=" + vendaItens
				+ ", nomeVendedor=" + nomeVendedor + "]";
	}

}
