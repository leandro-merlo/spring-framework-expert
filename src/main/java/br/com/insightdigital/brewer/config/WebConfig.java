package br.com.insightdigital.brewer.config;

import java.math.BigDecimal;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.number.NumberStyleFormatter;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

import br.com.insightdigital.brewer.controllers.CervejasController;
import br.com.insightdigital.brewer.controllers.converters.EstiloConverter;
import br.com.insightdigital.brewer.repository.Estilos;
import nz.net.ultraq.thymeleaf.LayoutDialect;

@Configuration
@ComponentScan(basePackageClasses = { CervejasController.class, EstiloConverter.class })
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware {

	
	private ApplicationContext applicationContext;
	
	/**
	 * Método necessário para que o Spring Configure o View Resolver.
	 * ViewResolver é o objeto responsável por localizar e devolver a view que deve ser renderizada
	 */
	@Bean
	public ViewResolver viewResolver(){
		ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
		viewResolver.setTemplateEngine(templateEngine());
		viewResolver.setCharacterEncoding("UTF-8");
		return viewResolver;
	}
	
	/**
	 * O Template resolver é responsável por informar onde e como os templates devem ser resgatados
	 */
	private ITemplateResolver templateResolver(){
		SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
		resolver.setApplicationContext(this.applicationContext);
		resolver.setPrefix("classpath:/templates/");
		resolver.setSuffix(".html");
		resolver.setTemplateMode(TemplateMode.HTML);
		return resolver;
	}
	
	@Bean
	public TemplateEngine templateEngine(){
		SpringTemplateEngine engine = new SpringTemplateEngine();
		engine.setEnableSpringELCompiler(true);
		engine.setTemplateResolver(templateResolver());
		engine.addDialect(new LayoutDialect());
		return engine;
	}
	
	
	public void setApplicationContext(ApplicationContext ctx){
		this.applicationContext = ctx;
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
	}
	
	@Bean
	public FormattingConversionService mvcConversionService(){
		DefaultFormattingConversionService service = new DefaultFormattingConversionService();
		service.addConverter(new EstiloConverter());
		service.addFormatterForFieldType(BigDecimal.class, new NumberStyleFormatter("#,##0.00"));
		service.addFormatterForFieldType(Integer.class, new NumberStyleFormatter("#,##0"));
		return service;
	}
}
