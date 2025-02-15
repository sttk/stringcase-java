/*
 * StringCase class.
 * Copyright (C) 2024-2025 Takayuki Sato. All Rights Reserved.
 */
package com.github.sttk.stringcase;

import com.github.sttk.stringcase.codepoint.Ascii;
import com.github.sttk.stringcase.codepoint.CodepointBuffer;
import java.util.Arrays;

/**
 * Is the class that provides the static methods to convert a string to following cases.
 *
 * - camelCase
 * - COBOL-CASE
 * - kebab-case
 * - MACRO_CASE
 * - PascalCase
 * - snake_case
 * - Train-Case
 */
public final class StringCase {
  private StringCase() {}

  private enum ChIs {
    FirstOfStr,
    NextOfUpper,
    NextOfContdUpper,
    NextOfSepMark,
    NextOfKeptMark,
    Others,
  }

  /**
   * Converts the input string to camel case with the specified options.
   *
   * @param input  The input string.
   * @param opts  The options which specifies the ways of case conversion.
   * @return  A string converted to camel case.
   */
  public static String camelCaseWithOptions(String input, Options opts) {
    var result = new CodepointBuffer(input.length());

    var flag = ChIs.FirstOfStr;

    int[] sepChs = null;
    if (opts.separators != null && !opts.separators.isEmpty()) {
      sepChs = opts.separators.codePoints().toArray();
      Arrays.sort(sepChs);
    }

    int[] keptChs = null;
    if (opts.keep != null && !opts.keep.isEmpty()) {
      keptChs = opts.keep.codePoints().toArray();
      Arrays.sort(keptChs);
    }

    for (int ch : input.codePoints().toArray()) {
      if (Ascii.isUpperCase(ch)) {
        if (flag == ChIs.FirstOfStr) {
          result.append(Ascii.toLowerCase(ch));
          flag = ChIs.NextOfUpper;
        } else if (flag == ChIs.NextOfUpper || flag == ChIs.NextOfContdUpper ||
          (!opts.separateAfterNonAlphabets && flag == ChIs.NextOfKeptMark)) {
          result.append(Ascii.toLowerCase(ch));
          flag = ChIs.NextOfContdUpper;
        } else {
          result.append(ch);
          flag = ChIs.NextOfUpper;
        }
      } else if (Ascii.isLowerCase(ch)) {
        if (flag == ChIs.NextOfContdUpper) {
          int prev = result.last();
          if (Ascii.isLowerCase(prev)) {
            prev = Ascii.toUpperCase(prev);
          }
          result.replaceLast(prev, ch);
        } else if (flag == ChIs.NextOfSepMark ||
          (opts.separateAfterNonAlphabets && flag == ChIs.NextOfKeptMark)) {
          result.append(Ascii.toUpperCase(ch));
        } else {
          result.append(ch);
        }
        flag = ChIs.Others;
      } else {
        var isKeptChar = false;
        if (Ascii.isDigit(ch)) {
          isKeptChar = true;
        } else if (sepChs != null) {
          if (Arrays.binarySearch(sepChs, ch) < 0) {
            isKeptChar = true;
          }
        } else if (keptChs != null) {
          if (Arrays.binarySearch(keptChs, ch) >= 0) {
            isKeptChar = true;
          }
        }

        if (isKeptChar) {
          result.append(ch);
          flag = ChIs.NextOfKeptMark;
        } else {
          if (flag != ChIs.FirstOfStr) {
            flag = ChIs.NextOfSepMark;
          }
        }
      }
    }

    return result.toString();
  }

  /**
   * Converts the input string to camel case.
   * <p>
   * It treats the end of a sequence of non-alphabetical characters as a word boundary, but not
   * the beginning.
   *
   * @param input  The input string.
   * @return  A string converted to camel case.
   */
  public static String camelCase(String input) {
    return camelCaseWithOptions(input, new Options(false, true, null, null));
  }

  /**
   * Converts the input string to camel case with the specified separator characters.
   *
   * @param input  The input string.
   * @param seps  The symbol characters to be treated as separators.
   * @return  A string converted to camel case.
   *
   * @deprecated Should use CamelCaseWithOptions instead
   */
  @Deprecated
  public static String camelCaseWithSep(String input, String seps) {
    return camelCaseWithOptions(input, new Options(false, true, seps, null));
  }

  /**
   * Converts the input string to camel case with the specified characters to be kept.
   *
   * @param input  The input string.
   * @param kept  The symbol characters not to be treated as separators.
   * @return  A string converted to camel case.
   *
   * @deprecated Should use CamelCaseWithOptions instead
   */
  @Deprecated
  public static String camelCaseWithKeep(String input, String kept) {
    return camelCaseWithOptions(input, new Options(false, true, null, kept));
  }

  /**
   * Converts a string to cobol case.
   *
   * This method takes a string as its argument, then returns a string of which
   * the case style is cobol case.
   *
   * This method targets the upper and lower cases of ASCII alphabets for
   * capitalization, and all characters except ASCII alphabets and ASCII
   * numbers  are replaced to hyphens as word separators.
   *
   * <pre>{@code
   *     String cobol = StringCase.cobolCase("foo_bar_baz");
   *     // => "FOO-BAR-BAZ"
   * }</pre>
   *
   * @param input  A string to be converted.
   * @return  A string converted to cobol case.
   */
  public static String cobolCase(String input) {
    var result = new CodepointBuffer(input.length() + input.length() / 2);

    final int HYPHEN = 0x2d;

    enum ChIs {
      FirstOfStr,
      NextOfUpper,
      NextOfContdUpper,
      NextOfSepMark,
      NextOfKeepedMark,
      Others,
    }
    var flag = ChIs.FirstOfStr;

    for (int ch : input.codePoints().toArray()) {
      if (Ascii.isUpperCase(ch)) {
        switch (flag) {
        case ChIs.FirstOfStr:
          result.append(ch);
          flag = ChIs.NextOfUpper;
          break;
        case ChIs.NextOfUpper:
        case ChIs.NextOfContdUpper:
          result.append(ch);
          flag = ChIs.NextOfContdUpper;
          break;
        default:
          result.append(HYPHEN, ch);
          flag = ChIs.NextOfUpper;
          break;
        }
      } else if (Ascii.isLowerCase(ch)) {
        switch (flag) {
        case ChIs.NextOfContdUpper:
          int prev = result.last();
          result.replaceLast(HYPHEN, prev, Ascii.toUpperCase(ch));
          break;
        case ChIs.NextOfSepMark:
        case ChIs.NextOfKeepedMark:
          result.append(HYPHEN, Ascii.toUpperCase(ch));
          break;
        default:
          result.append(Ascii.toUpperCase(ch));
          break;
        }
        flag = ChIs.Others;
      } else if (Ascii.isDigit(ch)) {
        if (flag == ChIs.NextOfSepMark) {
          result.append(HYPHEN, ch);
        } else {
          result.append(ch);
        }
        flag = ChIs.NextOfKeepedMark;
      } else {
        if (flag != ChIs.FirstOfStr) {
          flag = ChIs.NextOfSepMark;
        }
      }
    }

    return result.toString();
  }

