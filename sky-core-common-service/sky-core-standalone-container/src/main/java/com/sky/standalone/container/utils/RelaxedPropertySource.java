package com.sky.standalone.container.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.core.env.MapPropertySource;
import org.springframework.util.Assert;

import java.util.Map;

public class RelaxedPropertySource extends MapPropertySource {

    @Autowired
    GenericConversionService conversionService;

    public RelaxedPropertySource(String name, Map<String, Object> source) {
        super(name, source);
    }

    @Override
    public boolean containsProperty(String name) {
        boolean contains = (getProperty(name) != null);
        if (!contains && name.contains("[")) {
            return (getProperty(encodedArray(name)) != null);
        }

        return contains;
    }

    @Override
    public Object getProperty(String name) {
        String actualName = resolvePropertyName(name);
        if (logger.isDebugEnabled() && !name.equals(actualName)) {
            logger.debug(String.format("PropertySource [%s] does not contain '%s', but found equivalent '%s'",
                    getName(), name, actualName));
        }
        return super.getProperty(actualName);
    }

    private String resolvePropertyName(String name) {
        Assert.notNull(name, "Property name must not be null");
        String resolvedName = checkPropertyName(name);
        if (resolvedName != null) {
            return resolvedName;
        }
        String uppercasedName = name.toUpperCase();
        if (!name.equals(uppercasedName)) {
            resolvedName = checkPropertyName(uppercasedName);
            if (resolvedName != null) {
                return resolvedName;
            }
        }

        return name;
    }

    private String checkPropertyName(String name) {
        // Check name as-is
        if (containsKey(name)) {
            return name;
        }

        // Check name with just dots replaced
        String noDotName = name.replace('.', '_');
        if (!name.equals(noDotName) && containsKey(noDotName)) {
            return noDotName;
        }
        // Check name with just hyphens replaced
        String noHyphenName = name.replace("-", "");
        if (!name.equals(noHyphenName) && containsKey(noHyphenName)) {
            return noHyphenName;
        }
        // Check name with dots and hyphens replaced
        String noHyphenNameAndDotName = noDotName.replace("-", "");
        if (!name.equals(noHyphenNameAndDotName) && containsKey(noHyphenNameAndDotName)) {
            return noHyphenNameAndDotName;
        }
        // Check if name is an array
        if(name.split(":")[0].contains("[")) {
            return checkPropertyName(encodedArray(name));
        }

        // Give up
        return null;
    }
    private String encodedArray(String name) {
        String[] keyWithDefault = name.split(":");
        String encodedKey = keyWithDefault[0];
        if(keyWithDefault[0].contains("[")) {
            encodedKey = encodedKey
                    .replace("[", ".")
                    .replace("]", "");
        }
        return keyWithDefault.length == 1 ? encodedKey : encodedKey + ":" + keyWithDefault[1];
    }

    private boolean containsKey(String name) {
        return (isSecurityManagerPresent() ? this.source.keySet().contains(name) : this.source.containsKey(name));
    }

    protected boolean isSecurityManagerPresent() {
        return (System.getSecurityManager() != null);
    }

}
