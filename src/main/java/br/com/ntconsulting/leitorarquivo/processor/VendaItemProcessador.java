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
import br.com.ntconsulting.leitorarquivo.model.Vendedor;

public class VendaItemProcessador implements ItemProcessor<VendaArquivo, Venda> {

	private static final Logger log = LoggerFactory.getLogger(VendaItemProcessador.class);

	@Override
	public Venda process(final VendaArquivo vendaArquivo) throws Exception {
		
		if(null == vendaArquivo || !"003".equals(vendaArquivo.getIdentificador())) {
    		return null;
    	}
		
		final String nomeVendedor = vendaArquivo.getNomeVendedor().toUpperCase();
		final String itens = vendaArquivo.getVendaItens();

		final Vendedor vendedor = new Vendedor(null, null, nomeVendedor, null);

		final Venda vendaProcessada = new Venda(null, vendaArquivo.getId(), vendedor,
				processarItensVenda(vendaArquivo.getVendaItens()));

		log.debug("Venda processada de (" + vendaArquivo + ") para (" + vendaProcessada + ")");

		return vendaProcessada;
	}

	private List<VendaItem> processarItensVenda(String itens) {
		final List<VendaItem> retorno = new ArrayList<VendaItem>();

		if (null != itens && !itens.trim().isEmpty()) {
			itens = itens.replace("[", "").replace("]", "");
			retorno.addAll(
					Arrays.stream(itens.split(",")).map(item -> criarVendaItem(item)).collect(Collectors.toList()));
		}

		return retorno;
	}

	protected VendaItem criarVendaItem(final String item) {
		final VendaItem retorno = new VendaItem();

		if (null != item && !item.trim().isEmpty()) {
			String[] dados = item.split("-");
			retorno.setId(Long.valueOf(dados[0]));
			retorno.setQuantidade(Integer.valueOf(dados[1]));
			retorno.setPreco(new BigDecimal(dados[2]));
		}

		return retorno;
	}	
	
}
