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
package com.hemajoo.commerce.cherry.server.commons.entity.query.condition;

import com.hemajoo.commerce.cherry.server.shared.data.model.entity.base.type.QueryOperatorType;
import lombok.*;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a query <b>condition</b> to issue queries on server data model entities.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Log4j2
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "add")
public final class QueryCondition
{
    /**
     * Field's name.
     */
    @Getter
    @Setter
    private String field;

    /**
     * Operator type.
     */
    @Getter
    @Setter
    private QueryOperatorType operator;

    /**
     * List of values.
     */
    @Getter
    @Setter
    @Singular // For chaining of arguments
    private List<Object> values;

    /**
     * Sets the value for the given index.
     * @param index Index of the value to set.
     * @param value Value to set.
     */
    public void setValue(final int index, final @NonNull Object value)
    {
        if (index <= values.size())
        {
            List<Object> list = new ArrayList<>(List.of(values));
            list.set(index, value);
            values = list;
        }
        else
        {
            LOGGER.warn(String.format("Cannot set value: '%s' for search condition with index: '%s' because index is out of bound!", value, index));
        }
    }
}
