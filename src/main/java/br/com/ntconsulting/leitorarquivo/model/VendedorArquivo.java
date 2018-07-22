package br.com.ntconsulting.leitorarquivo.model;

import java.math.BigDecimal;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

public class VendedorArquivo {

	private String nomeArquivo;
	private Long idArquivo;
	private String identificador;
	private String cpf;
	private String nome;
	private BigDecimal salario;

	public VendedorArquivo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public VendedorArquivo(Long idArquivo, String nomeArquivo, String identificador, String cpf, String nome,
			BigDecimal salario) {
		super();
		this.idArquivo = idArquivo;
		this.nomeArquivo = nomeArquivo;
		this.identificador = identificador;
		this.cpf = cpf;
		this.nome = nome;
		this.salario = salario;
	}

	public static class VendedorFieldSetMapper implements FieldSetMapper<VendedorArquivo> {

		private String nomeArquivo;
		private Long idArquivo;

		public VendedorFieldSetMapper(String nomeArquivo, Long idArquivo) {
			super();
			this.nomeArquivo = nomeArquivo;
			this.idArquivo = idArquivo;
		}

		public VendedorArquivo mapFieldSet(FieldSet fieldSet) {
			VendedorArquivo vendedor = new VendedorArquivo();

			if (null != fieldSet) {
				vendedor.setNomeArquivo(this.nomeArquivo);
				vendedor.setIdArquivo(this.idArquivo);
				vendedor.setIdentificador(fieldSet.readString(0));
				if ("001".equals(vendedor.getIdentificador())) {
					vendedor.setCpf(fieldSet.readString(1));
					vendedor.setNome(fieldSet.readString(2));
					vendedor.setSalario(fieldSet.readBigDecimal(3));
				}
			}
			return vendedor;
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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getSalario() {
		return salario;
	}

	public void setSalario(BigDecimal salario) {
		this.salario = salario;
	}

	@Override
	public String toString() {
		return "VendedorArquivo [nomeArquivo=" + nomeArquivo + ", idArquivo=" + idArquivo + ", identificador="
				+ identificador + ", cpf=" + cpf + ", nome=" + nome + ", salario=" + salario + "]";
	}

}
