/*
 * (C) Copyright Hemajoo Systems Inc.  2022 - All Rights Reserved
 * -----------------------------------------------------------------------------------------------
 * All information contained herein is, and remains the property of
 * Hemajoo Inc. and its suppliers, if any. The intellectual and technical
 * concepts contained herein are proprietary to Hemajoo Inc. and its
 * suppliers and may be covered by U.S. and Foreign Patents, patents
 * in process, and are protected by trade secret or copyright law.
 *
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained from
 * Hemajoo Systems Inc.
 * -----------------------------------------------------------------------------------------------
 */
package com.hemajoo.commerce.cherry.server.commons.entity.query;

import com.hemajoo.commerce.cherry.server.commons.entity.query.condition.QueryCondition;
import com.hemajoo.commerce.cherry.server.commons.entity.query.condition.QueryField;
import com.hemajoo.commerce.cherry.server.shared.data.model.entity.base.type.FieldDataType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serial;
import java.util.*;

/**
 * Generic specification embedding query criteria to be applied to a <b>JPA</b> repository to retrieve records.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@NoArgsConstructor
@Data
public final class GenericSpecification<T> implements Specification<T>
{
    /**
     * Serialization identifier.
     */
    @Serial
    private static final long serialVersionUID = 1900581010229669687L;

    /**
     * List of search criteria.
     */
    private List<QueryCondition> list = new ArrayList<>();

    @Override
    public Predicate toPredicate(@NotNull Root<T> root, @NotNull CriteriaQuery<?> query, @NotNull CriteriaBuilder builder)
    {
        List<Predicate> predicates = new ArrayList<>();

        // Substitute special characters before processing criteria!
        substituteSpecialCharacters();

        // Add criteria to list of predicates.
        for (QueryCondition criteria : list)
        {
            switch (criteria.getOperator())
            {
                case EQUAL:
                    predicates.add(builder.equal(root.get(criteria.getField()), criteria.getValues().get(0)));
                    break;

                case MATCH:
                    predicates.add(builder.like(builder.lower(root.get(criteria.getField())), criteria.getValues().get(0).toString().toLowerCase()));
                    break;

                case LESS_THAN:
                    predicates.add(builder.lessThan(root.get(criteria.getField()), criteria.getValues().get(0).toString()));
                    break;

                case END_WITH:
                    predicates.add(builder.like(builder.lower(root.get(criteria.getField())),criteria.getValues().get(0).toString().toLowerCase() + "%"));
                    break;

                case NOT_EQUAL:
                    predicates.add(builder.notEqual(root.get(criteria.getField()), criteria.getValues().get(0)));
                    break;

                case GREATER_THAN:
                    predicates.add(builder.greaterThan(root.get(criteria.getField()), criteria.getValues().get(0).toString()));
                    break;

                case START_WITH:
                    predicates.add(builder.like(builder.lower(root.get(criteria.getField())),"%" + criteria.getValues().get(0).toString().toLowerCase()));
                    break;

                case CONTAINS:
                    predicates.add(builder.like(builder.lower(root.get(criteria.getField())),"%" + criteria.getValues().get(0).toString().toLowerCase() + "%"));
                    break;

                case LESS_THAN_EQUAL:
                    predicates.add(builder.lessThanOrEqualTo(root.get(criteria.getField()), criteria.getValues().get(0).toString()));
                    break;

                case GREATER_THAN_EQUAL:
                    predicates.add(builder.greaterThanOrEqualTo(root.get(criteria.getField()), criteria.getValues().get(0).toString()));
                    break;

                case EQUAL_OBJECT_UUID:
                    predicates.add(builder.equal(root.get(criteria.getField()).get("id"), UUID.fromString(criteria.getValues().get(0).toString())));
                    break;

                case BETWEEN:
                    if (criteria.getValues().get(0) instanceof Date)
                    {
                        predicates.add(builder.between(root.get(criteria.getField()), (Date) criteria.getValues().get(0), (Date) criteria.getValues().get(1)));
                    }
                    if (criteria.getValues().get(0) instanceof Long)
                    {
                        predicates.add(builder.between(root.get(criteria.getField()), (Long) criteria.getValues().get(0), (Long) criteria.getValues().get(1)));
                    }
                    if (criteria.getValues().get(0) instanceof Integer)
                    {
                        predicates.add(builder.between(root.get(criteria.getField()), (Integer) criteria.getValues().get(0), (Integer) criteria.getValues().get(1)));
                    }
                    if (criteria.getValues().get(0) instanceof Double)
                    {
                        predicates.add(builder.between(root.get(criteria.getField()), (Double) criteria.getValues().get(0), (Double) criteria.getValues().get(1)));
                    }
                    if (criteria.getValues().get(0) instanceof Float)
                    {
                        predicates.add(builder.between(root.get(criteria.getField()), (Float) criteria.getValues().get(0), (Float) criteria.getValues().get(1)));
                    }
                    break;
            }
        }

        return builder.and(predicates.toArray(new Predicate[0]));
    }

    /**
     * Adds a search condition to the list of the specification predicates.
     * @param condition Condition to add.
     * @param field Field the condition belongs to.
     */
    public void add(final QueryCondition condition, final QueryField field)
    {
        List<Enum<?>> values = new ArrayList<>();

        if (field != null && field.getFieldType() == FieldDataType.ENUM)
        {
            for (Object value : condition.getValues())
            {
                if (value instanceof String)
                {
                    for (Object eValue : field.getFieldClassType().getEnumConstants())
                    {
                        if (eValue.toString().equals(value))
                        {
                            values.add((Enum<?>) eValue);
                        }
                    }
                }
            }

            if (!values.isEmpty())
            {
                condition.setValues(Collections.singletonList(values));
            }
        }

        if (condition != null && (condition.getValues().get(0) != null || condition.getValues().get(1) != null))
        {
            list.add(condition);
        }
    }

    /**
     * Returns the number of criteria.
     * @return Number of criteria.
     */
    public int count()
    {
        return list.size();
    }

    /**
     * Substitutes special characters from the list of received criteria.
     */
    public void substituteSpecialCharacters()
    {
        String value;

        for (QueryCondition criteria : list)
        {
            if (criteria.getValues().get(0) instanceof String)
            {
                value = (String) criteria.getValues().get(0);
                value = value.replace('*', '%');
                criteria.setValue(0, value);
            }
        }
    }
}
