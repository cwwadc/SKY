package com.sky.standalone.container.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Represents the version information.
 *
 * @author David BRASSELY (brasseld at gmail.com)
 * @author GraviteeSource Team
 */
public final class Version {

    /**
     * Represents the build id, which is a string like "b13".
     */
    public final String BUILD_ID;

    /**
     * Represents the complete build number
     */
    public final String BUILD_NUMBER;

    /**
     * Represents the major version, such as "2.0".
     */
    public final String MAJOR_VERSION;

    /**
     * Represents the latest Revision number.
     */
    public final String REVISION;

    /**
     * The Runtime Version.
     */
    public static final Version RUNTIME_VERSION = Version.create(Version.class.getResourceAsStream("/version.properties"));

    private Version(String buildId, String buildVersion, String majorVersion, String revision) {
        this.BUILD_ID = fixNull(buildId);
        this.BUILD_NUMBER = fixNull(buildVersion);
        this.MAJOR_VERSION = fixNull(majorVersion);
        this.REVISION = fixNull(revision);
    }

    public static Version create(InputStream is) {
        Properties props = new Properties();
        try {
            props.load(is);
        } catch (IOException e) {
            // ignore even if the property was not found. we'll treat everything as unknown
        } catch (Exception e) {
            //ignore even if property not found
        }

        return new Version(
                props.getProperty("build-id"),
                props.getProperty("build-version"),
                props.getProperty("major-version"),
                props.getProperty("revision"));
    }

    private String fixNull(String v) {
        if(v==null) return "unknown";
        return v;
    }

    public String toString() {
        return MAJOR_VERSION + " (build: " + BUILD_ID + ") revision#" + REVISION;
    }
}
