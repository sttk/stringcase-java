package com.github.sttk.stringcase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import static com.github.sttk.stringcase.StringCase.*;

import static java.lang.Character.codePointAt;

@SuppressWarnings("missing-explicit-ctor")
public class StringCase_SnakeCaseTest {

  @Nested
  class SnakeCase {
    @Test
    void convertCamelCase() {
      var result = snakeCase("abcDefGHIjk");
      assertThat(result).isEqualTo("abc_def_gh_ijk");
    }

    @Test
    void convertPascalCase() {
      var result = snakeCase("AbcDefGHIjk");
      assertThat(result).isEqualTo("abc_def_gh_ijk");
    }

    @Test
    void convertSnakeCase() {
      var result = snakeCase("abc_def_ghi");
      assertThat(result).isEqualTo("abc_def_ghi");
    }

    @Test
    void convertKebabCase() {
      var result = snakeCase("abc-def-ghi");
      assertThat(result).isEqualTo("abc_def_ghi");
    }

    @Test
    void convertTrainCase() {
      var result = snakeCase("Abc-Def-Ghi");
      assertThat(result).isEqualTo("abc_def_ghi");
    }

    @Test
    void convertMacroCase() {
      var result = snakeCase("ABC_DEF_GHI");
      assertThat(result).isEqualTo("abc_def_ghi");
    }

    @Test
    void convertCobolCase() {
      var result = snakeCase("ABC-DEF-GHI");
      assertThat(result).isEqualTo("abc_def_ghi");
    }

    @Test
    void convertWithKeepingDigits() {
      var result = snakeCase("abc123-456defG89HIJklMN12");
      assertThat(result).isEqualTo("abc123_456_def_g89_hi_jkl_mn12");
    }

    @Test
    void convertWithSymbolsAsSeparators() {
   		var result = snakeCase(":.abc~!@def#$ghi%&jk(lm)no/?");
  		assertThat(result).isEqualTo("abc_def_ghi_jk_lm_no");
    }

    @Test
    void convertWhenStartingWithDigit() {
		  var result = snakeCase("123abc456def");
		  assertThat(result).isEqualTo("123_abc456_def");

		  result = snakeCase("123ABC456DEF");
		  assertThat(result).isEqualTo("123_abc456_def");

		  result = snakeCase("123Abc456Def");
		  assertThat(result).isEqualTo("123_abc456_def");
    }

    @Test
    void convertAnEmptyString() {
		  var result = snakeCase("");
		  assertThat(result).isEqualTo("");
    }
  }

