utils4j
=======

A small Java library that contains several helpful utility classes.

[![Build Status](https://fuin-org.ci.cloudbees.com/job/utils4j/badge/icon)](https://fuin-org.ci.cloudbees.com/job/utils4j/)
[![Coverage Status](https://coveralls.io/repos/fuinorg/utils4j/badge.svg?branch=master)](https://coveralls.io/r/fuinorg/utils4j?branch=master)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.fuin/utils4j/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.fuin/utils4j/)
[![LGPLv3 License](http://img.shields.io/badge/license-LGPLv3-blue.svg)](https://www.gnu.org/licenses/lgpl.html)
[![Java Development Kit 1.8](https://img.shields.io/badge/JDK-1.8-green.svg)](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

<a href="https://fuin-org.ci.cloudbees.com/job/utils4j"><img src="http://www.fuin.org/images/Button-Built-on-CB-1.png" width="213" height="72" border="0" alt="Built on CloudBees"/></a>

**JDK 1.8 for releases >= 0.9.0  /  JDK 1.4.2 for releases up to 0.8.0**

* * *

##Examples

###ZIP directory
Compress a complete directory and it's subdirectories with a single line of code using Utils4J
```Java
// Given a local directory "mydir" on drive "c:" that contains
// three files "x.txt", "y.txt" and "z.txt"
File dirToZip = new File("c:/mydir");

// Run the ZIP command
Utils4J.zipDir(dirToZip, "abc/def", new File("c:/myfile1.zip"));
// The archive will contain the following structure:
// abc/def/x.txt
// abc/def/y.txt
// abc/def/z.txt

// Run the ZIP command again
Utils4J.zipDir(dirToZip, "", new File("c:/myfile2.zip"));
// The second archive will contain the following structure:
// x.txt
// y.txt
// z.txt
```

###Decompress a ZIP archive
Decompress a zip file with a single line of code using Utils4J 
```Java
// ZIP archive to decompress
File zipFile = new File("c:/myfile.zip");

// Target directory
File targetDir = new File("c:/test");

// Unzip the content into the target directory
Utils4J.unzip(zipFile, targetDir);
```

###Tracking changes of a list
This is a wrapper for lists that keeps track of all changes made to the list. This means adding, replacing or deleting elements is tracked - not the changes to the objects itself. It's also possible to revert (undo) all changes made to the list. A restriction is that no duplicate elements are allowed in the list. 
```Java
// Create a standard string list
List list = new ArrayList();

// Add some initial content
list.add("one");
list.add("two");
list.add("three");

// Wrap the list to track changes
final ChangeTrackingUniqueList trackingList = new ChangeTrackingUniqueList(list);

// Add and remove some items
trackingList.add("four");
trackingList.add("five");
trackingList.remove("three");

// Print the status
// Output: true
System.out.println("HAS CHANGED:");
System.out.println(trackingList.isChanged());
System.out.println();

// Print the deleted items
// Output: "three"
System.out.println("DELETED:");
printList(trackingList.getDeleted());

// Print the added items
// Output: "four", "five"
System.out.println("ADDED:");
printList(trackingList.getAdded());

// Revert all changes        
trackingList.revert();

System.out.println("REVERTED:");
// Output: "one", "two", "three"
printList(trackingList);        
```

###Tracking changes of a map
This is a wrapper for maps that keeps track of all changes made to the map. This means adding, replacing or deleting elements is tracked - not the changes to the objects itself. It's also possible to revert (undo) all changes made to the map.
```Java
// Create a standard map
Map map = new HashMap();

// Add some initial content
map.put("one", Integer.valueOf(1));
map.put("two", Integer.valueOf(2));
map.put("three", Integer.valueOf(3));

// Wrap the map to track changes
ChangeTrackingMap trackingMap = new ChangeTrackingMap(map);

// Add/change/remove item
trackingMap.put("four", Integer.valueOf(4));
trackingMap.put("three", Integer.valueOf(10));
trackingMap.remove("one");

// Print the status
// Output: true
System.out.println("HAS CHANGED:");
System.out.println(trackingMap.isChanged());
System.out.println();

// Print the added items
// Output: "four=4"
System.out.println("ADDED:");
printMap(trackingMap.getAdded());

// Print the changed items
// Output: "three=3"
System.out.println("CHANGED:");
printMap(trackingMap.getChanged());

// Print the removed items
// Output: "one=1"
System.out.println("REMOVED:");
printMap(trackingMap.getRemoved());        

// Revert all changes        
trackingMap.revert();

System.out.println("REVERTED:");
// Output: "one=1", "two=2", "three=3"
printMap(trackingMap);        
```

###Properties file preferences
Shows the use of a directory and properties file based [Preferences API](http://docs.oracle.com/javase/7/docs/technotes/guides/preferences/) implementation. It's basically a replacement for the registry based implementation on Windows.
```Java
// Create an application wide preferences directory 
// named "config" in the current startup directory
final File systemPrefDir = new File("./config");
if (!systemPrefDir.exists()) {
    systemPrefDir.mkdir();
}

// Create a user preferences directory with your 
// applications name in the user's home directory
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
```
A file ${user.home}/.myapp/preferences.properties will be created:
```
# DO NOT EDIT!
# Created by org.fuin.utils4j.PropertiesFilePreferences
# yyyy-MM-dd HH:mm:ss
a=1
b=test
```

* * *

##Snapshots

Snapshots can be found on the [OSS Sonatype Snapshots Repository](http://oss.sonatype.org/content/repositories/snapshots/org/fuin "Snapshot Repository"). 

Add the following to your .m2/settings.xml to enable snapshots in your Maven build:

```xml
<repository>
    <id>sonatype.oss.snapshots</id>
    <name>Sonatype OSS Snapshot Repository</name>
    <url>http://oss.sonatype.org/content/repositories/snapshots</url>
    <releases>
        <enabled>false</enabled>
    </releases>
    <snapshots>
        <enabled>true</enabled>
    </snapshots>
</repository>
```

 