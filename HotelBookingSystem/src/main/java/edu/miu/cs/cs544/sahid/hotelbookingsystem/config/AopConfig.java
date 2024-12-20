package edu.miu.cs.cs544.sahid.hotelbookingsystem.config;

import edu.miu.cs.cs544.sahid.hotelbookingsystem.config.aspectConfig.LoggingAspect;
import edu.miu.cs.cs544.sahid.hotelbookingsystem.config.aspectConfig.PerformanceAspect;
import edu.miu.cs.cs544.sahid.hotelbookingsystem.config.aspectConfig.RoomStatusAspect;
import edu.miu.cs.cs544.sahid.hotelbookingsystem.config.aspectConfig.SecurityAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class AopConfig {

    @Bean
    public LoggingAspect loggingAspect() {
        return new LoggingAspect();
    }

    @Bean
    public PerformanceAspect performanceAspect() {
        return new PerformanceAspect();
    }

    @Bean
    public RoomStatusAspect roomStatusAspect() {
        return new RoomStatusAspect();
    }

    @Bean
    public SecurityAspect securityAspect() {
        return new SecurityAspect();
    }
}
