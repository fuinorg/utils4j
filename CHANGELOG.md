# Release Notes

## 0.16.1
- Import new Super BOM
- Avoid snapshot dependencies when releasing
- Added [flatten-maven-plugin](https://www.mojohaus.org/flatten-maven-plugin/)

## 0.16.0
- Added new [Utils4J](src/main/java/org/fuin/utils4j/Utils4J.java) method to encode a UUID to a short string and back
- Added [JSpecify](https://jspecify.dev/) and [NullAway](https://github.com/uber/nullaway)

## 0.15.0
- Added new [JandexUtils](src/main/java/org/fuin/utils4j/jandex/JandexUtils.java) methods
- Added new [TechnicalId](src/main/java/org/fuin/utils4j/TechnicalId.java) tag interface.

## 0.14.0

### General
- Dependency updates
- New [MultipleCommands](src/main/java/org/fuin/utils4j/MultipleCommands.java) class
- New [TestCommand](src/main/java/org/fuin/utils4j/TestCommand.java) class
- New [TestOmitted](src/main/java/org/fuin/utils4j/TestOmitted.java) annotation

### Jandex
- [JandexUtils](src/main/java/org/fuin/utils4j/jandex/JandexUtils.java) moved to "jandex" subdirectory
- New [JandexIndexFileReader](src/main/java/org/fuin/utils4j/jandex/JandexIndexFileReader.java) and [JandexIndexFileWriter](src/main/java/org/fuin/utils4j/jandex/JandexIndexWriter.java)

### JAX-B
- [MarshallerBuilder](src/main/java/org/fuin/utils4j/jaxb/MarshallerBuilder.java) 
- Several static methods in [JaxbUtils](src/main/java/org/fuin/utils4j/jaxb/JaxbUtils.java) are now deprecated in favour of [MarshallerBuilder](src/main/java/org/fuin/utils4j/jaxb/MarshallerBuilder.java) and [UnmarshallerBuilder](src/main/java/org/fuin/utils4j/jaxb/UnmarshallerBuilder.java) 
