package com.github.sttk.stringcase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import static com.github.sttk.stringcase.StringCase.*;

import static java.lang.Character.codePointAt;

@SuppressWarnings("missing-explicit-ctor")
public class StringCase_MacroCaseTest {

  @Nested
  class MacroCase {
    @Test
    void convertCamelCase() {
      var result = macroCase("abcDefGHIjk");
      assertThat(result).isEqualTo("ABC_DEF_GH_IJK");
    }

    @Test
    void convertPascalCase() {
      var result = macroCase("AbcDefGHIjk");
      assertThat(result).isEqualTo("ABC_DEF_GH_IJK");
    }

    @Test
    void convertSnakeCase() {
      var result = macroCase("abc_def_ghi");
      assertThat(result).isEqualTo("ABC_DEF_GHI");
    }

    @Test
    void convertKebabCase() {
      var result = macroCase("abc-def-ghi");
      assertThat(result).isEqualTo("ABC_DEF_GHI");
    }

    @Test
    void convertTrainCase() {
      var result = macroCase("Abc-Def-Ghi");
      assertThat(result).isEqualTo("ABC_DEF_GHI");
    }

    @Test
    void convertMacroCase() {
      var result = macroCase("ABC_DEF_GHI");
      assertThat(result).isEqualTo("ABC_DEF_GHI");
    }

    @Test
    void convertCobolCase() {
      var result = macroCase("ABC-DEF-GHI");
      assertThat(result).isEqualTo("ABC_DEF_GHI");
    }

    @Test
    void convertWithKeepingDigits() {
      var result = macroCase("abc123-456defG89HIJklMN12");
      assertThat(result).isEqualTo("ABC123_456_DEF_G89_HI_JKL_MN12");
    }

    @Test
    void convertWithSymbolsAsSeparators() {
   		var result = macroCase(":.abc~!@def#$ghi%&jk(lm)no/?");
  		assertThat(result).isEqualTo("ABC_DEF_GHI_JK_LM_NO");
    }

    @Test
    void convertWhenStartingWithDigit() {
		  var result = macroCase("123abc456def");
		  assertThat(result).isEqualTo("123_ABC456_DEF");

		  result = macroCase("123ABC456DEF");
		  assertThat(result).isEqualTo("123_ABC456_DEF");

		  result = macroCase("123Abc456Def");
		  assertThat(result).isEqualTo("123_ABC456_DEF");
    }

    @Test
    void convertAnEmptyString() {
		  var result = macroCase("");
		  assertThat(result).isEqualTo("");
    }
  }

