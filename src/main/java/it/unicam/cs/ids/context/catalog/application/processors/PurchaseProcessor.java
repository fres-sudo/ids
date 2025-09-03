package it.unicam.cs.ids.context.catalog.application.processors;

import it.unicam.cs.ids.context.catalog.domain.model.Purchase;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.PurchaseDTO;
import it.unicam.cs.ids.shared.application.DTO;
import it.unicam.cs.ids.shared.application.Purchasable;

public interface PurchaseProcessor<T extends Purchasable, R extends DTO> {
    PurchaseDTO<R> process(T item, Long buyerId, int quantity, Purchase purchase);
    boolean canHandle(Purchasable item);
}