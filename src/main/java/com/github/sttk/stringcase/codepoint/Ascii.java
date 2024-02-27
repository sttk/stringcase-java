/*
 * Ascii class.
 * Copyright (C) 2024 Takayuki Sato. All Rights Reserved.
 */
package com.github.sttk.stringcase.codepoint;

public final class Ascii {
  private Ascii() {}

  public static boolean isUpperCase(int codepoint) {
    return (0x41 <= codepoint && codepoint <= 0x5a);
  }

  public static boolean isLowerCase(int codepoint) {
    return (0x61 <= codepoint && codepoint <= 0x7a);
  }

  public static boolean isDigit(int codepoint) {
    return (0x30 <= codepoint && codepoint <= 0x39);
  }

  public static int toUpperCase(int codepoint) {
    return (codepoint + 0x41 - 0x61);
  }

  public static int toLowerCase(int codepoint) {
    return (codepoint + 0x61 - 0x41);
  }
}
