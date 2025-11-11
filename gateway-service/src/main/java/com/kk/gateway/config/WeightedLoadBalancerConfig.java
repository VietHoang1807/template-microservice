//package com.kk.gateway.config;
//
//import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
//import org.springframework.context.ConfigurableApplicationContext;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.concurrent.ThreadLocalRandom;
//
//@Configuration
//public class WeightedLoadBalancerConfig {
//
//    /**
//     * <a href="https://docs.spring.io/spring-cloud-commons/reference/spring-cloud-commons/loadbalancer.html">...</a>
//     * @param context
//     * @return ServiceInstanceListSupplier
//     */
//    @Bean
//    public ServiceInstanceListSupplier discoveryClientServiceInstanceListSupplier(
//            ConfigurableApplicationContext context) {
//        return ServiceInstanceListSupplier.builder()
//                .withDiscoveryClient()
//                .withCaching()
//                .withWeighted(instance -> ThreadLocalRandom.current().nextInt(1, 101))
//                .withHints()
//                .build(context);
//    }
//}
