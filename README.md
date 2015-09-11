utils4j
=======

A small Java library that contains several helpful utility classes.

For more details and examples see:
http://www.fuin.org/utils4j/index.html

[![Build Status](https://fuin-org.ci.cloudbees.com/job/utils4j/badge/icon)](https://fuin-org.ci.cloudbees.com/job/utils4j/)
[![Coverage Status](https://coveralls.io/repos/fuinorg/utils4j/badge.svg?branch=master)](https://coveralls.io/r/fuinorg/utils4j?branch=master)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.fuin/utils4j/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.fuin/utils4j/)
[![LGPLv3 License](http://img.shields.io/badge/license-LGPLv3-blue.svg)](https://www.gnu.org/licenses/lgpl.html)
[![Java Development Kit 1.7](https://img.shields.io/badge/JDK-1.7-green.svg)](http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html)

<a href="https://fuin-org.ci.cloudbees.com/job/utils4j"><img src="http://www.fuin.org/images/Button-Built-on-CB-1.png" width="213" height="72" border="0" alt="Built on CloudBees"/></a>

#Examples

##ZIP directory
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

###Snapshots

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
 
