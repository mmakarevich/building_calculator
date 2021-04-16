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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ElementService {

    private final ElementRepository elementRepository;

    public Object calculate(CalculatingInputModel calculatingInputModel) {
        log.info("calculate() - call; {}", calculatingInputModel);

        var id = calculatingInputModel.getTreeId();
        var attributeName = calculatingInputModel.getProp();

        var elementProjections = elementRepository.getAllByTreeId(id);

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

                        var valueSelf = attributes.stream()
                                .filter(a -> a.getElementId().equals(e.getId()))
                                .map(AttributeProjection::getAttrValue)
                                .reduce(BigDecimal.ZERO, BigDecimal::add);
                        cv.setValue(valueSelf);

                        if (cml == null) {
                            cv.setItems(new ArrayList<>(0));
                        } else {
                            var children = cml.stream()
                                    .filter(c -> c.getParentId().equals(e.getId()))
                                    .collect(Collectors.toList());

                            cv.setItems(children);
                            var valueChildren = children
                                    .stream()
                                    .map(CalculatedValuesModel::getValue)
                                    .reduce(BigDecimal.ZERO, BigDecimal::add);

                            cv.setValue(cv.getValue().add(valueChildren));

                        }
                        return cv;
                    })
                    .collect(Collectors.toList());
        }

       return result != null ? result.get(0) : null;
    }

}
