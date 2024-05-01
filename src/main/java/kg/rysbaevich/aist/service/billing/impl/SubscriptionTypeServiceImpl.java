package kg.rysbaevich.aist.service.billing.impl;

import kg.rysbaevich.aist.enums.SubscriptionName;
import kg.rysbaevich.aist.model.dto.billing.SubscriptionTypeDto;
import kg.rysbaevich.aist.model.entity.billing.SubscriptionType;
import kg.rysbaevich.aist.repository.billing.SubscriptionTypeRepository;
import kg.rysbaevich.aist.service.billing.SubscriptionTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SubscriptionTypeServiceImpl implements SubscriptionTypeService {

    private final SubscriptionTypeRepository subscriptionTypeRepository;

    @Override
    public List<SubscriptionTypeDto> getAll() {
        List<SubscriptionType> list = subscriptionTypeRepository.findAll();

        List<SubscriptionTypeDto> dtoList = new ArrayList<>();

        for (SubscriptionType subscriptionType : list) {
            SubscriptionTypeDto subscriptionTypeDto = new SubscriptionTypeDto(subscriptionType);
            dtoList.add(subscriptionTypeDto);
        }

        return dtoList;
    }

    @Override
    public Set<SubscriptionName> getAllExcept(Set<SubscriptionName> names) {
        List<SubscriptionType> list = subscriptionTypeRepository.findDistinctByNameNotIn(names);

        Set<SubscriptionName> subscriptionNameSet = new HashSet<>();

        for (SubscriptionType subscriptionType : list) {
            subscriptionNameSet.add(subscriptionType.getName());
        }

        return subscriptionNameSet;
    }

    @Override
    public List<SubscriptionTypeDto> getAllByType(SubscriptionName name) {
        List<SubscriptionType> list = subscriptionTypeRepository.findAllByName(name);

        List<SubscriptionTypeDto> dtoList = new ArrayList<>();

        for (SubscriptionType subscriptionType : list) {
            SubscriptionTypeDto subscriptionTypeDto = new SubscriptionTypeDto(subscriptionType);
            dtoList.add(subscriptionTypeDto);
        }

        return dtoList;
    }

    @Override
    public SubscriptionTypeDto getById(Long id) {

        SubscriptionType subscriptionType = subscriptionTypeRepository.findById(id).orElseThrow();

        return new SubscriptionTypeDto(subscriptionType);
    }
}
