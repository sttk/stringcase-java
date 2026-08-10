package com.github.sttk.stringcase;

import static com.github.sttk.stringcase.StringCase.*;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@SuppressWarnings("missing-explicit-ctor")
public class StringCase_LowerizeTest {

  @Nested
  class Lowerize {
    @Nested
    class NonAlphabetsAsHeadOfWord {
      @Test
      void convertCamelCase() {
        var opts = new Options(true, false, null, null);
        var result = lowerize("abcDefGHIjk", '.', opts);
        assertThat(result).isEqualTo("abc.def.gh.ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(true, false, "", "");
        var result = lowerize("AbcDefGHIjk", '.', opts);
        assertThat(result).isEqualTo("abc.def.gh.ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(true, false, null, null);
        var result = lowerize("abc_def_ghi", '.', opts);
        assertThat(result).isEqualTo("abc.def.ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(true, false, null, null);
        var result = lowerize("abc-def-ghi", '.', opts);
        assertThat(result).isEqualTo("abc.def.ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(true, false, null, null);
        var result = lowerize("Abc-Def-Ghi", '.', opts);
        assertThat(result).isEqualTo("abc.def.ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(true, false, null, null);
        var result = lowerize("ABC_DEF_GHI", '.', opts);
        assertThat(result).isEqualTo("abc.def.ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(true, false, null, null);
        var result = lowerize("ABC-DEF-GHI", '.', opts);
        assertThat(result).isEqualTo("abc.def.ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(true, false, null, null);
        var result = lowerize("abc123-456defG89HIJklMN12", '.', opts);
        assertThat(result).isEqualTo("abc.123.456def.g.89hi.jkl.mn.12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(true, false, null, null);
        var result = lowerize(":.abc~!@def#$ghi%&jk(lm)no/?", '.', opts);
        assertThat(result).isEqualTo("abc.def.ghi.jk.lm.no");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(true, false, null, null);
        var result = lowerize("123abc456def", '.', opts);
        assertThat(result).isEqualTo("123abc.456def");

        result = lowerize("123ABC456DEF", '.', opts);
        assertThat(result).isEqualTo("123abc.456def");

        result = lowerize("123Abc456Def", '.', opts);
        assertThat(result).isEqualTo("123.abc.456.def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(true, false, null, null);
        var result = lowerize("", '.', opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsAsTailOfWord {
      @Test
      void convertCamelCase() {
        var opts = new Options(false, true, null, null);
        var result = lowerize("abcDefGHIjk", '.', opts);
        assertThat(result).isEqualTo("abc.def.gh.ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(false, true, "", "");
        var result = lowerize("AbcDefGHIjk", '.', opts);
        assertThat(result).isEqualTo("abc.def.gh.ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(false, true, null, null);
        var result = lowerize("abc_def_ghi", '.', opts);
        assertThat(result).isEqualTo("abc.def.ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(false, true, null, null);
        var result = lowerize("abc-def-ghi", '.', opts);
        assertThat(result).isEqualTo("abc.def.ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(false, true, null, null);
        var result = lowerize("Abc-Def-Ghi", '.', opts);
        assertThat(result).isEqualTo("abc.def.ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(false, true, null, null);
        var result = lowerize("ABC_DEF_GHI", '.', opts);
        assertThat(result).isEqualTo("abc.def.ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(false, true, null, null);
        var result = lowerize("ABC-DEF-GHI", '.', opts);
        assertThat(result).isEqualTo("abc.def.ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(false, true, null, null);
        var result = lowerize("abc123-456defG89HIJklMN12", '.', opts);
        assertThat(result).isEqualTo("abc123.456.def.g89.hi.jkl.mn12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(false, true, null, null);
        var result = lowerize(":.abc~!@def#$ghi%&jk(lm)no/?", '.', opts);
        assertThat(result).isEqualTo("abc.def.ghi.jk.lm.no");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(false, true, null, null);
        var result = lowerize("123abc456def", '.', opts);
        assertThat(result).isEqualTo("123.abc456.def");

        result = lowerize("123ABC456DEF", '.', opts);
        assertThat(result).isEqualTo("123.abc456.def");

        result = lowerize("123Abc456Def", '.', opts);
        assertThat(result).isEqualTo("123.abc456.def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(false, true, null, null);
        var result = lowerize("", '.', opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsAsWord {
      @Test
      void convertCamelCase() {
        var opts = new Options(true, true, null, null);
        var result = lowerize("abcDefGHIjk", '.', opts);
        assertThat(result).isEqualTo("abc.def.gh.ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(true, true, "", "");
        var result = lowerize("AbcDefGHIjk", '.', opts);
        assertThat(result).isEqualTo("abc.def.gh.ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(true, true, null, null);
        var result = lowerize("abc_def_ghi", '.', opts);
        assertThat(result).isEqualTo("abc.def.ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(true, true, null, null);
        var result = lowerize("abc-def-ghi", '.', opts);
        assertThat(result).isEqualTo("abc.def.ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(true, true, null, null);
        var result = lowerize("Abc-Def-Ghi", '.', opts);
        assertThat(result).isEqualTo("abc.def.ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(true, true, null, null);
        var result = lowerize("ABC_DEF_GHI", '.', opts);
        assertThat(result).isEqualTo("abc.def.ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(true, true, null, null);
        var result = lowerize("ABC-DEF-GHI", '.', opts);
        assertThat(result).isEqualTo("abc.def.ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(true, true, null, null);
        var result = lowerize("abc123-456defG89HIJklMN12", '.', opts);
        assertThat(result).isEqualTo("abc.123.456.def.g.89.hi.jkl.mn.12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(true, true, null, null);
        var result = lowerize(":.abc~!@def#$ghi%&jk(lm)no/?", '.', opts);
        assertThat(result).isEqualTo("abc.def.ghi.jk.lm.no");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(true, true, null, null);
        var result = lowerize("123abc456def", '.', opts);
        assertThat(result).isEqualTo("123.abc.456.def");

        result = lowerize("123ABC456DEF", '.', opts);
        assertThat(result).isEqualTo("123.abc.456.def");

        result = lowerize("123Abc456Def", '.', opts);
        assertThat(result).isEqualTo("123.abc.456.def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(true, true, null, null);
        var result = lowerize("", '.', opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsPartAsWord {
      @Test
      void convertCamelCase() {
        var opts = new Options(false, false, null, null);
        var result = lowerize("abcDefGHIjk", '.', opts);
        assertThat(result).isEqualTo("abc.def.gh.ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(false, false, "", "");
        var result = lowerize("AbcDefGHIjk", '.', opts);
        assertThat(result).isEqualTo("abc.def.gh.ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(false, false, null, null);
        var result = lowerize("abc_def_ghi", '.', opts);
        assertThat(result).isEqualTo("abc.def.ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(false, false, null, null);
        var result = lowerize("abc-def-ghi", '.', opts);
        assertThat(result).isEqualTo("abc.def.ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(false, false, null, null);
        var result = lowerize("Abc-Def-Ghi", '.', opts);
        assertThat(result).isEqualTo("abc.def.ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(false, false, null, null);
        var result = lowerize("ABC_DEF_GHI", '.', opts);
        assertThat(result).isEqualTo("abc.def.ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(false, false, null, null);
        var result = lowerize("ABC-DEF-GHI", '.', opts);
        assertThat(result).isEqualTo("abc.def.ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(false, false, null, null);
        var result = lowerize("abc123-456defG89HIJklMN12", '.', opts);
        assertThat(result).isEqualTo("abc123.456def.g89hi.jkl.mn12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(false, false, null, null);
        var result = lowerize(":.abc~!@def#$ghi%&jk(lm)no/?", '.', opts);
        assertThat(result).isEqualTo("abc.def.ghi.jk.lm.no");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(false, false, null, null);
        var result = lowerize("123abc456def", '.', opts);
        assertThat(result).isEqualTo("123abc456def");

        result = lowerize("123ABC456DEF", '.', opts);
        assertThat(result).isEqualTo("123abc456def");

        result = lowerize("123Abc456Def", '.', opts);
        assertThat(result).isEqualTo("123.abc456.def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(false, false, null, null);
        var result = lowerize("", '.', opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsAsHeadOfWordAndWithSeparators {
      @Test
      void convertCamelCase() {
        var opts = new Options(true, false, "-_", null);
        var result = lowerize("abcDefGHIjk", '.', opts);
        assertThat(result).isEqualTo("abc.def.gh.ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(true, false, "-_", "");
        var result = lowerize("AbcDefGHIjk", '.', opts);
        assertThat(result).isEqualTo("abc.def.gh.ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(true, false, "_", null);
        var result = lowerize("abc_def_ghi", '.', opts);
        assertThat(result).isEqualTo("abc.def.ghi");

        opts = new Options(true, false, "-", null);
        result = lowerize("abc_def_ghi", '.', opts);
        assertThat(result).isEqualTo("abc._def._ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(true, false, "-", null);
        var result = lowerize("abc-def-ghi", '.', opts);
        assertThat(result).isEqualTo("abc.def.ghi");

        opts = new Options(true, false, "_", null);
        result = lowerize("abc-def-ghi", '.', opts);
        assertThat(result).isEqualTo("abc.-def.-ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(true, false, "-", null);
        var result = lowerize("Abc-Def-Ghi", '.', opts);
        assertThat(result).isEqualTo("abc.def.ghi");

        opts = new Options(true, false, "_", null);
        result = lowerize("Abc-Def-Ghi", '.', opts);
        assertThat(result).isEqualTo("abc.-.def.-.ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(true, false, "_", null);
        var result = lowerize("ABC_DEF_GHI", '.', opts);
        assertThat(result).isEqualTo("abc.def.ghi");

        opts = new Options(true, false, "-", null);
        result = lowerize("ABC_DEF_GHI", '.', opts);
        assertThat(result).isEqualTo("abc._def._ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(true, false, "-", null);
        var result = lowerize("ABC-DEF-GHI", '.', opts);
        assertThat(result).isEqualTo("abc.def.ghi");

        opts = new Options(true, false, "_", null);
        result = lowerize("ABC-DEF-GHI", '.', opts);
        assertThat(result).isEqualTo("abc.-def.-ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(true, false, "-", null);
        var result = lowerize("abc123-456defG89HIJklMN12", '.', opts);
        assertThat(result).isEqualTo("abc.123.456def.g.89hi.jkl.mn.12");

        opts = new Options(true, false, "_", null);
        result = lowerize("abc123-456defG89HIJklMN12", '.', opts);
        assertThat(result).isEqualTo("abc.123-456def.g.89hi.jkl.mn.12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(true, false, ":@$&()/", null);
        var result = lowerize(":.abc~!@def#$ghi%&jk(lm)no/?", '.', opts);
        assertThat(result).isEqualTo(".abc.~!.def.#.ghi.%.jk.lm.no.?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(true, false, "-", null);
        var result = lowerize("123abc456def", '.', opts);
        assertThat(result).isEqualTo("123abc.456def");

        result = lowerize("123ABC456DEF", '.', opts);
        assertThat(result).isEqualTo("123abc.456def");

        result = lowerize("123Abc456Def", '.', opts);
        assertThat(result).isEqualTo("123.abc.456.def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(true, false, null, null);
        var result = lowerize("", '.', opts);
        assertThat(result).isEqualTo("");
      }

      @Test
      void alphabetsAndNumbersInSeparatorsAreNoEffect() {
        var opts = new Options(true, false, "-b2", null);
        var result = lowerize("abc123def", '.', opts);
        assertThat(result).isEqualTo("abc.123def");
      }
    }

    @Nested
    class NonAlphabetsAsTailOfWordAndWithSeparators {
      @Test
      void convertCamelCase() {
        var opts = new Options(false, true, "-_", null);
        var result = lowerize("abcDefGHIjk", '.', opts);
        assertThat(result).isEqualTo("abc.def.gh.ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(false, true, "-_", "");
        var result = lowerize("AbcDefGHIjk", '.', opts);
        assertThat(result).isEqualTo("abc.def.gh.ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(false, true, "_", null);
        var result = lowerize("abc_def_ghi", '.', opts);
        assertThat(result).isEqualTo("abc.def.ghi");

        opts = new Options(false, true, "-", null);
        result = lowerize("abc_def_ghi", '.', opts);
        assertThat(result).isEqualTo("abc_.def_.ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(false, true, "-", null);
        var result = lowerize("abc-def-ghi", '.', opts);
        assertThat(result).isEqualTo("abc.def.ghi");

        opts = new Options(false, true, "_", null);
        result = lowerize("abc-def-ghi", '.', opts);
        assertThat(result).isEqualTo("abc-.def-.ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(false, true, "-", null);
        var result = lowerize("Abc-Def-Ghi", '.', opts);
        assertThat(result).isEqualTo("abc.def.ghi");

        opts = new Options(false, true, "_", null);
        result = lowerize("Abc-Def-Ghi", '.', opts);
        assertThat(result).isEqualTo("abc-.def-.ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(false, true, "_", null);
        var result = lowerize("ABC_DEF_GHI", '.', opts);
        assertThat(result).isEqualTo("abc.def.ghi");

        opts = new Options(false, true, "-", null);
        result = lowerize("ABC_DEF_GHI", '.', opts);
        assertThat(result).isEqualTo("abc_.def_.ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(false, true, "-", null);
        var result = lowerize("ABC-DEF-GHI", '.', opts);
        assertThat(result).isEqualTo("abc.def.ghi");

        opts = new Options(false, true, "_", null);
        result = lowerize("ABC-DEF-GHI", '.', opts);
        assertThat(result).isEqualTo("abc-.def-.ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(false, true, "-", null);
        var result = lowerize("abc123-456defG89HIJklMN12", '.', opts);
        assertThat(result).isEqualTo("abc123.456.def.g89.hi.jkl.mn12");

        opts = new Options(false, true, "_", null);
        result = lowerize("abc123-456defG89HIJklMN12", '.', opts);
        assertThat(result).isEqualTo("abc123-456.def.g89.hi.jkl.mn12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(false, true, ":@$&()/", null);
        var result = lowerize(":.abc~!@def#$ghi%&jk(lm)no/?", '.', opts);
        assertThat(result).isEqualTo("..abc~!.def#.ghi%.jk.lm.no.?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(false, true, "-", null);
        var result = lowerize("123abc456def", '.', opts);
        assertThat(result).isEqualTo("123.abc456.def");

        result = lowerize("123ABC456DEF", '.', opts);
        assertThat(result).isEqualTo("123.abc456.def");

        result = lowerize("123Abc456Def", '.', opts);
        assertThat(result).isEqualTo("123.abc456.def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(false, true, "-_", null);
        var result = lowerize("", '.', opts);
        assertThat(result).isEqualTo("");
      }

      @Test
      void alphabetsAndNumbersInSeparatorsAreNoEffect() {
        var opts = new Options(false, true, "-b2", null);
        var result = lowerize("abc123def", '.', opts);
        assertThat(result).isEqualTo("abc123.def");
      }
    }

    @Nested
    class NonAlphabetsAsWordAndWithSeparators {
      @Test
      void convertCamelCase() {
        var opts = new Options(true, true, "-_", null);
        var result = lowerize("abcDefGHIjk", '.', opts);
        assertThat(result).isEqualTo("abc.def.gh.ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(true, true, "-_", "");
        var result = lowerize("AbcDefGHIjk", '.', opts);
        assertThat(result).isEqualTo("abc.def.gh.ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(true, true, "_", null);
        var result = lowerize("abc_def_ghi", '.', opts);
        assertThat(result).isEqualTo("abc.def.ghi");

        opts = new Options(true, true, "-", null);
        result = lowerize("abc_def_ghi", '.', opts);
        assertThat(result).isEqualTo("abc._.def._.ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(true, true, "-", null);
        var result = lowerize("abc-def-ghi", '.', opts);
        assertThat(result).isEqualTo("abc.def.ghi");

        opts = new Options(true, true, "_", null);
        result = lowerize("abc-def-ghi", '.', opts);
        assertThat(result).isEqualTo("abc.-.def.-.ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(true, true, "-", null);
        var result = lowerize("Abc-Def-Ghi", '.', opts);
        assertThat(result).isEqualTo("abc.def.ghi");

        opts = new Options(true, true, "_", null);
        result = lowerize("Abc-Def-Ghi", '.', opts);
        assertThat(result).isEqualTo("abc.-.def.-.ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(true, true, "_", null);
        var result = lowerize("ABC_DEF_GHI", '.', opts);
        assertThat(result).isEqualTo("abc.def.ghi");

        opts = new Options(true, true, "-", null);
        result = lowerize("ABC_DEF_GHI", '.', opts);
        assertThat(result).isEqualTo("abc._.def._.ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(true, true, "-", null);
        var result = lowerize("ABC-DEF-GHI", '.', opts);
        assertThat(result).isEqualTo("abc.def.ghi");

        opts = new Options(true, true, "_", null);
        result = lowerize("ABC-DEF-GHI", '.', opts);
        assertThat(result).isEqualTo("abc.-.def.-.ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(true, true, "-", null);
        var result = lowerize("abc123-456defG89HIJklMN12", '.', opts);
        assertThat(result).isEqualTo("abc.123.456.def.g.89.hi.jkl.mn.12");

        opts = new Options(true, true, "_", null);
        result = lowerize("abc123-456defG89HIJklMN12", '.', opts);
        assertThat(result).isEqualTo("abc.123-456.def.g.89.hi.jkl.mn.12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(true, true, ":@$&()/", null);
        var result = lowerize(":.abc~!@def#$ghi%&jk(lm)no/?", '.', opts);
        assertThat(result).isEqualTo("..abc.~!.def.#.ghi.%.jk.lm.no.?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(true, true, "-_", null);
        var result = lowerize("123abc456def", '.', opts);
        assertThat(result).isEqualTo("123.abc.456.def");

        result = lowerize("123ABC456DEF", '.', opts);
        assertThat(result).isEqualTo("123.abc.456.def");

        result = lowerize("123Abc456Def", '.', opts);
        assertThat(result).isEqualTo("123.abc.456.def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(true, true, "-_", null);
        var result = lowerize("", '.', opts);
        assertThat(result).isEqualTo("");
      }

      @Test
      void alphabetsAndNumbersInSeparatorsAreNoEffect() {
        var opts = new Options(true, true, "-b2", null);
        var result = lowerize("abc123def", '.', opts);
        assertThat(result).isEqualTo("abc.123.def");
      }
    }

    @Nested
    class NonAlphabetsAsPartOfWordAndWithSeparators {
      @Test
      void convertCamelCase() {
        var opts = new Options(false, false, "-_", null);
        var result = lowerize("abcDefGHIjk", '.', opts);
        assertThat(result).isEqualTo("abc.def.gh.ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(false, false, "-_", "");
        var result = lowerize("AbcDefGHIjk", '.', opts);
        assertThat(result).isEqualTo("abc.def.gh.ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(false, false, "_", null);
        var result = lowerize("abc_def_ghi", '.', opts);
        assertThat(result).isEqualTo("abc.def.ghi");

        opts = new Options(false, false, "-", null);
        result = lowerize("abc_def_ghi", '.', opts);
        assertThat(result).isEqualTo("abc_def_ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(false, false, "-", null);
        var result = lowerize("abc-def-ghi", '.', opts);
        assertThat(result).isEqualTo("abc.def.ghi");

        opts = new Options(false, false, "_", null);
        result = lowerize("abc-def-ghi", '.', opts);
        assertThat(result).isEqualTo("abc-def-ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(false, false, "-", null);
        var result = lowerize("Abc-Def-Ghi", '.', opts);
        assertThat(result).isEqualTo("abc.def.ghi");

        opts = new Options(false, false, "_", null);
        result = lowerize("Abc-Def-Ghi", '.', opts);
        assertThat(result).isEqualTo("abc-.def-.ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(false, false, "_", null);
        var result = lowerize("ABC_DEF_GHI", '.', opts);
        assertThat(result).isEqualTo("abc.def.ghi");

        opts = new Options(false, false, "-", null);
        result = lowerize("ABC_DEF_GHI", '.', opts);
        assertThat(result).isEqualTo("abc_def_ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(false, false, "-", null);
        var result = lowerize("ABC-DEF-GHI", '.', opts);
        assertThat(result).isEqualTo("abc.def.ghi");

        opts = new Options(false, false, "_", null);
        result = lowerize("ABC-DEF-GHI", '.', opts);
        assertThat(result).isEqualTo("abc-def-ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(false, false, "-", null);
        var result = lowerize("abc123-456defG89HIJklMN12", '.', opts);
        assertThat(result).isEqualTo("abc123.456def.g89hi.jkl.mn12");

        opts = new Options(false, false, "_", null);
        result = lowerize("abc123-456defG89HIJklMN12", '.', opts);
        assertThat(result).isEqualTo("abc123-456def.g89hi.jkl.mn12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(false, false, ":@$&()/", null);
        var result = lowerize(":.abc~!@def#$ghi%&jk(lm)no/?", '.', opts);
        assertThat(result).isEqualTo(".abc~!.def#.ghi%.jk.lm.no.?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(false, false, "-_", null);
        var result = lowerize("123abc456def", '.', opts);
        assertThat(result).isEqualTo("123abc456def");

        result = lowerize("123ABC456DEF", '.', opts);
        assertThat(result).isEqualTo("123abc456def");

        result = lowerize("123Abc456Def", '.', opts);
        assertThat(result).isEqualTo("123.abc456.def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(false, false, "-_", null);
        var result = lowerize("", '.', opts);
        assertThat(result).isEqualTo("");
      }

      @Test
      void alphabetsAndNumbersInSeparatorsAreNoEffect() {
        var opts = new Options(false, false, "-b2", null);
        var result = lowerize("abc123def", '.', opts);
        assertThat(result).isEqualTo("abc123def");
      }
    }

    @Nested
    class NonAlphabetsAsHeadOfWordAndWithKeptCharacters {
      @Test
      void convertCamelCase() {
        var opts = new Options(true, false, null, "-_");
        var result = lowerize("abcDefGHIjk", '.', opts);
        assertThat(result).isEqualTo("abc.def.gh.ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(true, false, "", "-_");
        var result = lowerize("AbcDefGHIjk", '.', opts);
        assertThat(result).isEqualTo("abc.def.gh.ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(true, false, null, "-");
        var result = lowerize("abc_def_ghi", '.', opts);
        assertThat(result).isEqualTo("abc.def.ghi");

        opts = new Options(true, false, null, "_");
        result = lowerize("abc_def_ghi", '.', opts);
        assertThat(result).isEqualTo("abc._def._ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(true, false, null, "_");
        var result = lowerize("abc-def-ghi", '.', opts);
        assertThat(result).isEqualTo("abc.def.ghi");

        opts = new Options(true, false, null, "-");
        result = lowerize("abc-def-ghi", '.', opts);
        assertThat(result).isEqualTo("abc.-def.-ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(true, false, null, "_");
        var result = lowerize("Abc-Def-Ghi", '.', opts);
        assertThat(result).isEqualTo("abc.def.ghi");

        opts = new Options(true, false, null, "-");
        result = lowerize("Abc-Def-Ghi", '.', opts);
        assertThat(result).isEqualTo("abc.-.def.-.ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(true, false, null, "-");
        var result = lowerize("ABC_DEF_GHI", '.', opts);
        assertThat(result).isEqualTo("abc.def.ghi");

        opts = new Options(true, false, null, "_");
        result = lowerize("ABC_DEF_GHI", '.', opts);
        assertThat(result).isEqualTo("abc._def._ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(true, false, null, "_");
        var result = lowerize("ABC-DEF-GHI", '.', opts);
        assertThat(result).isEqualTo("abc.def.ghi");

        opts = new Options(true, false, null, "-");
        result = lowerize("ABC-DEF-GHI", '.', opts);
        assertThat(result).isEqualTo("abc.-def.-ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(true, false, null, "_");
        var result = lowerize("abc123-456defG89HIJklMN12", '.', opts);
        assertThat(result).isEqualTo("abc.123.456def.g.89hi.jkl.mn.12");

        opts = new Options(true, false, null, "-");
        result = lowerize("abc123-456defG89HIJklMN12", '.', opts);
        assertThat(result).isEqualTo("abc.123-456def.g.89hi.jkl.mn.12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(true, false, null, ".~!#%?");
        var result = lowerize(":.abc~!@def#$ghi%&jk(lm)no/?", '.', opts);
        assertThat(result).isEqualTo(".abc.~!.def.#.ghi.%.jk.lm.no.?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(true, false, null, "-");
        var result = lowerize("123abc456def", '.', opts);
        assertThat(result).isEqualTo("123abc.456def");

        result = lowerize("123ABC456DEF", '.', opts);
        assertThat(result).isEqualTo("123abc.456def");

        result = lowerize("123Abc456Def", '.', opts);
        assertThat(result).isEqualTo("123.abc.456.def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(true, false, null, "-_");
        var result = lowerize("", '.', opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsAsTailOfWordAndWithKeptCharacters {
      @Test
      void convertCamelCase() {
        var opts = new Options(false, true, null, "-_");
        var result = lowerize("abcDefGHIjk", '.', opts);
        assertThat(result).isEqualTo("abc.def.gh.ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(false, true, "", "-_");
        var result = lowerize("AbcDefGHIjk", '.', opts);
        assertThat(result).isEqualTo("abc.def.gh.ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(false, true, null, "-");
        var result = lowerize("abc_def_ghi", '.', opts);
        assertThat(result).isEqualTo("abc.def.ghi");

        opts = new Options(false, true, null, "_");
        result = lowerize("abc_def_ghi", '.', opts);
        assertThat(result).isEqualTo("abc_.def_.ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(false, true, null, "_");
        var result = lowerize("abc-def-ghi", '.', opts);
        assertThat(result).isEqualTo("abc.def.ghi");

        opts = new Options(false, true, null, "-");
        result = lowerize("abc-def-ghi", '.', opts);
        assertThat(result).isEqualTo("abc-.def-.ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(false, true, null, "_");
        var result = lowerize("Abc-Def-Ghi", '.', opts);
        assertThat(result).isEqualTo("abc.def.ghi");

        opts = new Options(false, true, null, "-");
        result = lowerize("Abc-Def-Ghi", '.', opts);
        assertThat(result).isEqualTo("abc-.def-.ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(false, true, null, "-");
        var result = lowerize("ABC_DEF_GHI", '.', opts);
        assertThat(result).isEqualTo("abc.def.ghi");

        opts = new Options(false, true, null, "_");
        result = lowerize("ABC_DEF_GHI", '.', opts);
        assertThat(result).isEqualTo("abc_.def_.ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(false, true, null, "_");
        var result = lowerize("ABC-DEF-GHI", '.', opts);
        assertThat(result).isEqualTo("abc.def.ghi");

        opts = new Options(false, true, null, "-");
        result = lowerize("ABC-DEF-GHI", '.', opts);
        assertThat(result).isEqualTo("abc-.def-.ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(false, true, null, "_");
        var result = lowerize("abc123-456defG89HIJklMN12", '.', opts);
        assertThat(result).isEqualTo("abc123.456.def.g89.hi.jkl.mn12");

        opts = new Options(false, true, null, "-");
        result = lowerize("abc123-456defG89HIJklMN12", '.', opts);
        assertThat(result).isEqualTo("abc123-456.def.g89.hi.jkl.mn12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(false, true, null, ".~!#%?");
        var result = lowerize(":.abc~!@def#$ghi%&jk(lm)no/?", '.', opts);
        assertThat(result).isEqualTo("..abc~!.def#.ghi%.jk.lm.no.?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(false, true, null, "_");
        var result = lowerize("123abc456def", '.', opts);
        assertThat(result).isEqualTo("123.abc456.def");

        result = lowerize("123ABC456DEF", '.', opts);
        assertThat(result).isEqualTo("123.abc456.def");

        result = lowerize("123Abc456Def", '.', opts);
        assertThat(result).isEqualTo("123.abc456.def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(false, true, null, "-_");
        var result = lowerize("", '.', opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsAsWordAndWithKeptCharacters {
      @Test
      void convertCamelCase() {
        var opts = new Options(true, true, null, "-_");
        var result = lowerize("abcDefGHIjk", '.', opts);
        assertThat(result).isEqualTo("abc.def.gh.ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(true, true, "", "-_");
        var result = lowerize("AbcDefGHIjk", '.', opts);
        assertThat(result).isEqualTo("abc.def.gh.ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(true, true, null, "-");
        var result = lowerize("abc_def_ghi", '.', opts);
        assertThat(result).isEqualTo("abc.def.ghi");

        opts = new Options(true, true, null, "_");
        result = lowerize("abc_def_ghi", '.', opts);
        assertThat(result).isEqualTo("abc._.def._.ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(true, true, null, "_");
        var result = lowerize("abc-def-ghi", '.', opts);
        assertThat(result).isEqualTo("abc.def.ghi");

        opts = new Options(true, true, null, "-");
        result = lowerize("abc-def-ghi", '.', opts);
        assertThat(result).isEqualTo("abc.-.def.-.ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(true, true, null, "_");
        var result = lowerize("Abc-Def-Ghi", '.', opts);
        assertThat(result).isEqualTo("abc.def.ghi");

        opts = new Options(true, true, null, "-");
        result = lowerize("Abc-Def-Ghi", '.', opts);
        assertThat(result).isEqualTo("abc.-.def.-.ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(true, true, null, "-");
        var result = lowerize("ABC_DEF_GHI", '.', opts);
        assertThat(result).isEqualTo("abc.def.ghi");

        opts = new Options(true, true, null, "_");
        result = lowerize("ABC_DEF_GHI", '.', opts);
        assertThat(result).isEqualTo("abc._.def._.ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(true, true, null, "_");
        var result = lowerize("ABC-DEF-GHI", '.', opts);
        assertThat(result).isEqualTo("abc.def.ghi");

        opts = new Options(true, true, null, "-");
        result = lowerize("ABC-DEF-GHI", '.', opts);
        assertThat(result).isEqualTo("abc.-.def.-.ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(true, true, null, "_");
        var result = lowerize("abc123-456defG89HIJklMN12", '.', opts);
        assertThat(result).isEqualTo("abc.123.456.def.g.89.hi.jkl.mn.12");

        opts = new Options(true, true, null, "-");
        result = lowerize("abc123-456defG89HIJklMN12", '.', opts);
        assertThat(result).isEqualTo("abc.123-456.def.g.89.hi.jkl.mn.12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(true, true, null, ".~!#%?");
        var result = lowerize(":.abc~!@def#$ghi%&jk(lm)no/?", '.', opts);
        assertThat(result).isEqualTo("..abc.~!.def.#.ghi.%.jk.lm.no.?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(true, true, null, "-_");
        var result = lowerize("123abc456def", '.', opts);
        assertThat(result).isEqualTo("123.abc.456.def");

        result = lowerize("123ABC456DEF", '.', opts);
        assertThat(result).isEqualTo("123.abc.456.def");

        result = lowerize("123Abc456Def", '.', opts);
        assertThat(result).isEqualTo("123.abc.456.def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(true, true, null, "-_");
        var result = lowerize("", '.', opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsAsPartOfWordAndWithKeptCharacters {
      @Test
      void convertCamelCase() {
        var opts = new Options(false, false, null, "-_");
        var result = lowerize("abcDefGHIjk", '.', opts);
        assertThat(result).isEqualTo("abc.def.gh.ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(false, false, "", "-_");
        var result = lowerize("AbcDefGHIjk", '.', opts);
        assertThat(result).isEqualTo("abc.def.gh.ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(false, false, null, "-");
        var result = lowerize("abc_def_ghi", '.', opts);
        assertThat(result).isEqualTo("abc.def.ghi");

        opts = new Options(false, false, null, "_");
        result = lowerize("abc_def_ghi", '.', opts);
        assertThat(result).isEqualTo("abc_def_ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(false, false, null, "_");
        var result = lowerize("abc-def-ghi", '.', opts);
        assertThat(result).isEqualTo("abc.def.ghi");

        opts = new Options(false, false, null, "-");
        result = lowerize("abc-def-ghi", '.', opts);
        assertThat(result).isEqualTo("abc-def-ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(false, false, null, "_");
        var result = lowerize("Abc-Def-Ghi", '.', opts);
        assertThat(result).isEqualTo("abc.def.ghi");

        opts = new Options(false, false, null, "-");
        result = lowerize("Abc-Def-Ghi", '.', opts);
        assertThat(result).isEqualTo("abc-.def-.ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(false, false, null, "-");
        var result = lowerize("ABC_DEF_GHI", '.', opts);
        assertThat(result).isEqualTo("abc.def.ghi");

        opts = new Options(false, false, null, "_");
        result = lowerize("ABC_DEF_GHI", '.', opts);
        assertThat(result).isEqualTo("abc_def_ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(false, false, null, "_");
        var result = lowerize("ABC-DEF-GHI", '.', opts);
        assertThat(result).isEqualTo("abc.def.ghi");

        opts = new Options(false, false, null, "-");
        result = lowerize("ABC-DEF-GHI", '.', opts);
        assertThat(result).isEqualTo("abc-def-ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(false, false, null, "_");
        var result = lowerize("abc123-456defG89HIJklMN12", '.', opts);
        assertThat(result).isEqualTo("abc123.456def.g89hi.jkl.mn12");

        opts = new Options(false, false, null, "-");
        result = lowerize("abc123-456defG89HIJklMN12", '.', opts);
        assertThat(result).isEqualTo("abc123-456def.g89hi.jkl.mn12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(false, false, null, ".~!#%?");
        var result = lowerize(":.abc~!@def#$ghi%&jk(lm)no/?", '.', opts);
        assertThat(result).isEqualTo(".abc~!.def#.ghi%.jk.lm.no.?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(false, false, null, "-_");
        var result = lowerize("123abc456def", '.', opts);
        assertThat(result).isEqualTo("123abc456def");

        result = lowerize("123ABC456DEF", '.', opts);
        assertThat(result).isEqualTo("123abc456def");

        result = lowerize("123Abc456Def", '.', opts);
        assertThat(result).isEqualTo("123.abc456.def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(false, false, null, "-_");
        var result = lowerize("", '.', opts);
        assertThat(result).isEqualTo("");
      }
    }
  }
}
