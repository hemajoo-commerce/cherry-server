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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hemajoo.commerce.cherry.server.shared.data.model.entity.base.ClientEntity;
import com.hemajoo.commerce.cherry.server.shared.data.model.entity.base.type.EntityType;
import com.hemajoo.commerce.cherry.server.shared.data.model.entity.person.address.type.AddressType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Provides an implementation of an email address client data model entity.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
//@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@ToString(callSuper = true)
//@Builder(setterPrefix = "with") // Does not work well with MapStruct!
@EqualsAndHashCode(callSuper = true)
public class ClientEmailAddress extends ClientEntity implements IClientEmailAddress
{
    /**
     * Email address.
     */
    @JsonProperty("email")
    @Schema(name = "email", description = "Email address", example = "joe.doe@gmail.com")
    //@Email(message = "email: '${validatedValue}' is not a valid email!")
    private String email;

    /**
     * Is it the default email address?
     */
    @JsonProperty("isDefault")
    @Schema(name = "defaultEmail", description = "Is it the default email address", example = "true")
    private Boolean isDefaultEmail;

    /**
     * Email address type.
     */
    @JsonProperty("addressType")
    @Schema(name = "addressType", description = "Address type", example = "PRIVATE")
    //@Enumerated(EnumType.STRING)
    private AddressType addressType;

    /**
     * Creates a new client email address entity.
     */
    public ClientEmailAddress()
    {
        super(EntityType.EMAIL_ADDRESS);
    }
}
