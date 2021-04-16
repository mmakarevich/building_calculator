package com.company.demo.web.rest;

import com.company.demo.models.CalculatingInputModel;
import com.company.demo.service.ElementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ElementResource {

    private final ElementService elementService;

    @RequestMapping(value = "/calculate", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<Object> calculateAttributeValues(@RequestBody CalculatingInputModel calculatingInputModel) {
        log.info("calculateAttributeValues() - call; {}", calculatingInputModel);
        if (calculatingInputModel.getId() == null || calculatingInputModel.getProp() == null || calculatingInputModel.getProp().isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(elementService.calculate(calculatingInputModel));
    }


}