  /**
   * Converts a string to cobol case using the specified characters as
   * separators.
   *
   * This method takes a string as its argument, then returns a string of which
   * the case style is cobol case.
   *
   * This method targets only the upper and lower cases of ASCII alphabets for
   * capitalization, and the characters specified as the second argument of
   * this method are regarded as word separators and are replaced to hyphens.
   *
   * <pre>{@code
   *     String cobol = StringCase.cobolCaseWithSep("foo-bar100%baz", "- ");
   *     // => "FOO-BAR100%-BAZ"
   * }</pre>
   *
   * @param input  A string to be converted.
   * @param seps  A string that consists of characters that are word
   *   separators.
   * @return  A string converted to cobol case.
   */
  public static String cobolCaseWithSep(String input, String seps) {
    var result = new CodepointBuffer(input.length() + input.length() / 2);

    final int HYPHEN = 0x2d;

    var sepChs = seps.codePoints().toArray();
    Arrays.sort(sepChs);

    enum ChIs {
      FirstOfStr,
      NextOfUpper,
      NextOfContdUpper,
      NextOfSepMark,
      NextOfKeepedMark,
      Others,
    }
    var flag = ChIs.FirstOfStr;

    for (int ch : input.codePoints().toArray()) {
      if (Arrays.binarySearch(sepChs, ch) >= 0) {
        if (flag != ChIs.FirstOfStr) {
          flag = ChIs.NextOfSepMark;
        }
      } else if (Ascii.isUpperCase(ch)) {
        switch (flag) {
        case ChIs.FirstOfStr:
          result.append(ch);
          flag = ChIs.NextOfUpper;
          break;
        case ChIs.NextOfUpper:
        case ChIs.NextOfContdUpper:
          result.append(ch);
          flag = ChIs.NextOfContdUpper;
          break;
        default:
          result.append(HYPHEN, ch);
          flag = ChIs.NextOfUpper;
          break;
        }
      } else if (Ascii.isLowerCase(ch)) {
        switch (flag) {
        case ChIs.NextOfContdUpper:
          int prev = result.last();
          result.replaceLast(HYPHEN, prev, Ascii.toUpperCase(ch));
          break;
        case ChIs.NextOfSepMark:
        case ChIs.NextOfKeepedMark:
          result.append(HYPHEN, Ascii.toUpperCase(ch));
          break;
        default:
          result.append(Ascii.toUpperCase(ch));
          break;
        }
        flag = ChIs.Others;
      } else {
        if (flag == ChIs.NextOfSepMark) {
          result.append(HYPHEN, ch);
        } else {
          result.append(ch);
        }
        flag = ChIs.NextOfKeepedMark;
      }
    }

    return result.toString();
  }

  /**
   * Converts a string to cobol case using characters other than the specified
   * characters as separators.
   *
   * This method takes a string as its argument, then returns a string of which
   * the case style is cobol case.
   *
   * This method targets only the upper and lower cases of ASCII alphabets
   * for capitalization, and the characters other than the specified characters
   * as the second argument of this method are regard as word separators and
   * are replaced to hyphens.
   *
   * <pre>{@code
   *     String cobol = StringCase.cobolCaseWithKeep("foo-bar100%baz", "%");
   *     // => "FOO-BAR100%-BAZ"
   * }</pre>
   *
   * @param input  A string to be converted.
   * @param keeped  A string that consists of characters that are not word
   *   separators.
   * @return  A string converted to cobol case.
   */
  public static String cobolCaseWithKeep(String input, String keeped) {
    var result = new CodepointBuffer(input.length() + input.length() / 2);

    final int HYPHEN = 0x2d;

    var keepChs = keeped.codePoints().toArray();
    Arrays.sort(keepChs);

    enum ChIs {
      FirstOfStr,
      NextOfUpper,
      NextOfContdUpper,
      NextOfSepMark,
      NextOfKeepedMark,
      Others,
    }
    var flag = ChIs.FirstOfStr;

    for (int ch : input.codePoints().toArray()) {
      if (Ascii.isUpperCase(ch)) {
        switch (flag) {
        case ChIs.FirstOfStr:
          result.append(ch);
          flag = ChIs.NextOfUpper;
          break;
        case ChIs.NextOfUpper:
        case ChIs.NextOfContdUpper:
          result.append(ch);
          flag = ChIs.NextOfContdUpper;
          break;
        default:
          result.append(HYPHEN, ch);
          flag = ChIs.NextOfUpper;
        }
      } else if (Ascii.isLowerCase(ch)) {
        switch (flag) {
        case ChIs.NextOfContdUpper:
          int prev = result.last();
          result.replaceLast(HYPHEN, prev, Ascii.toUpperCase(ch));
          break;
        case ChIs.NextOfSepMark:
        case ChIs.NextOfKeepedMark:
          result.append(HYPHEN, Ascii.toUpperCase(ch));
          break;
        default:
          result.append(Ascii.toUpperCase(ch));
          break;
        }
        flag = ChIs.Others;
      } else if (Ascii.isDigit(ch) || Arrays.binarySearch(keepChs, ch) >= 0) {
        if (flag == ChIs.NextOfSepMark) {
          result.append(HYPHEN, ch);
        } else {
          result.append(ch);
        }
        flag = ChIs.NextOfKeepedMark;
      } else {
        if (flag != ChIs.FirstOfStr) {
          flag = ChIs.NextOfSepMark;
        }
      }
    }

    return result.toString();
  }

