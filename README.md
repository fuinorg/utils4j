utils4j
=======

A small Java library that contains several helpful utility classes.

[![Build Status](https://fuin-org.ci.cloudbees.com/job/utils4j/badge/icon)](https://fuin-org.ci.cloudbees.com/job/utils4j/)
[![Coverage Status](https://coveralls.io/repos/fuinorg/utils4j/badge.svg?branch=master)](https://coveralls.io/r/fuinorg/utils4j?branch=master)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.fuin/utils4j/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.fuin/utils4j/)
[![Javadocs](https://www.javadoc.io/badge/org.fuin/utils4j.svg)](https://www.javadoc.io/doc/org.fuin/utils4j)
[![LGPLv3 License](http://img.shields.io/badge/license-LGPLv3-blue.svg)](https://www.gnu.org/licenses/lgpl.html)
[![Java Development Kit 1.8](https://img.shields.io/badge/JDK-1.8-green.svg)](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

<a href="https://fuin-org.ci.cloudbees.com/job/utils4j"><img src="http://www.fuin.org/images/Button-Built-on-CB-1.png" width="213" height="72" border="0" alt="Built on CloudBees"/></a>

**JDK 1.8 for releases >= 0.9.0  /  JDK 1.4.2 for releases up to 0.8.0**

* * *

# Features

* [URL support for "classpath:" scheme](#url-support-for-classpath-scheme)
* [Variable resolver](#variable-resolver)
* [ZIP and UNZIP](#zip-and-unzip)
* [Tracking changes of a list / map](##tracking-changes-of-a-list--map)
* [Easy file locking](#easy-file-locking)
* [Properties file preferences](#properties-file-preferences)
* [JAXB CDATA Stream Writer](#jaxb-cdata-stream-writer)

* * *

# Description

### URL support for "classpath:" scheme
```Java
URL url = Utils4J.url("classpath:org/fuin/utils4j/test.properties");
```
A full example can be found here: [ClasspathURLExample.java](https://github.com/fuinorg/utils4j/blob/master/src/test/java/org/fuin/utils4j/examples/ClasspathURLExample.java)


### Variable resolver
Resolves variable references in a map and prevents on cycles.
```
a=1
b=${a}/2
c=${b}/3
```
After calling the resolver a map is returned that has the replaced values:
```
a=1
b=1/2
c=1/2/3
```
A full example can be found here: [VariableResolverExample.java](https://github.com/fuinorg/utils4j/blob/master/src/test/java/org/fuin/utils4j/examples/VariableResolverExample.java)


### ZIP and UNZIP
Compress and descompress a complete directory and it's subdirectories with a single line of code
```Java
final File zipDir = new File(Utils4J.getTempDir(), "mydir");
final File zipFile = new File(Utils4J.getTempDir(), "myfile1.zip");
Utils4J.zipDir(zipDir, "abc/def", zipFile);
Utils4J.unzip(zipFile, Utils4J.getTempDir());
```
A full example can be found here: [ZipDirExample.java](https://github.com/fuinorg/utils4j/blob/master/src/test/java/org/fuin/utils4j/examples/ZipDirExample.java)


### Tracking changes of a list / map
This is a wrapper for lists that keeps track of all changes made to the list. This means adding, replacing or deleting elements is tracked - not the changes to the objects itself. It's also possible to revert (undo) all changes made to the list. A restriction is that no duplicate elements are allowed in the list.
```Java
List<String> list = new ArrayList<>();
ChangeTrackingUniqueList<String> trackingList = new ChangeTrackingUniqueList<>(list);
System.out.println(trackingList.isChanged());
System.out.println(trackingList.getDeleted());
System.out.println(trackingList.getAdded());
trackingList.revert();
```
A list example can be found here: [ChangeTrackingUniqueListExample.java](https://github.com/fuinorg/utils4j/blob/master/src/test/java/org/fuin/utils4j/examples/ChangeTrackingUniqueListExample.java)
A map example can be found here: [ChangeTrackingMapExample.java](https://github.com/fuinorg/utils4j/blob/master/src/test/java/org/fuin/utils4j/examples/ChangeTrackingMapExample.java)


### Easy file locking
```Java
FileLock lock = Utils4J.lockRandomAccessFile(randomAccessFile, tryLockMax, tryWaitMillis);
try {
   // Do something...
} finally {
    lock.release();
}
```
A full example can be found here: [LockFileExample.java](https://github.com/fuinorg/utils4j/blob/master/src/test/java/org/fuin/utils4j/examples/LockFileExample.java)


### Properties file preferences
Shows the use of a directory and properties file based [Preferences API](http://docs.oracle.com/javase/7/docs/technotes/guides/preferences/) implementation. It's basically a replacement for the registry based implementation on Windows.
```Java
System.setProperty("java.util.prefs.PreferencesFactory",  PropertiesFilePreferencesFactory.class.getName());
Preferences userPrefs = Preferences.userRoot();
```
A full example can be found here: [PropertiesFilePreferencesFactoryExample.java](https://github.com/fuinorg/utils4j/blob/master/src/test/java/org/fuin/utils4j/examples/PropertiesFilePreferencesFactoryExample.java)


### JAXB CDATA Stream Writer

XML stream writer that does **not** escape the content of a CDATA section. 
This is meant to be used with JAXB to serialize a string field to a CDATA section. 
The field to write as CDATA section is annotated with a CDataXmlAdapter.

Example of class using the adapter:
```Java
/**
 * Example class with CDATA field.
 */
@XmlRootElement(name = "my-class-with-cdata")
public final class MyClassWithCData {

    // Field has the CDataXmlAdapter
    @XmlValue
    @XmlJavaTypeAdapter(CDataXmlAdapter.class)
    private String content;
    
}
```

Example of serializing the above class:
```Java
// Create writers
final StringWriter writer = new StringWriter();
final XMLStreamWriter xmlWriter = XMLOutputFactory.newInstance().createXMLStreamWriter(writer);
final CDataXmlStreamWriter cdataWriter = new CDataXmlStreamWriter(xmlWriter);

// Create JAXB context with example class
final JAXBContext ctx = JAXBContext.newInstance(MyClassWithCData.class);
final MyClassWithCData testee = new MyClassWithCData("<whatever this=\"is\"/>");

// Marshal the instance to XML
marshal(ctx, testee, null, cdataWriter);

// Prints out the result
System.out.println(writer.toString());
```

XML output (formatted):
```xml
<?xml version="1.0" ?>
<my-class-with-cdata>
<![CDATA[<whatever this="is"/>]]>
</my-class-with-cdata>
```


A full example can be found here: [CDataJaxbExample.java](https://github.com/fuinorg/utils4j/blob/master/src/test/java/org/fuin/utils4j/examples/CDataJaxbExample.java)

* * *

## Snapshots

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

 