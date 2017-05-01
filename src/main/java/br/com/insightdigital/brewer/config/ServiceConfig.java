package br.com.insightdigital.brewer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import br.com.insightdigital.brewer.services.CadastroCervejaService;
import br.com.insightdigital.brewer.services.CadastroEstiloService;
import br.com.insightdigital.brewer.storage.FotoStorage;
import br.com.insightdigital.brewer.storage.local.FotoStorageLocal;

@Configuration
@ComponentScan(basePackageClasses = { CadastroCervejaService.class, CadastroEstiloService.class })
public class ServiceConfig {

	@Bean
	public FotoStorage fotoStorage(){
		return new FotoStorageLocal();
	}
}
