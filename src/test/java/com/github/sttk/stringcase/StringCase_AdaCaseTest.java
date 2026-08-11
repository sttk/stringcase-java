package com.github.sttk.stringcase;

import static com.github.sttk.stringcase.StringCase.*;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@SuppressWarnings("missing-explicit-ctor")
public class StringCase_AdaCaseTest {

  @Nested
  class AdaCase {
    @Test
    void convertCamelCase() {
      var result = adaCase("abcDefGHIjk");
      assertThat(result).isEqualTo("Abc_Def_Gh_Ijk");
    }

    @Test
    void convertPascalCase() {
      var result = adaCase("AbcDefGHIjk");
      assertThat(result).isEqualTo("Abc_Def_Gh_Ijk");
    }

    @Test
    void convertSnakeCase() {
      var result = adaCase("abc_def_ghi");
      assertThat(result).isEqualTo("Abc_Def_Ghi");
    }

    @Test
    void convertKebabCase() {
      var result = adaCase("abc-def-ghi");
      assertThat(result).isEqualTo("Abc_Def_Ghi");
    }

    @Test
    void convertTrainCase() {
      var result = adaCase("Abc-Def-Ghi");
      assertThat(result).isEqualTo("Abc_Def_Ghi");
    }

    @Test
    void convertMacroCase() {
      var result = adaCase("ABC_DEF_GHI");
      assertThat(result).isEqualTo("Abc_Def_Ghi");
    }

    @Test
    void convertCobolCase() {
      var result = adaCase("ABC-DEF-GHI");
      assertThat(result).isEqualTo("Abc_Def_Ghi");
    }

    @Test
    void convertWithKeepingDigits() {
      var result = adaCase("abc123-456defG89HIJklMN12");
      assertThat(result).isEqualTo("Abc123_456_Def_G89_Hi_Jkl_Mn12");
    }

    @Test
    void convertWithSymbolsAsSeparators() {
      var result = adaCase(":.abc~!@def#$ghi%&jk(lm)no/?");
      assertThat(result).isEqualTo("Abc_Def_Ghi_Jk_Lm_No");
    }

    @Test
    void convertWhenStartingWithDigit() {
      var result = adaCase("123abc456def");
      assertThat(result).isEqualTo("123_Abc456_Def");

      result = adaCase("123ABC456DEF");
      assertThat(result).isEqualTo("123_Abc456_Def");

      result = adaCase("123Abc456Def");
      assertThat(result).isEqualTo("123_Abc456_Def");
    }

    @Test
    void convertAnEmptyString() {
      var result = adaCase("");
      assertThat(result).isEqualTo("");
    }
  }

