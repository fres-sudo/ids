package it.unicam.cs.ids.context.certification.application.config;

import it.unicam.cs.ids.context.certification.application.strategies.ApprovalStrategy;
import it.unicam.cs.ids.context.certification.application.strategies.BundleApprovalStrategy;
import it.unicam.cs.ids.context.certification.application.strategies.EventApprovalStrategy;
import it.unicam.cs.ids.context.certification.application.strategies.ProductApprovalStrategy;
import it.unicam.cs.ids.context.certification.domain.model.RequestEntityType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ApprovalConfig {

    @Bean
    public Map<RequestEntityType, ApprovalStrategy<?>> approvalStrategies(
            ProductApprovalStrategy productStrategy,
            BundleApprovalStrategy bundleStrategy,
            EventApprovalStrategy eventStrategy) {

        Map<RequestEntityType, ApprovalStrategy<?>> strategies = new HashMap<>();
        strategies.put(RequestEntityType.PRODUCT, productStrategy);
        strategies.put(RequestEntityType.BUNDLE, bundleStrategy);
        strategies.put(RequestEntityType.EVENT, eventStrategy);
        return strategies;
    }
}