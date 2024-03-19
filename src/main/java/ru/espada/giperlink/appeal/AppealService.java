package ru.espada.giperlink.appeal;

import org.springframework.stereotype.Service;
import ru.espada.giperlink.appeal.appeal.AppealEntity;
import ru.espada.giperlink.appeal.appeal.AppealRepository;
import ru.espada.giperlink.user.UserEntity;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppealService {


    private final AppealRepository appealRepository;

    public AppealService(AppealRepository appealRepository) {
        this.appealRepository = appealRepository;
    }

    public List<AppealEntity> getAllUserAppeals12Month(UserEntity user) {
        long date = System.currentTimeMillis() - 12L * 30 * 24 * 60 * 60 * 1000;
        long date2 = System.currentTimeMillis();
        return appealRepository.findAppealEntitiesByUserAndDateBetweenOrderByDate(user, date, date2).orElse(new ArrayList<>());
    }

    public List<AppealEntity> getAllUserAppealsByDateBetween(UserEntity user, long date, long date2) {
        return appealRepository.findAppealEntitiesByUserAndDateBetweenOrderByDate(user, date, date2).orElse(new ArrayList<>());
    }
}
