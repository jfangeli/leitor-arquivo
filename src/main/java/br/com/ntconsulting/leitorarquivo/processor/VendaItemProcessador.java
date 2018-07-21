package br.com.ntconsulting.leitorarquivo.processor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import br.com.ntconsulting.leitorarquivo.model.Venda;
import br.com.ntconsulting.leitorarquivo.model.VendaArquivo;
import br.com.ntconsulting.leitorarquivo.model.VendaItem;

public class VendaItemProcessador implements ItemProcessor<VendaArquivo, Venda> {

	private static final Logger log = LoggerFactory.getLogger(VendaItemProcessador.class);

	@Override
	public Venda process(final VendaArquivo vendaArquivo) throws Exception {
		
		if(null == vendaArquivo || !"003".equals(vendaArquivo.getIdentificador())) {
    		return null;
    	}
		
		final String nomeVendedor = vendaArquivo.getNomeVendedor().toUpperCase();

		final Venda vendaProcessada = new Venda(vendaArquivo.getNomeArquivo(), 
				vendaArquivo.getId(), 
				nomeVendedor);
		
		vendaProcessada.setItens( processarItensVenda(vendaArquivo.getVendaItens(), vendaProcessada) );

		log.debug("Venda processada de (" + vendaArquivo + ") para (" + vendaProcessada + ")");

		return vendaProcessada;
	}

	private List<VendaItem> processarItensVenda(String itens, Venda venda) {
		final List<VendaItem> retorno = new ArrayList<VendaItem>();

		if (null != itens && !itens.trim().isEmpty()) {
			itens = itens.replace("[", "").replace("]", "");
			retorno.addAll(
					Arrays.stream(itens.split(",")).map(item -> criarVendaItem(item, venda)).collect(Collectors.toList()));
		}

		return retorno;
	}

	protected VendaItem criarVendaItem(final String item, Venda venda) {
		final VendaItem retorno = new VendaItem();

		if (null != item && !item.trim().isEmpty()) {
			String[] dados = item.split("-");
			retorno.setId(Long.valueOf(dados[0]));
			retorno.setQuantidade(Integer.valueOf(dados[1]));
			retorno.setPreco(new BigDecimal(dados[2]));
			retorno.setVenda(venda);
		}

		return retorno;
	}	
	
}
