package com.yanoos.event;

import com.yanoos.global.entity.event.ErrorLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ErrorLogEntityService {
    private final ErrorLogRepository errorLogRepository;

    @Transactional
    public void save(ErrorLog errorLog) {
        errorLogRepository.save(errorLog);
    }
}
