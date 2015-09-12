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

###URL support for "classpath:" scheme
```Java
URL url = Utils4J.url("classpath:org/fuin/utils4j/test.properties");
```
A full example can be found here: [ClasspathURLExample.java](https://github.com/fuinorg/utils4j/blob/master/src/test/java/org/fuin/utils4j/examples/ClasspathURLExample.java)

###ZIP and UNZIP
Compress and descompress a complete directory and it's subdirectories with a single line of code using Utils4J
```Java
final File zipDir = new File(Utils4J.getTempDir(), "mydir");
final File zipFile = new File(Utils4J.getTempDir(), "myfile1.zip");
Utils4J.zipDir(zipDir, "abc/def", zipFile);
Utils4J.unzip(zipFile, Utils4J.getTempDir());
```
A full example can be found here: [ZipDirExample.java](https://github.com/fuinorg/utils4j/blob/master/src/test/java/org/fuin/utils4j/examples/ZipDirExample.java)

###Tracking changes of a map
This is a wrapper for maps that keeps track of all changes made to the map. This means adding, replacing or deleting elements is tracked - not the changes to the objects itself. It's also possible to revert (undo) all changes made to the map. 
```Java
Map<String, Integer> map = new HashMap<>();
ChangeTrackingMap<String, Integer> trackingMap = new ChangeTrackingMap<>(map);
System.out.println(trackingMap.isChanged());
System.out.println(trackingMap.getAdded());
System.out.println(trackingMap.getChanged());
System.out.println(trackingMap.getRemoved());
trackingMap.revert();
```
A full example can be found here: [ChangeTrackingMapExample.java](https://github.com/fuinorg/utils4j/blob/master/src/test/java/org/fuin/utils4j/examples/ChangeTrackingMapExample.java)

###Tracking changes of a list
This is a wrapper for lists that keeps track of all changes made to the list. This means adding, replacing or deleting elements is tracked - not the changes to the objects itself. It's also possible to revert (undo) all changes made to the list. A restriction is that no duplicate elements are allowed in the list.
```Java
List<String> list = new ArrayList<>();
ChangeTrackingUniqueList<String> trackingList = new ChangeTrackingUniqueList<>(list);
System.out.println(trackingList.isChanged());
System.out.println(trackingList.getDeleted());
System.out.println(trackingList.getAdded());
trackingList.revert();
```
A full example can be found here: [ChangeTrackingUniqueListExample.java](https://github.com/fuinorg/utils4j/blob/master/src/test/java/org/fuin/utils4j/examples/ChangeTrackingUniqueListExample.java)

###Properties file preferences
Shows the use of a directory and properties file based [Preferences API](http://docs.oracle.com/javase/7/docs/technotes/guides/preferences/) implementation. It's basically a replacement for the registry based implementation on Windows.
```Java
System.setProperty("java.util.prefs.PreferencesFactory",  PropertiesFilePreferencesFactory.class.getName());
Preferences userPrefs = Preferences.userRoot();
```
A full example can be found here: [PropertiesFilePreferencesFactoryExample.java](https://github.com/fuinorg/utils4j/blob/master/src/test/java/org/fuin/utils4j/examples/PropertiesFilePreferencesFactoryExample.java)

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

 