package com.natixis.problem.adapter.infra;

import com.natixis.problem.adapter.service.HttpAdapter;
import com.natixis.problem.domain.ports.*;
import com.natixis.problem.domain.services.DomainOrchestrator;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfig {

    @Bean
    public HttpAdapter httpAdapter(PortAdapter portAdapter){
        return new DomainOrchestrator(portAdapter);
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
