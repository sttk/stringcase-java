/*
 * CodepointBuffer class.
 * Copyright (C) 2024 Takayuki Sato. All Rights Reserved.
 */
package com.github.sttk.stringcase.codepoint;

public final class CodepointBuffer {

  final StringBuilder buf;
  int lastCpIndex = 0;

  public CodepointBuffer(int capacity) {
    buf = new StringBuilder(capacity);
  }

  public void append(int ...cps) {
    switch (cps.length) {
    case 0:
      return;
    case 1:
      lastCpIndex = buf.length();
      buf.appendCodePoint(cps[0]);
      return;
    default:
      buf.append(new String(cps, 0, cps.length));
      lastCpIndex = buf.length() - Character.charCount(cps[cps.length - 1]);
    }
  }

  public boolean isEmpty() {
    return (buf.length() == 0);
  }

  public int last() {
    return buf.codePointAt(lastCpIndex);
  }

  public void replaceLast(int cp0, int ...cps) {
    buf.delete(lastCpIndex, buf.length());

    buf.appendCodePoint(cp0);
    if (cps.length == 0) {
      return;
    }

    buf.append(new String(cps, 0, cps.length));
    lastCpIndex = buf.length() - Character.charCount(cps[cps.length - 1]);
  }

  @Override
  public String toString() {
    return buf.toString();
  }
}
