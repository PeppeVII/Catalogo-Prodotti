package dev.giuseppe.SpringMvc3.ui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.filter.HiddenHttpMethodFilter;

import dev.giuseppe.SpringMvc3.service.UtenteService;


@SpringBootApplication(scanBasePackages = { "dev.giuseppe.SpringMvc3.controller" , "dev.giuseppe.SpringMvc3.service"})
@EnableJpaRepositories( basePackages = { "dev.giuseppe.SpringMvc3.repository" })
@EntityScan( basePackages = { "dev.giuseppe.SpringMvc3.model" })
public class SpringMvc3Application {

	public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(SpringMvc3Application.class, args);
        UtenteService utenteService = applicationContext.getBean("utenteService", UtenteService.class);
        utenteService.createRolesIfNotPresent();
        utenteService.createUsersIfNotPresent();
    }

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
    }
}
