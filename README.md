# [stringcase-java][repo-url] [![Maven Central][mvn-img]][mvn-url] [![GitHub.io][io-img]][io-url] [![CI Status][ci-img]][ci-url] [![MIT License][mit-img]][mit-url]

This library provides some static methods of `StringCase` class that convert string cases between
Ada_Case, camelCase, COBOL-CASE, kebab-case, MACRO_CASE, PascalCase, snake_case, Title Case, and
Train-Case.
In addition, the static methods of `StringCase` class: `capitalize`, `lowerize`, and `upperize` are
provided to convert string cases with a custom joiner character.

Essentially, these static methods only target ASCII uppercase and lowercase letters for
capitalization.
All characters other than ASCII uppercase and lowercase letters and ASCII numbers are removed as
word separators.

If you want to use some symbols as separators, specify those symbols in the `separators` field of
an `Options` instance and use the `〜CaseWithOptions` static methods for the desired case.
If you want to retain certain symbols and use everything else as separators, specify those symbols
in `keep` field of an `Options` instance and use the `〜CaseWithOptions` static methods for the
desired case.

Additionally, you can specify whether to place word boundaries before and/or after non-alphabetic
characters with conversion options.
This can be set using the `separateBeforeNonAlphabets` and `separateAfterNonAlphabets` fields in
the `Options` instance.

The `〜Case` static methods that do not take `Options` as an argument only place word boundaries
after non-alphabetic characters.
In other words, they behave as if
`separateBeforeNonAlphabets = false` and `separateAfterNonAlphabets = true`.

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
      <version>0.2.0</version>
    </dependency>
  </dependencies>
```

### for Gradle

```
repositories {
  mavenCentral()
}
dependencies {
  implementation 'io.github.sttk:stringcase:0.2.0'
}
```

## Usage

The static methods contained in this library are executed as follows:

```java
import com.github.sttk.stringcase.StringCase;

public static void main(String[] args) {
    var input = "fooBar123Baz";
    var snake = StringCase.snakeCase(input);
    System.out.println(snake);  // => "foo_bar123_baz"
}
```

If you want the conversion to behave differently, use `〜CaseWithOptions`.

```java
import com.github.sttk.stringcase.StringCase;
import com.github.sttk.stringcase.Options;

public static void main(String[] args) {
    var opts = new Options(true, true, null, null);
    var input = "fooBar123Baz";
    var snake = StringCase.snakeCaseWithOptions(input, opts);
    System.out.println(snake);  // => "foo_bar_123_baz"
}
```

You can also use the static method `capitalize`, `lowerize`, and `upperize` to convert strings into capitalized, lowercased, or uppercased words joined by a custom joiner character:

```java
import com.github.sttk.stringcase.StringCase;
import com.github.sttk.stringcase.Options;

public static void main(String[] args) {
    var opts = new Options(true, true, null, null);
    var input = "fooBar123Baz";
    var output = StringCase.capitalize(input, '.', opts);
    System.out.println(snake);  // => "Foo.Bar.123.Baz"
}
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

- Oracle GraalVM 21.0.6+8.1 (java version "21.0.6" 2025-01-21 LTS)
- Oracle GraalVM 25.0.1+8.1 (java version "25.0.1" 2025-10-21 LTS)

## License

Copyright (C) 2024-2025 Takayuki Sato

This program is free software under MIT License.<br>
See the file LICENSE in this distribution for more details.


[repo-url]: https://github.com/sttk/stringcase-java
[mvn-img]: https://img.shields.io/badge/maven_central-0.2.0-276bdd.svg
[mvn-url]: https://central.sonatype.com/artifact/io.github.sttk/stringcase/0.2.0
[io-img]: https://img.shields.io/badge/github.io-Javadoc-4d7a97.svg
[io-url]: https://sttk.github.io/stringcase-java/
[ci-img]: https://github.com/sttk/stringcase-java/actions/workflows/java-ci.yml/badge.svg?branch=main
[ci-url]: https://github.com/sttk/stringcase-java/actions
[mit-img]: https://img.shields.io/badge/license-MIT-green.svg
[mit-url]: https://opensource.org/licenses/MIT
