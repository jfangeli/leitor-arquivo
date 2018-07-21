package br.com.ntconsulting.leitorarquivo.processor;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import br.com.ntconsulting.leitorarquivo.model.Cliente;
import br.com.ntconsulting.leitorarquivo.model.ClienteArquivo;

public class ClienteItemProcessador implements ItemProcessor<ClienteArquivo, Cliente> {

    private static final Logger log = LoggerFactory.getLogger(ClienteItemProcessador.class);

    @Override
    public Cliente process(final ClienteArquivo cliente) throws Exception {
        
    	if(null == cliente || !"002".equals(cliente.getIdentificador())) {
    		return null;
    	}
    	
    	final String nome = cliente.getNome().toUpperCase();
    	final String area = cliente.getAreaNegocio().toUpperCase();
        
        final Cliente clienteProcessado = new Cliente(cliente.getNomeArquivo(), cliente.getCnpj(), nome, area);

        log.debug("Cliente processado de (" + cliente + ") para (" + clienteProcessado + ")");

        return clienteProcessado;        
    }

}
