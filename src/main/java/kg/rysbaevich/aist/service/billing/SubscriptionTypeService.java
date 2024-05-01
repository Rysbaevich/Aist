package kg.rysbaevich.aist.service.billing;


import kg.rysbaevich.aist.enums.SubscriptionName;
import kg.rysbaevich.aist.model.dto.billing.SubscriptionTypeDto;

import java.util.List;
import java.util.Set;

public interface SubscriptionTypeService {
    List<SubscriptionTypeDto> getAll();
    Set<SubscriptionName> getAllExcept(Set<SubscriptionName> names);
    List<SubscriptionTypeDto> getAllByType(SubscriptionName type);

    SubscriptionTypeDto getById(Long id);
}
