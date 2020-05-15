package com.seasolutions.stock_management;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.concurrent.Executor;


@SpringBootApplication()
@EnableScheduling
public class Application implements SchedulingConfigurer {
	public static void main(String[] args) {
		SpringApplication.run(com.seasolutions.stock_management.Application.class, args);
	}

	    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("POST","GET","PUT","PATCH","DELETE")
                        .allowCredentials(true);
            }
        };
    }


	// Tao ra 1 excutor service bean de dung khi cung
	// Neu lam the nay thi khi can dung se dung autowrite duoc chu khong can tao instance giong nhuw o send email job nua
	// Co the chuyen phan nay sang package config cung duoc
	@Bean(name = "threadPoolTaskExecutor")
	public Executor threadPoolTaskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(5);
		executor.setMaxPoolSize(15);
		executor.setQueueCapacity(500);
		//executor.setThreadNamePrefix("GithubLookup-");
		executor.initialize();
		return executor;
	}



	//Cau hinh tao ra 5 thread de xu ly cac scheduler
	@Override
	public void configureTasks(final ScheduledTaskRegistrar taskRegistrar) {
		final ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
		taskScheduler.setPoolSize(5);
		taskScheduler.initialize();
		taskRegistrar.setTaskScheduler(taskScheduler);
	}


}
