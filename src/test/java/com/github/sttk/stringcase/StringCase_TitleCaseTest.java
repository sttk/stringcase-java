package com.github.sttk.stringcase;

import static com.github.sttk.stringcase.StringCase.*;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@SuppressWarnings("missing-explicit-ctor")
public class StringCase_TitleCaseTest {

  @Nested
  class TitleCase {
    @Test
    void convertCamelCase() {
      var result = titleCase("abcDefGHIjk");
      assertThat(result).isEqualTo("Abc Def Gh Ijk");
    }

    @Test
    void convertPascalCase() {
      var result = titleCase("AbcDefGHIjk");
      assertThat(result).isEqualTo("Abc Def Gh Ijk");
    }

    @Test
    void convertSnakeCase() {
      var result = titleCase("abc_def_ghi");
      assertThat(result).isEqualTo("Abc Def Ghi");
    }

    @Test
    void convertKebabCase() {
      var result = titleCase("abc-def-ghi");
      assertThat(result).isEqualTo("Abc Def Ghi");
    }

    @Test
    void convertTrainCase() {
      var result = titleCase("Abc-Def-Ghi");
      assertThat(result).isEqualTo("Abc Def Ghi");
    }

    @Test
    void convertMacroCase() {
      var result = titleCase("ABC_DEF_GHI");
      assertThat(result).isEqualTo("Abc Def Ghi");
    }

    @Test
    void convertCobolCase() {
      var result = titleCase("ABC-DEF-GHI");
      assertThat(result).isEqualTo("Abc Def Ghi");
    }

    @Test
    void convertWithKeepingDigits() {
      var result = titleCase("abc123-456defG89HIJklMN12");
      assertThat(result).isEqualTo("Abc123 456 Def G89 Hi Jkl Mn12");
    }

    @Test
    void convertWithSymbolsAsSeparators() {
      var result = titleCase(":.abc~!@def#$ghi%&jk(lm)no/?");
      assertThat(result).isEqualTo("Abc Def Ghi Jk Lm No");
    }

    @Test
    void convertWhenStartingWithDigit() {
      var result = titleCase("123abc456def");
      assertThat(result).isEqualTo("123 Abc456 Def");

      result = titleCase("123ABC456DEF");
      assertThat(result).isEqualTo("123 Abc456 Def");

      result = titleCase("123Abc456Def");
      assertThat(result).isEqualTo("123 Abc456 Def");
    }

    @Test
    void convertAnEmptyString() {
      var result = titleCase("");
      assertThat(result).isEqualTo("");
    }
  }

