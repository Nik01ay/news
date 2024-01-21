package example.news.service;

import example.news.dto.AbstractDto;
import example.news.dto.InterfaceDto;
import example.news.entity.InterfaceEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GeneralService {
    List<InterfaceDto> findAll(Pageable page);
    InterfaceDto findById(Long id);
    InterfaceEntity findByIdOrNodFoundException(Long id);
    InterfaceDto create(InterfaceDto dto);
    InterfaceDto update(InterfaceDto dto);
    void deleteById(Long id);
}