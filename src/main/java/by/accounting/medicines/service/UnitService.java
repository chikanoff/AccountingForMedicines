package by.accounting.medicines.service;

import by.accounting.medicines.exception.ItemNotFoundException;
import by.accounting.medicines.mapper.UnitMapper;
import by.accounting.medicines.model.dto.entity.UnitDto;
import by.accounting.medicines.model.dto.response.UnitResponse;
import by.accounting.medicines.model.entity.Unit;
import by.accounting.medicines.repository.UnitRepository;
import by.accounting.medicines.util.ErrorResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UnitService {
    private final UnitRepository unitRepository;
    private final UnitMapper unitMapper;

    public List<UnitResponse> getAll() {
        return unitRepository.findAll().stream().map(unitMapper::toResponse).toList();
    }

    public UnitResponse getById(Long id) {
        return unitMapper.toResponse(findByIdOrThrow(id));
    }

    public UnitResponse create(UnitDto unitDto) {
        return unitMapper.toResponse(unitRepository.save(unitMapper.toEntity(unitDto)));
    }

    public UnitResponse update(Long id, UnitDto unitDto) {
        Unit unit = findByIdOrThrow(id);
        unitMapper.updateEntity(unitDto, unit);
        return unitMapper.toResponse(unitRepository.save(unit));
    }

    public void delete(Long id) {
        unitRepository.deleteById(id);
    }

    public Unit findByIdOrThrow(Long id) {
        return unitRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Unit with id " + id + " not found", ErrorResponseUtil.UNIT_NOT_FOUND));
    }
}
