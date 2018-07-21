package br.com.ntconsulting.leitorarquivo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JobArquivoListener extends JobExecutionListenerSupport {

	private static final Logger log = LoggerFactory.getLogger(JobArquivoListener.class);

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public JobArquivoListener(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
			log.info("!!! JOB FINISHED! Time to verify the results");

//			jdbcTemplate.query("SELECT first_name, last_name FROM people",
//				(rs, row) -> new Person(
//					rs.getString(1),
//					rs.getString(2))
//			).forEach(person -> log.info("Found <" + person + "> in the database."));
		}else {
			log.error("Erro ao processar o JOB. Status:"+jobExecution.getStatus() + ", Exist Status: "+jobExecution.getExitStatus());
		}
	}
}