  /**
   * Converts a string to kebab case.
   *
   * This method takes a string as its argument, then returns a string of which
   * the case style is kebab case.
   *
   * This method targets the upper and lower cases of ASCII alphabets for
   * capitalization, and all characters except ASCII alphabets and ASCII
   * numbers are eliminated as word separators.
   *
   * <pre>{@code
   *     String kebab = StringCase.kebabCase("fooBarBaz");
   *     // => "foo-bar-baz;
   * }</pre>
   *
   * @param input  A string to be converted.
   * @return  A string converted to kebab case.
   */
  public static String kebabCase(String input) {
    var result = new CodepointBuffer(input.length() + input.length() / 2);

    final int HYPHEN = 0x2d;

    enum ChIs {
      FirstOfStr,
      NextOfUpper,
      NextOfContdUpper,
      NextOfSepMark,
      NextOfKeepedMark,
      Others,
    }
    var flag = ChIs.FirstOfStr;

    for (int ch : input.codePoints().toArray()) {
      if (Ascii.isUpperCase(ch)) {
        switch (flag) {
        case ChIs.FirstOfStr:
          result.append(Ascii.toLowerCase(ch));
          flag = ChIs.NextOfUpper;
          break;
        case ChIs.NextOfUpper:
        case ChIs.NextOfContdUpper:
          result.append(Ascii.toLowerCase(ch));
          flag = ChIs.NextOfContdUpper;
          break;
        default:
          result.append(HYPHEN, Ascii.toLowerCase(ch));
          flag = ChIs.NextOfUpper;
          break;
        }
      } else if (Ascii.isLowerCase(ch)) {
        switch (flag) {
        case ChIs.NextOfContdUpper:
          int prev = result.last();
          result.replaceLast(HYPHEN, prev, ch);
          break;
        case ChIs.NextOfSepMark:
        case ChIs.NextOfKeepedMark:
          result.append(HYPHEN, ch);
          break;
        default:
          result.append(ch);
          break;
        }
        flag = ChIs.Others;
      } else if (Ascii.isDigit(ch)) {
        switch (flag) {
        case ChIs.NextOfSepMark:
          result.append(HYPHEN, ch);
          break;
        default:
          result.append(ch);
          break;
        }
        flag = ChIs.NextOfKeepedMark;
      } else {
        if (flag != ChIs.FirstOfStr) {
          flag = ChIs.NextOfSepMark;
        }
      }
    }

    return result.toString();
  }

  /**
   * Converts a string to kebab case using the specified characters as
   * separators.
   *
   * This method takes a string as its argument, then returns a string of which
   * the case style is kebab case.
   *
   * This method targets only the upper and lower cases of ASCII alphabets for
   * capitalization, and the characters specified as the second argument of
   * this method are regarded as word separators and are replaced to hyphens.
   *
   * <pre>{@code
   *     String kebab = StringCase.kebabCaseWithSep("foo-Bar100%Baz", "- ");
   *     // => "foo-bar100%-baz"
   * }</pre>
   *
   * @param input  A string to be converted.
   * @param seps  A string that consists of characters that are word
   *   separators.
   * @return  A string converted to kebab case.
   */
  public static String kebabCaseWithSep(String input, String seps) {
    var result = new CodepointBuffer(input.length() + input.length() / 2);

    final int HYPHEN = 0x2d;

    var sepChs = seps.codePoints().toArray();
    Arrays.sort(sepChs);

    enum ChIs {
      FirstOfStr,
      NextOfUpper,
      NextOfContdUpper,
      NextOfSepMark,
      NextOfKeepedMark,
      Others,
    }
    var flag = ChIs.FirstOfStr;

    for (int ch : input.codePoints().toArray()) {
      if (Arrays.binarySearch(sepChs, ch) >= 0) {
        if (flag != ChIs.FirstOfStr) {
          flag = ChIs.NextOfSepMark;
        }
      } else if (Ascii.isUpperCase(ch)) {
        switch (flag) {
        case ChIs.FirstOfStr:
          result.append(Ascii.toLowerCase(ch));
          flag = ChIs.NextOfUpper;
          break;
        case ChIs.NextOfUpper:
        case ChIs.NextOfContdUpper:
          result.append(Ascii.toLowerCase(ch));
          flag = ChIs.NextOfContdUpper;
          break;
        default:
          result.append(HYPHEN, Ascii.toLowerCase(ch));
          flag = ChIs.NextOfUpper;
          break;
        }
      } else if (Ascii.isLowerCase(ch)) {
        switch (flag) {
        case ChIs.NextOfContdUpper:
          int prev = result.last();
          result.replaceLast(HYPHEN, prev, ch);
          break;
        case ChIs.NextOfSepMark:
        case ChIs.NextOfKeepedMark:
          result.append(HYPHEN, ch);
          break;
        default:
          result.append(ch);
          break;
        }
        flag = ChIs.Others;
      } else {
        if (flag == ChIs.NextOfSepMark) {
          result.append(HYPHEN, ch);
        } else {
          result.append(ch);
        }
        flag = ChIs.NextOfKeepedMark;
      }
    }

    return result.toString();
  }

  /**
   * Converts a string to kebab case using characters other than the specified
   * characters as separators.
   *
   * This method takes a string as its argument, then returns a string of which
   * the case style is kebab case.
   *
   * This method targets only the upper and lower cases of ASCII alphabets for
   * capitalization, and the characters other than the specified characters as
   * the second argument of this method are regarded as word separators and
   * are replaced to hyphens.
   *
   * <pre>{@code
   *     String kebab = StringCase.kebabCaseWithKeep("foo-Bar100%Baz", "%");
   *     // => "foo-bar100%-baz"
   * }</pre>
   *
   * @param input  A string to be converted.
   * @param keeped  A string that consists of characters that are not word
   *   separators.
   * @return  A string converted to kebab case.
   */
  public static String kebabCaseWithKeep(String input, String keeped) {
    var result = new CodepointBuffer(input.length() + input.length() / 2);

    final int HYPHEN = 0x2d;

    var keepChs = keeped.codePoints().toArray();
    Arrays.sort(keepChs);

    enum ChIs {
      FirstOfStr,
      NextOfUpper,
      NextOfContdUpper,
      NextOfSepMark,
      NextOfKeepedMark,
      Others,
    }
    var flag = ChIs.FirstOfStr;

    for (int ch : input.codePoints().toArray()) {
      if (Ascii.isUpperCase(ch)) {
        switch (flag) {
        case ChIs.FirstOfStr:
          result.append(Ascii.toLowerCase(ch));
          flag = ChIs.NextOfUpper;
          break;
        case ChIs.NextOfUpper:
        case ChIs.NextOfContdUpper:
          result.append(Ascii.toLowerCase(ch));
          flag = ChIs.NextOfContdUpper;
          break;
        default:
          result.append(HYPHEN, Ascii.toLowerCase(ch));
          flag = ChIs.NextOfUpper;
          break;
        }
      } else if (Ascii.isLowerCase(ch)) {
        switch (flag) {
        case ChIs.NextOfContdUpper:
          int prev = result.last();
          result.replaceLast(HYPHEN, prev, ch);
          break;
        case ChIs.NextOfSepMark:
        case ChIs.NextOfKeepedMark:
          result.append(HYPHEN, ch);
          break;
        default:
          result.append(ch);
          break;
        }
        flag = ChIs.Others;
      } else if (Ascii.isDigit(ch) || Arrays.binarySearch(keepChs, ch) >= 0) {
        if (flag == ChIs.NextOfSepMark) {
          result.append(HYPHEN, ch);
        } else {
          result.append(ch);
        }
        flag = ChIs.NextOfKeepedMark;
      } else {
        if (flag != ChIs.FirstOfStr) {
          flag = ChIs.NextOfSepMark;
        }
      }
    }

    return result.toString();
  }

