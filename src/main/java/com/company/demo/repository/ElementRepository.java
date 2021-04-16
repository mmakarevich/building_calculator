package com.company.demo.repository;

import com.company.demo.domain.AttributeProjection;
import com.company.demo.domain.Element;
import com.company.demo.domain.ElementProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ElementRepository extends JpaRepository<Element, Long> {


    @Query(value = "with recursive sub_element(id, title, parent_id, level) as ( " +
            "    select id, title, parent_id, 1 from element where id = :treeId " +
            "    union all " +
            "    select e.id, e.title, e.parent_id, level+1 " +
            "    from element e, sub_element se " +
            "    where e.parent_id = se.id " +
            ") " +
            " select se.id as id, se.title as title, se.parent_id as parent, level as level from sub_element se ",
            nativeQuery = true)
    List<ElementProjection> getAllByTreeId(@Param("treeId") Long treeId);


    @Query(value = "with recursive sub_element(id, title, parent_id, level) as ( " +
            "    select id, title, parent_id, 1 from element where id = :treeId " +
            "    union all " +
            "    select e.id, e.title, e.parent_id, level+1 " +
            "    from element e, sub_element se " +
            "    where e.parent_id = se.id " +
            ") " +
            " select se.id as elementId, se.parent_id as parentId, se.title as title, a.value as attrValue, level as level from sub_element se " +
            "    left join element_attributes ea on ea.element_id = se.id " +
            "    left join attribute a on a.id = ea.attributes_id " +
            " where a.name = :attrName ",
            nativeQuery = true)
    List<AttributeProjection> getAllByTreeIdAndAttributeName(@Param("treeId") Long treeId,
                                                             @Param("attrName") String attrName);

}
