package com.company.demo;

import com.company.demo.domain.Attribute;
import com.company.demo.domain.Element;
import com.company.demo.repository.ElementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class DbInitializer {

    private final ElementRepository elementRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        log.info("init DB call");

        var elements = new ArrayList<Element>();

        // lev 1
        var building = new Element("здание");

        // lev 2
        var windows = new Element("окна");
        windows.setParent(building);

        var doors = new Element("двери");
        doors.setParent(building);

        // lev 3
        var profWindows = new Element("профильные");
        var otherWindows = new Element("витражи");
        profWindows.setParent(windows);
        otherWindows.setParent(windows);

        var fireDoors = new Element("пожарные");
        fireDoors.setParent(doors);

        // lev 4
        var size500x600Windows = new Element("500x600");
        var size800x700Windows = new Element("800x700");
        size500x600Windows.setParent(profWindows);
        size800x700Windows.setParent(profWindows);

        var size1500x400Windows = new Element("1500x400");
        size1500x400Windows.setParent(otherWindows);

        var size120x230Door = new Element("500x600");
        size120x230Door.setParent(fireDoors);


        // lev 5
        var window1 = new Element("окно 1");
        window1.setAttributes(Set.of(new Attribute("стоимость", BigDecimal.valueOf(100.01))));
        window1.setParent(size500x600Windows);
        elements.add(window1);
        var window2 = new Element("окно 2");
        window2.setAttributes(Set.of(new Attribute("стоимость", BigDecimal.valueOf(100.01))));
        window2.setParent(size500x600Windows);
        elements.add(window2);
        var window3 = new Element("окно 3");
        window3.setAttributes(Set.of(new Attribute("стоимость", BigDecimal.valueOf(100.01))));
        window3.setParent(size500x600Windows);
        elements.add(window3);
        var window4 = new Element("окно 4");
        window4.setAttributes(Set.of(new Attribute("стоимость", BigDecimal.valueOf(100.01))));
        window4.setParent(size500x600Windows);
        elements.add(window4);

        var window1_1 = new Element("окно 5");
        window1_1.setAttributes(Set.of(new Attribute("стоимость", BigDecimal.valueOf(100.01))));
        window1_1.setParent(size800x700Windows);
        elements.add(window1_1);
        var window1_2 = new Element("окно 6");
        window1_2.setAttributes(Set.of(new Attribute("стоимость", BigDecimal.valueOf(100.01))));
        window1_2.setParent(size800x700Windows);
        elements.add(window1_2);
        var window1_3 = new Element("окно 7");
        window1_3.setAttributes(Set.of(new Attribute("стоимость", BigDecimal.valueOf(100.01))));
        window1_3.setParent(size800x700Windows);
        elements.add(window1_3);
        var window1_4 = new Element("окно 8");
        window1_4.setAttributes(Set.of(new Attribute("стоимость", BigDecimal.valueOf(100.01))));
        window1_4.setParent(size800x700Windows);
        elements.add(window1_4);

        var window2_1 = new Element("витраж 1");
        window2_1.setAttributes(Set.of(new Attribute("стоимость", BigDecimal.valueOf(100.01))));
        window2_1.setParent(size1500x400Windows);
        elements.add(window2_1);
        var window2_2 = new Element("витраж 2");
        window2_2.setAttributes(Set.of(new Attribute("стоимость", BigDecimal.valueOf(100.01))));
        window2_2.setParent(size1500x400Windows);
        elements.add(window2_2);
        var window2_3 = new Element("витраж 3");
        window2_3.setAttributes(Set.of(new Attribute("стоимость", BigDecimal.valueOf(100.01))));
        window2_3.setParent(size1500x400Windows);
        elements.add(window2_3);
        var window2_4 = new Element("витраж 4");
        window2_4.setAttributes(Set.of(new Attribute("стоимость", BigDecimal.valueOf(100.01))));
        window2_4.setParent(size1500x400Windows);
        elements.add(window2_4);

        var door1 = new Element("дверь 1");
        door1.setAttributes(Set.of(new Attribute("стоимость", BigDecimal.valueOf(50.01))));
        door1.setParent(size120x230Door);
        elements.add(door1);
        var door2 = new Element("дверь 2");
        door2.setAttributes(Set.of(new Attribute("стоимость", BigDecimal.valueOf(50.01))));
        door2.setParent(size120x230Door);
        elements.add(door2);
        var door3 = new Element("дверь 3");
        door3.setAttributes(Set.of(new Attribute("стоимость", BigDecimal.valueOf(50.01))));
        door3.setParent(size120x230Door);
        elements.add(door3);
        var door4 = new Element("дверь 4");
        door4.setAttributes(Set.of(new Attribute("стоимость", BigDecimal.valueOf(50.01))));
        door4.setParent(size120x230Door);
        elements.add(door4);



        elementRepository.saveAll(elements);


    }



}