  /**
   * Converts a string to macro case.
   *
   * This method takes a string as its argument, then returns a string of which
   * the case style is macro case.
   *
   * This method targets the upper and lower cases of ASCII alphabets for
   * capitalization, and all characters except ASCII alphabets and ASCII
   * numbers are eliminated as word separators.
   *
   * <pre>{@code
   *     String macro = StringCase.macroCase("fooBarBaz");
   *     // => "FOO_BAR_BAZ"
   * }</pre>
   *
   * @param input  A string to be converted.
   * @return  A string converted to macro case.
   */
  public static String macroCase(String input) {
    var result = new CodepointBuffer(input.length() + input.length() / 2);

    final int UNDERSCORE = 0x5f;

    enum ChIs {
      FirstOfStr,
      NextOfUpper,
      NextOfContdUpper,
      NextOfSepMark,
      NextOfKeepedMark,
      Others,
    }
    var flag = ChIs.FirstOfStr;

    for (int ch : input.codePoints().toArray()) {
      if (Ascii.isUpperCase(ch)) {
        switch (flag) {
        case ChIs.FirstOfStr:
          result.append(ch);
          flag = ChIs.NextOfUpper;
          break;
        case ChIs.NextOfUpper:
        case ChIs.NextOfContdUpper:
          result.append(ch);
          flag = ChIs.NextOfContdUpper;
          break;
        default:
          result.append(UNDERSCORE, ch);
          flag = ChIs.NextOfUpper;
          break;
        }
      } else if (Ascii.isLowerCase(ch)) {
        switch (flag) {
        case ChIs.NextOfContdUpper:
          int prev = result.last();
          result.replaceLast(UNDERSCORE, prev, Ascii.toUpperCase(ch));
          break;
        case ChIs.NextOfSepMark:
        case ChIs.NextOfKeepedMark:
          result.append(UNDERSCORE, Ascii.toUpperCase(ch));
          break;
        default:
          result.append(Ascii.toUpperCase(ch));
          break;
        }
        flag = ChIs.Others;
      } else if (Ascii.isDigit(ch)) {
        if (flag == ChIs.NextOfSepMark) {
          result.append(UNDERSCORE, ch);
        } else {
          result.append(ch);
        }
        flag = ChIs.NextOfKeepedMark;
      } else {
        if (flag != ChIs.FirstOfStr) {
          flag = ChIs.NextOfSepMark;
        }
      }
    }

    return result.toString();
  }

  /**
   * Converts a string to macro case using the specified characters as
   * separators.
   *
   * This method takes a string as its argument, then returns a string of which
   * the case style is macro case.
   *
   * This method targets only the upper and lower cases of ASCII alphabets for
   * capitalization, and the characters specified as the second argument of
   * this method are regarded as word separators and are replaced to
   * underscores.
   *
   * <pre>{@code
   *     String macro = StringCase.macroCaseWithSep("foo-Bar100%Baz", "- ");
   *     // => "FOO_BAR100%_BAZ"
   * }</pre>
   *
   * @param input  A string to be converted.
   * @param seps  A string that consists of characters that are word
   *   separators.
   * @return  A string converted to macro case.
   */
  public static String macroCaseWithSep(String input, String seps) {
    var result = new CodepointBuffer(input.length() + input.length() / 2);

    final int UNDERSCORE = 0x5f;

    var sepChs = seps.codePoints().toArray();
    Arrays.sort(sepChs);

    enum ChIs {
      FirstOfStr,
      NextOfUpper,
      NextOfContdUpper,
      NextOfSepMark,
      NextOfKeepedMark,
      Others,
    }
    var flag = ChIs.FirstOfStr;

    for (int ch : input.codePoints().toArray()) {
      if (Arrays.binarySearch(sepChs, ch) >= 0) {
        if (flag != ChIs.FirstOfStr) {
          flag = ChIs.NextOfSepMark;
        }
      } else if (Ascii.isUpperCase(ch)) {
        switch (flag) {
        case ChIs.FirstOfStr:
          result.append(ch);
          flag = ChIs.NextOfUpper;
          break;
        case ChIs.NextOfUpper:
        case ChIs.NextOfContdUpper:
          result.append(ch);
          flag = ChIs.NextOfContdUpper;
          break;
        default:
          result.append(UNDERSCORE, ch);
          flag = ChIs.NextOfUpper;
          break;
        }
      } else if (Ascii.isLowerCase(ch)) {
        switch (flag) {
        case ChIs.NextOfContdUpper:
          int prev = result.last();
          result.replaceLast(UNDERSCORE, prev, Ascii.toUpperCase(ch));
          break;
        case ChIs.NextOfSepMark:
        case ChIs.NextOfKeepedMark:
          result.append(UNDERSCORE, Ascii.toUpperCase(ch));
          break;
        default:
          result.append(Ascii.toUpperCase(ch));
          break;
        }
        flag = ChIs.Others;
      } else {
        if (flag == ChIs.NextOfSepMark) {
          result.append(UNDERSCORE, ch);
        } else {
          result.append(ch);
        }
        flag = ChIs.NextOfKeepedMark;
      }
    }

    return result.toString();
  }