  @Nested
  class TrainCaseWithOptions {
    @Nested
    class NonAlphabetsAsHeadOfWord {
      @Test
      void convertCamelCase() {
        var opts = new Options(true, false, null, null);
        var result = titleCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("Abc Def Gh Ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(true, false, "", "");
        var result = titleCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("Abc Def Gh Ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(true, false, null, null);
        var result = titleCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("Abc Def Ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(true, false, null, null);
        var result = titleCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("Abc Def Ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(true, false, null, null);
        var result = titleCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("Abc Def Ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(true, false, null, null);
        var result = titleCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("Abc Def Ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(true, false, null, null);
        var result = titleCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("Abc Def Ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(true, false, null, null);
        var result = titleCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc 123 456def G 89hi Jkl Mn 12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(true, false, null, null);
        var result = titleCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo("Abc Def Ghi Jk Lm No");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(true, false, null, null);
        var result = titleCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123abc 456def");

        result = titleCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123abc 456def");

        result = titleCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123 Abc 456 Def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(true, false, null, null);
        var result = titleCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsAsTailOfWord {
      @Test
      void convertCamelCase() {
        var opts = new Options(false, true, null, null);
        var result = titleCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("Abc Def Gh Ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(false, true, "", "");
        var result = titleCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("Abc Def Gh Ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(false, true, null, null);
        var result = titleCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("Abc Def Ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(false, true, null, null);
        var result = titleCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("Abc Def Ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(false, true, null, null);
        var result = titleCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("Abc Def Ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(false, true, null, null);
        var result = titleCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("Abc Def Ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(false, true, null, null);
        var result = titleCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("Abc Def Ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(false, true, null, null);
        var result = titleCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc123 456 Def G89 Hi Jkl Mn12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(false, true, null, null);
        var result = titleCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo("Abc Def Ghi Jk Lm No");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(false, true, null, null);
        var result = titleCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123 Abc456 Def");

        result = titleCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123 Abc456 Def");

        result = titleCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123 Abc456 Def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(false, true, null, null);
        var result = titleCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsAsWord {
      @Test
      void convertCamelCase() {
        var opts = new Options(true, true, null, null);
        var result = titleCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("Abc Def Gh Ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(true, true, "", "");
        var result = titleCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("Abc Def Gh Ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(true, true, null, null);
        var result = titleCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("Abc Def Ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(true, true, null, null);
        var result = titleCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("Abc Def Ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(true, true, null, null);
        var result = titleCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("Abc Def Ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(true, true, null, null);
        var result = titleCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("Abc Def Ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(true, true, null, null);
        var result = titleCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("Abc Def Ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(true, true, null, null);
        var result = titleCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc 123 456 Def G 89 Hi Jkl Mn 12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(true, true, null, null);
        var result = titleCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo("Abc Def Ghi Jk Lm No");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(true, true, null, null);
        var result = titleCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123 Abc 456 Def");

        result = titleCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123 Abc 456 Def");

        result = titleCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123 Abc 456 Def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(true, true, null, null);
        var result = titleCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsPartAsWord {
      @Test
      void convertCamelCase() {
        var opts = new Options(false, false, null, null);
        var result = titleCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("Abc Def Gh Ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(false, false, "", "");
        var result = titleCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("Abc Def Gh Ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(false, false, null, null);
        var result = titleCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("Abc Def Ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(false, false, null, null);
        var result = titleCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("Abc Def Ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(false, false, null, null);
        var result = titleCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("Abc Def Ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(false, false, null, null);
        var result = titleCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("Abc Def Ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(false, false, null, null);
        var result = titleCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("Abc Def Ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(false, false, null, null);
        var result = titleCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc123 456def G89hi Jkl Mn12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(false, false, null, null);
        var result = titleCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo("Abc Def Ghi Jk Lm No");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(false, false, null, null);
        var result = titleCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123abc456def");

        result = titleCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123abc456def");

        result = titleCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123 Abc456 Def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(false, false, null, null);
        var result = titleCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsAsHeadOfWordAndWithSeparators {
      @Test
      void convertCamelCase() {
        var opts = new Options(true, false, "-_", null);
        var result = titleCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("Abc Def Gh Ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(true, false, "-_", "");
        var result = titleCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("Abc Def Gh Ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(true, false, "_", null);
        var result = titleCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("Abc Def Ghi");

        opts = new Options(true, false, "-", null);
        result = titleCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("Abc _def _ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(true, false, "-", null);
        var result = titleCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("Abc Def Ghi");

        opts = new Options(true, false, "_", null);
        result = titleCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("Abc -def -ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(true, false, "-", null);
        var result = titleCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("Abc Def Ghi");

        opts = new Options(true, false, "_", null);
        result = titleCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("Abc - Def - Ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(true, false, "_", null);
        var result = titleCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("Abc Def Ghi");

        opts = new Options(true, false, "-", null);
        result = titleCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("Abc _def _ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(true, false, "-", null);
        var result = titleCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("Abc Def Ghi");

        opts = new Options(true, false, "_", null);
        result = titleCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("Abc -def -ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(true, false, "-", null);
        var result = titleCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc 123 456def G 89hi Jkl Mn 12");

        opts = new Options(true, false, "_", null);
        result = titleCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc 123-456def G 89hi Jkl Mn 12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(true, false, ":@$&()/", null);
        var result = titleCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo(".abc ~! Def # Ghi % Jk Lm No ?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(true, false, "-", null);
        var result = titleCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123abc 456def");

        result = titleCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123abc 456def");

        result = titleCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123 Abc 456 Def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(true, false, null, null);
        var result = titleCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }

      @Test
      void alphabetsAndNumbersInSeparatorsAreNoEffect() {
        var opts = new Options(true, false, "-b2", null);
        var result = titleCaseWithOptions("abc123def", opts);
        assertThat(result).isEqualTo("Abc 123def");
      }
    }

    @Nested
    class NonAlphabetsAsTailOfWordAndWithSeparators {
      @Test
      void convertCamelCase() {
        var opts = new Options(false, true, "-_", null);
        var result = titleCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("Abc Def Gh Ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(false, true, "-_", "");
        var result = titleCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("Abc Def Gh Ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(false, true, "_", null);
        var result = titleCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("Abc Def Ghi");

        opts = new Options(false, true, "-", null);
        result = titleCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("Abc_ Def_ Ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(false, true, "-", null);
        var result = titleCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("Abc Def Ghi");

        opts = new Options(false, true, "_", null);
        result = titleCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("Abc- Def- Ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(false, true, "-", null);
        var result = titleCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("Abc Def Ghi");

        opts = new Options(false, true, "_", null);
        result = titleCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("Abc- Def- Ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(false, true, "_", null);
        var result = titleCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("Abc Def Ghi");

        opts = new Options(false, true, "-", null);
        result = titleCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("Abc_ Def_ Ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(false, true, "-", null);
        var result = titleCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("Abc Def Ghi");

        opts = new Options(false, true, "_", null);
        result = titleCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("Abc- Def- Ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(false, true, "-", null);
        var result = titleCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc123 456 Def G89 Hi Jkl Mn12");

        opts = new Options(false, true, "_", null);
        result = titleCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc123-456 Def G89 Hi Jkl Mn12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(false, true, ":@$&()/", null);
        var result = titleCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo(". Abc~! Def# Ghi% Jk Lm No ?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(false, true, "-", null);
        var result = titleCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123 Abc456 Def");

        result = titleCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123 Abc456 Def");

        result = titleCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123 Abc456 Def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(false, true, "-_", null);
        var result = titleCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }

      @Test
      void alphabetsAndNumbersInSeparatorsAreNoEffect() {
        var opts = new Options(false, true, "-b2", null);
        var result = titleCaseWithOptions("abc123def", opts);
        assertThat(result).isEqualTo("Abc123 Def");
      }
    }

    @Nested
    class NonAlphabetsAsWordAndWithSeparators {
      @Test
      void convertCamelCase() {
        var opts = new Options(true, true, "-_", null);
        var result = titleCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("Abc Def Gh Ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(true, true, "-_", "");
        var result = titleCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("Abc Def Gh Ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(true, true, "_", null);
        var result = titleCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("Abc Def Ghi");

        opts = new Options(true, true, "-", null);
        result = titleCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("Abc _ Def _ Ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(true, true, "-", null);
        var result = titleCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("Abc Def Ghi");

        opts = new Options(true, true, "_", null);
        result = titleCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("Abc - Def - Ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(true, true, "-", null);
        var result = titleCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("Abc Def Ghi");

        opts = new Options(true, true, "_", null);
        result = titleCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("Abc - Def - Ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(true, true, "_", null);
        var result = titleCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("Abc Def Ghi");

        opts = new Options(true, true, "-", null);
        result = titleCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("Abc _ Def _ Ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(true, true, "-", null);
        var result = titleCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("Abc Def Ghi");

        opts = new Options(true, true, "_", null);
        result = titleCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("Abc - Def - Ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(true, true, "-", null);
        var result = titleCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc 123 456 Def G 89 Hi Jkl Mn 12");

        opts = new Options(true, true, "_", null);
        result = titleCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc 123-456 Def G 89 Hi Jkl Mn 12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(true, true, ":@$&()/", null);
        var result = titleCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo(". Abc ~! Def # Ghi % Jk Lm No ?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(true, true, "-_", null);
        var result = titleCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123 Abc 456 Def");

        result = titleCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123 Abc 456 Def");

        result = titleCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123 Abc 456 Def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(true, true, "-_", null);
        var result = titleCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }

      @Test
      void alphabetsAndNumbersInSeparatorsAreNoEffect() {
        var opts = new Options(true, true, "-b2", null);
        var result = titleCaseWithOptions("abc123def", opts);
        assertThat(result).isEqualTo("Abc 123 Def");
      }
    }

    @Nested
    class NonAlphabetsAsPartOfWordAndWithSeparators {
      @Test
      void convertCamelCase() {
        var opts = new Options(false, false, "-_", null);
        var result = titleCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("Abc Def Gh Ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(false, false, "-_", "");
        var result = titleCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("Abc Def Gh Ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(false, false, "_", null);
        var result = titleCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("Abc Def Ghi");

        opts = new Options(false, false, "-", null);
        result = titleCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("Abc_def_ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(false, false, "-", null);
        var result = titleCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("Abc Def Ghi");

        opts = new Options(false, false, "_", null);
        result = titleCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("Abc-def-ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(false, false, "-", null);
        var result = titleCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("Abc Def Ghi");

        opts = new Options(false, false, "_", null);
        result = titleCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("Abc- Def- Ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(false, false, "_", null);
        var result = titleCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("Abc Def Ghi");

        opts = new Options(false, false, "-", null);
        result = titleCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("Abc_def_ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(false, false, "-", null);
        var result = titleCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("Abc Def Ghi");

        opts = new Options(false, false, "_", null);
        result = titleCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("Abc-def-ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(false, false, "-", null);
        var result = titleCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc123 456def G89hi Jkl Mn12");

        opts = new Options(false, false, "_", null);
        result = titleCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc123-456def G89hi Jkl Mn12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(false, false, ":@$&()/", null);
        var result = titleCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo(".abc~! Def# Ghi% Jk Lm No ?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(false, false, "-_", null);
        var result = titleCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123abc456def");

        result = titleCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123abc456def");

        result = titleCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123 Abc456 Def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(false, false, "-_", null);
        var result = titleCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }

      @Test
      void alphabetsAndNumbersInSeparatorsAreNoEffect() {
        var opts = new Options(false, false, "-b2", null);
        var result = titleCaseWithOptions("abc123def", opts);
        assertThat(result).isEqualTo("Abc123def");
      }
    }

    @Nested
    class NonAlphabetsAsHeadOfWordAndWithKeptCharacters {
      @Test
      void convertCamelCase() {
        var opts = new Options(true, false, null, "-_");
        var result = titleCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("Abc Def Gh Ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(true, false, "", "-_");
        var result = titleCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("Abc Def Gh Ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(true, false, null, "-");
        var result = titleCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("Abc Def Ghi");

        opts = new Options(true, false, null, "_");
        result = titleCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("Abc _def _ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(true, false, null, "_");
        var result = titleCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("Abc Def Ghi");

        opts = new Options(true, false, null, "-");
        result = titleCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("Abc -def -ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(true, false, null, "_");
        var result = titleCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("Abc Def Ghi");

        opts = new Options(true, false, null, "-");
        result = titleCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("Abc - Def - Ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(true, false, null, "-");
        var result = titleCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("Abc Def Ghi");

        opts = new Options(true, false, null, "_");
        result = titleCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("Abc _def _ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(true, false, null, "_");
        var result = titleCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("Abc Def Ghi");

        opts = new Options(true, false, null, "-");
        result = titleCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("Abc -def -ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(true, false, null, "_");
        var result = titleCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc 123 456def G 89hi Jkl Mn 12");

        opts = new Options(true, false, null, "-");
        result = titleCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc 123-456def G 89hi Jkl Mn 12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(true, false, null, ".~!#%?");
        var result = titleCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo(".abc ~! Def # Ghi % Jk Lm No ?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(true, false, null, "-");
        var result = titleCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123abc 456def");

        result = titleCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123abc 456def");

        result = titleCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123 Abc 456 Def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(true, false, null, "-_");
        var result = titleCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsAsTailOfWordAndWithKeptCharacters {
      @Test
      void convertCamelCase() {
        var opts = new Options(false, true, null, "-_");
        var result = titleCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("Abc Def Gh Ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(false, true, "", "-_");
        var result = titleCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("Abc Def Gh Ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(false, true, null, "-");
        var result = titleCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("Abc Def Ghi");

        opts = new Options(false, true, null, "_");
        result = titleCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("Abc_ Def_ Ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(false, true, null, "_");
        var result = titleCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("Abc Def Ghi");

        opts = new Options(false, true, null, "-");
        result = titleCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("Abc- Def- Ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(false, true, null, "_");
        var result = titleCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("Abc Def Ghi");

        opts = new Options(false, true, null, "-");
        result = titleCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("Abc- Def- Ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(false, true, null, "-");
        var result = titleCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("Abc Def Ghi");

        opts = new Options(false, true, null, "_");
        result = titleCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("Abc_ Def_ Ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(false, true, null, "_");
        var result = titleCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("Abc Def Ghi");

        opts = new Options(false, true, null, "-");
        result = titleCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("Abc- Def- Ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(false, true, null, "_");
        var result = titleCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc123 456 Def G89 Hi Jkl Mn12");

        opts = new Options(false, true, null, "-");
        result = titleCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc123-456 Def G89 Hi Jkl Mn12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(false, true, null, ".~!#%?");
        var result = titleCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo(". Abc~! Def# Ghi% Jk Lm No ?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(false, true, null, "_");
        var result = titleCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123 Abc456 Def");

        result = titleCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123 Abc456 Def");

        result = titleCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123 Abc456 Def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(false, true, null, "-_");
        var result = titleCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsAsWordAndWithKeptCharacters {
      @Test
      void convertCamelCase() {
        var opts = new Options(true, true, null, "-_");
        var result = titleCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("Abc Def Gh Ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(true, true, "", "-_");
        var result = titleCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("Abc Def Gh Ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(true, true, null, "-");
        var result = titleCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("Abc Def Ghi");

        opts = new Options(true, true, null, "_");
        result = titleCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("Abc _ Def _ Ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(true, true, null, "_");
        var result = titleCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("Abc Def Ghi");

        opts = new Options(true, true, null, "-");
        result = titleCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("Abc - Def - Ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(true, true, null, "_");
        var result = titleCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("Abc Def Ghi");

        opts = new Options(true, true, null, "-");
        result = titleCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("Abc - Def - Ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(true, true, null, "-");
        var result = titleCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("Abc Def Ghi");

        opts = new Options(true, true, null, "_");
        result = titleCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("Abc _ Def _ Ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(true, true, null, "_");
        var result = titleCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("Abc Def Ghi");

        opts = new Options(true, true, null, "-");
        result = titleCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("Abc - Def - Ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(true, true, null, "_");
        var result = titleCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc 123 456 Def G 89 Hi Jkl Mn 12");

        opts = new Options(true, true, null, "-");
        result = titleCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc 123-456 Def G 89 Hi Jkl Mn 12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(true, true, null, ".~!#%?");
        var result = titleCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo(". Abc ~! Def # Ghi % Jk Lm No ?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(true, true, null, "-_");
        var result = titleCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123 Abc 456 Def");

        result = titleCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123 Abc 456 Def");

        result = titleCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123 Abc 456 Def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(true, true, null, "-_");
        var result = titleCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsAsPartOfWordAndWithKeptCharacters {
      @Test
      void convertCamelCase() {
        var opts = new Options(false, false, null, "-_");
        var result = titleCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("Abc Def Gh Ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(false, false, "", "-_");
        var result = titleCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("Abc Def Gh Ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(false, false, null, "-");
        var result = titleCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("Abc Def Ghi");

        opts = new Options(false, false, null, "_");
        result = titleCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("Abc_def_ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(false, false, null, "_");
        var result = titleCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("Abc Def Ghi");

        opts = new Options(false, false, null, "-");
        result = titleCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("Abc-def-ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(false, false, null, "_");
        var result = titleCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("Abc Def Ghi");

        opts = new Options(false, false, null, "-");
        result = titleCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("Abc- Def- Ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(false, false, null, "-");
        var result = titleCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("Abc Def Ghi");

        opts = new Options(false, false, null, "_");
        result = titleCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("Abc_def_ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(false, false, null, "_");
        var result = titleCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("Abc Def Ghi");

        opts = new Options(false, false, null, "-");
        result = titleCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("Abc-def-ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(false, false, null, "_");
        var result = titleCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc123 456def G89hi Jkl Mn12");

        opts = new Options(false, false, null, "-");
        result = titleCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc123-456def G89hi Jkl Mn12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(false, false, null, ".~!#%?");
        var result = titleCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo(".abc~! Def# Ghi% Jk Lm No ?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(false, false, null, "-_");
        var result = titleCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123abc456def");

        result = titleCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123abc456def");

        result = titleCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123 Abc456 Def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(false, false, null, "-_");
        var result = titleCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }
    }
  }
}
