package com.company.demo.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Element {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @OneToOne(cascade = CascadeType.ALL)
    private Element parent;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Attribute> attributes = new HashSet<>();

    public Element(String title) {
        this.title = title;
    }
}