  /**
   * Converts a string to macro case using characters other than the specified
   * characters as separators.
   *
   * This mthod takes a string slice as its argument, then returns a `String`
   * of which the case style is macro case.
   *
   * This method targets only the upper and lower cases of ASCII alphabets for
   * capitalization, and the characters other than the specified characters as
   * the second argument of this method are regarded as word separators and
   * are replaced to underscores.
   *
   * <pre>{@code
   *     String macro = StringCase.macroCaseWithKeep("foo-bar100%baz", "%");
   *     // => "FOO_BAR100%_BAZ;
   * }</pre>
   *
   * @param input  A string to be converted.
   * @param keeped  A string that consists of characters that are not word
   *   separators.
   * @return  A string converted to macro case.
   */
  public static String macroCaseWithKeep(String input, String keeped) {
    var result = new CodepointBuffer(input.length() + input.length() / 2);

    final int UNDERSCORE = 0x5f;

    var keepChs = keeped.codePoints().toArray();
    Arrays.sort(keepChs);

    enum ChIs {
      FirstOfStr,
      NextOfUpper,
      NextOfContdUpper,
      NextOfSepMark,
      NextOfKeepedMark,
      Others,
    }
    var flag = ChIs.FirstOfStr;

    for (int ch : input.codePoints().toArray()) {
      if (Ascii.isUpperCase(ch)) {
        switch (flag) {
        case ChIs.FirstOfStr:
          result.append(ch);
          flag = ChIs.NextOfUpper;
          break;
        case ChIs.NextOfUpper:
        case ChIs.NextOfContdUpper:
          result.append(ch);
          flag = ChIs.NextOfContdUpper;
          break;
        default:
          result.append(UNDERSCORE, ch);
          flag = ChIs.NextOfUpper;
          break;
        }
      } else if (Ascii.isLowerCase(ch)) {
        switch (flag) {
        case ChIs.NextOfContdUpper:
          int prev = result.last();
          result.replaceLast(UNDERSCORE, prev, Ascii.toUpperCase(ch));
          break;
        case ChIs.NextOfSepMark:
        case ChIs.NextOfKeepedMark:
          result.append(UNDERSCORE, Ascii.toUpperCase(ch));
          break;
        default:
          result.append(Ascii.toUpperCase(ch));
          break;
        }
        flag = ChIs.Others;
      } else if (Ascii.isDigit(ch) || Arrays.binarySearch(keepChs, ch) >= 0) {
        if (flag == ChIs.NextOfSepMark) {
          result.append(UNDERSCORE, ch);
        } else {
          result.append(ch);
        }
        flag = ChIs.NextOfKeepedMark;
      } else {
        if (flag != ChIs.FirstOfStr) {
          flag = ChIs.NextOfSepMark;
        }
      }
    }

    return result.toString();
  }

  /**
   * Converts a string to pascal case.
   *
   * This method takes a string as its argument, then returns a string of which
   * the case style is pascal case.
   *
   * This method targets the upper and lower cases of ASCII alphabets for
   * capitalization, and all characters except ASCII alphabets and ASCII
   * numbers are eliminated as word separators.
   *
   * <pre>{@code
   *     String pascal = StringCase.pascalCase("foo_bar_baz");
   *     // => "FooBarBaz"
   * }</pre>
   *
   * @param input  A string to be converted.
   * @return  A string converted to pascal case.
   */
  public static String pascalCase(String input) {
    var result = new CodepointBuffer(input.length());

    enum ChIs {
      FirstOfStr,
      NextOfUpper,
      NextOfMark,
      Others,
    }
    var flag = ChIs.FirstOfStr;

    for (int ch : input.codePoints().toArray()) {
      if (Ascii.isUpperCase(ch)) {
        switch (flag) {
        case ChIs.NextOfUpper:
          result.append(Ascii.toLowerCase(ch));
          //flag = ChIs.NextOfUpper;
          break;
        default:
          result.append(ch);
          flag = ChIs.NextOfUpper;
          break;
        }
      } else if (Ascii.isLowerCase(ch)) {
        switch (flag) {
        case ChIs.NextOfUpper:
          int prev = result.last();
          if (Ascii.isLowerCase(prev)) {
            prev = Ascii.toUpperCase(prev);
          }
          result.replaceLast(prev, ch);
          flag = ChIs.Others;
          break;
        case ChIs.FirstOfStr:
        case ChIs.NextOfMark:
          result.append(Ascii.toUpperCase(ch));
          flag = ChIs.NextOfUpper;
          break;
        default:
          result.append(ch);
          flag = ChIs.Others;
          break;
        }
      } else if (Ascii.isDigit(ch)) {
        result.append(ch);
        flag = ChIs.NextOfMark;
      } else {
        if (flag != ChIs.FirstOfStr) {
          flag = ChIs.NextOfMark;
        }
      }
    }

    return result.toString();
  }

  /**
   * Converts a string to pascal case using the specified characters as
   * separators.
   *
   * This method takes a string as its argument, then returns a string of which
   * the case style is pascal case.
   *
   * This method targets only the upper and lower cases of ASCII alphabets for
   * capitalization, and the characters specified as the second argument of
   * this method are regarded as word separators and are eliminated.
   *
   * <pre>{@code
   *     String pascal = StringCase.pascalCaseWithSep("foo-Bar100%Baz", "- ");
   *     // => "FooBar100%Baz"
   * }</pre>
   *
   * @param input  A string to be converted.
   * @param seps  A string that consists of characters that are word
   *   separators.
   * @return  A string converted to pascal case.
   */
  public static String pascalCaseWithSep(String input, String seps) {
    var result = new CodepointBuffer(input.length());

    var sepChs = seps.codePoints().toArray();
    Arrays.sort(sepChs);

    enum ChIs {
      FirstOfStr,
      NextOfUpper,
      NextOfMark,
      Others,
    }
    var flag = ChIs.FirstOfStr;

    for (int ch : input.codePoints().toArray()) {
      if (Arrays.binarySearch(sepChs, ch) >= 0) {
        if (flag != ChIs.FirstOfStr) {
          flag = ChIs.NextOfMark;
        }
      } else if (Ascii.isUpperCase(ch)) {
        switch (flag) {
        case ChIs.NextOfUpper:
          result.append(Ascii.toLowerCase(ch));
          //flag = ChIs.NextOfUpper;
          break;
        default:
          result.append(ch);
          flag = ChIs.NextOfUpper;
          break;
        }
      } else if (Ascii.isLowerCase(ch)) {
        switch (flag) {
        case ChIs.NextOfUpper:
          int prev = result.last();
          if (Ascii.isLowerCase(prev)) {
            prev = Ascii.toUpperCase(prev);
          }
          result.replaceLast(prev, ch);
          flag = ChIs.Others;
          break;
        case ChIs.FirstOfStr:
        case ChIs.NextOfMark:
          result.append(Ascii.toUpperCase(ch));
          flag = ChIs.NextOfUpper;
          break;
        default:
          result.append(ch);
          flag = ChIs.Others;
          break;
        }
      } else {
        result.append(ch);
        flag = ChIs.NextOfMark;
      }
    }

    return result.toString();
  }

