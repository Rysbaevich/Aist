package kg.rysbaevich.aist.service.billing.impl;

import kg.rysbaevich.aist.service.billing.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskService {

    private final SubscriptionService subscriptionService;

    /**
     * Метод для отмены подписок по расписанию
     * Каждый день в полночь
     */
    @Scheduled(cron = "0 0 0 * * ?", zone = "Asia/Bishkek")
    public void cancelExpiredSubscriptions() {
        log.info("#cancelExpiredSubscriptions()");
        subscriptionService.refreshSubscriptions();
    }
}
