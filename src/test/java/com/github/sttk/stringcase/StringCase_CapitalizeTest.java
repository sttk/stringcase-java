package com.github.sttk.stringcase;

import static com.github.sttk.stringcase.StringCase.*;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@SuppressWarnings("missing-explicit-ctor")
public class StringCase_CapitalizeTest {

  @Nested
  class Capitalize {
    @Nested
    class NonAlphabetsAsHeadOfWord {
      @Test
      void convertCamelCase() {
        var opts = new Options(true, false, null, null);
        var result = capitalize("abcDefGHIjk", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Gh.Ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(true, false, "", "");
        var result = capitalize("AbcDefGHIjk", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Gh.Ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(true, false, null, null);
        var result = capitalize("abc_def_ghi", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(true, false, null, null);
        var result = capitalize("abc-def-ghi", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(true, false, null, null);
        var result = capitalize("Abc-Def-Ghi", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(true, false, null, null);
        var result = capitalize("ABC_DEF_GHI", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(true, false, null, null);
        var result = capitalize("ABC-DEF-GHI", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(true, false, null, null);
        var result = capitalize("abc123-456defG89HIJklMN12", '.', opts);
        assertThat(result).isEqualTo("Abc.123.456def.G.89hi.Jkl.Mn.12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(true, false, null, null);
        var result = capitalize(":.abc~!@def#$ghi%&jk(lm)no/?", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Ghi.Jk.Lm.No");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(true, false, null, null);
        var result = capitalize("123abc456def", '.', opts);
        assertThat(result).isEqualTo("123abc.456def");

        result = capitalize("123ABC456DEF", '.', opts);
        assertThat(result).isEqualTo("123abc.456def");

        result = capitalize("123Abc456Def", '.', opts);
        assertThat(result).isEqualTo("123.Abc.456.Def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(true, false, null, null);
        var result = capitalize("", '.', opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsAsTailOfWord {
      @Test
      void convertCamelCase() {
        var opts = new Options(false, true, null, null);
        var result = capitalize("abcDefGHIjk", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Gh.Ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(false, true, "", "");
        var result = capitalize("AbcDefGHIjk", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Gh.Ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(false, true, null, null);
        var result = capitalize("abc_def_ghi", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(false, true, null, null);
        var result = capitalize("abc-def-ghi", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(false, true, null, null);
        var result = capitalize("Abc-Def-Ghi", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(false, true, null, null);
        var result = capitalize("ABC_DEF_GHI", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(false, true, null, null);
        var result = capitalize("ABC-DEF-GHI", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(false, true, null, null);
        var result = capitalize("abc123-456defG89HIJklMN12", '.', opts);
        assertThat(result).isEqualTo("Abc123.456.Def.G89.Hi.Jkl.Mn12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(false, true, null, null);
        var result = capitalize(":.abc~!@def#$ghi%&jk(lm)no/?", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Ghi.Jk.Lm.No");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(false, true, null, null);
        var result = capitalize("123abc456def", '.', opts);
        assertThat(result).isEqualTo("123.Abc456.Def");

        result = capitalize("123ABC456DEF", '.', opts);
        assertThat(result).isEqualTo("123.Abc456.Def");

        result = capitalize("123Abc456Def", '.', opts);
        assertThat(result).isEqualTo("123.Abc456.Def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(false, true, null, null);
        var result = capitalize("", '.', opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsAsWord {
      @Test
      void convertCamelCase() {
        var opts = new Options(true, true, null, null);
        var result = capitalize("abcDefGHIjk", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Gh.Ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(true, true, "", "");
        var result = capitalize("AbcDefGHIjk", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Gh.Ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(true, true, null, null);
        var result = capitalize("abc_def_ghi", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(true, true, null, null);
        var result = capitalize("abc-def-ghi", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(true, true, null, null);
        var result = capitalize("Abc-Def-Ghi", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(true, true, null, null);
        var result = capitalize("ABC_DEF_GHI", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(true, true, null, null);
        var result = capitalize("ABC-DEF-GHI", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(true, true, null, null);
        var result = capitalize("abc123-456defG89HIJklMN12", '.', opts);
        assertThat(result).isEqualTo("Abc.123.456.Def.G.89.Hi.Jkl.Mn.12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(true, true, null, null);
        var result = capitalize(":.abc~!@def#$ghi%&jk(lm)no/?", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Ghi.Jk.Lm.No");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(true, true, null, null);
        var result = capitalize("123abc456def", '.', opts);
        assertThat(result).isEqualTo("123.Abc.456.Def");

        result = capitalize("123ABC456DEF", '.', opts);
        assertThat(result).isEqualTo("123.Abc.456.Def");

        result = capitalize("123Abc456Def", '.', opts);
        assertThat(result).isEqualTo("123.Abc.456.Def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(true, true, null, null);
        var result = capitalize("", '.', opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsPartAsWord {
      @Test
      void convertCamelCase() {
        var opts = new Options(false, false, null, null);
        var result = capitalize("abcDefGHIjk", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Gh.Ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(false, false, "", "");
        var result = capitalize("AbcDefGHIjk", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Gh.Ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(false, false, null, null);
        var result = capitalize("abc_def_ghi", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(false, false, null, null);
        var result = capitalize("abc-def-ghi", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(false, false, null, null);
        var result = capitalize("Abc-Def-Ghi", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(false, false, null, null);
        var result = capitalize("ABC_DEF_GHI", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(false, false, null, null);
        var result = capitalize("ABC-DEF-GHI", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(false, false, null, null);
        var result = capitalize("abc123-456defG89HIJklMN12", '.', opts);
        assertThat(result).isEqualTo("Abc123.456def.G89hi.Jkl.Mn12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(false, false, null, null);
        var result = capitalize(":.abc~!@def#$ghi%&jk(lm)no/?", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Ghi.Jk.Lm.No");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(false, false, null, null);
        var result = capitalize("123abc456def", '.', opts);
        assertThat(result).isEqualTo("123abc456def");

        result = capitalize("123ABC456DEF", '.', opts);
        assertThat(result).isEqualTo("123abc456def");

        result = capitalize("123Abc456Def", '.', opts);
        assertThat(result).isEqualTo("123.Abc456.Def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(false, false, null, null);
        var result = capitalize("", '.', opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsAsHeadOfWordAndWithSeparators {
      @Test
      void convertCamelCase() {
        var opts = new Options(true, false, "-_", null);
        var result = capitalize("abcDefGHIjk", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Gh.Ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(true, false, "-_", "");
        var result = capitalize("AbcDefGHIjk", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Gh.Ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(true, false, "_", null);
        var result = capitalize("abc_def_ghi", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Ghi");

        opts = new Options(true, false, "-", null);
        result = capitalize("abc_def_ghi", '.', opts);
        assertThat(result).isEqualTo("Abc._def._ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(true, false, "-", null);
        var result = capitalize("abc-def-ghi", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Ghi");

        opts = new Options(true, false, "_", null);
        result = capitalize("abc-def-ghi", '.', opts);
        assertThat(result).isEqualTo("Abc.-def.-ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(true, false, "-", null);
        var result = capitalize("Abc-Def-Ghi", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Ghi");

        opts = new Options(true, false, "_", null);
        result = capitalize("Abc-Def-Ghi", '.', opts);
        assertThat(result).isEqualTo("Abc.-.Def.-.Ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(true, false, "_", null);
        var result = capitalize("ABC_DEF_GHI", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Ghi");

        opts = new Options(true, false, "-", null);
        result = capitalize("ABC_DEF_GHI", '.', opts);
        assertThat(result).isEqualTo("Abc._def._ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(true, false, "-", null);
        var result = capitalize("ABC-DEF-GHI", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Ghi");

        opts = new Options(true, false, "_", null);
        result = capitalize("ABC-DEF-GHI", '.', opts);
        assertThat(result).isEqualTo("Abc.-def.-ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(true, false, "-", null);
        var result = capitalize("abc123-456defG89HIJklMN12", '.', opts);
        assertThat(result).isEqualTo("Abc.123.456def.G.89hi.Jkl.Mn.12");

        opts = new Options(true, false, "_", null);
        result = capitalize("abc123-456defG89HIJklMN12", '.', opts);
        assertThat(result).isEqualTo("Abc.123-456def.G.89hi.Jkl.Mn.12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(true, false, ":@$&()/", null);
        var result = capitalize(":.abc~!@def#$ghi%&jk(lm)no/?", '.', opts);
        assertThat(result).isEqualTo(".abc.~!.Def.#.Ghi.%.Jk.Lm.No.?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(true, false, "-", null);
        var result = capitalize("123abc456def", '.', opts);
        assertThat(result).isEqualTo("123abc.456def");

        result = capitalize("123ABC456DEF", '.', opts);
        assertThat(result).isEqualTo("123abc.456def");

        result = capitalize("123Abc456Def", '.', opts);
        assertThat(result).isEqualTo("123.Abc.456.Def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(true, false, null, null);
        var result = capitalize("", '.', opts);
        assertThat(result).isEqualTo("");
      }

      @Test
      void alphabetsAndNumbersInSeparatorsAreNoEffect() {
        var opts = new Options(true, false, "-b2", null);
        var result = capitalize("abc123def", '.', opts);
        assertThat(result).isEqualTo("Abc.123def");
      }
    }

    @Nested
    class NonAlphabetsAsTailOfWordAndWithSeparators {
      @Test
      void convertCamelCase() {
        var opts = new Options(false, true, "-_", null);
        var result = capitalize("abcDefGHIjk", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Gh.Ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(false, true, "-_", "");
        var result = capitalize("AbcDefGHIjk", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Gh.Ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(false, true, "_", null);
        var result = capitalize("abc_def_ghi", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Ghi");

        opts = new Options(false, true, "-", null);
        result = capitalize("abc_def_ghi", '.', opts);
        assertThat(result).isEqualTo("Abc_.Def_.Ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(false, true, "-", null);
        var result = capitalize("abc-def-ghi", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Ghi");

        opts = new Options(false, true, "_", null);
        result = capitalize("abc-def-ghi", '.', opts);
        assertThat(result).isEqualTo("Abc-.Def-.Ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(false, true, "-", null);
        var result = capitalize("Abc-Def-Ghi", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Ghi");

        opts = new Options(false, true, "_", null);
        result = capitalize("Abc-Def-Ghi", '.', opts);
        assertThat(result).isEqualTo("Abc-.Def-.Ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(false, true, "_", null);
        var result = capitalize("ABC_DEF_GHI", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Ghi");

        opts = new Options(false, true, "-", null);
        result = capitalize("ABC_DEF_GHI", '.', opts);
        assertThat(result).isEqualTo("Abc_.Def_.Ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(false, true, "-", null);
        var result = capitalize("ABC-DEF-GHI", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Ghi");

        opts = new Options(false, true, "_", null);
        result = capitalize("ABC-DEF-GHI", '.', opts);
        assertThat(result).isEqualTo("Abc-.Def-.Ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(false, true, "-", null);
        var result = capitalize("abc123-456defG89HIJklMN12", '.', opts);
        assertThat(result).isEqualTo("Abc123.456.Def.G89.Hi.Jkl.Mn12");

        opts = new Options(false, true, "_", null);
        result = capitalize("abc123-456defG89HIJklMN12", '.', opts);
        assertThat(result).isEqualTo("Abc123-456.Def.G89.Hi.Jkl.Mn12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(false, true, ":@$&()/", null);
        var result = capitalize(":.abc~!@def#$ghi%&jk(lm)no/?", '.', opts);
        assertThat(result).isEqualTo("..Abc~!.Def#.Ghi%.Jk.Lm.No.?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(false, true, "-", null);
        var result = capitalize("123abc456def", '.', opts);
        assertThat(result).isEqualTo("123.Abc456.Def");

        result = capitalize("123ABC456DEF", '.', opts);
        assertThat(result).isEqualTo("123.Abc456.Def");

        result = capitalize("123Abc456Def", '.', opts);
        assertThat(result).isEqualTo("123.Abc456.Def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(false, true, "-_", null);
        var result = capitalize("", '.', opts);
        assertThat(result).isEqualTo("");
      }

      @Test
      void alphabetsAndNumbersInSeparatorsAreNoEffect() {
        var opts = new Options(false, true, "-b2", null);
        var result = capitalize("abc123def", '.', opts);
        assertThat(result).isEqualTo("Abc123.Def");
      }
    }

    @Nested
    class NonAlphabetsAsWordAndWithSeparators {
      @Test
      void convertCamelCase() {
        var opts = new Options(true, true, "-_", null);
        var result = capitalize("abcDefGHIjk", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Gh.Ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(true, true, "-_", "");
        var result = capitalize("AbcDefGHIjk", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Gh.Ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(true, true, "_", null);
        var result = capitalize("abc_def_ghi", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Ghi");

        opts = new Options(true, true, "-", null);
        result = capitalize("abc_def_ghi", '.', opts);
        assertThat(result).isEqualTo("Abc._.Def._.Ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(true, true, "-", null);
        var result = capitalize("abc-def-ghi", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Ghi");

        opts = new Options(true, true, "_", null);
        result = capitalize("abc-def-ghi", '.', opts);
        assertThat(result).isEqualTo("Abc.-.Def.-.Ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(true, true, "-", null);
        var result = capitalize("Abc-Def-Ghi", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Ghi");

        opts = new Options(true, true, "_", null);
        result = capitalize("Abc-Def-Ghi", '.', opts);
        assertThat(result).isEqualTo("Abc.-.Def.-.Ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(true, true, "_", null);
        var result = capitalize("ABC_DEF_GHI", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Ghi");

        opts = new Options(true, true, "-", null);
        result = capitalize("ABC_DEF_GHI", '.', opts);
        assertThat(result).isEqualTo("Abc._.Def._.Ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(true, true, "-", null);
        var result = capitalize("ABC-DEF-GHI", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Ghi");

        opts = new Options(true, true, "_", null);
        result = capitalize("ABC-DEF-GHI", '.', opts);
        assertThat(result).isEqualTo("Abc.-.Def.-.Ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(true, true, "-", null);
        var result = capitalize("abc123-456defG89HIJklMN12", '.', opts);
        assertThat(result).isEqualTo("Abc.123.456.Def.G.89.Hi.Jkl.Mn.12");

        opts = new Options(true, true, "_", null);
        result = capitalize("abc123-456defG89HIJklMN12", '.', opts);
        assertThat(result).isEqualTo("Abc.123-456.Def.G.89.Hi.Jkl.Mn.12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(true, true, ":@$&()/", null);
        var result = capitalize(":.abc~!@def#$ghi%&jk(lm)no/?", '.', opts);
        assertThat(result).isEqualTo("..Abc.~!.Def.#.Ghi.%.Jk.Lm.No.?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(true, true, "-_", null);
        var result = capitalize("123abc456def", '.', opts);
        assertThat(result).isEqualTo("123.Abc.456.Def");

        result = capitalize("123ABC456DEF", '.', opts);
        assertThat(result).isEqualTo("123.Abc.456.Def");

        result = capitalize("123Abc456Def", '.', opts);
        assertThat(result).isEqualTo("123.Abc.456.Def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(true, true, "-_", null);
        var result = capitalize("", '.', opts);
        assertThat(result).isEqualTo("");
      }

      @Test
      void alphabetsAndNumbersInSeparatorsAreNoEffect() {
        var opts = new Options(true, true, "-b2", null);
        var result = capitalize("abc123def", '.', opts);
        assertThat(result).isEqualTo("Abc.123.Def");
      }
    }

    @Nested
    class NonAlphabetsAsPartOfWordAndWithSeparators {
      @Test
      void convertCamelCase() {
        var opts = new Options(false, false, "-_", null);
        var result = capitalize("abcDefGHIjk", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Gh.Ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(false, false, "-_", "");
        var result = capitalize("AbcDefGHIjk", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Gh.Ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(false, false, "_", null);
        var result = capitalize("abc_def_ghi", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Ghi");

        opts = new Options(false, false, "-", null);
        result = capitalize("abc_def_ghi", '.', opts);
        assertThat(result).isEqualTo("Abc_def_ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(false, false, "-", null);
        var result = capitalize("abc-def-ghi", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Ghi");

        opts = new Options(false, false, "_", null);
        result = capitalize("abc-def-ghi", '.', opts);
        assertThat(result).isEqualTo("Abc-def-ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(false, false, "-", null);
        var result = capitalize("Abc-Def-Ghi", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Ghi");

        opts = new Options(false, false, "_", null);
        result = capitalize("Abc-Def-Ghi", '.', opts);
        assertThat(result).isEqualTo("Abc-.Def-.Ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(false, false, "_", null);
        var result = capitalize("ABC_DEF_GHI", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Ghi");

        opts = new Options(false, false, "-", null);
        result = capitalize("ABC_DEF_GHI", '.', opts);
        assertThat(result).isEqualTo("Abc_def_ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(false, false, "-", null);
        var result = capitalize("ABC-DEF-GHI", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Ghi");

        opts = new Options(false, false, "_", null);
        result = capitalize("ABC-DEF-GHI", '.', opts);
        assertThat(result).isEqualTo("Abc-def-ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(false, false, "-", null);
        var result = capitalize("abc123-456defG89HIJklMN12", '.', opts);
        assertThat(result).isEqualTo("Abc123.456def.G89hi.Jkl.Mn12");

        opts = new Options(false, false, "_", null);
        result = capitalize("abc123-456defG89HIJklMN12", '.', opts);
        assertThat(result).isEqualTo("Abc123-456def.G89hi.Jkl.Mn12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(false, false, ":@$&()/", null);
        var result = capitalize(":.abc~!@def#$ghi%&jk(lm)no/?", '.', opts);
        assertThat(result).isEqualTo(".abc~!.Def#.Ghi%.Jk.Lm.No.?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(false, false, "-_", null);
        var result = capitalize("123abc456def", '.', opts);
        assertThat(result).isEqualTo("123abc456def");

        result = capitalize("123ABC456DEF", '.', opts);
        assertThat(result).isEqualTo("123abc456def");

        result = capitalize("123Abc456Def", '.', opts);
        assertThat(result).isEqualTo("123.Abc456.Def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(false, false, "-_", null);
        var result = capitalize("", '.', opts);
        assertThat(result).isEqualTo("");
      }

      @Test
      void alphabetsAndNumbersInSeparatorsAreNoEffect() {
        var opts = new Options(false, false, "-b2", null);
        var result = capitalize("abc123def", '.', opts);
        assertThat(result).isEqualTo("Abc123def");
      }
    }

    @Nested
    class NonAlphabetsAsHeadOfWordAndWithKeptCharacters {
      @Test
      void convertCamelCase() {
        var opts = new Options(true, false, null, "-_");
        var result = capitalize("abcDefGHIjk", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Gh.Ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(true, false, "", "-_");
        var result = capitalize("AbcDefGHIjk", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Gh.Ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(true, false, null, "-");
        var result = capitalize("abc_def_ghi", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Ghi");

        opts = new Options(true, false, null, "_");
        result = capitalize("abc_def_ghi", '.', opts);
        assertThat(result).isEqualTo("Abc._def._ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(true, false, null, "_");
        var result = capitalize("abc-def-ghi", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Ghi");

        opts = new Options(true, false, null, "-");
        result = capitalize("abc-def-ghi", '.', opts);
        assertThat(result).isEqualTo("Abc.-def.-ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(true, false, null, "_");
        var result = capitalize("Abc-Def-Ghi", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Ghi");

        opts = new Options(true, false, null, "-");
        result = capitalize("Abc-Def-Ghi", '.', opts);
        assertThat(result).isEqualTo("Abc.-.Def.-.Ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(true, false, null, "-");
        var result = capitalize("ABC_DEF_GHI", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Ghi");

        opts = new Options(true, false, null, "_");
        result = capitalize("ABC_DEF_GHI", '.', opts);
        assertThat(result).isEqualTo("Abc._def._ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(true, false, null, "_");
        var result = capitalize("ABC-DEF-GHI", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Ghi");

        opts = new Options(true, false, null, "-");
        result = capitalize("ABC-DEF-GHI", '.', opts);
        assertThat(result).isEqualTo("Abc.-def.-ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(true, false, null, "_");
        var result = capitalize("abc123-456defG89HIJklMN12", '.', opts);
        assertThat(result).isEqualTo("Abc.123.456def.G.89hi.Jkl.Mn.12");

        opts = new Options(true, false, null, "-");
        result = capitalize("abc123-456defG89HIJklMN12", '.', opts);
        assertThat(result).isEqualTo("Abc.123-456def.G.89hi.Jkl.Mn.12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(true, false, null, ".~!#%?");
        var result = capitalize(":.abc~!@def#$ghi%&jk(lm)no/?", '.', opts);
        assertThat(result).isEqualTo(".abc.~!.Def.#.Ghi.%.Jk.Lm.No.?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(true, false, null, "-");
        var result = capitalize("123abc456def", '.', opts);
        assertThat(result).isEqualTo("123abc.456def");

        result = capitalize("123ABC456DEF", '.', opts);
        assertThat(result).isEqualTo("123abc.456def");

        result = capitalize("123Abc456Def", '.', opts);
        assertThat(result).isEqualTo("123.Abc.456.Def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(true, false, null, "-_");
        var result = capitalize("", '.', opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsAsTailOfWordAndWithKeptCharacters {
      @Test
      void convertCamelCase() {
        var opts = new Options(false, true, null, "-_");
        var result = capitalize("abcDefGHIjk", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Gh.Ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(false, true, "", "-_");
        var result = capitalize("AbcDefGHIjk", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Gh.Ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(false, true, null, "-");
        var result = capitalize("abc_def_ghi", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Ghi");

        opts = new Options(false, true, null, "_");
        result = capitalize("abc_def_ghi", '.', opts);
        assertThat(result).isEqualTo("Abc_.Def_.Ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(false, true, null, "_");
        var result = capitalize("abc-def-ghi", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Ghi");

        opts = new Options(false, true, null, "-");
        result = capitalize("abc-def-ghi", '.', opts);
        assertThat(result).isEqualTo("Abc-.Def-.Ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(false, true, null, "_");
        var result = capitalize("Abc-Def-Ghi", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Ghi");

        opts = new Options(false, true, null, "-");
        result = capitalize("Abc-Def-Ghi", '.', opts);
        assertThat(result).isEqualTo("Abc-.Def-.Ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(false, true, null, "-");
        var result = capitalize("ABC_DEF_GHI", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Ghi");

        opts = new Options(false, true, null, "_");
        result = capitalize("ABC_DEF_GHI", '.', opts);
        assertThat(result).isEqualTo("Abc_.Def_.Ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(false, true, null, "_");
        var result = capitalize("ABC-DEF-GHI", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Ghi");

        opts = new Options(false, true, null, "-");
        result = capitalize("ABC-DEF-GHI", '.', opts);
        assertThat(result).isEqualTo("Abc-.Def-.Ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(false, true, null, "_");
        var result = capitalize("abc123-456defG89HIJklMN12", '.', opts);
        assertThat(result).isEqualTo("Abc123.456.Def.G89.Hi.Jkl.Mn12");

        opts = new Options(false, true, null, "-");
        result = capitalize("abc123-456defG89HIJklMN12", '.', opts);
        assertThat(result).isEqualTo("Abc123-456.Def.G89.Hi.Jkl.Mn12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(false, true, null, ".~!#%?");
        var result = capitalize(":.abc~!@def#$ghi%&jk(lm)no/?", '.', opts);
        assertThat(result).isEqualTo("..Abc~!.Def#.Ghi%.Jk.Lm.No.?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(false, true, null, "_");
        var result = capitalize("123abc456def", '.', opts);
        assertThat(result).isEqualTo("123.Abc456.Def");

        result = capitalize("123ABC456DEF", '.', opts);
        assertThat(result).isEqualTo("123.Abc456.Def");

        result = capitalize("123Abc456Def", '.', opts);
        assertThat(result).isEqualTo("123.Abc456.Def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(false, true, null, "-_");
        var result = capitalize("", '.', opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsAsWordAndWithKeptCharacters {
      @Test
      void convertCamelCase() {
        var opts = new Options(true, true, null, "-_");
        var result = capitalize("abcDefGHIjk", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Gh.Ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(true, true, "", "-_");
        var result = capitalize("AbcDefGHIjk", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Gh.Ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(true, true, null, "-");
        var result = capitalize("abc_def_ghi", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Ghi");

        opts = new Options(true, true, null, "_");
        result = capitalize("abc_def_ghi", '.', opts);
        assertThat(result).isEqualTo("Abc._.Def._.Ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(true, true, null, "_");
        var result = capitalize("abc-def-ghi", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Ghi");

        opts = new Options(true, true, null, "-");
        result = capitalize("abc-def-ghi", '.', opts);
        assertThat(result).isEqualTo("Abc.-.Def.-.Ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(true, true, null, "_");
        var result = capitalize("Abc-Def-Ghi", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Ghi");

        opts = new Options(true, true, null, "-");
        result = capitalize("Abc-Def-Ghi", '.', opts);
        assertThat(result).isEqualTo("Abc.-.Def.-.Ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(true, true, null, "-");
        var result = capitalize("ABC_DEF_GHI", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Ghi");

        opts = new Options(true, true, null, "_");
        result = capitalize("ABC_DEF_GHI", '.', opts);
        assertThat(result).isEqualTo("Abc._.Def._.Ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(true, true, null, "_");
        var result = capitalize("ABC-DEF-GHI", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Ghi");

        opts = new Options(true, true, null, "-");
        result = capitalize("ABC-DEF-GHI", '.', opts);
        assertThat(result).isEqualTo("Abc.-.Def.-.Ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(true, true, null, "_");
        var result = capitalize("abc123-456defG89HIJklMN12", '.', opts);
        assertThat(result).isEqualTo("Abc.123.456.Def.G.89.Hi.Jkl.Mn.12");

        opts = new Options(true, true, null, "-");
        result = capitalize("abc123-456defG89HIJklMN12", '.', opts);
        assertThat(result).isEqualTo("Abc.123-456.Def.G.89.Hi.Jkl.Mn.12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(true, true, null, ".~!#%?");
        var result = capitalize(":.abc~!@def#$ghi%&jk(lm)no/?", '.', opts);
        assertThat(result).isEqualTo("..Abc.~!.Def.#.Ghi.%.Jk.Lm.No.?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(true, true, null, "-_");
        var result = capitalize("123abc456def", '.', opts);
        assertThat(result).isEqualTo("123.Abc.456.Def");

        result = capitalize("123ABC456DEF", '.', opts);
        assertThat(result).isEqualTo("123.Abc.456.Def");

        result = capitalize("123Abc456Def", '.', opts);
        assertThat(result).isEqualTo("123.Abc.456.Def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(true, true, null, "-_");
        var result = capitalize("", '.', opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsAsPartOfWordAndWithKeptCharacters {
      @Test
      void convertCamelCase() {
        var opts = new Options(false, false, null, "-_");
        var result = capitalize("abcDefGHIjk", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Gh.Ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(false, false, "", "-_");
        var result = capitalize("AbcDefGHIjk", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Gh.Ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(false, false, null, "-");
        var result = capitalize("abc_def_ghi", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Ghi");

        opts = new Options(false, false, null, "_");
        result = capitalize("abc_def_ghi", '.', opts);
        assertThat(result).isEqualTo("Abc_def_ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(false, false, null, "_");
        var result = capitalize("abc-def-ghi", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Ghi");

        opts = new Options(false, false, null, "-");
        result = capitalize("abc-def-ghi", '.', opts);
        assertThat(result).isEqualTo("Abc-def-ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(false, false, null, "_");
        var result = capitalize("Abc-Def-Ghi", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Ghi");

        opts = new Options(false, false, null, "-");
        result = capitalize("Abc-Def-Ghi", '.', opts);
        assertThat(result).isEqualTo("Abc-.Def-.Ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(false, false, null, "-");
        var result = capitalize("ABC_DEF_GHI", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Ghi");

        opts = new Options(false, false, null, "_");
        result = capitalize("ABC_DEF_GHI", '.', opts);
        assertThat(result).isEqualTo("Abc_def_ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(false, false, null, "_");
        var result = capitalize("ABC-DEF-GHI", '.', opts);
        assertThat(result).isEqualTo("Abc.Def.Ghi");

        opts = new Options(false, false, null, "-");
        result = capitalize("ABC-DEF-GHI", '.', opts);
        assertThat(result).isEqualTo("Abc-def-ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(false, false, null, "_");
        var result = capitalize("abc123-456defG89HIJklMN12", '.', opts);
        assertThat(result).isEqualTo("Abc123.456def.G89hi.Jkl.Mn12");

        opts = new Options(false, false, null, "-");
        result = capitalize("abc123-456defG89HIJklMN12", '.', opts);
        assertThat(result).isEqualTo("Abc123-456def.G89hi.Jkl.Mn12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(false, false, null, ".~!#%?");
        var result = capitalize(":.abc~!@def#$ghi%&jk(lm)no/?", '.', opts);
        assertThat(result).isEqualTo(".abc~!.Def#.Ghi%.Jk.Lm.No.?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(false, false, null, "-_");
        var result = capitalize("123abc456def", '.', opts);
        assertThat(result).isEqualTo("123abc456def");

        result = capitalize("123ABC456DEF", '.', opts);
        assertThat(result).isEqualTo("123abc456def");

        result = capitalize("123Abc456Def", '.', opts);
        assertThat(result).isEqualTo("123.Abc456.Def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(false, false, null, "-_");
        var result = capitalize("", '.', opts);
        assertThat(result).isEqualTo("");
      }
    }
  }
}
