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
package com.hemajoo.commerce.cherry.server.shared.data.model.entity.person.address.email;

import com.hemajoo.commerce.cherry.server.shared.data.model.entity.base.IEntity;
import com.hemajoo.commerce.cherry.server.shared.data.model.entity.person.address.type.AddressType;

/**
 * Defines the behavior of an <b>email address</b>.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @since Cherry 0.1.0
 * @version 1.0.0
 */
public interface IEmailAddress extends IEntity
{
    /**
     * Returns the email address.
     * @return Email address.
     */
    String getEmail();

    /**
     * Sets the email address.
     * @param email Email address.
     */
    void setEmail(final String email);

    /**
     * Returns if this email address if the default email address.
     * @return True if this email address is the default one, false otherwise.
     */
    Boolean getIsDefaultEmail();

    /**
     * Sets if this email address is the default one.
     * @param isDefaultEmail True to set this email address as the default one, false otherwise.
     */
    void setIsDefaultEmail(final Boolean isDefaultEmail);

    /**
     * Returns the email address type.
     * @return Email address type
     */
    AddressType getAddressType();

    /**
     * Sets the email address type.
     * @param type Email address type.
     */
    void setAddressType(final AddressType type);
}
