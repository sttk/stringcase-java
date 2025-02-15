/*
 * Options class.
 * Copyright (C) 2025 Takayuki Sato. All Rights Reserved.
 */
package com.github.sttk.stringcase;

/**
 * Is a class that represents options which specifies the ways of case conversion of strings.
 */
public class Options {
  /**
   * Specifies whether to treat the beginning of a sequence of non-alphabetical characters as a
   * word boundary.
   */
  public final boolean separateBeforeNonAlphabets;

  /**
   * Specifies whether to treat the end of a sequence of non-alphabetical characters as a word
   * boundary.
   */
  public final boolean separateAfterNonAlphabets;

  /**
   * Specifies the set of characters to be treated as word separators and removed from the result
   * string.
   */
  public final String separators;

  /**
   * Specifies the set of characters not to be treated as word separators and kept in the result
   * string.
   */
  public final String keep;

  /**
   * The constructor which takes the arguments that specifies the ways of case conversion.
   *
   * @param separateBeforeNonAlphabets  The flag that specifies whether to treat the beginning of
   *   a sequence of non-alphabetical characters as a word boundary.
   * @param separateAfterNonAlphabets  The flag that specifies whether to treat the end of a
   *   sequence of non-alphabetical characters as a word boundary.
   * @param separators  The symbol characters to be treated as word separators and removed from
   *   the result string.
   * @param keep  The symbol characters to be treated as word separators and kept in the result
   *   string.
   */
  public Options(
    boolean separateBeforeNonAlphabets, boolean separateAfterNonAlphabets,
    String separators, String keep
  ) {
    this.separateBeforeNonAlphabets = separateBeforeNonAlphabets;
    this.separateAfterNonAlphabets = separateAfterNonAlphabets;
    this.separators = separators;
    this.keep = keep;
  }
}