  @Nested
  class SnakeCaseWithOptions {
    @Nested
    class NonAlphabetsAsHeadOfWord {
      @Test
      void convertCamelCase() {
        var opts = new Options(true, false, null, null);
        var result = snakeCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("abc_def_gh_ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(true, false, "", "");
        var result = snakeCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("abc_def_gh_ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(true, false, null, null);
        var result = snakeCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("abc_def_ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(true, false, null, null);
        var result = snakeCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("abc_def_ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(true, false, null, null);
        var result = snakeCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("abc_def_ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(true, false, null, null);
        var result = snakeCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("abc_def_ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(true, false, null, null);
        var result = snakeCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("abc_def_ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(true, false, null, null);
        var result = snakeCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("abc_123_456def_g_89hi_jkl_mn_12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(true, false, null, null);
        var result = snakeCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo("abc_def_ghi_jk_lm_no");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(true, false, null, null);
        var result = snakeCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123abc_456def");

        result = snakeCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123abc_456def");

        result = snakeCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123_abc_456_def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(true, false, null, null);
        var result = snakeCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsAsTailOfWord {
      @Test
      void convertCamelCase() {
        var opts = new Options(false, true, null, null);
        var result = snakeCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("abc_def_gh_ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(false, true, "", "");
        var result = snakeCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("abc_def_gh_ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(false, true, null, null);
        var result = snakeCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("abc_def_ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(false, true, null, null);
        var result = snakeCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("abc_def_ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(false, true, null, null);
        var result = snakeCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("abc_def_ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(false, true, null, null);
        var result = snakeCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("abc_def_ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(false, true, null, null);
        var result = snakeCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("abc_def_ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(false, true, null, null);
        var result = snakeCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("abc123_456_def_g89_hi_jkl_mn12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(false, true, null, null);
        var result = snakeCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo("abc_def_ghi_jk_lm_no");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(false, true, null, null);
        var result = snakeCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123_abc456_def");

        result = snakeCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123_abc456_def");

        result = snakeCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123_abc456_def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(false, true, null, null);
        var result = snakeCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsAsWord {
      @Test
      void convertCamelCase() {
        var opts = new Options(true, true, null, null);
        var result = snakeCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("abc_def_gh_ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(true, true, "", "");
        var result = snakeCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("abc_def_gh_ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(true, true, null, null);
        var result = snakeCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("abc_def_ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(true, true, null, null);
        var result = snakeCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("abc_def_ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(true, true, null, null);
        var result = snakeCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("abc_def_ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(true, true, null, null);
        var result = snakeCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("abc_def_ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(true, true, null, null);
        var result = snakeCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("abc_def_ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(true, true, null, null);
        var result = snakeCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("abc_123_456_def_g_89_hi_jkl_mn_12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(true, true, null, null);
        var result = snakeCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo("abc_def_ghi_jk_lm_no");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(true, true, null, null);
        var result = snakeCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123_abc_456_def");

        result = snakeCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123_abc_456_def");

        result = snakeCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123_abc_456_def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(true, true, null, null);
        var result = snakeCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsPartAsWord {
      @Test
      void convertCamelCase() {
        var opts = new Options(false, false, null, null);
        var result = snakeCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("abc_def_gh_ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(false, false, "", "");
        var result = snakeCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("abc_def_gh_ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(false, false, null, null);
        var result = snakeCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("abc_def_ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(false, false, null, null);
        var result = snakeCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("abc_def_ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(false, false, null, null);
        var result = snakeCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("abc_def_ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(false, false, null, null);
        var result = snakeCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("abc_def_ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(false, false, null, null);
        var result = snakeCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("abc_def_ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(false, false, null, null);
        var result = snakeCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("abc123_456def_g89hi_jkl_mn12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(false, false, null, null);
        var result = snakeCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo("abc_def_ghi_jk_lm_no");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(false, false, null, null);
        var result = snakeCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123abc456def");

        result = snakeCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123abc456def");

        result = snakeCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123_abc456_def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(false, false, null, null);
        var result = snakeCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsAsHeadOfWordAndWithSeparators {
      @Test
      void convertCamelCase() {
        var opts = new Options(true, false, "-_", null);
        var result = snakeCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("abc_def_gh_ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(true, false, "-_", "");
        var result = snakeCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("abc_def_gh_ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(true, false, "_", null);
        var result = snakeCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("abc_def_ghi");

        opts = new Options(true, false, "-", null);
        result = snakeCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("abc__def__ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(true, false, "-", null);
        var result = snakeCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("abc_def_ghi");

        opts = new Options(true, false, "_", null);
        result = snakeCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("abc_-def_-ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(true, false, "-", null);
        var result = snakeCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("abc_def_ghi");

        opts = new Options(true, false, "_", null);
        result = snakeCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("abc_-_def_-_ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(true, false, "_", null);
        var result = snakeCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("abc_def_ghi");

        opts = new Options(true, false, "-", null);
        result = snakeCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("abc__def__ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(true, false, "-", null);
        var result = snakeCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("abc_def_ghi");

        opts = new Options(true, false, "_", null);
        result = snakeCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("abc_-def_-ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(true, false, "-", null);
        var result = snakeCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("abc_123_456def_g_89hi_jkl_mn_12");

        opts = new Options(true, false, "_", null);
        result = snakeCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("abc_123-456def_g_89hi_jkl_mn_12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(true, false, ":@$&()/", null);
        var result = snakeCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo(".abc_~!_def_#_ghi_%_jk_lm_no_?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(true, false, "-", null);
        var result = snakeCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123abc_456def");

        result = snakeCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123abc_456def");

        result = snakeCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123_abc_456_def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(true, false, null, null);
        var result = snakeCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }

      @Test
      void alphabetsAndNumbersInSeparatorsAreNoEffect() {
        var opts = new Options(true, false, "-b2", null);
        var result = snakeCaseWithOptions("abc123def", opts);
        assertThat(result).isEqualTo("abc_123def");
      }
    }

    @Nested
    class NonAlphabetsAsTailOfWordAndWithSeparators {
      @Test
      void convertCamelCase() {
        var opts = new Options(false, true, "-_", null);
        var result = snakeCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("abc_def_gh_ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(false, true, "-_", "");
        var result = snakeCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("abc_def_gh_ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(false, true, "_", null);
        var result = snakeCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("abc_def_ghi");

        opts = new Options(false, true, "-", null);
        result = snakeCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("abc__def__ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(false, true, "-", null);
        var result = snakeCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("abc_def_ghi");

        opts = new Options(false, true, "_", null);
        result = snakeCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("abc-_def-_ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(false, true, "-", null);
        var result = snakeCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("abc_def_ghi");

        opts = new Options(false, true, "_", null);
        result = snakeCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("abc-_def-_ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(false, true, "_", null);
        var result = snakeCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("abc_def_ghi");

        opts = new Options(false, true, "-", null);
        result = snakeCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("abc__def__ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(false, true, "-", null);
        var result = snakeCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("abc_def_ghi");

        opts = new Options(false, true, "_", null);
        result = snakeCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("abc-_def-_ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(false, true, "-", null);
        var result = snakeCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("abc123_456_def_g89_hi_jkl_mn12");

        opts = new Options(false, true, "_", null);
        result = snakeCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("abc123-456_def_g89_hi_jkl_mn12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(false, true, ":@$&()/", null);
        var result = snakeCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo("._abc~!_def#_ghi%_jk_lm_no_?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(false, true, "-", null);
        var result = snakeCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123_abc456_def");

        result = snakeCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123_abc456_def");

        result = snakeCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123_abc456_def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(false, true, "-_", null);
        var result = snakeCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }

      @Test
      void alphabetsAndNumbersInSeparatorsAreNoEffect() {
        var opts = new Options(false, true, "-b2", null);
        var result = snakeCaseWithOptions("abc123def", opts);
        assertThat(result).isEqualTo("abc123_def");
      }
    }

    @Nested
    class NonAlphabetsAsWordAndWithSeparators {
      @Test
      void convertCamelCase() {
        var opts = new Options(true, true, "-_", null);
        var result = snakeCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("abc_def_gh_ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(true, true, "-_", "");
        var result = snakeCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("abc_def_gh_ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(true, true, "_", null);
        var result = snakeCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("abc_def_ghi");

        opts = new Options(true, true, "-", null);
        result = snakeCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("abc___def___ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(true, true, "-", null);
        var result = snakeCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("abc_def_ghi");

        opts = new Options(true, true, "_", null);
        result = snakeCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("abc_-_def_-_ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(true, true, "-", null);
        var result = snakeCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("abc_def_ghi");

        opts = new Options(true, true, "_", null);
        result = snakeCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("abc_-_def_-_ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(true, true, "_", null);
        var result = snakeCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("abc_def_ghi");

        opts = new Options(true, true, "-", null);
        result = snakeCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("abc___def___ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(true, true, "-", null);
        var result = snakeCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("abc_def_ghi");

        opts = new Options(true, true, "_", null);
        result = snakeCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("abc_-_def_-_ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(true, true, "-", null);
        var result = snakeCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("abc_123_456_def_g_89_hi_jkl_mn_12");

        opts = new Options(true, true, "_", null);
        result = snakeCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("abc_123-456_def_g_89_hi_jkl_mn_12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(true, true, ":@$&()/", null);
        var result = snakeCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo("._abc_~!_def_#_ghi_%_jk_lm_no_?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(true, true, "-_", null);
        var result = snakeCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123_abc_456_def");

        result = snakeCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123_abc_456_def");

        result = snakeCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123_abc_456_def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(true, true, "-_", null);
        var result = snakeCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }

      @Test
      void alphabetsAndNumbersInSeparatorsAreNoEffect() {
        var opts = new Options(true, true, "-b2", null);
        var result = snakeCaseWithOptions("abc123def", opts);
        assertThat(result).isEqualTo("abc_123_def");
      }
    }

    @Nested
    class NonAlphabetsAsPartOfWordAndWithSeparators {
      @Test
      void convertCamelCase() {
        var opts = new Options(false, false, "-_", null);
        var result = snakeCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("abc_def_gh_ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(false, false, "-_", "");
        var result = snakeCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("abc_def_gh_ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(false, false, "_", null);
        var result = snakeCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("abc_def_ghi");

        opts = new Options(false, false, "-", null);
        result = snakeCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("abc_def_ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(false, false, "-", null);
        var result = snakeCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("abc_def_ghi");

        opts = new Options(false, false, "_", null);
        result = snakeCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("abc-def-ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(false, false, "-", null);
        var result = snakeCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("abc_def_ghi");

        opts = new Options(false, false, "_", null);
        result = snakeCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("abc-_def-_ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(false, false, "_", null);
        var result = snakeCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("abc_def_ghi");

        opts = new Options(false, false, "-", null);
        result = snakeCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("abc_def_ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(false, false, "-", null);
        var result = snakeCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("abc_def_ghi");

        opts = new Options(false, false, "_", null);
        result = snakeCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("abc-def-ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(false, false, "-", null);
        var result = snakeCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("abc123_456def_g89hi_jkl_mn12");

        opts = new Options(false, false, "_", null);
        result = snakeCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("abc123-456def_g89hi_jkl_mn12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(false, false, ":@$&()/", null);
        var result = snakeCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo(".abc~!_def#_ghi%_jk_lm_no_?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(false, false, "-_", null);
        var result = snakeCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123abc456def");

        result = snakeCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123abc456def");

        result = snakeCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123_abc456_def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(false, false, "-_", null);
        var result = snakeCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }

      @Test
      void alphabetsAndNumbersInSeparatorsAreNoEffect() {
        var opts = new Options(false, false, "-b2", null);
        var result = snakeCaseWithOptions("abc123def", opts);
        assertThat(result).isEqualTo("abc123def");
      }
    }

    @Nested
    class NonAlphabetsAsHeadOfWordAndWithKeptCharacters {
      @Test
      void convertCamelCase() {
        var opts = new Options(true, false, null, "-_");
        var result = snakeCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("abc_def_gh_ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(true, false, "", "-_");
        var result = snakeCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("abc_def_gh_ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(true, false, null, "-");
        var result = snakeCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("abc_def_ghi");

        opts = new Options(true, false, null, "_");
        result = snakeCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("abc__def__ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(true, false, null, "_");
        var result = snakeCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("abc_def_ghi");

        opts = new Options(true, false, null, "-");
        result = snakeCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("abc_-def_-ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(true, false, null, "_");
        var result = snakeCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("abc_def_ghi");

        opts = new Options(true, false, null, "-");
        result = snakeCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("abc_-_def_-_ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(true, false, null, "-");
        var result = snakeCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("abc_def_ghi");

        opts = new Options(true, false, null, "_");
        result = snakeCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("abc__def__ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(true, false, null, "_");
        var result = snakeCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("abc_def_ghi");

        opts = new Options(true, false, null, "-");
        result = snakeCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("abc_-def_-ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(true, false, null, "_");
        var result = snakeCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("abc_123_456def_g_89hi_jkl_mn_12");

        opts = new Options(true, false, null, "-");
        result = snakeCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("abc_123-456def_g_89hi_jkl_mn_12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(true, false, null, ".~!#%?");
        var result = snakeCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo(".abc_~!_def_#_ghi_%_jk_lm_no_?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(true, false, null, "-");
        var result = snakeCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123abc_456def");

        result = snakeCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123abc_456def");

        result = snakeCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123_abc_456_def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(true, false, null, "-_");
        var result = snakeCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsAsTailOfWordAndWithKeptCharacters {
      @Test
      void convertCamelCase() {
        var opts = new Options(false, true, null, "-_");
        var result = snakeCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("abc_def_gh_ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(false, true, "", "-_");
        var result = snakeCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("abc_def_gh_ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(false, true, null, "-");
        var result = snakeCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("abc_def_ghi");

        opts = new Options(false, true, null, "_");
        result = snakeCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("abc__def__ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(false, true, null, "_");
        var result = snakeCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("abc_def_ghi");

        opts = new Options(false, true, null, "-");
        result = snakeCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("abc-_def-_ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(false, true, null, "_");
        var result = snakeCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("abc_def_ghi");

        opts = new Options(false, true, null, "-");
        result = snakeCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("abc-_def-_ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(false, true, null, "-");
        var result = snakeCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("abc_def_ghi");

        opts = new Options(false, true, null, "_");
        result = snakeCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("abc__def__ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(false, true, null, "_");
        var result = snakeCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("abc_def_ghi");

        opts = new Options(false, true, null, "-");
        result = snakeCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("abc-_def-_ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(false, true, null, "_");
        var result = snakeCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("abc123_456_def_g89_hi_jkl_mn12");

        opts = new Options(false, true, null, "-");
        result = snakeCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("abc123-456_def_g89_hi_jkl_mn12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(false, true, null, ".~!#%?");
        var result = snakeCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo("._abc~!_def#_ghi%_jk_lm_no_?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(false, true, null, "_");
        var result = snakeCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123_abc456_def");

        result = snakeCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123_abc456_def");

        result = snakeCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123_abc456_def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(false, true, null, "-_");
        var result = snakeCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsAsWordAndWithKeptCharacters {
      @Test
      void convertCamelCase() {
        var opts = new Options(true, true, null, "-_");
        var result = snakeCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("abc_def_gh_ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(true, true, "", "-_");
        var result = snakeCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("abc_def_gh_ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(true, true, null, "-");
        var result = snakeCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("abc_def_ghi");

        opts = new Options(true, true, null, "_");
        result = snakeCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("abc___def___ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(true, true, null, "_");
        var result = snakeCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("abc_def_ghi");

        opts = new Options(true, true, null, "-");
        result = snakeCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("abc_-_def_-_ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(true, true, null, "_");
        var result = snakeCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("abc_def_ghi");

        opts = new Options(true, true, null, "-");
        result = snakeCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("abc_-_def_-_ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(true, true, null, "-");
        var result = snakeCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("abc_def_ghi");

        opts = new Options(true, true, null, "_");
        result = snakeCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("abc___def___ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(true, true, null, "_");
        var result = snakeCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("abc_def_ghi");

        opts = new Options(true, true, null, "-");
        result = snakeCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("abc_-_def_-_ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(true, true, null, "_");
        var result = snakeCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("abc_123_456_def_g_89_hi_jkl_mn_12");

        opts = new Options(true, true, null, "-");
        result = snakeCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("abc_123-456_def_g_89_hi_jkl_mn_12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(true, true, null, ".~!#%?");
        var result = snakeCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo("._abc_~!_def_#_ghi_%_jk_lm_no_?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(true, true, null, "-_");
        var result = snakeCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123_abc_456_def");

        result = snakeCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123_abc_456_def");

        result = snakeCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123_abc_456_def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(true, true, null, "-_");
        var result = snakeCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsAsPartOfWordAndWithKeptCharacters {
      @Test
      void convertCamelCase() {
        var opts = new Options(false, false, null, "-_");
        var result = snakeCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("abc_def_gh_ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(false, false, "", "-_");
        var result = snakeCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("abc_def_gh_ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(false, false, null, "-");
        var result = snakeCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("abc_def_ghi");

        opts = new Options(false, false, null, "_");
        result = snakeCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("abc_def_ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(false, false, null, "_");
        var result = snakeCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("abc_def_ghi");

        opts = new Options(false, false, null, "-");
        result = snakeCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("abc-def-ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(false, false, null, "_");
        var result = snakeCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("abc_def_ghi");

        opts = new Options(false, false, null, "-");
        result = snakeCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("abc-_def-_ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(false, false, null, "-");
        var result = snakeCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("abc_def_ghi");

        opts = new Options(false, false, null, "_");
        result = snakeCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("abc_def_ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(false, false, null, "_");
        var result = snakeCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("abc_def_ghi");

        opts = new Options(false, false, null, "-");
        result = snakeCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("abc-def-ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(false, false, null, "_");
        var result = snakeCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("abc123_456def_g89hi_jkl_mn12");

        opts = new Options(false, false, null, "-");
        result = snakeCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("abc123-456def_g89hi_jkl_mn12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(false, false, null, ".~!#%?");
        var result = snakeCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo(".abc~!_def#_ghi%_jk_lm_no_?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(false, false, null, "-_");
        var result = snakeCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123abc456def");

        result = snakeCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123abc456def");

        result = snakeCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123_abc456_def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(false, false, null, "-_");
        var result = snakeCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }
    }
  }
}
