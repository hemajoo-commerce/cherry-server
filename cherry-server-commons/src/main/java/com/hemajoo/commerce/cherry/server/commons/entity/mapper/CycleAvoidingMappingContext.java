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
package com.hemajoo.commerce.cherry.server.commons.entity.mapper;

import org.mapstruct.BeforeMapping;
import org.mapstruct.Context;
import org.mapstruct.MappingTarget;
import org.mapstruct.TargetType;

import java.util.IdentityHashMap;
import java.util.Map;

/**
 * A type to be used as {@link Context} parameter to track cycle references in graphs. Is intended to be used when
 * converting beans using the {@code MapStruct} mapper library.
 * <p>
 * Depending on the actual use case, the two methods below could also be changed to only accept certain argument types,
 * e.g. base classes of graph nodes, avoiding the need to capture any other objects that wouldn't necessarily result in
 * cycles.
 * @author MapStruct authors
 * @version 1.0.0
 */
public class CycleAvoidingMappingContext
{
    /**
     * Collection of already known instances.
     */
    private final Map<Object, Object> knownInstances = new IdentityHashMap<>();

    /**
     * Finds the mapped instance.
     * @param source Source object.
     * @param targetType Target type.
     * @param <T> Object type.
     * @return Mapped instance.
     */
    @BeforeMapping
    public <T> T getMappedInstance(Object source, @TargetType Class<T> targetType)
    {
        return (T) knownInstances.get(source);
    }

    /**
     * Stores a mapped instance.
     * @param source Source object.
     * @param target Target object.
     */
    @BeforeMapping
    public void storeMappedInstance(Object source, @MappingTarget Object target)
    {
        knownInstances.put(source, target);
    }
}
