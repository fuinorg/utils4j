/**
 * Copyright (C) 2015 Michael Schnell. All rights reserved. 
 * http://www.fuin.org/
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 3 of the License, or (at your option) any
 * later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library. If not, see http://www.gnu.org/licenses/.
 */
package org.fuin.utils4j.examples;

import java.io.File;
import java.util.prefs.Preferences;

import org.fuin.utils4j.PropertiesFilePreferencesFactory;

/**
 * Example using the file based preferences factory.
 */
public final class PropertiesFilePreferencesFactoryExample {

    /**
     * Private constructor.
     */
    private PropertiesFilePreferencesFactoryExample() {
        throw new UnsupportedOperationException(
                "It's not allowed to create an instance of this class!");
    }

    /**
     * Executes the example.
     * 
     * @param args
     *            Not used.
     */
    public static void main(final String[] args) {

        // Create an application wide preferences directory named "config" in
        // the current startup directory
        final File systemPrefDir = new File("./config");
        if (!systemPrefDir.exists()) {
            systemPrefDir.mkdir();
        }

        // Create a user preferences directory with your applications name in
        // the user's home directory
        final File userHomeDir = new File(System.getProperty("user.home"));
        final File userPrefDir = new File(userHomeDir, ".myapp");
        if (!userPrefDir.exists()) {
            userPrefDir.mkdir();
        }

        // Set both directories as system properties
        System.setProperty(PropertiesFilePreferencesFactory.SYSTEM_PREF_DIR,
                systemPrefDir.toString());
        System.setProperty(PropertiesFilePreferencesFactory.USER_PREF_DIR,
                userPrefDir.toString());

        // Set the factory
        System.setProperty("java.util.prefs.PreferencesFactory",
                PropertiesFilePreferencesFactory.class.getName());

        // Write something to the preferences
        final Preferences userPrefs = Preferences.userRoot();
        userPrefs.putInt("a", 1);
        userPrefs.put("b", "test");

        // The following file will be created:
        //
        // ${user.home}/.myapp/preferences.properties
        //
        // It contains the following text:
        //
        // # DO NOT EDIT!
        // # Created by org.fuin.utils4j.PropertiesFilePreferences
        // # yyyy-MM-dd HH:mm:ss
        // a=1
        // b=test
        //

    }

}
