package br.com.ntconsulting.leitorarquivo.model;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Vendedor {

	@Id
	@GeneratedValue
	private Long id;

	private String cpf;
	private String nome;
	private BigDecimal salario;

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "arquivo_id", updatable= false, insertable=true)
	private Arquivo arquivo;

	public Vendedor() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Vendedor(String cpf, String nome, BigDecimal salario) {
		super();
		this.cpf = cpf;
		this.nome = nome;
		this.salario = salario;
	}

	public Arquivo getArquivo() {
		return arquivo;
	}

	public void setArquivo(Arquivo arquivo) {
		this.arquivo = arquivo;
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
		return "Vendedor [id=" + id + ", cpf=" + cpf + ", nome=" + nome + ", salario=" + salario + "]";
	}

}
