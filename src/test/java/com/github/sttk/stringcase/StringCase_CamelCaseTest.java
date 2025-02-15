package com.github.sttk.stringcase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import static com.github.sttk.stringcase.StringCase.*;

import static java.lang.Character.codePointAt;

@SuppressWarnings("missing-explicit-ctor")
public class StringCase_CamelCaseTest {

  @Nested
  class CamelCase {
    @Test
    void convertCamelCase() {
      var result = camelCase("abcDefGHIjk");
      assertThat(result).isEqualTo("abcDefGhIjk");
    }

    @Test
    void convertPascalCase() {
      var result = camelCase("AbcDefGHIjk");
      assertThat(result).isEqualTo("abcDefGhIjk");
    }

    @Test
    void convertSnakeCase() {
      var result = camelCase("abc_def_ghi");
      assertThat(result).isEqualTo("abcDefGhi");
    }

    @Test
    void convertKebabCase() {
      var result = camelCase("abc-def-ghi");
      assertThat(result).isEqualTo("abcDefGhi");
    }

    @Test
    void convertTrainCase() {
      var result = camelCase("Abc-Def-Ghi");
      assertThat(result).isEqualTo("abcDefGhi");
    }

    @Test
    void convertMacroCase() {
      var result = camelCase("ABC_DEF_GHI");
      assertThat(result).isEqualTo("abcDefGhi");
    }

    @Test
    void convertCobolCase() {
      var result = camelCase("ABC-DEF-GHI");
      assertThat(result).isEqualTo("abcDefGhi");
    }

    @Test
    void convertWithKeepingDigits() {
      var result = camelCase("abc123-456defG89HIJklMN12");
      assertThat(result).isEqualTo("abc123456DefG89HiJklMn12");
    }

    @Test
    void convertWithSymbolsAsSeparators() {
   		var result = camelCase(":.abc~!@def#$ghi%&jk(lm)no/?");
  		assertThat(result).isEqualTo("abcDefGhiJkLmNo");
    }

    @Test
    void convertWhenStartingWithDigit() {
		  var result = camelCase("123abc456def");
		  assertThat(result).isEqualTo("123Abc456Def");

		  result = camelCase("123ABC456DEF");
		  assertThat(result).isEqualTo("123Abc456Def");

		  result = camelCase("123Abc456Def");
		  assertThat(result).isEqualTo("123Abc456Def");
    }

    @Test
    void convertAnEmptyString() {
		  var result = camelCase("");
		  assertThat(result).isEqualTo("");
    }
  }

