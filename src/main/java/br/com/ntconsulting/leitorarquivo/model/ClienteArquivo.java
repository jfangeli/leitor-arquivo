package br.com.ntconsulting.leitorarquivo.model;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

public class ClienteArquivo {

	private String nomeArquivo;
	private Long idArquivo;
	private String identificador;
	private String cnpj;
	private String nome;
	private String areaNegocio;

	public ClienteArquivo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ClienteArquivo(Long idArquivo, String nomeArquivo, String identificador, String cnpj, String nome,
			String areaNegocio) {
		super();
		this.idArquivo = idArquivo;
		this.nomeArquivo = nomeArquivo;
		this.identificador = identificador;
		this.cnpj = cnpj;
		this.nome = nome;
		this.areaNegocio = areaNegocio;
	}

	public static class ClienteFieldSetMapper implements FieldSetMapper<ClienteArquivo> {

		private String nomeArquivo;
		private Long idArquivo;

		public ClienteFieldSetMapper(String nomeArquivo, Long idArquivo) {
			super();
			this.nomeArquivo = nomeArquivo;
			this.idArquivo = idArquivo;
		}

		public ClienteArquivo mapFieldSet(FieldSet fieldSet) {
			ClienteArquivo cliente = new ClienteArquivo();

			if (null != fieldSet) {
				cliente.setNomeArquivo(this.nomeArquivo);
				cliente.setIdArquivo(this.idArquivo);
				cliente.setIdentificador(fieldSet.readString(0));
				if ("002".equals(cliente.getIdentificador())) {
					cliente.setCnpj(fieldSet.readString(1));
					cliente.setNome(fieldSet.readString(2));
					cliente.setAreaNegocio(fieldSet.readString(3));
				}
			}
			return cliente;
		}
	}

	public Long getIdArquivo() {
		return idArquivo;
	}

	public void setIdArquivo(Long idArquivo) {
		this.idArquivo = idArquivo;
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
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
		return "ClienteArquivo [nomeArquivo=" + nomeArquivo + ", idArquivo=" + idArquivo + ", identificador="
				+ identificador + ", cnpj=" + cnpj + ", nome=" + nome + ", areaNegocio=" + areaNegocio + "]";
	}

}