  @Nested
  class AdaCaseWithOptions {
    @Nested
    class NonAlphabetsAsHeadOfWord {
      @Test
      void convertCamelCase() {
        var opts = new Options(true, false, null, null);
        var result = adaCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("Abc_Def_Gh_Ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(true, false, "", "");
        var result = adaCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("Abc_Def_Gh_Ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(true, false, null, null);
        var result = adaCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("Abc_Def_Ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(true, false, null, null);
        var result = adaCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("Abc_Def_Ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(true, false, null, null);
        var result = adaCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("Abc_Def_Ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(true, false, null, null);
        var result = adaCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("Abc_Def_Ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(true, false, null, null);
        var result = adaCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("Abc_Def_Ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(true, false, null, null);
        var result = adaCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc_123_456def_G_89hi_Jkl_Mn_12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(true, false, null, null);
        var result = adaCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo("Abc_Def_Ghi_Jk_Lm_No");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(true, false, null, null);
        var result = adaCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123abc_456def");

        result = adaCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123abc_456def");

        result = adaCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123_Abc_456_Def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(true, false, null, null);
        var result = adaCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsAsTailOfWord {
      @Test
      void convertCamelCase() {
        var opts = new Options(false, true, null, null);
        var result = adaCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("Abc_Def_Gh_Ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(false, true, "", "");
        var result = adaCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("Abc_Def_Gh_Ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(false, true, null, null);
        var result = adaCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("Abc_Def_Ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(false, true, null, null);
        var result = adaCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("Abc_Def_Ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(false, true, null, null);
        var result = adaCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("Abc_Def_Ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(false, true, null, null);
        var result = adaCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("Abc_Def_Ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(false, true, null, null);
        var result = adaCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("Abc_Def_Ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(false, true, null, null);
        var result = adaCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc123_456_Def_G89_Hi_Jkl_Mn12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(false, true, null, null);
        var result = adaCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo("Abc_Def_Ghi_Jk_Lm_No");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(false, true, null, null);
        var result = adaCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123_Abc456_Def");

        result = adaCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123_Abc456_Def");

        result = adaCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123_Abc456_Def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(false, true, null, null);
        var result = adaCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsAsWord {
      @Test
      void convertCamelCase() {
        var opts = new Options(true, true, null, null);
        var result = adaCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("Abc_Def_Gh_Ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(true, true, "", "");
        var result = adaCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("Abc_Def_Gh_Ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(true, true, null, null);
        var result = adaCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("Abc_Def_Ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(true, true, null, null);
        var result = adaCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("Abc_Def_Ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(true, true, null, null);
        var result = adaCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("Abc_Def_Ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(true, true, null, null);
        var result = adaCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("Abc_Def_Ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(true, true, null, null);
        var result = adaCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("Abc_Def_Ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(true, true, null, null);
        var result = adaCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc_123_456_Def_G_89_Hi_Jkl_Mn_12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(true, true, null, null);
        var result = adaCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo("Abc_Def_Ghi_Jk_Lm_No");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(true, true, null, null);
        var result = adaCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123_Abc_456_Def");

        result = adaCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123_Abc_456_Def");

        result = adaCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123_Abc_456_Def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(true, true, null, null);
        var result = adaCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsPartAsWord {
      @Test
      void convertCamelCase() {
        var opts = new Options(false, false, null, null);
        var result = adaCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("Abc_Def_Gh_Ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(false, false, "", "");
        var result = adaCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("Abc_Def_Gh_Ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(false, false, null, null);
        var result = adaCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("Abc_Def_Ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(false, false, null, null);
        var result = adaCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("Abc_Def_Ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(false, false, null, null);
        var result = adaCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("Abc_Def_Ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(false, false, null, null);
        var result = adaCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("Abc_Def_Ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(false, false, null, null);
        var result = adaCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("Abc_Def_Ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(false, false, null, null);
        var result = adaCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc123_456def_G89hi_Jkl_Mn12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(false, false, null, null);
        var result = adaCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo("Abc_Def_Ghi_Jk_Lm_No");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(false, false, null, null);
        var result = adaCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123abc456def");

        result = adaCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123abc456def");

        result = adaCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123_Abc456_Def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(false, false, null, null);
        var result = adaCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsAsHeadOfWordAndWithSeparators {
      @Test
      void convertCamelCase() {
        var opts = new Options(true, false, "-_", null);
        var result = adaCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("Abc_Def_Gh_Ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(true, false, "-_", "");
        var result = adaCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("Abc_Def_Gh_Ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(true, false, "_", null);
        var result = adaCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("Abc_Def_Ghi");

        opts = new Options(true, false, "-", null);
        result = adaCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("Abc__def__ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(true, false, "-", null);
        var result = adaCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("Abc_Def_Ghi");

        opts = new Options(true, false, "_", null);
        result = adaCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("Abc_-def_-ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(true, false, "-", null);
        var result = adaCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("Abc_Def_Ghi");

        opts = new Options(true, false, "_", null);
        result = adaCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("Abc_-_Def_-_Ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(true, false, "_", null);
        var result = adaCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("Abc_Def_Ghi");

        opts = new Options(true, false, "-", null);
        result = adaCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("Abc__def__ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(true, false, "-", null);
        var result = adaCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("Abc_Def_Ghi");

        opts = new Options(true, false, "_", null);
        result = adaCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("Abc_-def_-ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(true, false, "-", null);
        var result = adaCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc_123_456def_G_89hi_Jkl_Mn_12");

        opts = new Options(true, false, "_", null);
        result = adaCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc_123-456def_G_89hi_Jkl_Mn_12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(true, false, ":@$&()/", null);
        var result = adaCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo(".abc_~!_Def_#_Ghi_%_Jk_Lm_No_?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(true, false, "-", null);
        var result = adaCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123abc_456def");

        result = adaCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123abc_456def");

        result = adaCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123_Abc_456_Def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(true, false, null, null);
        var result = adaCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }

      @Test
      void alphabetsAndNumbersInSeparatorsAreNoEffect() {
        var opts = new Options(true, false, "-b2", null);
        var result = adaCaseWithOptions("abc123def", opts);
        assertThat(result).isEqualTo("Abc_123def");
      }
    }

    @Nested
    class NonAlphabetsAsTailOfWordAndWithSeparators {
      @Test
      void convertCamelCase() {
        var opts = new Options(false, true, "-_", null);
        var result = adaCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("Abc_Def_Gh_Ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(false, true, "-_", "");
        var result = adaCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("Abc_Def_Gh_Ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(false, true, "_", null);
        var result = adaCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("Abc_Def_Ghi");

        opts = new Options(false, true, "-", null);
        result = adaCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("Abc__Def__Ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(false, true, "-", null);
        var result = adaCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("Abc_Def_Ghi");

        opts = new Options(false, true, "_", null);
        result = adaCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("Abc-_Def-_Ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(false, true, "-", null);
        var result = adaCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("Abc_Def_Ghi");

        opts = new Options(false, true, "_", null);
        result = adaCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("Abc-_Def-_Ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(false, true, "_", null);
        var result = adaCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("Abc_Def_Ghi");

        opts = new Options(false, true, "-", null);
        result = adaCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("Abc__Def__Ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(false, true, "-", null);
        var result = adaCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("Abc_Def_Ghi");

        opts = new Options(false, true, "_", null);
        result = adaCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("Abc-_Def-_Ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(false, true, "-", null);
        var result = adaCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc123_456_Def_G89_Hi_Jkl_Mn12");

        opts = new Options(false, true, "_", null);
        result = adaCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc123-456_Def_G89_Hi_Jkl_Mn12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(false, true, ":@$&()/", null);
        var result = adaCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo("._Abc~!_Def#_Ghi%_Jk_Lm_No_?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(false, true, "-", null);
        var result = adaCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123_Abc456_Def");

        result = adaCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123_Abc456_Def");

        result = adaCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123_Abc456_Def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(false, true, "-_", null);
        var result = adaCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }

      @Test
      void alphabetsAndNumbersInSeparatorsAreNoEffect() {
        var opts = new Options(false, true, "-b2", null);
        var result = adaCaseWithOptions("abc123def", opts);
        assertThat(result).isEqualTo("Abc123_Def");
      }
    }

    @Nested
    class NonAlphabetsAsWordAndWithSeparators {
      @Test
      void convertCamelCase() {
        var opts = new Options(true, true, "-_", null);
        var result = adaCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("Abc_Def_Gh_Ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(true, true, "-_", "");
        var result = adaCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("Abc_Def_Gh_Ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(true, true, "_", null);
        var result = adaCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("Abc_Def_Ghi");

        opts = new Options(true, true, "-", null);
        result = adaCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("Abc___Def___Ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(true, true, "-", null);
        var result = adaCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("Abc_Def_Ghi");

        opts = new Options(true, true, "_", null);
        result = adaCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("Abc_-_Def_-_Ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(true, true, "-", null);
        var result = adaCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("Abc_Def_Ghi");

        opts = new Options(true, true, "_", null);
        result = adaCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("Abc_-_Def_-_Ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(true, true, "_", null);
        var result = adaCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("Abc_Def_Ghi");

        opts = new Options(true, true, "-", null);
        result = adaCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("Abc___Def___Ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(true, true, "-", null);
        var result = adaCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("Abc_Def_Ghi");

        opts = new Options(true, true, "_", null);
        result = adaCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("Abc_-_Def_-_Ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(true, true, "-", null);
        var result = adaCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc_123_456_Def_G_89_Hi_Jkl_Mn_12");

        opts = new Options(true, true, "_", null);
        result = adaCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc_123-456_Def_G_89_Hi_Jkl_Mn_12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(true, true, ":@$&()/", null);
        var result = adaCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo("._Abc_~!_Def_#_Ghi_%_Jk_Lm_No_?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(true, true, "-_", null);
        var result = adaCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123_Abc_456_Def");

        result = adaCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123_Abc_456_Def");

        result = adaCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123_Abc_456_Def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(true, true, "-_", null);
        var result = adaCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }

      @Test
      void alphabetsAndNumbersInSeparatorsAreNoEffect() {
        var opts = new Options(true, true, "-b2", null);
        var result = adaCaseWithOptions("abc123def", opts);
        assertThat(result).isEqualTo("Abc_123_Def");
      }
    }

    @Nested
    class NonAlphabetsAsPartOfWordAndWithSeparators {
      @Test
      void convertCamelCase() {
        var opts = new Options(false, false, "-_", null);
        var result = adaCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("Abc_Def_Gh_Ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(false, false, "-_", "");
        var result = adaCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("Abc_Def_Gh_Ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(false, false, "_", null);
        var result = adaCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("Abc_Def_Ghi");

        opts = new Options(false, false, "-", null);
        result = adaCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("Abc_def_ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(false, false, "-", null);
        var result = adaCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("Abc_Def_Ghi");

        opts = new Options(false, false, "_", null);
        result = adaCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("Abc-def-ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(false, false, "-", null);
        var result = adaCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("Abc_Def_Ghi");

        opts = new Options(false, false, "_", null);
        result = adaCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("Abc-_Def-_Ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(false, false, "_", null);
        var result = adaCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("Abc_Def_Ghi");

        opts = new Options(false, false, "-", null);
        result = adaCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("Abc_def_ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(false, false, "-", null);
        var result = adaCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("Abc_Def_Ghi");

        opts = new Options(false, false, "_", null);
        result = adaCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("Abc-def-ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(false, false, "-", null);
        var result = adaCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc123_456def_G89hi_Jkl_Mn12");

        opts = new Options(false, false, "_", null);
        result = adaCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc123-456def_G89hi_Jkl_Mn12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(false, false, ":@$&()/", null);
        var result = adaCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo(".abc~!_Def#_Ghi%_Jk_Lm_No_?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(false, false, "-_", null);
        var result = adaCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123abc456def");

        result = adaCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123abc456def");

        result = adaCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123_Abc456_Def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(false, false, "-_", null);
        var result = adaCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }

      @Test
      void alphabetsAndNumbersInSeparatorsAreNoEffect() {
        var opts = new Options(false, false, "-b2", null);
        var result = adaCaseWithOptions("abc123def", opts);
        assertThat(result).isEqualTo("Abc123def");
      }
    }

    @Nested
    class NonAlphabetsAsHeadOfWordAndWithKeptCharacters {
      @Test
      void convertCamelCase() {
        var opts = new Options(true, false, null, "-_");
        var result = adaCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("Abc_Def_Gh_Ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(true, false, "", "-_");
        var result = adaCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("Abc_Def_Gh_Ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(true, false, null, "-");
        var result = adaCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("Abc_Def_Ghi");

        opts = new Options(true, false, null, "_");
        result = adaCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("Abc__def__ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(true, false, null, "_");
        var result = adaCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("Abc_Def_Ghi");

        opts = new Options(true, false, null, "-");
        result = adaCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("Abc_-def_-ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(true, false, null, "_");
        var result = adaCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("Abc_Def_Ghi");

        opts = new Options(true, false, null, "-");
        result = adaCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("Abc_-_Def_-_Ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(true, false, null, "-");
        var result = adaCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("Abc_Def_Ghi");

        opts = new Options(true, false, null, "_");
        result = adaCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("Abc__def__ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(true, false, null, "_");
        var result = adaCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("Abc_Def_Ghi");

        opts = new Options(true, false, null, "-");
        result = adaCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("Abc_-def_-ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(true, false, null, "_");
        var result = adaCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc_123_456def_G_89hi_Jkl_Mn_12");

        opts = new Options(true, false, null, "-");
        result = adaCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc_123-456def_G_89hi_Jkl_Mn_12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(true, false, null, ".~!#%?");
        var result = adaCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo(".abc_~!_Def_#_Ghi_%_Jk_Lm_No_?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(true, false, null, "-");
        var result = adaCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123abc_456def");

        result = adaCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123abc_456def");

        result = adaCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123_Abc_456_Def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(true, false, null, "-_");
        var result = adaCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsAsTailOfWordAndWithKeptCharacters {
      @Test
      void convertCamelCase() {
        var opts = new Options(false, true, null, "-_");
        var result = adaCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("Abc_Def_Gh_Ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(false, true, "", "-_");
        var result = adaCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("Abc_Def_Gh_Ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(false, true, null, "-");
        var result = adaCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("Abc_Def_Ghi");

        opts = new Options(false, true, null, "_");
        result = adaCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("Abc__Def__Ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(false, true, null, "_");
        var result = adaCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("Abc_Def_Ghi");

        opts = new Options(false, true, null, "-");
        result = adaCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("Abc-_Def-_Ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(false, true, null, "_");
        var result = adaCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("Abc_Def_Ghi");

        opts = new Options(false, true, null, "-");
        result = adaCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("Abc-_Def-_Ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(false, true, null, "-");
        var result = adaCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("Abc_Def_Ghi");

        opts = new Options(false, true, null, "_");
        result = adaCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("Abc__Def__Ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(false, true, null, "_");
        var result = adaCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("Abc_Def_Ghi");

        opts = new Options(false, true, null, "-");
        result = adaCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("Abc-_Def-_Ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(false, true, null, "_");
        var result = adaCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc123_456_Def_G89_Hi_Jkl_Mn12");

        opts = new Options(false, true, null, "-");
        result = adaCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc123-456_Def_G89_Hi_Jkl_Mn12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(false, true, null, ".~!#%?");
        var result = adaCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo("._Abc~!_Def#_Ghi%_Jk_Lm_No_?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(false, true, null, "_");
        var result = adaCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123_Abc456_Def");

        result = adaCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123_Abc456_Def");

        result = adaCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123_Abc456_Def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(false, true, null, "-_");
        var result = adaCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsAsWordAndWithKeptCharacters {
      @Test
      void convertCamelCase() {
        var opts = new Options(true, true, null, "-_");
        var result = adaCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("Abc_Def_Gh_Ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(true, true, "", "-_");
        var result = adaCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("Abc_Def_Gh_Ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(true, true, null, "-");
        var result = adaCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("Abc_Def_Ghi");

        opts = new Options(true, true, null, "_");
        result = adaCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("Abc___Def___Ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(true, true, null, "_");
        var result = adaCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("Abc_Def_Ghi");

        opts = new Options(true, true, null, "-");
        result = adaCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("Abc_-_Def_-_Ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(true, true, null, "_");
        var result = adaCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("Abc_Def_Ghi");

        opts = new Options(true, true, null, "-");
        result = adaCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("Abc_-_Def_-_Ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(true, true, null, "-");
        var result = adaCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("Abc_Def_Ghi");

        opts = new Options(true, true, null, "_");
        result = adaCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("Abc___Def___Ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(true, true, null, "_");
        var result = adaCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("Abc_Def_Ghi");

        opts = new Options(true, true, null, "-");
        result = adaCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("Abc_-_Def_-_Ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(true, true, null, "_");
        var result = adaCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc_123_456_Def_G_89_Hi_Jkl_Mn_12");

        opts = new Options(true, true, null, "-");
        result = adaCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc_123-456_Def_G_89_Hi_Jkl_Mn_12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(true, true, null, ".~!#%?");
        var result = adaCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo("._Abc_~!_Def_#_Ghi_%_Jk_Lm_No_?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(true, true, null, "-_");
        var result = adaCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123_Abc_456_Def");

        result = adaCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123_Abc_456_Def");

        result = adaCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123_Abc_456_Def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(true, true, null, "-_");
        var result = adaCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsAsPartOfWordAndWithKeptCharacters {
      @Test
      void convertCamelCase() {
        var opts = new Options(false, false, null, "-_");
        var result = adaCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("Abc_Def_Gh_Ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(false, false, "", "-_");
        var result = adaCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("Abc_Def_Gh_Ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(false, false, null, "-");
        var result = adaCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("Abc_Def_Ghi");

        opts = new Options(false, false, null, "_");
        result = adaCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("Abc_def_ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(false, false, null, "_");
        var result = adaCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("Abc_Def_Ghi");

        opts = new Options(false, false, null, "-");
        result = adaCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("Abc-def-ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(false, false, null, "_");
        var result = adaCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("Abc_Def_Ghi");

        opts = new Options(false, false, null, "-");
        result = adaCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("Abc-_Def-_Ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(false, false, null, "-");
        var result = adaCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("Abc_Def_Ghi");

        opts = new Options(false, false, null, "_");
        result = adaCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("Abc_def_ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(false, false, null, "_");
        var result = adaCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("Abc_Def_Ghi");

        opts = new Options(false, false, null, "-");
        result = adaCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("Abc-def-ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(false, false, null, "_");
        var result = adaCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc123_456def_G89hi_Jkl_Mn12");

        opts = new Options(false, false, null, "-");
        result = adaCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc123-456def_G89hi_Jkl_Mn12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(false, false, null, ".~!#%?");
        var result = adaCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo(".abc~!_Def#_Ghi%_Jk_Lm_No_?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(false, false, null, "-_");
        var result = adaCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123abc456def");

        result = adaCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123abc456def");

        result = adaCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123_Abc456_Def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(false, false, null, "-_");
        var result = adaCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }
    }
  }
}
