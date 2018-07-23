package br.com.ntconsulting.leitorarquivo.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.ntconsulting.leitorarquivo.service.ArquivoService;

@Component
public class JobArquivoListener extends JobExecutionListenerSupport {

	private static final Logger log = LoggerFactory.getLogger(JobArquivoListener.class);
	
	@Autowired
	private ArquivoService arquivoService;
	
	@Override
	public void beforeJob(JobExecution jobExecution) {
		super.beforeJob(jobExecution);
	}
	
	@Override
	public void afterJob(JobExecution jobExecution) {
		if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
			Long idArquivo = jobExecution.getJobParameters().getLong("arquivo.id");
			
			log.info("JOB FINALIZADO, GERANDO RELATORIO FINAL PARA ID ARQUIVO "+idArquivo);
			
			arquivoService.gerarRelatorio(idArquivo);
			
			log.info("RELATORIO GERADO ID ARQUIVO "+idArquivo);
			
		}else {
			log.error("ERRO AO PROCESSAR JOB. Status:"+jobExecution.getStatus() + ", Exist Status: "+jobExecution.getExitStatus());
		}
	}
}