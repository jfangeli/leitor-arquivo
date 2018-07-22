package br.com.ntconsulting.leitorarquivo.processor;


import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import br.com.ntconsulting.leitorarquivo.model.Arquivo;
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
        
        final Cliente clienteProcessado = new Cliente(cliente.getCnpj(), nome, area);
        clienteProcessado.setArquivo(montarArquivo(cliente.getIdArquivo(), cliente.getNomeArquivo(), clienteProcessado));

        log.debug("Cliente processado de (" + cliente + ") para (" + clienteProcessado + ")");

        return clienteProcessado;        
    }
    
    protected Arquivo montarArquivo(Long idArquivo,String nomeArquivo, Cliente cliente) {
    	Arquivo arquivo = new Arquivo();
    	arquivo.setId(idArquivo);
    	arquivo.setNome(nomeArquivo);
    	arquivo.addCliente(cliente);
    	arquivo.setData(new Date());
    	return arquivo;
    }


}
