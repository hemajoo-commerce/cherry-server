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
package com.hemajoo.commerce.cherry.server.document.filter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hemajoo.commerce.cherry.server.base.filter.AbstractFilter;

/**
 * Represents a <b>document metadata</b> filter object.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public class DocumentFilterMetadata extends AbstractFilter
{
    /**
     * Filter for the document metadata: name.
     */
    @JsonIgnore
    public static final String DOCUMENT_METADATA_NAME = "name";

    /**
     * Filter for the document metadata: description.
     */
    @JsonIgnore
    public static final String DOCUMENT_METADATA_DESCRIPTION = "description";

    /**
     * Filter for the document metadata: reference.
     */
    @JsonIgnore
    public static final String DOCUMENT_METADATA_REFERENCE = "reference";

    /**
     * Filter for the document metadata: document type.
     */
    @JsonIgnore
    public static final String DOCUMENT_METADATA_TYPE = "documentType";

    /**
     * Filter for the document metadata: tags.
     */
    @JsonIgnore
    public static final String DOCUMENT_METADATA_TAGS = "tags";

    /**
     * Filter for the document metadata: status type.
     */
    @JsonIgnore
    public static final String DOCUMENT_METADATA_STATUS_TYPE = "statusType";

    /**
     * Creates a new <b>document metadata</b> filter object.
     */
    public DocumentFilterMetadata()
    {
        filters.add(DOCUMENT_METADATA_TYPE);
        filters.add(DOCUMENT_METADATA_TAGS);
        filters.add(DOCUMENT_METADATA_REFERENCE);
        filters.add(DOCUMENT_METADATA_DESCRIPTION);
        filters.add(DOCUMENT_METADATA_NAME);
        filters.add(DOCUMENT_METADATA_STATUS_TYPE);
    }

    /**
     * Build a document metadata filter.
     * @return Document metadata filter.
     */
    public static DocumentFilterMetadata build()
    {
        return new DocumentFilterMetadata();
    }
}
