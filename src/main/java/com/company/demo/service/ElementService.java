package com.company.demo.service;

import com.company.demo.domain.AttributeProjection;
import com.company.demo.domain.ElementProjection;
import com.company.demo.models.CalculatedValuesModel;
import com.company.demo.models.CalculatingInputModel;
import com.company.demo.repository.ElementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ElementService {

    private final ElementRepository elementRepository;

    public Object calculate(CalculatingInputModel calculatingInputModel) {
        log.info("calculate() - call; {}", calculatingInputModel);

        var id = calculatingInputModel.getId();
        var attributeName = calculatingInputModel.getProp();

        List<ElementProjection> elementProjections = elementRepository.getAllByTreeId(id);

        int maxLevel = elementProjections.stream().map(ElementProjection::getLevel).max(Integer::compareTo).orElse(0);

        var attributes = elementRepository.getAllByTreeIdAndAttributeName(id, attributeName);

        List<CalculatedValuesModel> result = null;
        for (int i = maxLevel; i > 0; i--) {
            var level = i;
            var cml = result;

            result = elementProjections.stream()
                    .filter(e -> e.getLevel() == level)
                    .map(e -> {
                        var cv = new CalculatedValuesModel();
                        cv.setParentId(e.getParent());
                        cv.setName(e.getTitle());
                        cv.setProp(attributeName);
                        cv.setId(e.getId());

                        if (cml == null) {
                            cv.setItems(cml);
                            var value = attributes.stream()
                                    .filter(a -> a.getElementId().equals(e.getId()))
                                    .map(AttributeProjection::getAttrValue)
                                    .reduce(BigDecimal.ZERO, BigDecimal::add);
                            cv.setValue(value);
                        } else {
                            var children = cml.stream()
                                    .filter(c -> c.getParentId().equals(e.getId()))
                                    .collect(Collectors.toList());

                            cv.setItems(children);
                            var value = children
                                    .stream()
                                    .map(CalculatedValuesModel::getValue)
                                    .reduce(BigDecimal.ZERO, BigDecimal::add);

                            cv.setValue(value);

                        }
                        return cv;
                    })
                    .collect(Collectors.toList());
        }

        return result;
    }

}
