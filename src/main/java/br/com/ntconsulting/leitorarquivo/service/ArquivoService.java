package br.com.ntconsulting.leitorarquivo.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ntconsulting.leitorarquivo.model.Arquivo;
import br.com.ntconsulting.leitorarquivo.model.Venda;
import br.com.ntconsulting.leitorarquivo.repository.ArquivoRepository;
import br.com.ntconsulting.leitorarquivo.repository.ClienteRepository;
import br.com.ntconsulting.leitorarquivo.repository.VendaRepository;
import br.com.ntconsulting.leitorarquivo.repository.VendedorRepository;

@Service
@Transactional(transactionManager="bTransactionManager")
public class ArquivoService {
	
	private static final Logger log = LoggerFactory.getLogger(ArquivoService.class);
	
	private VendedorRepository vendedorRepository;
	private ArquivoRepository arquivoRepository;
	private ClienteRepository clienteRepository;
	private VendaRepository vendaRepository;

	public ArquivoService(VendedorRepository vendedorRepository,
			ArquivoRepository arquivoRepository,
			ClienteRepository clienteRepository,
			VendaRepository vendaRepository
			) {
		this.vendedorRepository = vendedorRepository;
		this.arquivoRepository = arquivoRepository;
		this.vendaRepository = vendaRepository;
		this.clienteRepository = clienteRepository;
	}
	
	public void gerarRelatorio(Long idArquivo) {
		Optional<Arquivo> arquivo;
		
		if(idArquivo != null && idArquivo > 0) {
			arquivo = arquivoRepository.findById(idArquivo);
		}else {
			arquivo = arquivoRepository.findlast();
		}
		
		Integer vendedores = contarVendedoresArquivo(arquivo.get().getId());
		arquivo.get().setQtdVendedores(vendedores);
		
		Integer clientes = contarCLientesArquivo(arquivo.get().getId());
		arquivo.get().setQtdQcliente(clientes);
		
		Venda vendaMaisCara = encontrarVendaMaisCaraArquivo(arquivo.get().getId());
		arquivo.get().setIdMaiorVenda(vendaMaisCara.getIdVenda());
		
		Venda piorVendedor = encontrarPiorVendedorArquivo(arquivo.get().getId());
		arquivo.get().setNomePiorVendedor(piorVendedor.getNomeVendedor());
		
		arquivoRepository.save(arquivo.get());
		
		moverArquivo(arquivo.get());
		
		gerarArquivoResumo(arquivo.get());
		
		
	}
	
	public void moverArquivo(Arquivo arquivo) {
		try {
			Path file = Paths.get(arquivo.getNome());
			Path newFile = Paths.get(file.toFile().getParent(), "/processado/", file.toFile().getName());
			Files.createDirectories(newFile.getParent());
			Files.move(file, newFile, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			log.error("Erro ao mover o arquivo", e);
		}
				
	}

	public void gerarArquivoResumo(Arquivo arquivo) {
		try {
			Path file = Paths.get(arquivo.getNome());
			String nome = file.toFile().getName().split("\\.")[0] + ".done.dat";
			Path newFile = Paths.get(file.toFile().getParent(), "/out/", nome);
			Files.createDirectories(newFile.getParent());
			//Files.createFile(newFile);
			try (PrintWriter pw = new PrintWriter(Files.newBufferedWriter(newFile, java.nio.file.StandardOpenOption.CREATE, java.nio.file.StandardOpenOption.APPEND))) {
				pw.println("Clientes:"+arquivo.getQtdQcliente());
				pw.println("Vendedores:"+arquivo.getQtdVendedores());
				pw.println("ID Venda Mais Cara:"+arquivo.getIdMaiorVenda());
				pw.println("Pior Vendedor:"+arquivo.getNomePiorVendedor());
	        }
			
		} catch (IOException e) {
			log.error("Erro ao gerar relatorio",e);
		}
	}
	

	public Integer contarCLientesArquivo(Long idArquivo) {
		return clienteRepository.findByArquivo(idArquivo).size();
	}
	
	public Integer contarVendedoresArquivo(Long idArquivo) {
		return vendedorRepository.findByArquivo(idArquivo).size();		
	}
	
	public Venda encontrarVendaMaisCaraArquivo(Long idArquivo) {
		return vendaRepository.findVendaMaisCara(idArquivo);
	}
	
	public Venda encontrarPiorVendedorArquivo(Long idArquivo) {
		return vendaRepository.findPiorVendedor(idArquivo);
	}
	
}