  /**
   * Converts a string to pascal case using characters other than the specified
   * characters as separators.
   *
   * This method takes a string as its argument, then returns a string of which
   * the case style is pascal case.
   *
   * This method targets only the upper and lower cases of ASCII alphabets for
   * capitalization, and the characters other than the specified characters as
   * the second argument of this method are regarded as word separators and are
   * eliminated.
   *
   * <pre>{@code
   *     String pascal = StringCase.pascalCaseWithKeep("foo-bar100%baz", "%");
   *     // => "FooBar100%Baz"
   * }</pre>
   *
   * @param input  A string to be converted.
   * @param keeped  A string that consists of characters that are not word
   *   separators.
   * @return  A string converted to pascal case.
   */
  public static String pascalCaseWithKeep(String input, String keeped) {
    var result = new CodepointBuffer(input.length());

    var keepChs = keeped.codePoints().toArray();
    Arrays.sort(keepChs);

    enum ChIs {
      FirstOfStr,
      NextOfUpper,
      NextOfMark,
      Others,
    }
    var flag = ChIs.FirstOfStr;

    for (int ch : input.codePoints().toArray()) {
      if (Ascii.isUpperCase(ch)) {
        switch (flag) {
        case ChIs.NextOfUpper:
          result.append(Ascii.toLowerCase(ch));
          //flag = ChIs.NextOfUpper;
          break;
        default:
          result.append(ch);
          flag = ChIs.NextOfUpper;
          break;
        }
      } else if (Ascii.isLowerCase(ch)) {
        switch (flag) {
        case ChIs.NextOfUpper:
          int prev = result.last();
          if (Ascii.isLowerCase(prev)) {
            prev = Ascii.toUpperCase(prev);
          }
          result.replaceLast(prev, ch);
          flag = ChIs.Others;
          break;
        case ChIs.FirstOfStr:
        case ChIs.NextOfMark:
          result.append(Ascii.toUpperCase(ch));
          flag = ChIs.NextOfUpper;
          break;
        default:
          result.append(ch);
          flag = ChIs.Others;
          break;
        }
      } else if (Ascii.isDigit(ch) || Arrays.binarySearch(keepChs, ch) >= 0) {
        result.append(ch);
        flag = ChIs.NextOfMark;
      } else {
        if (flag != ChIs.FirstOfStr) {
          flag = ChIs.NextOfMark;
        }
      }
    }

    return result.toString();
  }

  /**
   * Converts a string to snake case.
   *
   * This method takes a string as its argument, then returns a string of which
   * the case style is snake case.
   *
   * This method targets the upper and lower cases of ASCII alphabets for
   * capitalization, and all characters except ASCII alphabets and ASCII
   * numbers are eliminated as word separators.
   *
   * <pre>{@code
   *     String snake = StringCase.snakeCase("fooBarBaz");
   *     // => "foo_bar_baz"
   * }</pre>
   *
   * @param input  A string to be converted.
   * @return  A string converted to snake case.
   */
  public static String snakeCase(String input) {
    var result = new CodepointBuffer(input.length() + input.length() / 2);

    final int UNDERSCORE = 0x5f;

    enum ChIs {
      FirstOfStr,
      NextOfUpper,
      NextOfContdUpper,
      NextOfSepMark,
      NextOfKeepedMark,
      Others,
    }
    var flag = ChIs.FirstOfStr;

    for (int ch : input.codePoints().toArray()) {
      if (Ascii.isUpperCase(ch)) {
        switch (flag) {
        case ChIs.FirstOfStr:
          result.append(Ascii.toLowerCase(ch));
          flag = ChIs.NextOfUpper;
          break;
        case ChIs.NextOfUpper:
        case ChIs.NextOfContdUpper:
          result.append(Ascii.toLowerCase(ch));
          flag = ChIs.NextOfContdUpper;
          break;
        default:
          result.append(UNDERSCORE, Ascii.toLowerCase(ch));
          flag = ChIs.NextOfUpper;
          break;
        }
      } else if (Ascii.isLowerCase(ch)) {
        switch (flag) {
        case ChIs.NextOfContdUpper:
          int prev = result.last();
          result.replaceLast(UNDERSCORE, prev, ch);
          break;
        case ChIs.NextOfSepMark:
        case ChIs.NextOfKeepedMark:
          result.append(UNDERSCORE, ch);
          break;
        default:
          result.append(ch);
          break;
        }
        flag = ChIs.Others;
      } else if (Ascii.isDigit(ch)) {
        switch (flag) {
        case ChIs.NextOfSepMark:
          result.append(UNDERSCORE, ch);
          break;
        default:
          result.append(ch);
          break;
        }
        flag = ChIs.NextOfKeepedMark;
      } else {
        if (flag != ChIs.FirstOfStr) {
          flag = ChIs.NextOfSepMark;
        }
      }
    }

    return result.toString();
  }

