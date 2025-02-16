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
   * @deprecated Should use {@link #camelCaseWithOptions} instead
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
   * @deprecated Should use {@link camelCaseWithOptions} instead
   */
  @Deprecated
  public static String camelCaseWithKeep(String input, String kept) {
    return camelCaseWithOptions(input, new Options(false, true, null, kept));
  }

  /**
   * Converts the input string to cobol case with the specified options.
   *
   * @param input  The input string.
   * @param opts  The options with specifies the ways of case conversion.
   * @return  A string converted to cobol case.
   */
  public static String cobolCaseWithOptions(String input, Options opts) {
    var result = new CodepointBuffer(input.length());

    final int HYPHEN = 0x2d;

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
          result.append(ch);
          flag = ChIs.NextOfUpper;
        } else if (flag == ChIs.NextOfUpper || flag == ChIs.NextOfContdUpper ||
          (!opts.separateAfterNonAlphabets && flag == ChIs.NextOfKeptMark)) {
          result.append(ch);
          flag = ChIs.NextOfContdUpper;
        } else {
          result.append(HYPHEN, ch);
          flag = ChIs.NextOfUpper;
        }
      } else if (Ascii.isLowerCase(ch)) {
        if (flag == ChIs.NextOfContdUpper) {
          int prev = result.last();
          result.replaceLast(HYPHEN, prev, Ascii.toUpperCase(ch));
        } else if (flag == ChIs.NextOfSepMark ||
          (opts.separateAfterNonAlphabets && flag == ChIs.NextOfKeptMark)) {
          result.append(HYPHEN, Ascii.toUpperCase(ch));
        } else {
          result.append(Ascii.toUpperCase(ch));
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
          if (opts.separateBeforeNonAlphabets) {
            if (flag == ChIs.FirstOfStr || flag == ChIs.NextOfKeptMark) {
              result.append(ch);
            } else {
              result.append(HYPHEN, ch);
            }
          } else {
            if (flag != ChIs.NextOfSepMark) {
              result.append(ch);
            } else {
              result.append(HYPHEN, ch);
            }
          }
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
   * Converts the input string to cobol case.
   * <p>
   * It treats the end of a sequence of non-alphabetical characters as a word boundary, but not
   * the beginning.
   *
   * @param input  The input string.
   * @return  A string converted to cobol case.
   */
  public static String cobolCase(String input) {
    return cobolCaseWithOptions(input, new Options(false, true, null, null));
  }

  /**
   * Converts the input string to cobol case with the specified separator characters.
   *
   * @param input  The input string.
   * @param seps  The symbol characters to be treated as separators.
   * @return  A string converted to cobol case.
   *
   * @deprecated Should use {@link #cobolCaseWithOptions} instead
   */
  @Deprecated
  public static String cobolCaseWithSep(String input, String seps) {
    return cobolCaseWithOptions(input, new Options(false, true, seps, null));
  }

  /**
   * Converts the input string to cobol case with the specified characters to be kept.
   *
   * @param input  The input string.
   * @param kept  The symbol characters not to be treated as separators.
   * @return  A string converted to cobol case.
   *
   * @deprecated Should use {@link #cobolCaseWithOptions} instead
   */
  @Deprecated
  public static String cobolCaseWithKeep(String input, String kept) {
    return cobolCaseWithOptions(input, new Options(false, true, null, kept));
  }

  /**
   * Converts the input string to kebab case with the specified options.
   *
   * @param input  The input string.
   * @param opts  The options with specifies the ways of case conversion.
   * @return  A string converted to kebab case.
   */
  public static String kebabCaseWithOptions(String input, Options opts) {
    var result = new CodepointBuffer(input.length());

    final int HYPHEN = 0x2d;

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
          result.append(HYPHEN, Ascii.toLowerCase(ch));
          flag = ChIs.NextOfUpper;
        }
      } else if (Ascii.isLowerCase(ch)) {
        if (flag == ChIs.NextOfContdUpper) {
          int prev = result.last();
          result.replaceLast(HYPHEN, prev, ch);
        } else if (flag == ChIs.NextOfSepMark ||
          (opts.separateAfterNonAlphabets && flag == ChIs.NextOfKeptMark)) {
          result.append(HYPHEN, ch);
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
          if (opts.separateBeforeNonAlphabets) {
            if (flag == ChIs.FirstOfStr || flag == ChIs.NextOfKeptMark) {
              result.append(ch);
            } else {
              result.append(HYPHEN, ch);
            }
          } else {
            if (flag != ChIs.NextOfSepMark) {
              result.append(ch);
            } else {
              result.append(HYPHEN, ch);
            }
          }
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
   * Converts the input string to kebab case.
   * <p>
   * It treats the end of a sequence of non-alphabetical characters as a word boundary, but not
   * the beginning.
   *
   * @param input  The input string.
   * @return  A string converted to kebab case.
   */
  public static String kebabCase(String input) {
    return kebabCaseWithOptions(input, new Options(false, true, null, null));
  }

  /**
   * Converts the input string to kebab case with the specified separator characters.
   *
   * @param input  The input string.
   * @param seps  The symbol characters to be treated as separators.
   * @return  A string converted to kebab case.
   *
   * @deprecated Should use {@link #kebabCaseWithOptions} instead
   */
  @Deprecated
  public static String kebabCaseWithSep(String input, String seps) {
    return kebabCaseWithOptions(input, new Options(false, true, seps, null));
  }

  /**
   * Converts the input string to kebab case with the specified characters to be kept.
   *
   * @param input  The input string.
   * @param kept  The symbol characters not to be treated as separators.
   * @return  A string converted to kebab case.
   *
   * @deprecated Should use {@link #kebabCaseWithOptions} instead
   */
  @Deprecated
  public static String kebabCaseWithKeep(String input, String kept) {
    return kebabCaseWithOptions(input, new Options(false, true, null, kept));
  }

  /**
   * Converts the input string to macro case with the specified options.
   *
   * @param input  The input string.
   * @param opts  The options with specifies the ways of case conversion.
   * @return  A string converted to macro case.
   */
  public static String macroCaseWithOptions(String input, Options opts) {
    var result = new CodepointBuffer(input.length());

    final int UNDERSCORE = 0x5f;

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
          result.append(ch);
          flag = ChIs.NextOfUpper;
        } else if (flag == ChIs.NextOfUpper || flag == ChIs.NextOfContdUpper ||
          (!opts.separateAfterNonAlphabets && flag == ChIs.NextOfKeptMark)) {
          result.append(ch);
          flag = ChIs.NextOfContdUpper;
        } else {
          result.append(UNDERSCORE, ch);
          flag = ChIs.NextOfUpper;
        }
      } else if (Ascii.isLowerCase(ch)) {
        if (flag == ChIs.NextOfContdUpper) {
          int prev = result.last();
          result.replaceLast(UNDERSCORE, prev, Ascii.toUpperCase(ch));
        } else if (flag == ChIs.NextOfSepMark ||
          (opts.separateAfterNonAlphabets && flag == ChIs.NextOfKeptMark)) {
          result.append(UNDERSCORE, Ascii.toUpperCase(ch));
        } else {
          result.append(Ascii.toUpperCase(ch));
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
          if (opts.separateBeforeNonAlphabets) {
            if (flag == ChIs.FirstOfStr || flag == ChIs.NextOfKeptMark) {
              result.append(ch);
            } else {
              result.append(UNDERSCORE, ch);
            }
          } else {
            if (flag != ChIs.NextOfSepMark) {
              result.append(ch);
            } else {
              result.append(UNDERSCORE, ch);
            }
          }
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
   * Converts the input string to macro case.
   * <p>
   * It treats the end of a sequence of non-alphabetical characters as a word boundary, but not
   * the beginning.
   *
   * @param input  The input string.
   * @return  A string converted to macro case.
   */
  public static String macroCase(String input) {
    return macroCaseWithOptions(input, new Options(false, true, null, null));
  }

  /**
   * Converts the input string to macro case with the specified separator characters.
   *
   * @param input  The input string.
   * @param seps  The symbol characters to be treated as separators.
   * @return  A string converted to macro case.
   *
   * @deprecated Should use {@link #macroCaseWithOptions} instead
   */
  @Deprecated
  public static String macroCaseWithSep(String input, String seps) {
    return macroCaseWithOptions(input, new Options(false, true, seps, null));
  }

  /**
   * Converts the input string to macro case with the specified characters to be kept.
   *
   * @param input  The input string.
   * @param kept  The symbol characters not to be treated as separators.
   * @return  A string converted to macro case.
   *
   * @deprecated Should use {@link #macroCaseWithOptions} instead
   */
  @Deprecated
  public static String macroCaseWithKeep(String input, String kept) {
    return macroCaseWithOptions(input, new Options(false, true, null, kept));
  }

  /**
   * Converts the input string to pascal case with the specified options.
   *
   * @param input  The input string.
   * @param opts  The options which specifies the ways of case conversion.
   * @return  A string converted to pascal case.
   */
  public static String pascalCaseWithOptions(String input, Options opts) {
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
        if (flag == ChIs.NextOfUpper || flag == ChIs.NextOfContdUpper ||
          (!opts.separateAfterNonAlphabets && flag == ChIs.NextOfKeptMark)) {
          result.append(Ascii.toLowerCase(ch));
          flag = ChIs.NextOfContdUpper;
        } else {
          result.append(ch);
          flag = ChIs.NextOfUpper;
        }
      } else if (Ascii.isLowerCase(ch)) {
        if (flag == ChIs.FirstOfStr) {
          result.append(Ascii.toUpperCase(ch));
        } else if (flag == ChIs.NextOfContdUpper) {
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
   * Converts the input string to pascal case.
   * <p>
   * It treats the end of a sequence of non-alphabetical characters as a word boundary, but not
   * the beginning.
   *
   * @param input  The input string.
   * @return  A string converted to pascal case.
   */
  public static String pascalCase(String input) {
    return pascalCaseWithOptions(input, new Options(false, true, null, null));
  }

  /**
   * Converts the input string to pascal case with the specified separator characters.
   *
   * @param input  The input string.
   * @param seps  The symbol characters to be treated as separators.
   * @return  A string converted to pascal case.
   *
   * @deprecated Should use {@link #pascalCaseWithOptions} instead
   */
  @Deprecated
  public static String pascalCaseWithSep(String input, String seps) {
    return pascalCaseWithOptions(input, new Options(false, true, seps, null));
  }

  /**
   * Converts the input string to pascal case with the specified characters to be kept.
   *
   * @param input  The input string.
   * @param kept  The symbol characters not to be treated as separators.
   * @return  A string converted to pascal case.
   *
   * @deprecated Should use {@link pascalCaseWithOptions} instead
   */
  @Deprecated
  public static String pascalCaseWithKeep(String input, String kept) {
    return pascalCaseWithOptions(input, new Options(false, true, null, kept));
  }

  /**
   * Converts the input string to snake case with the specified options.
   *
   * @param input  The input string.
   * @param opts  The options with specifies the ways of case conversion.
   * @return  A string converted to snake case.
   */
  public static String snakeCaseWithOptions(String input, Options opts) {
    var result = new CodepointBuffer(input.length());

    final int UNDERSCORE = 0x5f;

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
          result.append(UNDERSCORE, Ascii.toLowerCase(ch));
          flag = ChIs.NextOfUpper;
        }
      } else if (Ascii.isLowerCase(ch)) {
        if (flag == ChIs.NextOfContdUpper) {
          int prev = result.last();
          result.replaceLast(UNDERSCORE, prev, ch);
        } else if (flag == ChIs.NextOfSepMark ||
          (opts.separateAfterNonAlphabets && flag == ChIs.NextOfKeptMark)) {
          result.append(UNDERSCORE, ch);
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
          if (opts.separateBeforeNonAlphabets) {
            if (flag == ChIs.FirstOfStr || flag == ChIs.NextOfKeptMark) {
              result.append(ch);
            } else {
              result.append(UNDERSCORE, ch);
            }
          } else {
            if (flag != ChIs.NextOfSepMark) {
              result.append(ch);
            } else {
              result.append(UNDERSCORE, ch);
            }
          }
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
   * Converts the input string to snake case.
   * <p>
   * It treats the end of a sequence of non-alphabetical characters as a word boundary, but not
   * the beginning.
   *
   * @param input  The input string.
   * @return  A string converted to snake case.
   */
  public static String snakeCase(String input) {
    return snakeCaseWithOptions(input, new Options(false, true, null, null));
  }

  /**
   * Converts the input string to snake case with the specified separator characters.
   *
   * @param input  The input string.
   * @param seps  The symbol characters to be treated as separators.
   * @return  A string converted to snake case.
   *
   * @deprecated Should use {@link #snakeCaseWithOptions} instead
   */
  @Deprecated
  public static String snakeCaseWithSep(String input, String seps) {
    return snakeCaseWithOptions(input, new Options(false, true, seps, null));
  }

  /**
   * Converts the input string to snake case with the specified characters to be kept.
   *
   * @param input  The input string.
   * @param kept  The symbol characters not to be treated as separators.
   * @return  A string converted to snake case.
   *
   * @deprecated Should use {@link #snakeCaseWithOptions} instead
   */
  @Deprecated
  public static String snakeCaseWithKeep(String input, String kept) {
    return snakeCaseWithOptions(input, new Options(false, true, null, kept));
  }

  /**
   * Converts the input string to train case with the specified options.
   *
   * @param input  The input string.
   * @param opts  The options with specifies the ways of case conversion.
   * @return  A string converted to train case.
   */
  public static String trainCaseWithOptions(String input, Options opts) {
    var result = new CodepointBuffer(input.length());

    final int HYPHEN = 0x2d;

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
          result.append(ch);
          flag = ChIs.NextOfUpper;
        } else if (flag == ChIs.NextOfUpper || flag == ChIs.NextOfContdUpper ||
          (!opts.separateAfterNonAlphabets && flag == ChIs.NextOfKeptMark)) {
          result.append(Ascii.toLowerCase(ch));
          flag = ChIs.NextOfContdUpper;
        } else {
          result.append(HYPHEN, ch);
          flag = ChIs.NextOfUpper;
        }
      } else if (Ascii.isLowerCase(ch)) {
        if (flag == ChIs.FirstOfStr) {
          result.append(Ascii.toUpperCase(ch));
        } else if (flag == ChIs.NextOfContdUpper) {
          int prev = result.last();
          if (Ascii.isLowerCase(prev)) {
            prev = Ascii.toUpperCase(prev);
          }
          result.replaceLast(HYPHEN, prev, ch);
        } else if (flag == ChIs.NextOfSepMark ||
          (opts.separateAfterNonAlphabets && flag == ChIs.NextOfKeptMark)) {
          result.append(HYPHEN, Ascii.toUpperCase(ch));
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
          if (opts.separateBeforeNonAlphabets) {
            if (flag == ChIs.FirstOfStr || flag == ChIs.NextOfKeptMark) {
              result.append(ch);
            } else {
              result.append(HYPHEN, ch);
            }
          } else {
            if (flag != ChIs.NextOfSepMark) {
              result.append(ch);
            } else {
              result.append(HYPHEN, ch);
            }
          }
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
   * Converts the input string to train case.
   * <p>
   * It treats the end of a sequence of non-alphabetical characters as a word boundary, but not
   * the beginning.
   *
   * @param input  The input string.
   * @return  A string converted to train case.
   */
  public static String trainCase(String input) {
    return trainCaseWithOptions(input, new Options(false, true, null, null));
  }

  /**
   * Converts the input string to train case with the specified separator characters.
   *
   * @param input  The input string.
   * @param seps  The symbol characters to be treated as separators.
   * @return  A string converted to train case.
   *
   * @deprecated Should use {@link #trainCaseWithOptions} instead
   */
  @Deprecated
  public static String trainCaseWithSep(String input, String seps) {
    return trainCaseWithOptions(input, new Options(false, true, seps, null));
  }

  /**
   * Converts the input string to train case with the specified characters to be kept.
   *
   * @param input  The input string.
   * @param kept  The symbol characters not to be treated as separators.
   * @return  A string converted to train case.
   *
   * @deprecated Should use {@link #trainCaseWithOptions} instead
   */
  @Deprecated
  public static String trainCaseWithKeep(String input, String kept) {
    return trainCaseWithOptions(input, new Options(false, true, null, kept));
  }
}
