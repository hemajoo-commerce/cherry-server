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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hemajoo.commerce.cherry.server.commons.entity.query.condition.QueryCondition;
import com.hemajoo.commerce.cherry.server.commons.entity.query.condition.QueryConditionException;
import com.hemajoo.commerce.cherry.server.commons.entity.query.condition.QueryField;
import com.hemajoo.commerce.cherry.server.shared.data.model.entity.base.IAuditEntity;
import com.hemajoo.commerce.cherry.server.shared.data.model.entity.base.exception.NotYetImplementedException;
import com.hemajoo.commerce.cherry.server.shared.data.model.entity.base.type.EntityType;
import com.hemajoo.commerce.cherry.server.shared.data.model.entity.base.type.FieldDataType;
import com.hemajoo.commerce.cherry.server.shared.data.model.entity.base.type.QueryOperatorType;
import lombok.Data;
import lombok.NonNull;
import lombok.Singular;
import lombok.extern.log4j.Log4j2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents an abstract <b>query</b> object for the <b>audit</b> part of entities.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Data
@Log4j2
public abstract class AbstractQueryAudit implements IQuery, Serializable
{
    /**
     * Entity type.
     */
    private EntityType entityType = null;

    /**
     * Available fields that can be queried.
     */
    @JsonIgnore
    protected final List<QueryField> fields = new ArrayList<>();

    /**
     * Query conditions.
     */
    @Singular("addCondition")
    protected final List<QueryCondition> conditions = new ArrayList<>();

    /**
     * Adds a query condition.
     * @param condition Query condition.
     * @throws QueryConditionException Thrown to indicate an error occurred with a query condition.
     */
    @Override
    public final <T extends BaseQueryEntity> T addCondition(final @NonNull QueryCondition condition) throws QueryConditionException
    {
        if (condition.getField() != null && condition.getOperator() != null && !condition.getValues().isEmpty())
        {
            if (fields.stream().anyMatch(e -> e.getFieldName().equals(condition.getField())))
            {
                conditions.add(condition);
            }
            else
            {
                String message = String.format("Cannot add query condition for field with name: '%s' because this field is not part of the entity class hierarchy for: '%s'!",
                        condition.getField(),
                        this.getClass().getName());
                LOGGER.error(message);

                throw new QueryConditionException(message);
            }
        }
        else
        {
            String message = String.format("Invalid query condition field with name: '%s', with operator: '%s', with values: '%s'",
                    condition.getField(),
                    condition.getOperator(),
                    condition.getValues());
            LOGGER.error(message);

            throw new QueryConditionException(message);
        }

        QueryField field = fields.stream().filter(e -> e.getFieldName().equals(condition.getField())).findAny().orElse(null);
        if (field != null && field.getFieldType() == FieldDataType.ENUM)
        {
            checkConditionForEnumField(field, condition);
        }

        return (T) this;
    }

    /**
     * Checks query condition for fields of type {@link FieldDataType#ENUM}.
     * @param field Field.
     * @param condition Query condition.
     * @throws QueryConditionException Thrown to indicate an error occurred with a query condition.
     */
    private void checkConditionForEnumField(final @NonNull QueryField field, final @NonNull QueryCondition condition) throws QueryConditionException
    {
        if (condition.getOperator() != QueryOperatorType.EQUAL)
        {
            String message = String.format("Invalid query condition for field with name: '%s', with type: '%s', with operator: '%s'! Only: '%s' operator is allowed for fields of type: '%s'",
                    condition.getField(),
                    field.getFieldType(),
                    condition.getOperator(),
                    Arrays.toString(new QueryOperatorType[]{ QueryOperatorType.EQUAL }),
                    field.getFieldType());
            LOGGER.error(message);

            throw new QueryConditionException(message);
        }
    }

    @JsonIgnore
    @Override
    public final GenericSpecification<?> getSpecification()
    {
        GenericSpecification<?> specification = new GenericSpecification<>();
        QueryField field;

        for (QueryCondition condition : conditions)
        {
            field = fields.stream().filter(f -> f.getFieldName().equals(condition.getField())).findAny().orElse(null);
            specification.add(condition, field);
        }

        return specification;
    }

    @Override
    public void validate()
    {
        // TODO Implement checks of the conditions...
        throw new NotYetImplementedException();
    }

    /**
     * Creates a new abstract audit query instance.
     * @param entityType Entity type.
     */
    public AbstractQueryAudit(final @NonNull EntityType entityType)
    {
        super();

        this.entityType = entityType;

        fields.add(QueryField.builder()
                .withFieldName(IAuditEntity.BASE_CREATED_DATE)
                .withFieldType(FieldDataType.DATE)
                .build());
        fields.add(QueryField.builder()
                .withFieldName(IAuditEntity.BASE_MODIFIED_DATE)
                .withFieldType(FieldDataType.DATE)
                .build());
        fields.add(QueryField.builder()
                .withFieldName(IAuditEntity.BASE_CREATED_BY)
                .withFieldType(FieldDataType.STRING)
                .build());
        fields.add(QueryField.builder()
                .withFieldName(IAuditEntity.BASE_MODIFIED_BY)
                .withFieldType(FieldDataType.STRING)
                .build());
    }

    /**
     * Creates a new abstract audit query instance.
     */
    public AbstractQueryAudit()
    {
        fields.add(QueryField.builder()
                .withFieldName(IAuditEntity.BASE_CREATED_DATE)
                .withFieldType(FieldDataType.DATE)
                .build());
        fields.add(QueryField.builder()
                .withFieldName(IAuditEntity.BASE_MODIFIED_DATE)
                .withFieldType(FieldDataType.DATE)
                .build());
        fields.add(QueryField.builder()
                .withFieldName(IAuditEntity.BASE_CREATED_BY)
                .withFieldType(FieldDataType.STRING)
                .build());
        fields.add(QueryField.builder()
                .withFieldName(IAuditEntity.BASE_MODIFIED_BY)
                .withFieldType(FieldDataType.STRING)
                .build());
    }
}
