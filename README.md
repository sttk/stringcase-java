# [stringcase-java][repo-url] [![Maven Central][mvn-img]][mvn-url] [![GitHub.io][io-img]][io-url] [![CI Status][ci-img]][ci-url] [![MIT License][mit-img]][mit-url]

A Java library for converting string cases between camelCase, COBOL-CASE, kebab-case, MACRO_CASE, PascalCase, snake_case and Train-Case.

## Install

This package can be installed from [Maven Central Repository][mvn-url].

The examples of declaring that repository and the dependency on this package in
Maven `pom.xml` and Gradle `build.gradle` are as follows:

### for Maven

```
  <dependencies>
    <dependency>
      <groupId>io.github.sttk</groupId>
      <artifactId>stringcase</artifactId>
      <version>0.1.0</version>
    </dependency>
  </dependencies>
```

### for Gradle

```
repositories {
  mavenCentral()
}
dependencies {
  implementation 'io.github.sttk:stringcase:0.1.0'
}
```

## Usage

The following code converts the argument text into various cases.

```
import com.github.sttk.stringcase.StringCase;
...
    var input = "foo_barBAZQux";
    var camel = StringCase.camelCase(input); // => fooBarBazQux
    var cobol = StringCase.cobolCase(input); // => FOO-BAR-BAZ-QUX
    var kebab = StringCase.kebabCase(input); // => foo-bar-baz-qux
    var macro = StringCase.macroCase(input); // => FOO_BAR_BAZ_QUX
    var pascal = StringCase.pascalCase(input); // => FooBarBazQux
    var train = StringCase.trainCase(input); // => Foo-Bar-Baz-Qux
```

## Native build

This library supports native build with GraalVM.

See the following pages to setup native build environment on Linux/macOS or Windows.
- [Setup native build environment on Linux/macOS](https://www.graalvm.org/latest/reference-manual/native-image/)
- [Setup native build environment on Windows](https://www.graalvm.org/latest/docs/getting-started/windows/#prerequisites-for-native-image-on-windows)

And see the following pages to build native image with Maven or Gradle.
- [Native image building with Maven plugin](https://graalvm.github.io/native-build-tools/latest/maven-plugin.html)
- [Native image building with Gradle plugin](https://graalvm.github.io/native-build-tools/latest/gradle-plugin.html)

## Supporting JDK versions

This framework supports JDK 21 or later.

### Actually checked JDK versions:

- GraalVM CE 21.0.2+13.1 (openjdk version 21.0.2)

## License

Copyright (C) 2024 Takayuki Sato

This program is free software under MIT License.<br>
See the file LICENSE in this distribution for more details.


[repo-url]: https://github.com/sttk/stringcase-java
[mvn-img]: https://img.shields.io/badge/maven_central-0.1.0-276bdd.svg
[mvn-url]: https://central.sonatype.com/artifact/io.github.sttk/stringcase/0.1.0
[io-img]: https://img.shields.io/badge/github.io-Javadoc-4d7a97.svg
[io-url]: https://sttk.github.io/stringcase-java/
[ci-img]: https://github.com/sttk/stringcase-java/actions/workflows/java-ci.yml/badge.svg?branch=main
[ci-url]: https://github.com/sttk/stringcase-java/actions
[mit-img]: https://img.shields.io/badge/license-MIT-green.svg
[mit-url]: https://opensource.org/licenses/MIT