  /**
   * Converts a string to snake case using the specified characters as
   * separators.
   *
   * This method takes a string as its argument, then returns a strin` of which
   * the case style is snake case.
   *
   * This method targets only the upper and lower cases of ASCII alphabets for
   * capitalization, and the characters specified as the second argument of
   * this method are regarded as word separators and are replaced to
   * underscores.
   *
   * <pre>{@code
   *     String snake = StringCase.snakeCaseWithSep("foo-Bar100%Baz", "- ");
   *     // => "foo_bar100%_baz"
   * }</pre>
   *
   * @param input  A string to be converted.
   * @param seps  A string that consists of characters that are word
   *   separators.
   * @return  A string converted to snake case.
   */
  public static String snakeCaseWithSep(String input, String seps) {
    var result = new CodepointBuffer(input.length() + input.length()  / 2);

    final int UNDERSCORE = 0x5f;

    var sepChs = seps.codePoints().toArray();
    Arrays.sort(sepChs);

    enum ChIs {
      FirstOfStr,
      NextOfUpper,
      NextOfContdUpper,
      NextOfSepMark,
      NextOfKeepedMark,
      Others,
    }
    var flag = ChIs.FirstOfStr;

    for (int ch : input.codePoints().toArray()) {
      if (Arrays.binarySearch(sepChs, ch) >= 0) {
        if (flag != ChIs.FirstOfStr) {
          flag = ChIs.NextOfSepMark;
        }
      } else if (Ascii.isUpperCase(ch)) {
        switch (flag) {
        case ChIs.FirstOfStr:
          result.append(Ascii.toLowerCase(ch));
          flag = ChIs.NextOfUpper;
          break;
        case ChIs.NextOfUpper:
        case ChIs.NextOfContdUpper:
          result.append(Ascii.toLowerCase(ch));
          flag = ChIs.NextOfContdUpper;
          break;
        default:
          result.append(UNDERSCORE, Ascii.toLowerCase(ch));
          flag = ChIs.NextOfUpper;
          break;
        }
      } else if (Ascii.isLowerCase(ch)) {
        switch (flag) {
        case ChIs.NextOfContdUpper:
          int prev = result.last();
          result.replaceLast(UNDERSCORE, prev, ch);
          break;
        case ChIs.NextOfSepMark:
        case ChIs.NextOfKeepedMark:
          result.append(UNDERSCORE, ch);
          break;
        default:
          result.append(ch);
          break;
        }
        flag = ChIs.Others;
      } else {
        if (flag == ChIs.NextOfSepMark) {
          result.append(UNDERSCORE, ch);
        } else {
          result.append(ch);
        }
        flag = ChIs.NextOfKeepedMark;
      }
    }

    return result.toString();
  }

  /**
   * Converts a string to snake case using characters other than the specified
   * characters as separators.
   * 
   * This method takes a string as its argument, then returns a string of which
   * the case style is snake case.
   *
   * This method targets only the upper and lower cases of ASCII alphabets for
   * capitalization, and the characters other than the specified characters as
   * the second argument of this method are regarded as word separators and are
   * replaced to underscores.
   *
   * <pre>{@code
   *     let snake = stringcase::snake_case_with_keep("foo-bar100%baz", "%");
   *     // => "foo_bar100%_baz"
   * }</pre>
   *
   * @param input  A string to be converted.
   * @param keeped  A string that consists of characters that are not word
   *   separators.
   * @return  A string converted to snake case.
   */
  public static String snakeCaseWithKeep(String input, String keeped) {
    var result = new CodepointBuffer(input.length() + input.length()  / 2);

    final int UNDERSCORE = 0x5f;

    var keepChs = keeped.codePoints().toArray();
    Arrays.sort(keepChs);

    enum ChIs {
      FirstOfStr,
      NextOfUpper,
      NextOfContdUpper,
      NextOfSepMark,
      NextOfKeepedMark,
      Others,
    }
    var flag = ChIs.FirstOfStr;

    for (int ch : input.codePoints().toArray()) {
      if (Ascii.isUpperCase(ch)) {
        switch (flag) {
        case ChIs.FirstOfStr:
          result.append(Ascii.toLowerCase(ch));
          flag = ChIs.NextOfUpper;
          break;
        case ChIs.NextOfUpper:
        case ChIs.NextOfContdUpper:
          result.append(Ascii.toLowerCase(ch));
          flag = ChIs.NextOfContdUpper;
          break;
        default:
          result.append(UNDERSCORE, Ascii.toLowerCase(ch));
          flag = ChIs.NextOfUpper;
        }
      } else if (Ascii.isLowerCase(ch)) {
        switch (flag) {
        case ChIs.NextOfContdUpper:
          int prev = result.last();
          result.replaceLast(UNDERSCORE, prev, ch);
          break;
        case ChIs.NextOfSepMark:
        case ChIs.NextOfKeepedMark:
          result.append(UNDERSCORE, ch);
          break;
        default:
          result.append(ch);
          break;
        }
        flag = ChIs.Others;
      } else if (Ascii.isDigit(ch) || Arrays.binarySearch(keepChs, ch) >= 0) {
        if (flag == ChIs.NextOfSepMark) {
          result.append(UNDERSCORE, ch);
        } else {
          result.append(ch);
        }
        flag = ChIs.NextOfKeepedMark;
      } else {
        if (flag != ChIs.FirstOfStr) {
          flag = ChIs.NextOfSepMark;
        }
      }
    }

    return result.toString();
  }

  /**
   * Converts a string to train case.
   *
   * This method takes a string as its argument, then returns a string of which
   * the case style is train case.
   *
   * This method targets the upper and lower cases of ASCII alphabets for
   * capitalization, and all characters except ASCII alphabets and ASCII
   * numbers are eliminated as word separators.
   *
   * <pre>{@code
   *     String train = StringCase.trainCase("fooBarBaz");
   *     // => "Foo-Bar-Baz"
   * }</pre>
   *
   * @param input  A string to be converted.
   * @return  A string converted to train case.
   */
  public static String trainCase(String input) {
    var result = new CodepointBuffer(input.length() * 2);

    final int HYPHEN = 0x2d;

    enum ChIs {
      FirstOfStr,
      NextOfUpper,
      NextOfContdUpper,
      NextOfSepMark,
      NextOfKeepedMark,
      Others,
    }
    var flag = ChIs.FirstOfStr;

    for (int ch : input.codePoints().toArray()) {
      if (Ascii.isUpperCase(ch)) {
        switch (flag) {
        case ChIs.FirstOfStr:
          result.append(ch);
          flag = ChIs.NextOfUpper;
          break;
        case ChIs.NextOfUpper:
        case ChIs.NextOfContdUpper:
          result.append(Ascii.toLowerCase(ch));
          flag = ChIs.NextOfContdUpper;
          break;
        default:
          result.append(HYPHEN, ch);
          flag = ChIs.NextOfUpper;
          break;
        }
      } else if (Ascii.isLowerCase(ch)) {
        switch (flag) {
        case ChIs.FirstOfStr:
          result.append(Ascii.toUpperCase(ch));
          break;
        case ChIs.NextOfContdUpper:
          int prev = result.last();
          if (Ascii.isLowerCase(prev)) {
            prev = Ascii.toUpperCase(prev);
          }
          result.replaceLast(HYPHEN, prev, ch);
          break;
        case ChIs.NextOfSepMark:
        case ChIs.NextOfKeepedMark:
          result.append(HYPHEN, Ascii.toUpperCase(ch));
          break;
        default:
          result.append(ch);
          break;
        }
        flag = ChIs.Others;
      } else if (Ascii.isDigit(ch)) {
        if (flag == ChIs.NextOfSepMark) {
          result.append(HYPHEN, ch);
        } else {
          result.append(ch);
        }
        flag = ChIs.NextOfKeepedMark;
      } else {
        if (flag != ChIs.FirstOfStr) {
          flag = ChIs.NextOfSepMark;
        }
      }
    }

    return result.toString();
  }

