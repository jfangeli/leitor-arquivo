package br.com.ntconsulting.leitorarquivo.processor;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import br.com.ntconsulting.leitorarquivo.model.Vendedor;
import br.com.ntconsulting.leitorarquivo.model.VendedorArquivo;

public class VendedorItemProcessador implements ItemProcessor<VendedorArquivo, Vendedor> {

    private static final Logger log = LoggerFactory.getLogger(VendedorItemProcessador.class);

    @Override
    public Vendedor process(final VendedorArquivo vendedor) throws Exception {
        
    	if(null == vendedor || !"001".equals(vendedor.getIdentificador())) {
    		return null;
    	}
    	
    	final String nome = vendedor.getNome().toUpperCase();
        
        final Vendedor vendedorProcessado = new Vendedor(vendedor.getNomeArquivo(), vendedor.getCpf(), nome, vendedor.getSalario());

        log.debug("Vendedor processado de (" + vendedor + ") para (" + vendedorProcessado + ")");

        return vendedorProcessado;        
    }

}
