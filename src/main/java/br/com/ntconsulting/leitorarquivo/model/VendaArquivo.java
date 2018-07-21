package br.com.ntconsulting.leitorarquivo.model;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

public class VendaArquivo {

	private String nomeArquivo;
	private String identificador;
	private Long id;
	private String vendaItens;
	private String nomeVendedor;

	public VendaArquivo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public VendaArquivo(String nomeArquivo, String identificador, Long id, String vendaItens, String nomeVendedor) {
		super();
		this.nomeArquivo = nomeArquivo;
		this.identificador = identificador;
		this.id = id;
		this.vendaItens = vendaItens;
		this.nomeVendedor = nomeVendedor;
	}

	public static class VendaFieldSetMapper implements FieldSetMapper<VendaArquivo> {

		private String nomeArquivo;

		public VendaFieldSetMapper(String nomeArquivo) {
			super();
			this.nomeArquivo = nomeArquivo;
		}

		public VendaArquivo mapFieldSet(FieldSet fieldSet) {
			VendaArquivo venda = new VendaArquivo();

			if (null != fieldSet) {
				venda.setNomeArquivo(this.nomeArquivo);
				venda.setIdentificador(fieldSet.readString(0));
				if ("003".equals(venda.getIdentificador())) {
					venda.setId(fieldSet.readLong(1));
					venda.setVendaItens(fieldSet.readString(2));
					venda.setNomeVendedor(fieldSet.readString(3));
				}
			}
			return venda;
		}
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
		return "VendaArquivo [nomeArquivo=" + nomeArquivo + ", identificador=" + identificador + ", id=" + id
				+ ", vendaItens=" + vendaItens + ", nomeVendedor=" + nomeVendedor + "]";
	}

}
