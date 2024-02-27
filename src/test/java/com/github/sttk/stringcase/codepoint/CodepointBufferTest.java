package com.github.sttk.stringcase.codepoint;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

public class CodepointBufferTest {

  @Test
  void testConstructor() {
    var buf = new CodepointBuffer(10);
    assertThat(buf.toString()).isEqualTo("");
    assertThat(buf.isEmpty()).isTrue();

    try {
      buf.last();
      fail();
    } catch (IndexOutOfBoundsException e) {}
  }

  @Test
  void testAppend() {
    var buf = new CodepointBuffer(10);
    assertThat(buf.toString()).isEqualTo("");
    assertThat(buf.isEmpty()).isTrue();

    buf.append();
    assertThat(buf.toString()).isEqualTo("");
    assertThat(buf.isEmpty()).isTrue();

    buf.append(0x41);
    assertThat(buf.toString()).isEqualTo("A");
    assertThat(buf.isEmpty()).isFalse();
    assertThat(buf.last()).isEqualTo("A".codePointAt(0));

    buf.append(0x3042, 0x3044);
    assertThat(buf.toString()).isEqualTo("Aあい");
    assertThat(buf.isEmpty()).isFalse();
    assertThat(buf.last()).isEqualTo("い".codePointAt(0));

    buf.append(0x1f44d);
    assertThat(buf.toString()).isEqualTo("Aあい👍");
    assertThat(buf.isEmpty()).isFalse();
    assertThat(buf.last()).isEqualTo("👍".codePointAt(0));

    buf.append();
    assertThat(buf.toString()).isEqualTo("Aあい👍");
    assertThat(buf.isEmpty()).isFalse();
    assertThat(buf.last()).isEqualTo("👍".codePointAt(0));
  }

  @Test
  void testReplaceLast() {
    var buf = new CodepointBuffer(10);
    assertThat(buf.toString()).isEqualTo("");
    assertThat(buf.isEmpty()).isTrue();

    buf.append(0x41);
    assertThat(buf.toString()).isEqualTo("A");
    assertThat(buf.isEmpty()).isFalse();
    assertThat(buf.last()).isEqualTo("A".codePointAt(0));

    buf.replaceLast(0x42);
    assertThat(buf.toString()).isEqualTo("B");
    assertThat(buf.isEmpty()).isFalse();
    assertThat(buf.last()).isEqualTo("B".codePointAt(0));

    buf.append(0x3042);
    assertThat(buf.toString()).isEqualTo("Bあ");
    assertThat(buf.isEmpty()).isFalse();
    assertThat(buf.last()).isEqualTo("あ".codePointAt(0));

    buf.replaceLast(0x43, 0x44);
    assertThat(buf.toString()).isEqualTo("BCD");
    assertThat(buf.isEmpty()).isFalse();
    assertThat(buf.last()).isEqualTo("D".codePointAt(0));

    buf.append(0x1f44d);
    assertThat(buf.toString()).isEqualTo("BCD👍");
    assertThat(buf.isEmpty()).isFalse();
    assertThat(buf.last()).isEqualTo("👍".codePointAt(0));

    buf.replaceLast(0x45, 0x46, 0x47);
    assertThat(buf.toString()).isEqualTo("BCDEFG");
    assertThat(buf.isEmpty()).isFalse();
    assertThat(buf.last()).isEqualTo("G".codePointAt(0));
  }
}
