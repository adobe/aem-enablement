package com.acs.core.services;


import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "Fastly Purger", description = "Fastly Purger Config")
public @interface FastlyPurgerConfiguration {
    /**
     * Fastly Host
     *
     * @return fastlyHost
     */
    @AttributeDefinition(
            name = "Fastly Host",
            type = AttributeType.STRING,
            description = "The Cloud Service Environment Publish Host (Fastly) including scheme (http:// or https://)")
    String fastlyHost();

    /**
     * Fastly Purge Key
     *
     * @return fastlyPurgeKey
     */
    @AttributeDefinition(
            name = "Fastly Purge Key",
            type = AttributeType.STRING,
            description = "The Purge Key to use (Reach out to Adobe Support to get this key, it is environment specific.)")
    String fastlyPurgeKey();
}
