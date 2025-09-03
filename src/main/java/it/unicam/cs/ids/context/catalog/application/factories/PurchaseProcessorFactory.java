package it.unicam.cs.ids.context.catalog.application.factories;

import it.unicam.cs.ids.context.catalog.application.processors.PurchaseProcessor;
import it.unicam.cs.ids.shared.application.DTO;
import it.unicam.cs.ids.shared.application.Purchasable;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Factory class to retrieve the appropriate PurchaseProcessor based on the type of Purchasable item.
 * It uses Spring's dependency injection to manage available processors.
 *
 * @see PurchaseProcessor
 * @see Purchasable
 */
@Component
public class PurchaseProcessorFactory {

    private final List<PurchaseProcessor<?, ?>> processors;

    public PurchaseProcessorFactory(List<PurchaseProcessor<?, ?>> processors) {
        this.processors = processors;
    }

    @SuppressWarnings("unchecked")
    public <T extends Purchasable, R extends DTO> PurchaseProcessor<T, R> getProcessor(T item) {
        return (PurchaseProcessor<T, R>) processors.stream()
                .filter(processor -> processor.canHandle(item))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No processor found for: " + item.getClass()));
    }
}