  /**
   * Converts a string to train case using the specified characters as
   * separators.
   *
   * This method takes a string as its argument, then returns a string of which
   * the case style is train case.
   *
   * This method targets only the upper and lower cases of ASCII alphabets for
   * capitalization, and the characters specified as the second argument of
   * this method are regarded as word separators and are replaced to hyphens.
   *
   * <pre>{@code
   *     String train = StringCase.trainCaseWithSep("foo-Bar100%Baz", "- ");
   *     // => "Foo-Bar100%-Baz"
   * }</pre>
   *
   * @param input  A string to be converted.
   * @param seps  A string that consists of characters that are word
   *   separators.
   * @return  A string converted to train case.
   */
  public static String trainCaseWithSep(String input, String seps) {
    var result = new CodepointBuffer(input.length() * 2);

    final int HYPHEN = 0x2d;

    var sepChs = seps.codePoints().toArray();
    Arrays.sort(sepChs);

    enum ChIs {
      FirstOfStr,
      NextOfUpper,
      NextOfContdUpper,
      NextOfSepMark,
      NextOfKeepedMark,
      Others,
    }
    var flag = ChIs.FirstOfStr;

    for (int ch : input.codePoints().toArray()) {
      if (Arrays.binarySearch(sepChs, ch) >= 0) {
        if (flag != ChIs.FirstOfStr) {
          flag = ChIs.NextOfSepMark;
        }
      } else if (Ascii.isUpperCase(ch)) {
        switch (flag) {
        case ChIs.FirstOfStr:
          result.append(ch);
          flag = ChIs.NextOfUpper;
          break;
        case ChIs.NextOfUpper:
        case ChIs.NextOfContdUpper:
          result.append(Ascii.toLowerCase(ch));
          flag = ChIs.NextOfContdUpper;
          break;
        default:
          result.append(HYPHEN, ch);
          flag = ChIs.NextOfUpper;
          break;
        }
      } else if (Ascii.isLowerCase(ch)) {
        switch (flag) {
        case ChIs.FirstOfStr:
          result.append(Ascii.toUpperCase(ch));
          break;
        case ChIs.NextOfContdUpper:
          int prev = result.last();
          if (Ascii.isLowerCase(prev)) {
            prev = Ascii.toUpperCase(prev);
          }
          result.replaceLast(HYPHEN, prev, ch);
          break;
        case ChIs.NextOfSepMark:
        case ChIs.NextOfKeepedMark:
          result.append(HYPHEN, Ascii.toUpperCase(ch));
          break;
        default:
          result.append(ch);
          break;
        }
        flag = ChIs.Others;
      } else {
        if (flag == ChIs.NextOfSepMark) {
          result.append(HYPHEN, ch);
        } else {
          result.append(ch);
        }
        flag = ChIs.NextOfKeepedMark;
      }
    }

    return result.toString();
  }

  /**
   * Converts a string to train case using characters other than the specified
   * characters as separators.
   *
   * This method takes a string as its argument, then returns a string of which
   * the case style is train case.
   *
   * This method targets only the upper and lower cases of ASCII alphabets for
   * capitalization, and the characters other than the specified characters as
   * the second argument of this method are regarded as word separators and are
   * replaced to hyphens.
   *
   * <pre>{@code
   *     String train = StringCase.trainCaseWithKeep("foo-bar100%baz", "%");
   *     // => "Foo-Bar100%-Baz"
   * }</pre>
   *
   * @param input  A string to be converted.
   * @param keeped  A string that consists of characters that are not word
   *   separators.
   * @return  A string converted to train case.
   */
  public static String trainCaseWithKeep(String input, String keeped) {
    var result = new CodepointBuffer(input.length() * 2);

    final int HYPHEN = 0x2d;

    var keepChs = keeped.codePoints().toArray();
    Arrays.sort(keepChs);

    enum ChIs {
      FirstOfStr,
      NextOfUpper,
      NextOfContdUpper,
      NextOfSepMark,
      NextOfKeepedMark,
      Others,
    }
    var flag = ChIs.FirstOfStr;

    for (int ch : input.codePoints().toArray()) {
      if (Ascii.isUpperCase(ch)) {
        switch (flag) {
        case ChIs.FirstOfStr:
          result.append(ch);
          flag = ChIs.NextOfUpper;
          break;
        case ChIs.NextOfUpper:
        case ChIs.NextOfContdUpper:
          result.append(Ascii.toLowerCase(ch));
          flag = ChIs.NextOfContdUpper;
          break;
        default:
          result.append(HYPHEN, ch);
          flag = ChIs.NextOfUpper;
          break;
        }
      } else if (Ascii.isLowerCase(ch)) {
        switch (flag) {
        case ChIs.FirstOfStr:
          result.append(Ascii.toUpperCase(ch));
          break;
        case ChIs.NextOfContdUpper:
          int prev = result.last();
          if (Ascii.isLowerCase(prev)) {
            prev = Ascii.toUpperCase(prev);
          }
          result.replaceLast(HYPHEN, prev, ch);
          break;
        case ChIs.NextOfSepMark:
        case ChIs.NextOfKeepedMark:
          result.append(HYPHEN, Ascii.toUpperCase(ch));
          break;
        default:
          result.append(ch);
          break;
        }
        flag = ChIs.Others;
      } else if (Ascii.isDigit(ch) || Arrays.binarySearch(keepChs, ch) >= 0) {
        if (flag == ChIs.NextOfSepMark) {
          result.append(HYPHEN, ch);
        } else {
          result.append(ch);
        }
        flag = ChIs.NextOfKeepedMark;
      } else {
        if (flag != ChIs.FirstOfStr) {
          flag = ChIs.NextOfSepMark;
        }
      }
    }

    return result.toString();
  }
}
