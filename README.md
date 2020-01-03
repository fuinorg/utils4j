utils4j
=======

A small Java library that contains several helpful utility classes.

[![Build Status](https://jenkins.fuin.org/job/utils4j/badge/icon)](https://jenkins.fuin.org/job/utils4j/)
[![Coverage Status](https://sonarcloud.io/api/project_badges/measure?project=org.fuin%3Autils4j&metric=coverage)](https://sonarcloud.io/dashboard?id=org.fuin%3Autils4j)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.fuin/utils4j/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.fuin/utils4j/)
[![Javadocs](https://www.javadoc.io/badge/org.fuin/utils4j.svg)](https://www.javadoc.io/doc/org.fuin/utils4j)
[![LGPLv3 License](http://img.shields.io/badge/license-LGPLv3-blue.svg)](https://www.gnu.org/licenses/lgpl.html)
[![Java Development Kit 11](https://img.shields.io/badge/JDK-11-green.svg)](https://openjdk.java.net/projects/jdk/11/)

**Java 11 for releases >= 0.11.0** / Java 8 <= 0.10.1  /  Java 1.4.2 <= 0.8.0

* * *

# Features

* [URL support for "classpath:" scheme](#url-support-for-classpath-scheme)
* [Variable resolver](#variable-resolver)
* [ZIP and UNZIP](#zip-and-unzip)
* [Tracking changes of a list / map](#tracking-changes-of-a-list-map)
* [Easy file locking](#easy-file-locking)
* [Properties file preferences](#properties-file-preferences)
* [JAXB CDATA Stream Writer](#jaxb-cdata-stream-writer)
* [Wait for code to finish](#wait-for-code-to-finish) (Deprecated in favour of [Awaitility](https://github.com/awaitility/awaitility))
* [Find all JARs and classes in the classpath](#find-all-jars-and-classes-in-the-classpath)
* [Analyze classes in the classpath with Jandex](#analyze-classes-in-the-classpath-with-jandex)

* * *

# Description

### URL support for "classpath:" scheme
There are two variats you can use.

Directly create a URL using a utility method:
```Java
URL url = Utils4J.url("classpath:org/fuin/utils4j/test.properties");
```
A full example can be found here: [ClasspathURLExample.java](https://github.com/fuinorg/utils4j/blob/master/src/test/java/org/fuin/utils4j/examples/ClasspathURLExample.java)

If you register the URL stream handler, all URLs (no matter how they were constructed) will work:
```Java
Handler.add();
URL url = new URL("classpath:/org/fuin/utils4j/test.properties");
```

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
Allows to synchronize local processes by using a file based lock.
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
:information_source: If you just need to **unmarshal** (XML to Java) a CDATA section, using the above annotation is all you need to do.

:warning: If you need to **marshal** (Java to XML) a class with CDATA content you must use the CDataXmlStreamWriter in addition:
```Java
// Create writers
final StringWriter writer = new StringWriter();
final XMLStreamWriter xmlWriter = XMLOutputFactory.newInstance().createXMLStreamWriter(writer);
final CDataXmlStreamWriter cdataWriter = new CDataXmlStreamWriter(xmlWriter);

// Create JAXB context with example class
final JAXBContext ctx = JAXBContext.newInstance(MyClassWithCData.class);
final MyClassWithCData testee = new MyClassWithCData("<whatever this=\"is\"/>");

// Convert instance to XML
marshal(ctx, testee, null, cdataWriter);
final String xml = writer.toString();

// Prints out the result
System.out.println(xml);
// <?xml version="1.0" ?><my-class-with-cdata><![CDATA[<whatever this="is"/>]]></my-class-with-cdata>

// Convert it back to object
final MyClassWithCData copy = unmarshal(xml, MyClassWithCData.class);

// Print out cdata content
System.out.println(copy.getContent());
// <whatever this="is"/>
```

A full example can be found here: [CDataJaxbExample.java](https://github.com/fuinorg/utils4j/blob/master/src/test/java/org/fuin/utils4j/examples/CDataJaxbExample.java)

**Caution** - You must explicitly add the xml.bind-api dependency to your POM if you want to use this feature, because it's defined as optional here.

```xml
<dependency>
    <groupId>jakarta.xml.bind</groupId>
    <artifactId>jakarta.xml.bind-api</artifactId>
    <version>2.3.2</version>
</dependency>
```


### Wait for code to finish

> :warning: Deprecated in favour of [Awaitility](https://github.com/awaitility/awaitility)

The [WaitHelper](https://github.com/fuinorg/utils4j/blob/master/src/main/java/org/fuin/utils4j/WaitHelper.java) class supports waiting for some condition.

Example of waiting for a function to finish without an exception: 
```Java
// Try 5 times and wait 100 millis between tries (wait at max 10 seconds)
final WaitHelper waitHelper = new WaitHelper(1000, 5);

// Example of waiting for a customer to be found
waitHelper.waitUntilNoMoreException(() -> {

    // We want to wait some time to see if the CustomerNotFoundException 
    // disappears and a customer name is finally loaded
    loadCustomerName(customerId);

}, Arrays.asList(CustomerNotFoundException.class));
```

Example of waiting for an expected customer name:
```Java
waitHelper.waitUntilResult(() -> {

    // We want to wait some time to see if the CustomerNotFoundException
    // disappears and a customer name is finally loaded
    return loadCustomerName2(customerId);

}, Arrays.asList("Peter Parker, Inc"));
```

A full example can be found here: [WaitHelperExample.java](https://github.com/fuinorg/utils4j/blob/master/src/test/java/org/fuin/utils4j/examples/WaitHelperExample.java)


### Find all JARs and classes in the classpath
Easily return a list of all classes or JAR files in the classpath.
```Java
// List CLASS files
for (final File file : Utils4J.classpathFiles(Utils4J::classFile)) {
    System.out.println(file);
}

// List JAR files that are not located in the JRE directory
for (final File file : Utils4J.classpathFiles(Utils4J::nonJreJarFile)) {
    System.out.println(file);
}

// List JAR files that are located in the JRE directory
for (final File file : Utils4J.classpathFiles(Utils4J::jreJarFile)) {
    System.out.println(file);
}

// List JAR files that are located in the boot path of the JRE
for (final File file : Utils4J.pathsFiles(System.getProperty("sun.boot.class.path"), Utils4J::jreJarFile)) {
    System.out.println(file);
}
```
A full example can be found here: [FindJarsAndClassesInClasspath.java](https://github.com/fuinorg/utils4j/blob/master/src/test/java/org/fuin/utils4j/examples/FindJarsAndClassesInClasspath.java)

### Analyze classes in the classpath with Jandex
Easily find matching types from all classes or JAR files in the classpath using [Jandex](https://github.com/wildfly/jandex)
```Java
List<File> knownFiles = new ArrayList<>();
Indexer indexer = new Indexer();
JandexUtils.indexClasspath((URLClassLoader) this.getClass().getClassLoader(), indexer, knownFiles);
Index index = indexer.complete();
Set<ClassInfo> implementors = index.getAllKnownImplementors(DotName.createSimple(List.class.getName()));

// Print all classes that implement the 'List' interface
for (ClassInfo ci : implementors) {
    System.out.println(ci.name().toString());
}

// Print all files ("*.jar" and ".class") that were analyzed
for (File file : knownFiles) {
    System.out.println(file);
}
```
A test that shows the usage can be found here: [JandexUtilsTest](https://github.com/fuinorg/utils4j/blob/master/src/test/java/org/fuin/utils4j/JandexUtilsTest.java)

**Caution** - You must explicitly add the Jandex dependency to your POM if you want to use this feature, because it's defined as optional here.

```xml
<dependency>
    <groupId>org.jboss</groupId>
    <artifactId>jandex</artifactId>
    <version>2.0.4.Final</version>
</dependency>
```


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
