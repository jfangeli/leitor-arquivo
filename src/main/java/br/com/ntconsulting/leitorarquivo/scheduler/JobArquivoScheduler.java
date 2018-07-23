package br.com.ntconsulting.leitorarquivo.scheduler;

import java.io.File;
import java.io.FilenameFilter;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class JobArquivoScheduler {

	private static final Logger log = LoggerFactory.getLogger(JobArquivoScheduler.class);
	
	private final AtomicBoolean enabled = new AtomicBoolean(false);

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job job;

	@Scheduled(fixedRate = 600000)
	public void executar() throws Exception {
		if (enabled.get()) {
			log.info("SCHEDULER: JOB INICIADO");
			
			final String arquivo = getArquivo();
			
			if(arquivo != null) {
				JobParameters jobParameters = new JobParametersBuilder()
						.addLong("time", System.currentTimeMillis())
						.addString("arquivo", arquivo)
						.addLong("arquivo.id", getIdArquivo())
						.toJobParameters();
				jobLauncher.run(job, jobParameters);
				
			}else {
				log.info("SCHEDULER: NENHUM ARQUIVO PARA PROCESSAMENTO");
			}
		}else {
			log.info("SCHEDULER: AGUARDANDO ATIVACAO DO JOB EM  http://localhost/ativar PARA EXECUTAR JOBS");
		}
	}

	public void ativar() {
		enabled.set(true);
	}

	public void desativar() {
		enabled.set(false);
	}
	
	protected String getArquivo() {
		String diretorio = System.getenv("HOMEPATH");
		if (diretorio == null) {
			diretorio = System.getProperty("homepath");
		}
		diretorio = diretorio + "/data";
		log.info("DIRETORIO: " + diretorio);

		File file = new File(diretorio);
		File[] f = file.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.toUpperCase().endsWith(".DAT");
			}
		});
		
		String arquivo = null;
		if(f != null && f.length > 0) {

		arquivo = (f.length > 0 ? f[0].getPath() : "");
		log.info("ARQUIVO: " + arquivo);
		}
		
		return arquivo;
	}
	
	protected Long getIdArquivo() {
		final Long id = System.currentTimeMillis();
		log.info("IDARQUIVO: " + id);
		return id;
		
	}
	
}