  @Nested
  class CamelCaseWithOptions {
    @Nested
    class NonAlphabetsAsHeadOfWord {
      @Test
      void convertCamelCase() {
        var opts = new Options(true, false, null, null);
        var result = camelCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("abcDefGhIjk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(true, false, "", "");
        var result = camelCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("abcDefGhIjk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(true, false, null, null);
        var result = camelCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("abcDefGhi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(true, false, null, null);
        var result = camelCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("abcDefGhi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(true, false, null, null);
        var result = camelCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("abcDefGhi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(true, false, null, null);
        var result = camelCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("abcDefGhi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(true, false, null, null);
        var result = camelCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("abcDefGhi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(true, false, null, null);
        var result = camelCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("abc123456defG89hiJklMn12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(true, false, null, null);
        var result = camelCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo("abcDefGhiJkLmNo");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(true, false, null, null);
        var result = camelCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123abc456def");

        result = camelCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123abc456def");

        result = camelCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123Abc456Def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(true, false, null, null);
        var result = camelCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsAsTailOfWord {
      @Test
      void convertCamelCase() {
        var opts = new Options(false, true, null, null);
        var result = camelCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("abcDefGhIjk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(false, true, "", "");
        var result = camelCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("abcDefGhIjk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(false, true, null, null);
        var result = camelCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("abcDefGhi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(false, true, null, null);
        var result = camelCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("abcDefGhi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(false, true, null, null);
        var result = camelCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("abcDefGhi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(false, true, null, null);
        var result = camelCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("abcDefGhi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(false, true, null, null);
        var result = camelCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("abcDefGhi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(false, true, null, null);
        var result = camelCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("abc123456DefG89HiJklMn12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(false, true, null, null);
        var result = camelCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo("abcDefGhiJkLmNo");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(false, true, null, null);
        var result = camelCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123Abc456Def");

        result = camelCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123Abc456Def");

        result = camelCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123Abc456Def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(false, true, null, null);
        var result = camelCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsAsWord {
      @Test
      void convertCamelCase() {
        var opts = new Options(true, true, null, null);
        var result = camelCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("abcDefGhIjk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(true, true, "", "");
        var result = camelCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("abcDefGhIjk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(true, true, null, null);
        var result = camelCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("abcDefGhi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(true, true, null, null);
        var result = camelCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("abcDefGhi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(true, true, null, null);
        var result = camelCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("abcDefGhi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(true, true, null, null);
        var result = camelCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("abcDefGhi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(true, true, null, null);
        var result = camelCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("abcDefGhi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(true, true, null, null);
        var result = camelCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("abc123456DefG89HiJklMn12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(true, true, null, null);
        var result = camelCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo("abcDefGhiJkLmNo");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(true, true, null, null);
        var result = camelCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123Abc456Def");

        result = camelCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123Abc456Def");

        result = camelCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123Abc456Def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(true, true, null, null);
        var result = camelCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsPartAsWord {
      @Test
      void convertCamelCase() {
        var opts = new Options(false, false, null, null);
        var result = camelCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("abcDefGhIjk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(false, false, "", "");
        var result = camelCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("abcDefGhIjk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(false, false, null, null);
        var result = camelCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("abcDefGhi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(false, false, null, null);
        var result = camelCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("abcDefGhi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(false, false, null, null);
        var result = camelCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("abcDefGhi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(false, false, null, null);
        var result = camelCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("abcDefGhi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(false, false, null, null);
        var result = camelCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("abcDefGhi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(false, false, null, null);
        var result = camelCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("abc123456defG89hiJklMn12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(false, false, null, null);
        var result = camelCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo("abcDefGhiJkLmNo");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(false, false, null, null);
        var result = camelCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123abc456def");

        result = camelCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123abc456def");

        result = camelCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123Abc456Def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(false, false, null, null);
        var result = camelCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsAsHeadOfWordAndWithSeparators {
      @Test
      void convertCamelCase() {
        var opts = new Options(true, false, "-_", null);
        var result = camelCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("abcDefGhIjk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(true, false, "-_", "");
        var result = camelCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("abcDefGhIjk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(true, false, "_", null);
        var result = camelCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("abcDefGhi");

        opts = new Options(true, false, "-", null);
        result = camelCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("abc_def_ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(true, false, "-", null);
        var result = camelCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("abcDefGhi");

        opts = new Options(true, false, "_", null);
        result = camelCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("abc-def-ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(true, false, "-", null);
        var result = camelCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("abcDefGhi");

        opts = new Options(true, false, "_", null);
        result = camelCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("abc-Def-Ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(true, false, "_", null);
        var result = camelCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("abcDefGhi");

        opts = new Options(true, false, "-", null);
        result = camelCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("abc_def_ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(true, false, "-", null);
        var result = camelCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("abcDefGhi");

        opts = new Options(true, false, "_", null);
        result = camelCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("abc-def-ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(true, false, "-", null);
        var result = camelCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("abc123456defG89hiJklMn12");

        opts = new Options(true, false, "_", null);
        result = camelCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("abc123-456defG89hiJklMn12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(true, false, ":@$&()/", null);
        var result = camelCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo(".abc~!Def#Ghi%JkLmNo?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(true, false, "-", null);
        var result = camelCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123abc456def");

        result = camelCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123abc456def");

        result = camelCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123Abc456Def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(true, false, null, null);
        var result = camelCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }

      @Test
      void alphabetsAndNumbersInSeparatorsAreNoEffect() {
        var opts = new Options(true, false, "-b2", null);
        var result = camelCaseWithOptions("abc123def", opts);
        assertThat(result).isEqualTo("abc123def");
      }
    }

    @Nested
    class NonAlphabetsAsTailOfWordAndWithSeparators {
      @Test
      void convertCamelCase() {
        var opts = new Options(false, true, "-_", null);
        var result = camelCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("abcDefGhIjk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(false, true, "-_", "");
        var result = camelCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("abcDefGhIjk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(false, true, "_", null);
        var result = camelCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("abcDefGhi");

        opts = new Options(false, true, "-", null);
        result = camelCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("abc_Def_Ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(false, true, "-", null);
        var result = camelCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("abcDefGhi");

        opts = new Options(false, true, "_", null);
        result = camelCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("abc-Def-Ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(false, true, "-", null);
        var result = camelCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("abcDefGhi");

        opts = new Options(false, true, "_", null);
        result = camelCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("abc-Def-Ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(false, true, "_", null);
        var result = camelCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("abcDefGhi");

        opts = new Options(false, true, "-", null);
        result = camelCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("abc_Def_Ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(false, true, "-", null);
        var result = camelCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("abcDefGhi");

        opts = new Options(false, true, "_", null);
        result = camelCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("abc-Def-Ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(false, true, "-", null);
        var result = camelCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("abc123456DefG89HiJklMn12");

        opts = new Options(false, true, "_", null);
        result = camelCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("abc123-456DefG89HiJklMn12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(false, true, ":@$&()/", null);
        var result = camelCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo(".Abc~!Def#Ghi%JkLmNo?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(false, true, "-", null);
        var result = camelCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123Abc456Def");

        result = camelCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123Abc456Def");

        result = camelCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123Abc456Def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(false, true, "-_", null);
        var result = camelCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }

      @Test
      void alphabetsAndNumbersInSeparatorsAreNoEffect() {
        var opts = new Options(false, true, "-b2", null);
        var result = camelCaseWithOptions("abc123def", opts);
        assertThat(result).isEqualTo("abc123Def");
      }
    }

    @Nested
    class NonAlphabetsAsWordAndWithSeparators {
      @Test
      void convertCamelCase() {
        var opts = new Options(true, true, "-_", null);
        var result = camelCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("abcDefGhIjk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(true, true, "-_", "");
        var result = camelCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("abcDefGhIjk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(true, true, "_", null);
        var result = camelCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("abcDefGhi");

        opts = new Options(true, true, "-", null);
        result = camelCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("abc_Def_Ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(true, true, "-", null);
        var result = camelCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("abcDefGhi");

        opts = new Options(true, true, "_", null);
        result = camelCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("abc-Def-Ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(true, true, "-", null);
        var result = camelCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("abcDefGhi");

        opts = new Options(true, true, "_", null);
        result = camelCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("abc-Def-Ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(true, true, "_", null);
        var result = camelCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("abcDefGhi");

        opts = new Options(true, true, "-", null);
        result = camelCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("abc_Def_Ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(true, true, "-", null);
        var result = camelCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("abcDefGhi");

        opts = new Options(true, true, "_", null);
        result = camelCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("abc-Def-Ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(true, true, "-", null);
        var result = camelCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("abc123456DefG89HiJklMn12");

        opts = new Options(true, true, "_", null);
        result = camelCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("abc123-456DefG89HiJklMn12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(true, true, ":@$&()/", null);
        var result = camelCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo(".Abc~!Def#Ghi%JkLmNo?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(true, true, "-_", null);
        var result = camelCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123Abc456Def");

        result = camelCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123Abc456Def");

        result = camelCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123Abc456Def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(true, true, "-_", null);
        var result = camelCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }

      @Test
      void alphabetsAndNumbersInSeparatorsAreNoEffect() {
        var opts = new Options(true, true, "-b2", null);
        var result = camelCaseWithOptions("abc123def", opts);
        assertThat(result).isEqualTo("abc123Def");
      }
    }

    @Nested
    class NonAlphabetsAsPartOfWordAndWithSeparators {
      @Test
      void convertCamelCase() {
        var opts = new Options(false, false, "-_", null);
        var result = camelCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("abcDefGhIjk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(false, false, "-_", "");
        var result = camelCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("abcDefGhIjk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(false, false, "_", null);
        var result = camelCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("abcDefGhi");

        opts = new Options(false, false, "-", null);
        result = camelCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("abc_def_ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(false, false, "-", null);
        var result = camelCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("abcDefGhi");

        opts = new Options(false, false, "_", null);
        result = camelCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("abc-def-ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(false, false, "-", null);
        var result = camelCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("abcDefGhi");

        opts = new Options(false, false, "_", null);
        result = camelCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("abc-Def-Ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(false, false, "_", null);
        var result = camelCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("abcDefGhi");

        opts = new Options(false, false, "-", null);
        result = camelCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("abc_def_ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(false, false, "-", null);
        var result = camelCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("abcDefGhi");

        opts = new Options(false, false, "_", null);
        result = camelCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("abc-def-ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(false, false, "-", null);
        var result = camelCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("abc123456defG89hiJklMn12");

        opts = new Options(false, false, "_", null);
        result = camelCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("abc123-456defG89hiJklMn12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(false, false, ":@$&()/", null);
        var result = camelCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo(".abc~!Def#Ghi%JkLmNo?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(false, false, "-_", null);
        var result = camelCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123abc456def");

        result = camelCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123abc456def");

        result = camelCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123Abc456Def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(false, false, "-_", null);
        var result = camelCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }

      @Test
      void alphabetsAndNumbersInSeparatorsAreNoEffect() {
        var opts = new Options(false, false, "-b2", null);
        var result = camelCaseWithOptions("abc123def", opts);
        assertThat(result).isEqualTo("abc123def");
      }
    }

    @Nested
    class NonAlphabetsAsHeadOfWordAndWithKeptCharacters {
      @Test
      void convertCamelCase() {
        var opts = new Options(true, false, null, "-_");
        var result = camelCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("abcDefGhIjk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(true, false, "", "-_");
        var result = camelCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("abcDefGhIjk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(true, false, null, "-");
        var result = camelCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("abcDefGhi");

        opts = new Options(true, false, null, "_");
        result = camelCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("abc_def_ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(true, false, null, "_");
        var result = camelCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("abcDefGhi");

        opts = new Options(true, false, null, "-");
        result = camelCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("abc-def-ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(true, false, null, "_");
        var result = camelCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("abcDefGhi");

        opts = new Options(true, false, null, "-");
        result = camelCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("abc-Def-Ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(true, false, null, "-");
        var result = camelCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("abcDefGhi");

        opts = new Options(true, false, null, "_");
        result = camelCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("abc_def_ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(true, false, null, "_");
        var result = camelCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("abcDefGhi");

        opts = new Options(true, false, null, "-");
        result = camelCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("abc-def-ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(true, false, null, "_");
        var result = camelCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("abc123456defG89hiJklMn12");

        opts = new Options(true, false, null, "-");
        result = camelCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("abc123-456defG89hiJklMn12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(true, false, null, ".~!#%?");
        var result = camelCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo(".abc~!Def#Ghi%JkLmNo?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(true, false, null, "-");
        var result = camelCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123abc456def");

        result = camelCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123abc456def");

        result = camelCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123Abc456Def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(true, false, null, "-_");
        var result = camelCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsAsTailOfWordAndWithKeptCharacters {
      @Test
      void convertCamelCase() {
        var opts = new Options(false, true, null, "-_");
        var result = camelCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("abcDefGhIjk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(false, true, "", "-_");
        var result = camelCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("abcDefGhIjk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(false, true, null, "-");
        var result = camelCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("abcDefGhi");

        opts = new Options(false, true, null, "_");
        result = camelCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("abc_Def_Ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(false, true, null, "_");
        var result = camelCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("abcDefGhi");

        opts = new Options(false, true, null, "-");
        result = camelCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("abc-Def-Ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(false, true, null, "_");
        var result = camelCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("abcDefGhi");

        opts = new Options(false, true, null, "-");
        result = camelCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("abc-Def-Ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(false, true, null, "-");
        var result = camelCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("abcDefGhi");

        opts = new Options(false, true, null, "_");
        result = camelCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("abc_Def_Ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(false, true, null, "_");
        var result = camelCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("abcDefGhi");

        opts = new Options(false, true, null, "-");
        result = camelCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("abc-Def-Ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(false, true, null, "_");
        var result = camelCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("abc123456DefG89HiJklMn12");

        opts = new Options(false, true, null, "-");
        result = camelCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("abc123-456DefG89HiJklMn12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(false, true, null, ".~!#%?");
        var result = camelCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo(".Abc~!Def#Ghi%JkLmNo?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(false, true, null, "_");
        var result = camelCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123Abc456Def");

        result = camelCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123Abc456Def");

        result = camelCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123Abc456Def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(false, true, null, "-_");
        var result = camelCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsAsWordAndWithKeptCharacters {
      @Test
      void convertCamelCase() {
        var opts = new Options(true, true, null, "-_");
        var result = camelCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("abcDefGhIjk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(true, true, "", "-_");
        var result = camelCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("abcDefGhIjk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(true, true, null, "-");
        var result = camelCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("abcDefGhi");

        opts = new Options(true, true, null, "_");
        result = camelCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("abc_Def_Ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(true, true, null, "_");
        var result = camelCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("abcDefGhi");

        opts = new Options(true, true, null, "-");
        result = camelCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("abc-Def-Ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(true, true, null, "_");
        var result = camelCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("abcDefGhi");

        opts = new Options(true, true, null, "-");
        result = camelCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("abc-Def-Ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(true, true, null, "-");
        var result = camelCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("abcDefGhi");

        opts = new Options(true, true, null, "_");
        result = camelCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("abc_Def_Ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(true, true, null, "_");
        var result = camelCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("abcDefGhi");

        opts = new Options(true, true, null, "-");
        result = camelCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("abc-Def-Ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(true, true, null, "_");
        var result = camelCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("abc123456DefG89HiJklMn12");

        opts = new Options(true, true, null, "-");
        result = camelCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("abc123-456DefG89HiJklMn12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(true, true, null, ".~!#%?");
        var result = camelCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo(".Abc~!Def#Ghi%JkLmNo?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(true, true, null, "-_");
        var result = camelCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123Abc456Def");

        result = camelCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123Abc456Def");

        result = camelCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123Abc456Def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(true, true, null, "-_");
        var result = camelCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsAsPartOfWordAndWithKeptCharacters {
      @Test
      void convertCamelCase() {
        var opts = new Options(false, false, null, "-_");
        var result = camelCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("abcDefGhIjk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(false, false, "", "-_");
        var result = camelCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("abcDefGhIjk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(false, false, null, "-");
        var result = camelCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("abcDefGhi");

        opts = new Options(false, false, null, "_");
        result = camelCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("abc_def_ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(false, false, null, "_");
        var result = camelCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("abcDefGhi");

        opts = new Options(false, false, null, "-");
        result = camelCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("abc-def-ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(false, false, null, "_");
        var result = camelCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("abcDefGhi");

        opts = new Options(false, false, null, "-");
        result = camelCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("abc-Def-Ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(false, false, null, "-");
        var result = camelCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("abcDefGhi");

        opts = new Options(false, false, null, "_");
        result = camelCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("abc_def_ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(false, false, null, "_");
        var result = camelCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("abcDefGhi");

        opts = new Options(false, false, null, "-");
        result = camelCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("abc-def-ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(false, false, null, "_");
        var result = camelCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("abc123456defG89hiJklMn12");

        opts = new Options(false, false, null, "-");
        result = camelCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("abc123-456defG89hiJklMn12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(false, false, null, ".~!#%?");
        var result = camelCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo(".abc~!Def#Ghi%JkLmNo?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(false, false, null, "-_");
        var result = camelCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123abc456def");

        result = camelCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123abc456def");

        result = camelCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123Abc456Def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(false, false, null, "-_");
        var result = camelCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }
    }
  }
}
