package com.github.sttk.stringcase.codepoint;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

import static java.lang.Character.codePointAt;

public class AsciiTest {

  @Test
  void testIsUpperCase() {
    assertThat(Ascii.isUpperCase(codePointAt("/", 0))).isFalse(); // 0x2f
    assertThat(Ascii.isUpperCase(codePointAt("0", 0))).isFalse(); // 0x30
    assertThat(Ascii.isUpperCase(codePointAt("9", 0))).isFalse(); // 0x39
    assertThat(Ascii.isUpperCase(codePointAt(":", 0))).isFalse(); // 0x3a

    assertThat(Ascii.isUpperCase(codePointAt("@", 0))).isFalse(); // 0x40
    assertThat(Ascii.isUpperCase(codePointAt("A", 0))).isTrue();  // 0x41
    assertThat(Ascii.isUpperCase(codePointAt("Z", 0))).isTrue();  // 0x5a
    assertThat(Ascii.isUpperCase(codePointAt("[", 0))).isFalse(); // 0x5b

    assertThat(Ascii.isUpperCase(codePointAt("`", 0))).isFalse(); // 0x60
    assertThat(Ascii.isUpperCase(codePointAt("a", 0))).isFalse(); // 0x61
    assertThat(Ascii.isUpperCase(codePointAt("z", 0))).isFalse(); // 0x7a
    assertThat(Ascii.isUpperCase(codePointAt("{", 0))).isFalse(); // 0x7b
  }

  @Test
  void testIsLowerCase() {
    assertThat(Ascii.isLowerCase(codePointAt("/", 0))).isFalse(); // 0x2f
    assertThat(Ascii.isLowerCase(codePointAt("0", 0))).isFalse(); // 0x30
    assertThat(Ascii.isLowerCase(codePointAt("9", 0))).isFalse(); // 0x39
    assertThat(Ascii.isLowerCase(codePointAt(":", 0))).isFalse(); // 0x3a

    assertThat(Ascii.isLowerCase(codePointAt("@", 0))).isFalse(); // 0x40
    assertThat(Ascii.isLowerCase(codePointAt("A", 0))).isFalse(); // 0x41
    assertThat(Ascii.isLowerCase(codePointAt("Z", 0))).isFalse(); // 0x5a
    assertThat(Ascii.isLowerCase(codePointAt("[", 0))).isFalse(); // 0x5b

    assertThat(Ascii.isLowerCase(codePointAt("`", 0))).isFalse(); // 0x60
    assertThat(Ascii.isLowerCase(codePointAt("a", 0))).isTrue();  // 0x61
    assertThat(Ascii.isLowerCase(codePointAt("z", 0))).isTrue();  // 0x7a
    assertThat(Ascii.isLowerCase(codePointAt("{", 0))).isFalse(); // 0x7b
  }

  @Test
  void testIsDigit() {
    assertThat(Ascii.isDigit(codePointAt("/", 0))).isFalse(); // 0x2f
    assertThat(Ascii.isDigit(codePointAt("0", 0))).isTrue();  // 0x30
    assertThat(Ascii.isDigit(codePointAt("9", 0))).isTrue();  // 0x39
    assertThat(Ascii.isDigit(codePointAt(":", 0))).isFalse(); // 0x3a

    assertThat(Ascii.isDigit(codePointAt("@", 0))).isFalse(); // 0x40
    assertThat(Ascii.isDigit(codePointAt("A", 0))).isFalse(); // 0x41
    assertThat(Ascii.isDigit(codePointAt("Z", 0))).isFalse(); // 0x5a
    assertThat(Ascii.isDigit(codePointAt("[", 0))).isFalse(); // 0x5b

    assertThat(Ascii.isDigit(codePointAt("`", 0))).isFalse(); // 0x60
    assertThat(Ascii.isDigit(codePointAt("a", 0))).isFalse(); // 0x61
    assertThat(Ascii.isDigit(codePointAt("z", 0))).isFalse(); // 0x7a
    assertThat(Ascii.isDigit(codePointAt("{", 0))).isFalse(); // 0x7b
  }

  @Test
  void testToUpperCase() {
    assertThat(Ascii.toUpperCase(codePointAt("a", 0))).isEqualTo(0x41);
    assertThat(Ascii.toUpperCase(codePointAt("z", 0))).isEqualTo(0x5a);
  }

  @Test
  void testToLowerCase() {
    assertThat(Ascii.toLowerCase(codePointAt("A", 0))).isEqualTo(0x61);
    assertThat(Ascii.toLowerCase(codePointAt("Z", 0))).isEqualTo(0x7a);
  }
}
