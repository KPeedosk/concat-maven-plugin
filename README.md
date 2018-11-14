# Concat Maven Plugin #

Maven Plugin to allow concatenation of files.

Forked from [Bomas Contcat Maven Plugin](https://github.com/bomas/concat-maven-plugin "https://github.com/bomas/concat-maven-plugin").

This plugin is compiled against maven `3.2.5`, the last supported java 1.6 maven release, hosted on Maven Central.

```xml
<plugin>
    <groupId>io.github.flaw101</groupId>
    <artifactId>concat-maven-plugin</artifactId>
    <version><!-- A Version --> </version>
    <configuration>
      <!-- Configure me -->
    </configuration>
</plugin>
```

## Build Info ##

[![Build Status](https://travis-ci.org/Flaw101/concat-maven-plugin.svg?branch=master)](https://travis-ci.org/Flaw101/concat-maven-plugin)
[![Maven metadata URI](https://img.shields.io/maven-metadata/v/http/central.maven.org/maven2/io/github/flaw101/concat-maven-plugin/maven-metadata.xml.svg)](http://repo1.maven.org/maven2/io/github/flaw101/concat-maven-plugin/)

### Build ###

Mutation Testing, Code Formatting, OWASP Dependency Analysis, POM Ordering is all checked as part of the Travis CI build.

## Concatenation Types ##

Multiple methods of concatenating can be supported, the current implementions are,

* `FILE_LIST`
  * Default setting, expects a list of `concatFiles`.
* `DIRECTORY`
  * Given a specified `directory` all files will be concatenated to the output ordered by file name.
* `SEMVER`
  * Attempts to order a directory by [SEMVER](https://semver.org/) ordering.
### Param Validation ###

Validations are in place to check only the specified parameters required by the Concatentation Type, and are usable.

e.g. `directory` and `concatFiles` params are mutually exclusive and should not be set at the same time.

e.g. All files when using `FILE_LIST` must exist.

## File List Usage ##

A basic example, which will concat the `.input` files to the `concatfile.output`.

```xml
<plugin>
    <groupId>io.github.flaw101</groupId>
    <artifactId>concat-maven-plugin</artifactId>
    <configuration>
        <concatenationType>FILE_LIST</concatenationType>
        <outputFile>target/concatfile.output</outputFile>
        <concatFiles>
            <concatFile>src/test/resources/testfiles/file_1.input</concatFile>
            <concatFile>src/test/resources/testfiles/file_2.input</concatFile>
            <concatFile>src/test/resources/testfiles/file_3.input</concatFile>
        </concatFiles>
    </configuration>
</plugin>
```

## Directory Usage ##

Will concat all files in the directory to the output file.

```xml
<plugin>
    <groupId>io.github.flaw101</groupId>
    <artifactId>concat-maven-plugin</artifactId>
    <configuration>
        <outputFile>target/concatfile.output</outputFile>
        <directory>src/test/resources/testfiles/</directory>
        <concatenationType>
            DIRECTORY
        </concatenationType>
    </configuration>
</plugin>
```

## Params ##

* `appendNewline`
  * Defaults to false, will add a new line character between each concatenated file.
* `deleteTargetFile`
  * Defaults to false, if they target file should be deleted before concatenation.
* `concatenationType`
  * For selecting different types of concatentation implementations. Defaults to requiring a list of files as specified in the basic example.
* `directory`
  * When using `ConcatenationType.DIRECTORY` specify the directory from which to get all files. Natural ordering of the file name is used to sort the files.
* `startingFile`
  * When using directory this file will be used as the starting file for concatenation.
  * This file is _always_ appended first regardless of the ordering applied.

## Change Log ##

### 1.0.0 ###

* Correct maven dependencies to actually be able to test the project.
* Add ability to delete the target file.

### 1.1.0 ###

* Add ability to concat by a directory.

### 1.1.1 ###

* Set Commons IO to 2.5.
  * UTF-8 is used to write and read files.
* Use Toolchain to build against Java 6.

### 1.1.2 ###

* Updated to use Guice CDI
  * Why not over engineer

### 1.1.3 ###

* Remove final from `ConcatenationType` in mojo.

### 1.2.0 ###

* Make `concatenationType` required.
* Add debug logger for the parmas if enable.
* Move verification to CI profile

### 1.3.0 ###

* Add `startingFile` param used in `DIRECTORY` concatenation.
  * If specified this file will be the first file to be concatenated to with the rest following with natural ordering.

### 1.3.1 ###

* Correct `startingFile` for a `startingFile` that is contained within the same directory.

### 1.4.0 ###

* Add SLF4J Simple Logger.
  * Info, debug, and error logs are implemented for the flow of the plugin

### 1.5.0 ###

* Add SEMVER conact type. 
   * Follows directory for the most part except file comparison attempts [SEMVER](https://semver.org/) ordering.