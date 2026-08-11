/*
 * StringCase class.
 * Copyright (C) 2024-2026 Takayuki Sato. All Rights Reserved.
 */
package com.github.sttk.stringcase;

import com.github.sttk.stringcase.codepoint.Ascii;
import com.github.sttk.stringcase.codepoint.CodepointBuffer;
import java.util.Arrays;

/**
 * Is the class that provides the static methods to convert a string to following cases.
 *
 * <ul>
 *   <li>Ada_Case
 *   <li>camelCase
 *   <li>COBOL-CASE
 *   <li>kebab-case
 *   <li>MACRO_CASE
 *   <li>PascalCase
 *   <li>snake_case
 *   <li>Title Case
 *   <li>Train-Case
 * </ul>
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
   * Converts all ASCII alphabetic characters in the input string to uppercase, inserting the
   * specified joiner {@code char} between word boundaries according to the given options. It serves
   * as a core engine for transforming input strings into uppercase-based casing styles, such as
   * MACRO_CASE or COBOL-CASE, using custom joiner {@code char}s and customizable word separation
   * rules defined in Options.
   *
   * <p>During conversion, all ASCII lowercase letters are converted to ASCII uppercase letters, and
   * word boundaries are automatically recognized between casing transitions, such as between
   * lowercase and uppercase letters or before the final uppercase letter of an acronym preceding a
   * lowercase sequence. When non-alphanumeric characters are encountered, ASCII digits are kept by
   * default, while other characters are evaluated against {@link Options}. If {@code
   * opts.Separators} is non-empty, characters matching {@code opts.Separators} are removed as
   * separators while other non-alphanumeric characters are kept; otherwise, if {@code opts.Keep} is
   * non-empty, specified characters are kept and all other non-alphanumeric characters are removed.
   * If neither is specified, all non-alphanumeric characters are treated as separators and removed.
   * The fields {@code opts.SeparateBeforeNonAlphabets} and {@code opts.SeparateAfterNonAlphabets}
   * further determine whether word boundaries are inserted before or after non-alphabetic
   * sequences.
   *
   * <p>This static method never throws an exception on any input, returning an empty string when
   * the input is empty. Casing transformations and word boundary detections apply strictly to ASCII
   * letters, treating non-ASCII characters as non-alphanumeric. If both {@code opts.Separators} and
   * {@code opts.Keep} are specified, {@code opts.Separators} takes precedence and {@code opts.Keep}
   * is ignored, while any alphanumeric characters listed in either field are disregarded.
   * Additionally, leading and trailing separator characters are trimmed from the result without
   * producing leading or trailing joiners.
   *
   * @param input The input string.
   * @param joiner A joiner {@code char}.
   * @param opts The {@link Options} object which holds the fields to customize separation rules.
   * @return The converted string.
   */
  public static String upperize(String input, char joiner, Options opts) {
    return upperize(input, (int) joiner, opts);
  }

  /**
   * Converts all ASCII alphabetic characters in the input string to uppercase, inserting the
   * specified joiner code point between word boundaries according to the given options. It serves
   * as a core engine for transforming input strings into uppercase-based casing styles, such as
   * MACRO_CASE or COBOL-CASE, using custom joiner code points and customizable word separation
   * rules defined in Options.
   *
   * <p>During conversion, all ASCII lowercase letters are converted to ASCII uppercase letters, and
   * word boundaries are automatically recognized between casing transitions, such as between
   * lowercase and uppercase letters or before the final uppercase letter of an acronym preceding a
   * lowercase sequence. When non-alphanumeric characters are encountered, ASCII digits are kept by
   * default, while other characters are evaluated against {@link Options}. If {@code
   * opts.Separators} is non-empty, characters matching {@code opts.Separators} are removed as
   * separators while other non-alphanumeric characters are kept; otherwise, if {@code opts.Keep} is
   * non-empty, specified characters are kept and all other non-alphanumeric characters are removed.
   * If neither is specified, all non-alphanumeric characters are treated as separators and removed.
   * The fields {@code opts.SeparateBeforeNonAlphabets} and {@code opts.SeparateAfterNonAlphabets}
   * further determine whether word boundaries are inserted before or after non-alphabetic
   * sequences.
   *
   * <p>This static method never throws an exception on any input, returning an empty string when
   * the input is empty. Casing transformations and word boundary detections apply strictly to ASCII
   * letters, treating non-ASCII characters as non-alphanumeric. If both {@code opts.Separators} and
   * {@code opts.Keep} are specified, {@code opts.Separators} takes precedence and {@code opts.Keep}
   * is ignored, while any alphanumeric characters listed in either field are disregarded.
   * Additionally, leading and trailing separator characters are trimmed from the result without
   * producing leading or trailing joiners.
   *
   * @param input The input string.
   * @param joiner A joiner code point.
   * @param opts The {@link Options} object which holds the fields to customize separation rules.
   * @return The converted string.
   */
  public static String upperize(String input, int joiner, Options opts) {
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
          result.append(ch);
          flag = ChIs.NextOfUpper;
        } else if (flag == ChIs.NextOfUpper
            || flag == ChIs.NextOfContdUpper
            || (!opts.separateAfterNonAlphabets && flag == ChIs.NextOfKeptMark)) {
          result.append(ch);
          flag = ChIs.NextOfContdUpper;
        } else {
          result.append(joiner, ch);
          flag = ChIs.NextOfUpper;
        }
      } else if (Ascii.isLowerCase(ch)) {
        if (flag == ChIs.NextOfContdUpper) {
          int prev = result.last();
          result.replaceLast(joiner, prev, Ascii.toUpperCase(ch));
        } else if (flag == ChIs.NextOfSepMark
            || (opts.separateAfterNonAlphabets && flag == ChIs.NextOfKeptMark)) {
          result.append(joiner, Ascii.toUpperCase(ch));
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
              result.append(joiner, ch);
            }
          } else {
            if (flag != ChIs.NextOfSepMark) {
              result.append(ch);
            } else {
              result.append(joiner, ch);
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
   * Converts all ASCII alphabetic characters in the input string to lowercase, inserting the
   * specified joiner {@code char} between word boundaries according to the given options. It serves
   * as a core engine for transforming input strings into lowercase-based casing styles, such as
   * snake_case or kebab-case, using custom joiner {@code char}s and customizable word separation
   * rules defined in Options.
   *
   * <p>During conversion, all ASCII uppercase letters are converted to ASCII lowercase letters, and
   * word boundaries are automatically recognized between casing transitions, such as between
   * lowercase and uppercase letters or before the final uppercase letter of an acronym preceding a
   * lowercase sequence. When non-alphanumeric characters are encountered, ASCII digits are kept by
   * default, while other characters are evaluated against {@link Options}. If {@code
   * opts.Separators} is non-empty, characters matching {@code opts.Separators} are removed as
   * separators while other non-alphanumeric characters are kept; otherwise, if {@code opts.Keep} is
   * non-empty, specified characters are kept and all other non-alphanumeric characters are removed.
   * If neither is specified, all non-alphanumeric characters are treated as separators and removed.
   * The fields {@code opts.SeparateBeforeNonAlphabets} and {@code opts.SeparateAfterNonAlphabets}
   * further determine whether word boundaries are inserted before or after non-alphabetic
   * sequences.
   *
   * <p>This static method never throws an exception on any input, returning an empty string when
   * the input is empty. Casing transformations and word boundary detections apply strictly to ASCII
   * letters, treating non-ASCII characters as non-alphanumeric. If both opts.Separators and {@code
   * opts.Keep} are specified, {@code opts.Separators} takes precedence and {@code opts.Keep} is
   * ignored, while any alphanumeric characters listed in either field are disregarded.
   * Additionally, leading and trailing separator characters are trimmed from the result without
   * producing leading or trailing joiners.
   *
   * @param input The input string.
   * @param joiner A joiner {@code char}.
   * @param opts The {@link Options} object which holds the fields to customize separation rules.
   * @return The converted string.
   */
  public static String lowerize(String input, char joiner, Options opts) {
    return lowerize(input, (int) joiner, opts);
  }

  /**
   * Converts all ASCII alphabetic characters in the input string to lowercase, inserting the
   * specified joiner code point between word boundaries according to the given options. It serves
   * as a core engine for transforming input strings into lowercase-based casing styles, such as
   * snake_case or kebab-case, using custom joiner code points and customizable word separation
   * rules defined in Options.
   *
   * <p>During conversion, all ASCII uppercase letters are converted to ASCII lowercase letters, and
   * word boundaries are automatically recognized between casing transitions, such as between
   * lowercase and uppercase letters or before the final uppercase letter of an acronym preceding a
   * lowercase sequence. When non-alphanumeric characters are encountered, ASCII digits are kept by
   * default, while other characters are evaluated against {@link Options}. If {@code
   * opts.Separators} is non-empty, characters matching {@code opts.Separators} are removed as
   * separators while other non-alphanumeric characters are kept; otherwise, if {@code opts.Keep} is
   * non-empty, specified characters are kept and all other non-alphanumeric characters are removed.
   * If neither is specified, all non-alphanumeric characters are treated as separators and removed.
   * The fields {@code opts.SeparateBeforeNonAlphabets} and {@code opts.SeparateAfterNonAlphabets}
   * further determine whether word boundaries are inserted before or after non-alphabetic
   * sequences.
   *
   * <p>This static method never throws an exception on any input, returning an empty string when
   * the input is empty. Casing transformations and word boundary detections apply strictly to ASCII
   * letters, treating non-ASCII characters as non-alphanumeric. If both opts.Separators and {@code
   * opts.Keep} are specified, {@code opts.Separators} takes precedence and {@code opts.Keep} is
   * ignored, while any alphanumeric characters listed in either field are disregarded.
   * Additionally, leading and trailing separator characters are trimmed from the result without
   * producing leading or trailing joiners.
   *
   * @param input The input string.
   * @param joiner A joiner code point.
   * @param opts The {@link Options} object which holds the fields to customize separation rules.
   * @return The converted string.
   */
  public static String lowerize(String input, int joiner, Options opts) {
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
        } else if (flag == ChIs.NextOfUpper
            || flag == ChIs.NextOfContdUpper
            || (!opts.separateAfterNonAlphabets && flag == ChIs.NextOfKeptMark)) {
          result.append(Ascii.toLowerCase(ch));
          flag = ChIs.NextOfContdUpper;
        } else {
          result.append(joiner, Ascii.toLowerCase(ch));
          flag = ChIs.NextOfUpper;
        }
      } else if (Ascii.isLowerCase(ch)) {
        if (flag == ChIs.NextOfContdUpper) {
          int prev = result.last();
          result.replaceLast(joiner, prev, ch);
        } else if (flag == ChIs.NextOfSepMark
            || (opts.separateAfterNonAlphabets && flag == ChIs.NextOfKeptMark)) {
          result.append(joiner, ch);
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
              result.append(joiner, ch);
            }
          } else {
            if (flag != ChIs.NextOfSepMark) {
              result.append(ch);
            } else {
              result.append(joiner, ch);
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
   * Converts the input string by capitalizing the first ASCII letter of each word and lowercasing
   * subsequent letters, inserting the specified joiner {@code char} between word boundaries
   * according to the given options. It serves as a core engine for transforming input strings into
   * capitalized casing styles, such as Train-Case or PascalCase, using custom joiner {@code code}s
   * and customizable word separation rules defined in {@link Options}.
   *
   * <p>During conversion, the initial ASCII letter of each word is converted to ASCII uppercase,
   * while subsequent ASCII letters in that word are converted to ASCII lowercase. Word boundaries
   * are automatically recognized at casing transitions, such as between lowercase and uppercase
   * letters or before the final uppercase letter of an acronym preceding a lowercase sequence. When
   * non-alphanumeric characters are encountered, ASCII digits are kept by default, while other
   * characters are evaluated against {@link Options}. If {@code opts.Separators} is non-empty,
   * characters matching {@code opts.Separators} are removed as separators while other
   * non-alphanumeric characters are kept; otherwise, if {@code opts.Keep} is non-empty, specified
   * characters are kept and all other non-alphanumeric characters are removed. If neither is
   * specified, all non-alphanumeric characters are treated as separators and removed. The fields
   * {@code opts.SeparateBeforeNonAlphabets} and {@code opts.SeparateAfterNonAlphabets} further
   * determine whether word boundaries are inserted before or after non-alphabetic sequences.
   *
   * <p>This static method never throws an exception on any input, returning an empty string when
   * the input is empty. Casing transformations and word boundary detections apply strictly to ASCII
   * letters, treating non-ASCII characters as non-alphanumeric. If both {@code opts.Separators} and
   * {@code opts.Keep} are specified, {@code opts.Separators} takes precedence and {@code opts.Keep}
   * is ignored, while any alphanumeric characters listed in either field are disregarded.
   * Additionally, leading and trailing separator characters are trimmed from the result without
   * producing leading or trailing joiners.
   *
   * @param input The input string.
   * @param joiner A joiner {@code char}.
   * @param opts The {@link Options} object which holds the fields to customize separation rules.
   * @return The converted string.
   */
  public static String capitalize(String input, char joiner, Options opts) {
    return capitalize(input, (int) joiner, opts);
  }

  /**
   * Converts the input string by capitalizing the first ASCII letter of each word and lowercasing
   * subsequent letters, inserting the specified joiner code point between word boundaries according
   * to the given options. It serves as a core engine for transforming input strings into
   * capitalized casing styles, such as Train-Case or PascalCase, using custom joiner code points
   * and customizable word separation rules defined in {@link Options}.
   *
   * <p>During conversion, the initial ASCII letter of each word is converted to ASCII uppercase,
   * while subsequent ASCII letters in that word are converted to ASCII lowercase. Word boundaries
   * are automatically recognized at casing transitions, such as between lowercase and uppercase
   * letters or before the final uppercase letter of an acronym preceding a lowercase sequence. When
   * non-alphanumeric characters are encountered, ASCII digits are kept by default, while other
   * characters are evaluated against {@link Options}. If {@code opts.Separators} is non-empty,
   * characters matching {@code opts.Separators} are removed as separators while other
   * non-alphanumeric characters are kept; otherwise, if {@code opts.Keep} is non-empty, specified
   * characters are kept and all other non-alphanumeric characters are removed. If neither is
   * specified, all non-alphanumeric characters are treated as separators and removed. The fields
   * {@code opts.SeparateBeforeNonAlphabets} and {@code opts.SeparateAfterNonAlphabets} further
   * determine whether word boundaries are inserted before or after non-alphabetic sequences.
   *
   * <p>This static method never throws an exception on any input, returning an empty string when
   * the input is empty. Casing transformations and word boundary detections apply strictly to ASCII
   * letters, treating non-ASCII characters as non-alphanumeric. If both {@code opts.Separators} and
   * {@code opts.Keep} are specified, {@code opts.Separators} takes precedence and {@code opts.Keep}
   * is ignored, while any alphanumeric characters listed in either field are disregarded.
   * Additionally, leading and trailing separator characters are trimmed from the result without
   * producing leading or trailing joiners.
   *
   * @param input The input string.
   * @param joiner A joiner code point.
   * @param opts The {@link Options} object which holds the fields to customize separation rules.
   * @return The converted string.
   */
  public static String capitalize(String input, int joiner, Options opts) {
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
          result.append(ch);
          flag = ChIs.NextOfUpper;
        } else if (flag == ChIs.NextOfUpper
            || flag == ChIs.NextOfContdUpper
            || (!opts.separateAfterNonAlphabets && flag == ChIs.NextOfKeptMark)) {
          result.append(Ascii.toLowerCase(ch));
          flag = ChIs.NextOfContdUpper;
        } else {
          result.append(joiner, ch);
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
          result.replaceLast(joiner, prev, ch);
        } else if (flag == ChIs.NextOfSepMark
            || (opts.separateAfterNonAlphabets && flag == ChIs.NextOfKeptMark)) {
          result.append(joiner, Ascii.toUpperCase(ch));
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
              result.append(joiner, ch);
            }
          } else {
            if (flag != ChIs.NextOfSepMark) {
              result.append(ch);
            } else {
              result.append(joiner, ch);
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
   * Converts the input string to camel case with the specified options.
   *
   * @param input The input string.
   * @param opts The options which specifies the ways of case conversion.
   * @return A string converted to camel case.
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
        } else if (flag == ChIs.NextOfUpper
            || flag == ChIs.NextOfContdUpper
            || (!opts.separateAfterNonAlphabets && flag == ChIs.NextOfKeptMark)) {
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
        } else if (flag == ChIs.NextOfSepMark
            || (opts.separateAfterNonAlphabets && flag == ChIs.NextOfKeptMark)) {
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
   *
   * <p>It treats the end of a sequence of non-alphabetical characters as a word boundary, but not
   * the beginning.
   *
   * @param input The input string.
   * @return A string converted to camel case.
   */
  public static String camelCase(String input) {
    return camelCaseWithOptions(input, new Options(false, true, null, null));
  }

  /**
   * Converts the input string to cobol case with the specified options.
   *
   * @param input The input string.
   * @param opts The options which specifies the ways of case conversion.
   * @return A string converted to cobol case.
   */
  public static String cobolCaseWithOptions(String input, Options opts) {
    final int HYPHEN = 0x2d;
    return upperize(input, HYPHEN, opts);
  }

  /**
   * Converts the input string to cobol case.
   *
   * <p>It treats the end of a sequence of non-alphabetical characters as a word boundary, but not
   * the beginning.
   *
   * @param input The input string.
   * @return A string converted to cobol case.
   */
  public static String cobolCase(String input) {
    final int HYPHEN = 0x2d;
    return upperize(input, HYPHEN, new Options(false, true, null, null));
  }

  /**
   * Converts the input string to kebab case with the specified options.
   *
   * @param input The input string.
   * @param opts The options which specifies the ways of case conversion.
   * @return A string converted to kebab case.
   */
  public static String kebabCaseWithOptions(String input, Options opts) {
    final int HYPHEN = 0x2d;
    return lowerize(input, HYPHEN, opts);
  }

  /**
   * Converts the input string to kebab case.
   *
   * <p>It treats the end of a sequence of non-alphabetical characters as a word boundary, but not
   * the beginning.
   *
   * @param input The input string.
   * @return A string converted to kebab case.
   */
  public static String kebabCase(String input) {
    final int HYPHEN = 0x2d;
    return lowerize(input, HYPHEN, new Options(false, true, null, null));
  }

  /**
   * Converts the input string to macro case with the specified options.
   *
   * @param input The input string.
   * @param opts The options which specifies the ways of case conversion.
   * @return A string converted to macro case.
   */
  public static String macroCaseWithOptions(String input, Options opts) {
    final int UNDERSCORE = 0x5f;
    return upperize(input, UNDERSCORE, opts);
  }

  /**
   * Converts the input string to macro case.
   *
   * <p>It treats the end of a sequence of non-alphabetical characters as a word boundary, but not
   * the beginning.
   *
   * @param input The input string.
   * @return A string converted to macro case.
   */
  public static String macroCase(String input) {
    final int UNDERSCORE = 0x5f;
    return upperize(input, UNDERSCORE, new Options(false, true, null, null));
  }

  /**
   * Converts the input string to pascal case with the specified options.
   *
   * @param input The input string.
   * @param opts The options which specifies the ways of case conversion.
   * @return A string converted to pascal case.
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
        if (flag == ChIs.NextOfUpper
            || flag == ChIs.NextOfContdUpper
            || (!opts.separateAfterNonAlphabets && flag == ChIs.NextOfKeptMark)) {
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
        } else if (flag == ChIs.NextOfSepMark
            || (opts.separateAfterNonAlphabets && flag == ChIs.NextOfKeptMark)) {
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
   *
   * <p>It treats the end of a sequence of non-alphabetical characters as a word boundary, but not
   * the beginning.
   *
   * @param input The input string.
   * @return A string converted to pascal case.
   */
  public static String pascalCase(String input) {
    return pascalCaseWithOptions(input, new Options(false, true, null, null));
  }

  /**
   * Converts the input string to snake case with the specified options.
   *
   * @param input The input string.
   * @param opts The options which specifies the ways of case conversion.
   * @return A string converted to snake case.
   */
  public static String snakeCaseWithOptions(String input, Options opts) {
    final int UNDERSCORE = 0x5f;
    return lowerize(input, UNDERSCORE, opts);
  }

  /**
   * Converts the input string to snake case.
   *
   * <p>It treats the end of a sequence of non-alphabetical characters as a word boundary, but not
   * the beginning.
   *
   * @param input The input string.
   * @return A string converted to snake case.
   */
  public static String snakeCase(String input) {
    final int UNDERSCORE = 0x5f;
    return lowerize(input, UNDERSCORE, new Options(false, true, null, null));
  }

  /**
   * Converts the input string to train case with the specified options.
   *
   * @param input The input string.
   * @param opts The options which specifies the ways of case conversion.
   * @return A string converted to train case.
   */
  public static String trainCaseWithOptions(String input, Options opts) {
    final int HYPHEN = 0x2d;
    return capitalize(input, HYPHEN, opts);
  }

  /**
   * Converts the input string to train case.
   *
   * <p>It treats the end of a sequence of non-alphabetical characters as a word boundary, but not
   * the beginning.
   *
   * @param input The input string.
   * @return A string converted to train case.
   */
  public static String trainCase(String input) {
    final int HYPHEN = 0x2d;
    return capitalize(input, HYPHEN, new Options(false, true, null, null));
  }

  /**
   * Converts the input string to Ada case with the specified options.
   *
   * @param input The input string.
   * @param opts The options which specifies the ways of case conversion.
   * @return A string converted to Ada case.
   */
  public static String adaCaseWithOptions(String input, Options opts) {
    final int UNDERSCORE = 0x5f;
    return capitalize(input, UNDERSCORE, opts);
  }

  /**
   * Converts the input string to Ada case.
   *
   * <p>It treats the end of a sequence of non-alphabetical characters as a word boundary, but not
   * the beginning.
   *
   * @param input The input string.
   * @return A string converted to Ada case.
   */
  public static String adaCase(String input) {
    final int UNDERSCORE = 0x5f;
    return capitalize(input, UNDERSCORE, new Options(false, true, null, null));
  }

  /**
   * Converts the input string to title case with the specified options.
   *
   * @param input The input string.
   * @param opts The options which specifies the ways of case conversion.
   * @return A string converted to title case.
   */
  public static String titleCaseWithOptions(String input, Options opts) {
    final int SPACE = 0x20;
    return capitalize(input, SPACE, opts);
  }

  /**
   * Converts the input string to title case.
   *
   * <p>It treats the end of a sequence of non-alphabetical characters as a word boundary, but not
   * the beginning.
   *
   * @param input The input string.
   * @return A string converted to title case.
   */
  public static String titleCase(String input) {
    final int SPACE = 0x20;
    return capitalize(input, SPACE, new Options(false, true, null, null));
  }
}
