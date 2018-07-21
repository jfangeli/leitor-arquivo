package br.com.ntconsulting.leitorarquivo.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Vendedor {

	@Id
	@GeneratedValue
	private Long id;

	private String arquivo;
	private String cpf;
	private String nome;
	private BigDecimal salario;

	public Vendedor() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Vendedor(String arquivo, String cpf, String nome, BigDecimal salario) {
		super();
		this.arquivo = arquivo;
		this.cpf = cpf;
		this.nome = nome;
		this.salario = salario;
	}

	public String getArquivo() {
		return arquivo;
	}

	public void setArquivo(String arquivo) {
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
		return "Vendedor [arquivo=" + arquivo + ", cpf=" + cpf + ", nome=" + nome + ", salario=" + salario + "]";
	}

}
