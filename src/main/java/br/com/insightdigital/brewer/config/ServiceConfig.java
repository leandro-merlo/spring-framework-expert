package br.com.insightdigital.brewer.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import br.com.insightdigital.brewer.services.CadastroCervejaService;
import br.com.insightdigital.brewer.services.CadastroEstiloService;

@Configuration
@ComponentScan(basePackageClasses = { CadastroCervejaService.class, CadastroEstiloService.class })
public class ServiceConfig {

}
