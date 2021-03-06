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
package com.hemajoo.commerce.cherry.server.data.model.base;

import com.hemajoo.commerce.cherry.server.shared.data.model.entity.base.IAuditEntity;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * Represents an abstract server data model <b>auditable</b> entity.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractServerAuditEntity implements IAuditEntity
{
    /**
     * Entity creation date.
     */
    @Getter
    @Setter
    @Column(name = "CREATED_DATE", length = 26)
    @CreatedDate
    private Date createdDate;

    /**
     * Entity modification date.
     */
    @Getter
    @Setter
    @Column(name = "MODIFIED_DATE", length = 26)
    @LastModifiedDate
    private Date modifiedDate;

    /**
     * Entity author.
     */
    @Getter
    @Setter
    @Column(name = "CREATED_BY", length = 50)
    @CreatedBy
    private String createdBy;

    /**
     * Entity last modification author.
     */
    @Getter
    @Setter
    @Column(name = "MODIFIED_BY", length = 50)
    @LastModifiedBy
    private String modifiedBy;
}