  @Nested
  class MacroCaseWithOptions {
    @Nested
    class NonAlphabetsAsHeadOfWord {
      @Test
      void convertCamelCase() {
        var opts = new Options(true, false, null, null);
        var result = macroCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("ABC_DEF_GH_IJK");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(true, false, "", "");
        var result = macroCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("ABC_DEF_GH_IJK");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(true, false, null, null);
        var result = macroCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("ABC_DEF_GHI");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(true, false, null, null);
        var result = macroCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("ABC_DEF_GHI");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(true, false, null, null);
        var result = macroCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("ABC_DEF_GHI");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(true, false, null, null);
        var result = macroCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("ABC_DEF_GHI");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(true, false, null, null);
        var result = macroCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("ABC_DEF_GHI");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(true, false, null, null);
        var result = macroCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("ABC_123_456DEF_G_89HI_JKL_MN_12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(true, false, null, null);
        var result = macroCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo("ABC_DEF_GHI_JK_LM_NO");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(true, false, null, null);
        var result = macroCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123ABC_456DEF");

        result = macroCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123ABC_456DEF");

        result = macroCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123_ABC_456_DEF");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(true, false, null, null);
        var result = macroCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsAsTailOfWord {
      @Test
      void convertCamelCase() {
        var opts = new Options(false, true, null, null);
        var result = macroCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("ABC_DEF_GH_IJK");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(false, true, "", "");
        var result = macroCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("ABC_DEF_GH_IJK");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(false, true, null, null);
        var result = macroCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("ABC_DEF_GHI");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(false, true, null, null);
        var result = macroCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("ABC_DEF_GHI");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(false, true, null, null);
        var result = macroCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("ABC_DEF_GHI");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(false, true, null, null);
        var result = macroCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("ABC_DEF_GHI");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(false, true, null, null);
        var result = macroCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("ABC_DEF_GHI");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(false, true, null, null);
        var result = macroCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("ABC123_456_DEF_G89_HI_JKL_MN12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(false, true, null, null);
        var result = macroCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo("ABC_DEF_GHI_JK_LM_NO");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(false, true, null, null);
        var result = macroCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123_ABC456_DEF");

        result = macroCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123_ABC456_DEF");

        result = macroCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123_ABC456_DEF");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(false, true, null, null);
        var result = macroCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsAsWord {
      @Test
      void convertCamelCase() {
        var opts = new Options(true, true, null, null);
        var result = macroCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("ABC_DEF_GH_IJK");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(true, true, "", "");
        var result = macroCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("ABC_DEF_GH_IJK");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(true, true, null, null);
        var result = macroCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("ABC_DEF_GHI");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(true, true, null, null);
        var result = macroCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("ABC_DEF_GHI");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(true, true, null, null);
        var result = macroCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("ABC_DEF_GHI");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(true, true, null, null);
        var result = macroCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("ABC_DEF_GHI");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(true, true, null, null);
        var result = macroCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("ABC_DEF_GHI");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(true, true, null, null);
        var result = macroCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("ABC_123_456_DEF_G_89_HI_JKL_MN_12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(true, true, null, null);
        var result = macroCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo("ABC_DEF_GHI_JK_LM_NO");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(true, true, null, null);
        var result = macroCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123_ABC_456_DEF");

        result = macroCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123_ABC_456_DEF");

        result = macroCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123_ABC_456_DEF");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(true, true, null, null);
        var result = macroCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsPartAsWord {
      @Test
      void convertCamelCase() {
        var opts = new Options(false, false, null, null);
        var result = macroCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("ABC_DEF_GH_IJK");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(false, false, "", "");
        var result = macroCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("ABC_DEF_GH_IJK");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(false, false, null, null);
        var result = macroCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("ABC_DEF_GHI");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(false, false, null, null);
        var result = macroCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("ABC_DEF_GHI");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(false, false, null, null);
        var result = macroCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("ABC_DEF_GHI");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(false, false, null, null);
        var result = macroCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("ABC_DEF_GHI");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(false, false, null, null);
        var result = macroCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("ABC_DEF_GHI");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(false, false, null, null);
        var result = macroCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("ABC123_456DEF_G89HI_JKL_MN12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(false, false, null, null);
        var result = macroCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo("ABC_DEF_GHI_JK_LM_NO");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(false, false, null, null);
        var result = macroCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123ABC456DEF");

        result = macroCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123ABC456DEF");

        result = macroCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123_ABC456_DEF");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(false, false, null, null);
        var result = macroCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsAsHeadOfWordAndWithSeparators {
      @Test
      void convertCamelCase() {
        var opts = new Options(true, false, "-_", null);
        var result = macroCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("ABC_DEF_GH_IJK");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(true, false, "-_", "");
        var result = macroCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("ABC_DEF_GH_IJK");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(true, false, "_", null);
        var result = macroCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("ABC_DEF_GHI");

        opts = new Options(true, false, "-", null);
        result = macroCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("ABC__DEF__GHI");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(true, false, "-", null);
        var result = macroCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("ABC_DEF_GHI");

        opts = new Options(true, false, "_", null);
        result = macroCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("ABC_-DEF_-GHI");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(true, false, "-", null);
        var result = macroCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("ABC_DEF_GHI");

        opts = new Options(true, false, "_", null);
        result = macroCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("ABC_-_DEF_-_GHI");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(true, false, "_", null);
        var result = macroCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("ABC_DEF_GHI");

        opts = new Options(true, false, "-", null);
        result = macroCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("ABC__DEF__GHI");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(true, false, "-", null);
        var result = macroCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("ABC_DEF_GHI");

        opts = new Options(true, false, "_", null);
        result = macroCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("ABC_-DEF_-GHI");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(true, false, "-", null);
        var result = macroCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("ABC_123_456DEF_G_89HI_JKL_MN_12");

        opts = new Options(true, false, "_", null);
        result = macroCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("ABC_123-456DEF_G_89HI_JKL_MN_12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(true, false, ":@$&()/", null);
        var result = macroCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo(".ABC_~!_DEF_#_GHI_%_JK_LM_NO_?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(true, false, "-", null);
        var result = macroCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123ABC_456DEF");

        result = macroCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123ABC_456DEF");

        result = macroCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123_ABC_456_DEF");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(true, false, null, null);
        var result = macroCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }

      @Test
      void alphabetsAndNumbersInSeparatorsAreNoEffect() {
        var opts = new Options(true, false, "-b2", null);
        var result = macroCaseWithOptions("abc123def", opts);
        assertThat(result).isEqualTo("ABC_123DEF");
      }
    }

    @Nested
    class NonAlphabetsAsTailOfWordAndWithSeparators {
      @Test
      void convertCamelCase() {
        var opts = new Options(false, true, "-_", null);
        var result = macroCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("ABC_DEF_GH_IJK");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(false, true, "-_", "");
        var result = macroCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("ABC_DEF_GH_IJK");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(false, true, "_", null);
        var result = macroCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("ABC_DEF_GHI");

        opts = new Options(false, true, "-", null);
        result = macroCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("ABC__DEF__GHI");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(false, true, "-", null);
        var result = macroCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("ABC_DEF_GHI");

        opts = new Options(false, true, "_", null);
        result = macroCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("ABC-_DEF-_GHI");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(false, true, "-", null);
        var result = macroCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("ABC_DEF_GHI");

        opts = new Options(false, true, "_", null);
        result = macroCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("ABC-_DEF-_GHI");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(false, true, "_", null);
        var result = macroCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("ABC_DEF_GHI");

        opts = new Options(false, true, "-", null);
        result = macroCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("ABC__DEF__GHI");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(false, true, "-", null);
        var result = macroCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("ABC_DEF_GHI");

        opts = new Options(false, true, "_", null);
        result = macroCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("ABC-_DEF-_GHI");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(false, true, "-", null);
        var result = macroCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("ABC123_456_DEF_G89_HI_JKL_MN12");

        opts = new Options(false, true, "_", null);
        result = macroCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("ABC123-456_DEF_G89_HI_JKL_MN12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(false, true, ":@$&()/", null);
        var result = macroCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo("._ABC~!_DEF#_GHI%_JK_LM_NO_?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(false, true, "-", null);
        var result = macroCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123_ABC456_DEF");

        result = macroCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123_ABC456_DEF");

        result = macroCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123_ABC456_DEF");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(false, true, "-_", null);
        var result = macroCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }

      @Test
      void alphabetsAndNumbersInSeparatorsAreNoEffect() {
        var opts = new Options(false, true, "-b2", null);
        var result = macroCaseWithOptions("abc123def", opts);
        assertThat(result).isEqualTo("ABC123_DEF");
      }
    }

    @Nested
    class NonAlphabetsAsWordAndWithSeparators {
      @Test
      void convertCamelCase() {
        var opts = new Options(true, true, "-_", null);
        var result = macroCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("ABC_DEF_GH_IJK");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(true, true, "-_", "");
        var result = macroCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("ABC_DEF_GH_IJK");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(true, true, "_", null);
        var result = macroCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("ABC_DEF_GHI");

        opts = new Options(true, true, "-", null);
        result = macroCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("ABC___DEF___GHI");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(true, true, "-", null);
        var result = macroCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("ABC_DEF_GHI");

        opts = new Options(true, true, "_", null);
        result = macroCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("ABC_-_DEF_-_GHI");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(true, true, "-", null);
        var result = macroCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("ABC_DEF_GHI");

        opts = new Options(true, true, "_", null);
        result = macroCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("ABC_-_DEF_-_GHI");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(true, true, "_", null);
        var result = macroCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("ABC_DEF_GHI");

        opts = new Options(true, true, "-", null);
        result = macroCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("ABC___DEF___GHI");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(true, true, "-", null);
        var result = macroCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("ABC_DEF_GHI");

        opts = new Options(true, true, "_", null);
        result = macroCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("ABC_-_DEF_-_GHI");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(true, true, "-", null);
        var result = macroCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("ABC_123_456_DEF_G_89_HI_JKL_MN_12");

        opts = new Options(true, true, "_", null);
        result = macroCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("ABC_123-456_DEF_G_89_HI_JKL_MN_12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(true, true, ":@$&()/", null);
        var result = macroCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo("._ABC_~!_DEF_#_GHI_%_JK_LM_NO_?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(true, true, "-_", null);
        var result = macroCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123_ABC_456_DEF");

        result = macroCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123_ABC_456_DEF");

        result = macroCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123_ABC_456_DEF");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(true, true, "-_", null);
        var result = macroCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }

      @Test
      void alphabetsAndNumbersInSeparatorsAreNoEffect() {
        var opts = new Options(true, true, "-b2", null);
        var result = macroCaseWithOptions("abc123def", opts);
        assertThat(result).isEqualTo("ABC_123_DEF");
      }
    }

    @Nested
    class NonAlphabetsAsPartOfWordAndWithSeparators {
      @Test
      void convertCamelCase() {
        var opts = new Options(false, false, "-_", null);
        var result = macroCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("ABC_DEF_GH_IJK");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(false, false, "-_", "");
        var result = macroCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("ABC_DEF_GH_IJK");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(false, false, "_", null);
        var result = macroCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("ABC_DEF_GHI");

        opts = new Options(false, false, "-", null);
        result = macroCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("ABC_DEF_GHI");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(false, false, "-", null);
        var result = macroCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("ABC_DEF_GHI");

        opts = new Options(false, false, "_", null);
        result = macroCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("ABC-DEF-GHI");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(false, false, "-", null);
        var result = macroCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("ABC_DEF_GHI");

        opts = new Options(false, false, "_", null);
        result = macroCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("ABC-_DEF-_GHI");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(false, false, "_", null);
        var result = macroCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("ABC_DEF_GHI");

        opts = new Options(false, false, "-", null);
        result = macroCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("ABC_DEF_GHI");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(false, false, "-", null);
        var result = macroCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("ABC_DEF_GHI");

        opts = new Options(false, false, "_", null);
        result = macroCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("ABC-DEF-GHI");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(false, false, "-", null);
        var result = macroCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("ABC123_456DEF_G89HI_JKL_MN12");

        opts = new Options(false, false, "_", null);
        result = macroCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("ABC123-456DEF_G89HI_JKL_MN12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(false, false, ":@$&()/", null);
        var result = macroCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo(".ABC~!_DEF#_GHI%_JK_LM_NO_?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(false, false, "-_", null);
        var result = macroCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123ABC456DEF");

        result = macroCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123ABC456DEF");

        result = macroCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123_ABC456_DEF");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(false, false, "-_", null);
        var result = macroCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }

      @Test
      void alphabetsAndNumbersInSeparatorsAreNoEffect() {
        var opts = new Options(false, false, "-b2", null);
        var result = macroCaseWithOptions("abc123def", opts);
        assertThat(result).isEqualTo("ABC123DEF");
      }
    }

    @Nested
    class NonAlphabetsAsHeadOfWordAndWithKeptCharacters {
      @Test
      void convertCamelCase() {
        var opts = new Options(true, false, null, "-_");
        var result = macroCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("ABC_DEF_GH_IJK");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(true, false, "", "-_");
        var result = macroCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("ABC_DEF_GH_IJK");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(true, false, null, "-");
        var result = macroCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("ABC_DEF_GHI");

        opts = new Options(true, false, null, "_");
        result = macroCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("ABC__DEF__GHI");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(true, false, null, "_");
        var result = macroCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("ABC_DEF_GHI");

        opts = new Options(true, false, null, "-");
        result = macroCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("ABC_-DEF_-GHI");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(true, false, null, "_");
        var result = macroCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("ABC_DEF_GHI");

        opts = new Options(true, false, null, "-");
        result = macroCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("ABC_-_DEF_-_GHI");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(true, false, null, "-");
        var result = macroCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("ABC_DEF_GHI");

        opts = new Options(true, false, null, "_");
        result = macroCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("ABC__DEF__GHI");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(true, false, null, "_");
        var result = macroCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("ABC_DEF_GHI");

        opts = new Options(true, false, null, "-");
        result = macroCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("ABC_-DEF_-GHI");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(true, false, null, "_");
        var result = macroCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("ABC_123_456DEF_G_89HI_JKL_MN_12");

        opts = new Options(true, false, null, "-");
        result = macroCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("ABC_123-456DEF_G_89HI_JKL_MN_12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(true, false, null, ".~!#%?");
        var result = macroCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo(".ABC_~!_DEF_#_GHI_%_JK_LM_NO_?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(true, false, null, "-");
        var result = macroCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123ABC_456DEF");

        result = macroCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123ABC_456DEF");

        result = macroCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123_ABC_456_DEF");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(true, false, null, "-_");
        var result = macroCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsAsTailOfWordAndWithKeptCharacters {
      @Test
      void convertCamelCase() {
        var opts = new Options(false, true, null, "-_");
        var result = macroCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("ABC_DEF_GH_IJK");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(false, true, "", "-_");
        var result = macroCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("ABC_DEF_GH_IJK");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(false, true, null, "-");
        var result = macroCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("ABC_DEF_GHI");

        opts = new Options(false, true, null, "_");
        result = macroCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("ABC__DEF__GHI");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(false, true, null, "_");
        var result = macroCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("ABC_DEF_GHI");

        opts = new Options(false, true, null, "-");
        result = macroCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("ABC-_DEF-_GHI");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(false, true, null, "_");
        var result = macroCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("ABC_DEF_GHI");

        opts = new Options(false, true, null, "-");
        result = macroCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("ABC-_DEF-_GHI");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(false, true, null, "-");
        var result = macroCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("ABC_DEF_GHI");

        opts = new Options(false, true, null, "_");
        result = macroCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("ABC__DEF__GHI");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(false, true, null, "_");
        var result = macroCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("ABC_DEF_GHI");

        opts = new Options(false, true, null, "-");
        result = macroCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("ABC-_DEF-_GHI");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(false, true, null, "_");
        var result = macroCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("ABC123_456_DEF_G89_HI_JKL_MN12");

        opts = new Options(false, true, null, "-");
        result = macroCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("ABC123-456_DEF_G89_HI_JKL_MN12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(false, true, null, ".~!#%?");
        var result = macroCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo("._ABC~!_DEF#_GHI%_JK_LM_NO_?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(false, true, null, "_");
        var result = macroCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123_ABC456_DEF");

        result = macroCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123_ABC456_DEF");

        result = macroCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123_ABC456_DEF");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(false, true, null, "-_");
        var result = macroCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsAsWordAndWithKeptCharacters {
      @Test
      void convertCamelCase() {
        var opts = new Options(true, true, null, "-_");
        var result = macroCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("ABC_DEF_GH_IJK");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(true, true, "", "-_");
        var result = macroCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("ABC_DEF_GH_IJK");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(true, true, null, "-");
        var result = macroCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("ABC_DEF_GHI");

        opts = new Options(true, true, null, "_");
        result = macroCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("ABC___DEF___GHI");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(true, true, null, "_");
        var result = macroCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("ABC_DEF_GHI");

        opts = new Options(true, true, null, "-");
        result = macroCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("ABC_-_DEF_-_GHI");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(true, true, null, "_");
        var result = macroCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("ABC_DEF_GHI");

        opts = new Options(true, true, null, "-");
        result = macroCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("ABC_-_DEF_-_GHI");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(true, true, null, "-");
        var result = macroCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("ABC_DEF_GHI");

        opts = new Options(true, true, null, "_");
        result = macroCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("ABC___DEF___GHI");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(true, true, null, "_");
        var result = macroCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("ABC_DEF_GHI");

        opts = new Options(true, true, null, "-");
        result = macroCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("ABC_-_DEF_-_GHI");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(true, true, null, "_");
        var result = macroCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("ABC_123_456_DEF_G_89_HI_JKL_MN_12");

        opts = new Options(true, true, null, "-");
        result = macroCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("ABC_123-456_DEF_G_89_HI_JKL_MN_12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(true, true, null, ".~!#%?");
        var result = macroCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo("._ABC_~!_DEF_#_GHI_%_JK_LM_NO_?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(true, true, null, "-_");
        var result = macroCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123_ABC_456_DEF");

        result = macroCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123_ABC_456_DEF");

        result = macroCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123_ABC_456_DEF");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(true, true, null, "-_");
        var result = macroCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsAsPartOfWordAndWithKeptCharacters {
      @Test
      void convertCamelCase() {
        var opts = new Options(false, false, null, "-_");
        var result = macroCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("ABC_DEF_GH_IJK");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(false, false, "", "-_");
        var result = macroCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("ABC_DEF_GH_IJK");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(false, false, null, "-");
        var result = macroCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("ABC_DEF_GHI");

        opts = new Options(false, false, null, "_");
        result = macroCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("ABC_DEF_GHI");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(false, false, null, "_");
        var result = macroCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("ABC_DEF_GHI");

        opts = new Options(false, false, null, "-");
        result = macroCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("ABC-DEF-GHI");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(false, false, null, "_");
        var result = macroCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("ABC_DEF_GHI");

        opts = new Options(false, false, null, "-");
        result = macroCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("ABC-_DEF-_GHI");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(false, false, null, "-");
        var result = macroCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("ABC_DEF_GHI");

        opts = new Options(false, false, null, "_");
        result = macroCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("ABC_DEF_GHI");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(false, false, null, "_");
        var result = macroCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("ABC_DEF_GHI");

        opts = new Options(false, false, null, "-");
        result = macroCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("ABC-DEF-GHI");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(false, false, null, "_");
        var result = macroCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("ABC123_456DEF_G89HI_JKL_MN12");

        opts = new Options(false, false, null, "-");
        result = macroCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("ABC123-456DEF_G89HI_JKL_MN12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(false, false, null, ".~!#%?");
        var result = macroCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo(".ABC~!_DEF#_GHI%_JK_LM_NO_?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(false, false, null, "-_");
        var result = macroCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123ABC456DEF");

        result = macroCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123ABC456DEF");

        result = macroCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123_ABC456_DEF");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(false, false, null, "-_");
        var result = macroCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }
    }
  }
}
