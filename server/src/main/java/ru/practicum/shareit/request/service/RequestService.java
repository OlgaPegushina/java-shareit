package ru.practicum.shareit.request.service;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.request.dto.NewRequestDto;
import ru.practicum.shareit.request.dto.RequestDto;

import java.util.List;

@Transactional(readOnly = true)
public interface RequestService {
    @Transactional
    RequestDto create(Long userId, NewRequestDto requestDto);

    List<RequestDto> getAllRequestsById(Long userId);

    List<RequestDto> findAll(Long userId, Integer from, Integer size);

    RequestDto findById(Long userId, Long requestId);
}
