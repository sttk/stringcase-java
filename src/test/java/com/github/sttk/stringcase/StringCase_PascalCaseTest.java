package com.github.sttk.stringcase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import static com.github.sttk.stringcase.StringCase.*;

import static java.lang.Character.codePointAt;

@SuppressWarnings("missing-explicit-ctor")
public class StringCase_PascalCaseTest {

  @Nested
  class PascalCase {
    @Test
    void convertCamelCase() {
      var result = pascalCase("abcDefGHIjk");
      assertThat(result).isEqualTo("AbcDefGhIjk");
    }

    @Test
    void convertPascalCase() {
      var result = pascalCase("AbcDefGHIjk");
      assertThat(result).isEqualTo("AbcDefGhIjk");
    }

    @Test
    void convertSnakeCase() {
      var result = pascalCase("abc_def_ghi");
      assertThat(result).isEqualTo("AbcDefGhi");
    }

    @Test
    void convertKebabCase() {
      var result = pascalCase("abc-def-ghi");
      assertThat(result).isEqualTo("AbcDefGhi");
    }

    @Test
    void convertTrainCase() {
      var result = pascalCase("Abc-Def-Ghi");
      assertThat(result).isEqualTo("AbcDefGhi");
    }

    @Test
    void convertMacroCase() {
      var result = pascalCase("ABC_DEF_GHI");
      assertThat(result).isEqualTo("AbcDefGhi");
    }

    @Test
    void convertCobolCase() {
      var result = pascalCase("ABC-DEF-GHI");
      assertThat(result).isEqualTo("AbcDefGhi");
    }

    @Test
    void convertWithKeepingDigits() {
      var result = pascalCase("abc123-456defG89HIJklMN12");
      assertThat(result).isEqualTo("Abc123456DefG89HiJklMn12");
    }

    @Test
    void convertWithSymbolsAsSeparators() {
   		var result = pascalCase(":.abc~!@def#$ghi%&jk(lm)no/?");
  		assertThat(result).isEqualTo("AbcDefGhiJkLmNo");
    }

    @Test
    void convertWhenStartingWithDigit() {
		  var result = pascalCase("123abc456def");
		  assertThat(result).isEqualTo("123Abc456Def");

		  result = pascalCase("123ABC456DEF");
		  assertThat(result).isEqualTo("123Abc456Def");

		  result = pascalCase("123Abc456Def");
		  assertThat(result).isEqualTo("123Abc456Def");
    }

    @Test
    void convertAnEmptyString() {
		  var result = pascalCase("");
		  assertThat(result).isEqualTo("");
    }
  }

  @Nested
  class PascalCaseWithOptions {
    @Nested
    class NonAlphabetsAsHeadOfWord {
      @Test
      void convertCamelCase() {
        var opts = new Options(true, false, null, null);
        var result = pascalCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("AbcDefGhIjk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(true, false, "", "");
        var result = pascalCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("AbcDefGhIjk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(true, false, null, null);
        var result = pascalCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("AbcDefGhi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(true, false, null, null);
        var result = pascalCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("AbcDefGhi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(true, false, null, null);
        var result = pascalCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("AbcDefGhi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(true, false, null, null);
        var result = pascalCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("AbcDefGhi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(true, false, null, null);
        var result = pascalCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("AbcDefGhi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(true, false, null, null);
        var result = pascalCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc123456defG89hiJklMn12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(true, false, null, null);
        var result = pascalCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo("AbcDefGhiJkLmNo");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(true, false, null, null);
        var result = pascalCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123abc456def");

        result = pascalCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123abc456def");

        result = pascalCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123Abc456Def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(true, false, null, null);
        var result = pascalCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsAsTailOfWord {
      @Test
      void convertCamelCase() {
        var opts = new Options(false, true, null, null);
        var result = pascalCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("AbcDefGhIjk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(false, true, "", "");
        var result = pascalCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("AbcDefGhIjk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(false, true, null, null);
        var result = pascalCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("AbcDefGhi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(false, true, null, null);
        var result = pascalCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("AbcDefGhi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(false, true, null, null);
        var result = pascalCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("AbcDefGhi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(false, true, null, null);
        var result = pascalCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("AbcDefGhi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(false, true, null, null);
        var result = pascalCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("AbcDefGhi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(false, true, null, null);
        var result = pascalCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc123456DefG89HiJklMn12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(false, true, null, null);
        var result = pascalCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo("AbcDefGhiJkLmNo");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(false, true, null, null);
        var result = pascalCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123Abc456Def");

        result = pascalCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123Abc456Def");

        result = pascalCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123Abc456Def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(false, true, null, null);
        var result = pascalCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsAsWord {
      @Test
      void convertCamelCase() {
        var opts = new Options(true, true, null, null);
        var result = pascalCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("AbcDefGhIjk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(true, true, "", "");
        var result = pascalCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("AbcDefGhIjk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(true, true, null, null);
        var result = pascalCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("AbcDefGhi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(true, true, null, null);
        var result = pascalCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("AbcDefGhi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(true, true, null, null);
        var result = pascalCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("AbcDefGhi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(true, true, null, null);
        var result = pascalCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("AbcDefGhi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(true, true, null, null);
        var result = pascalCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("AbcDefGhi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(true, true, null, null);
        var result = pascalCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc123456DefG89HiJklMn12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(true, true, null, null);
        var result = pascalCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo("AbcDefGhiJkLmNo");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(true, true, null, null);
        var result = pascalCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123Abc456Def");

        result = pascalCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123Abc456Def");

        result = pascalCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123Abc456Def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(true, true, null, null);
        var result = pascalCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsPartAsWord {
      @Test
      void convertCamelCase() {
        var opts = new Options(false, false, null, null);
        var result = pascalCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("AbcDefGhIjk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(false, false, "", "");
        var result = pascalCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("AbcDefGhIjk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(false, false, null, null);
        var result = pascalCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("AbcDefGhi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(false, false, null, null);
        var result = pascalCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("AbcDefGhi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(false, false, null, null);
        var result = pascalCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("AbcDefGhi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(false, false, null, null);
        var result = pascalCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("AbcDefGhi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(false, false, null, null);
        var result = pascalCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("AbcDefGhi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(false, false, null, null);
        var result = pascalCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc123456defG89hiJklMn12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(false, false, null, null);
        var result = pascalCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo("AbcDefGhiJkLmNo");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(false, false, null, null);
        var result = pascalCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123abc456def");

        result = pascalCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123abc456def");

        result = pascalCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123Abc456Def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(false, false, null, null);
        var result = pascalCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsAsHeadOfWordAndWithSeparators {
      @Test
      void convertCamelCase() {
        var opts = new Options(true, false, "-_", null);
        var result = pascalCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("AbcDefGhIjk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(true, false, "-_", "");
        var result = pascalCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("AbcDefGhIjk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(true, false, "_", null);
        var result = pascalCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("AbcDefGhi");

        opts = new Options(true, false, "-", null);
        result = pascalCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("Abc_def_ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(true, false, "-", null);
        var result = pascalCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("AbcDefGhi");

        opts = new Options(true, false, "_", null);
        result = pascalCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("Abc-def-ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(true, false, "-", null);
        var result = pascalCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("AbcDefGhi");

        opts = new Options(true, false, "_", null);
        result = pascalCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(true, false, "_", null);
        var result = pascalCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("AbcDefGhi");

        opts = new Options(true, false, "-", null);
        result = pascalCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("Abc_def_ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(true, false, "-", null);
        var result = pascalCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("AbcDefGhi");

        opts = new Options(true, false, "_", null);
        result = pascalCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("Abc-def-ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(true, false, "-", null);
        var result = pascalCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc123456defG89hiJklMn12");

        opts = new Options(true, false, "_", null);
        result = pascalCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc123-456defG89hiJklMn12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(true, false, ":@$&()/", null);
        var result = pascalCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo(".abc~!Def#Ghi%JkLmNo?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(true, false, "-", null);
        var result = pascalCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123abc456def");

        result = pascalCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123abc456def");

        result = pascalCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123Abc456Def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(true, false, null, null);
        var result = pascalCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }

      @Test
      void alphabetsAndNumbersInSeparatorsAreNoEffect() {
        var opts = new Options(true, false, "-b2", null);
        var result = pascalCaseWithOptions("abc123def", opts);
        assertThat(result).isEqualTo("Abc123def");
      }
    }

    @Nested
    class NonAlphabetsAsTailOfWordAndWithSeparators {
      @Test
      void convertCamelCase() {
        var opts = new Options(false, true, "-_", null);
        var result = pascalCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("AbcDefGhIjk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(false, true, "-_", "");
        var result = pascalCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("AbcDefGhIjk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(false, true, "_", null);
        var result = pascalCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("AbcDefGhi");

        opts = new Options(false, true, "-", null);
        result = pascalCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("Abc_Def_Ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(false, true, "-", null);
        var result = pascalCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("AbcDefGhi");

        opts = new Options(false, true, "_", null);
        result = pascalCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(false, true, "-", null);
        var result = pascalCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("AbcDefGhi");

        opts = new Options(false, true, "_", null);
        result = pascalCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(false, true, "_", null);
        var result = pascalCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("AbcDefGhi");

        opts = new Options(false, true, "-", null);
        result = pascalCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("Abc_Def_Ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(false, true, "-", null);
        var result = pascalCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("AbcDefGhi");

        opts = new Options(false, true, "_", null);
        result = pascalCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(false, true, "-", null);
        var result = pascalCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc123456DefG89HiJklMn12");

        opts = new Options(false, true, "_", null);
        result = pascalCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc123-456DefG89HiJklMn12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(false, true, ":@$&()/", null);
        var result = pascalCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo(".Abc~!Def#Ghi%JkLmNo?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(false, true, "-", null);
        var result = pascalCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123Abc456Def");

        result = pascalCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123Abc456Def");

        result = pascalCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123Abc456Def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(false, true, "-_", null);
        var result = pascalCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }

      @Test
      void alphabetsAndNumbersInSeparatorsAreNoEffect() {
        var opts = new Options(false, true, "-b2", null);
        var result = pascalCaseWithOptions("abc123def", opts);
        assertThat(result).isEqualTo("Abc123Def");
      }
    }

    @Nested
    class NonAlphabetsAsWordAndWithSeparators {
      @Test
      void convertCamelCase() {
        var opts = new Options(true, true, "-_", null);
        var result = pascalCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("AbcDefGhIjk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(true, true, "-_", "");
        var result = pascalCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("AbcDefGhIjk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(true, true, "_", null);
        var result = pascalCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("AbcDefGhi");

        opts = new Options(true, true, "-", null);
        result = pascalCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("Abc_Def_Ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(true, true, "-", null);
        var result = pascalCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("AbcDefGhi");

        opts = new Options(true, true, "_", null);
        result = pascalCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(true, true, "-", null);
        var result = pascalCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("AbcDefGhi");

        opts = new Options(true, true, "_", null);
        result = pascalCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(true, true, "_", null);
        var result = pascalCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("AbcDefGhi");

        opts = new Options(true, true, "-", null);
        result = pascalCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("Abc_Def_Ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(true, true, "-", null);
        var result = pascalCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("AbcDefGhi");

        opts = new Options(true, true, "_", null);
        result = pascalCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(true, true, "-", null);
        var result = pascalCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc123456DefG89HiJklMn12");

        opts = new Options(true, true, "_", null);
        result = pascalCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc123-456DefG89HiJklMn12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(true, true, ":@$&()/", null);
        var result = pascalCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo(".Abc~!Def#Ghi%JkLmNo?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(true, true, "-_", null);
        var result = pascalCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123Abc456Def");

        result = pascalCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123Abc456Def");

        result = pascalCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123Abc456Def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(true, true, "-_", null);
        var result = pascalCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }

      @Test
      void alphabetsAndNumbersInSeparatorsAreNoEffect() {
        var opts = new Options(true, true, "-b2", null);
        var result = pascalCaseWithOptions("abc123def", opts);
        assertThat(result).isEqualTo("Abc123Def");
      }
    }

    @Nested
    class NonAlphabetsAsPartOfWordAndWithSeparators {
      @Test
      void convertCamelCase() {
        var opts = new Options(false, false, "-_", null);
        var result = pascalCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("AbcDefGhIjk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(false, false, "-_", "");
        var result = pascalCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("AbcDefGhIjk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(false, false, "_", null);
        var result = pascalCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("AbcDefGhi");

        opts = new Options(false, false, "-", null);
        result = pascalCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("Abc_def_ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(false, false, "-", null);
        var result = pascalCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("AbcDefGhi");

        opts = new Options(false, false, "_", null);
        result = pascalCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("Abc-def-ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(false, false, "-", null);
        var result = pascalCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("AbcDefGhi");

        opts = new Options(false, false, "_", null);
        result = pascalCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(false, false, "_", null);
        var result = pascalCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("AbcDefGhi");

        opts = new Options(false, false, "-", null);
        result = pascalCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("Abc_def_ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(false, false, "-", null);
        var result = pascalCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("AbcDefGhi");

        opts = new Options(false, false, "_", null);
        result = pascalCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("Abc-def-ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(false, false, "-", null);
        var result = pascalCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc123456defG89hiJklMn12");

        opts = new Options(false, false, "_", null);
        result = pascalCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc123-456defG89hiJklMn12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(false, false, ":@$&()/", null);
        var result = pascalCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo(".abc~!Def#Ghi%JkLmNo?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(false, false, "-_", null);
        var result = pascalCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123abc456def");

        result = pascalCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123abc456def");

        result = pascalCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123Abc456Def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(false, false, "-_", null);
        var result = pascalCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }

      @Test
      void alphabetsAndNumbersInSeparatorsAreNoEffect() {
        var opts = new Options(false, false, "-b2", null);
        var result = pascalCaseWithOptions("abc123def", opts);
        assertThat(result).isEqualTo("Abc123def");
      }
    }

    @Nested
    class NonAlphabetsAsHeadOfWordAndWithKeptCharacters {
      @Test
      void convertCamelCase() {
        var opts = new Options(true, false, null, "-_");
        var result = pascalCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("AbcDefGhIjk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(true, false, "", "-_");
        var result = pascalCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("AbcDefGhIjk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(true, false, null, "-");
        var result = pascalCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("AbcDefGhi");

        opts = new Options(true, false, null, "_");
        result = pascalCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("Abc_def_ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(true, false, null, "_");
        var result = pascalCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("AbcDefGhi");

        opts = new Options(true, false, null, "-");
        result = pascalCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("Abc-def-ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(true, false, null, "_");
        var result = pascalCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("AbcDefGhi");

        opts = new Options(true, false, null, "-");
        result = pascalCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(true, false, null, "-");
        var result = pascalCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("AbcDefGhi");

        opts = new Options(true, false, null, "_");
        result = pascalCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("Abc_def_ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(true, false, null, "_");
        var result = pascalCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("AbcDefGhi");

        opts = new Options(true, false, null, "-");
        result = pascalCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("Abc-def-ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(true, false, null, "_");
        var result = pascalCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc123456defG89hiJklMn12");

        opts = new Options(true, false, null, "-");
        result = pascalCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc123-456defG89hiJklMn12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(true, false, null, ".~!#%?");
        var result = pascalCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo(".abc~!Def#Ghi%JkLmNo?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(true, false, null, "-");
        var result = pascalCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123abc456def");

        result = pascalCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123abc456def");

        result = pascalCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123Abc456Def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(true, false, null, "-_");
        var result = pascalCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsAsTailOfWordAndWithKeptCharacters {
      @Test
      void convertCamelCase() {
        var opts = new Options(false, true, null, "-_");
        var result = pascalCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("AbcDefGhIjk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(false, true, "", "-_");
        var result = pascalCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("AbcDefGhIjk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(false, true, null, "-");
        var result = pascalCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("AbcDefGhi");

        opts = new Options(false, true, null, "_");
        result = pascalCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("Abc_Def_Ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(false, true, null, "_");
        var result = pascalCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("AbcDefGhi");

        opts = new Options(false, true, null, "-");
        result = pascalCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(false, true, null, "_");
        var result = pascalCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("AbcDefGhi");

        opts = new Options(false, true, null, "-");
        result = pascalCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(false, true, null, "-");
        var result = pascalCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("AbcDefGhi");

        opts = new Options(false, true, null, "_");
        result = pascalCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("Abc_Def_Ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(false, true, null, "_");
        var result = pascalCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("AbcDefGhi");

        opts = new Options(false, true, null, "-");
        result = pascalCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(false, true, null, "_");
        var result = pascalCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc123456DefG89HiJklMn12");

        opts = new Options(false, true, null, "-");
        result = pascalCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc123-456DefG89HiJklMn12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(false, true, null, ".~!#%?");
        var result = pascalCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo(".Abc~!Def#Ghi%JkLmNo?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(false, true, null, "_");
        var result = pascalCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123Abc456Def");

        result = pascalCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123Abc456Def");

        result = pascalCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123Abc456Def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(false, true, null, "-_");
        var result = pascalCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsAsWordAndWithKeptCharacters {
      @Test
      void convertCamelCase() {
        var opts = new Options(true, true, null, "-_");
        var result = pascalCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("AbcDefGhIjk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(true, true, "", "-_");
        var result = pascalCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("AbcDefGhIjk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(true, true, null, "-");
        var result = pascalCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("AbcDefGhi");

        opts = new Options(true, true, null, "_");
        result = pascalCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("Abc_Def_Ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(true, true, null, "_");
        var result = pascalCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("AbcDefGhi");

        opts = new Options(true, true, null, "-");
        result = pascalCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(true, true, null, "_");
        var result = pascalCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("AbcDefGhi");

        opts = new Options(true, true, null, "-");
        result = pascalCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(true, true, null, "-");
        var result = pascalCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("AbcDefGhi");

        opts = new Options(true, true, null, "_");
        result = pascalCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("Abc_Def_Ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(true, true, null, "_");
        var result = pascalCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("AbcDefGhi");

        opts = new Options(true, true, null, "-");
        result = pascalCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(true, true, null, "_");
        var result = pascalCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc123456DefG89HiJklMn12");

        opts = new Options(true, true, null, "-");
        result = pascalCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc123-456DefG89HiJklMn12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(true, true, null, ".~!#%?");
        var result = pascalCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo(".Abc~!Def#Ghi%JkLmNo?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(true, true, null, "-_");
        var result = pascalCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123Abc456Def");

        result = pascalCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123Abc456Def");

        result = pascalCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123Abc456Def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(true, true, null, "-_");
        var result = pascalCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsAsPartOfWordAndWithKeptCharacters {
      @Test
      void convertCamelCase() {
        var opts = new Options(false, false, null, "-_");
        var result = pascalCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("AbcDefGhIjk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(false, false, "", "-_");
        var result = pascalCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("AbcDefGhIjk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(false, false, null, "-");
        var result = pascalCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("AbcDefGhi");

        opts = new Options(false, false, null, "_");
        result = pascalCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("Abc_def_ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(false, false, null, "_");
        var result = pascalCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("AbcDefGhi");

        opts = new Options(false, false, null, "-");
        result = pascalCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("Abc-def-ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(false, false, null, "_");
        var result = pascalCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("AbcDefGhi");

        opts = new Options(false, false, null, "-");
        result = pascalCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(false, false, null, "-");
        var result = pascalCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("AbcDefGhi");

        opts = new Options(false, false, null, "_");
        result = pascalCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("Abc_def_ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(false, false, null, "_");
        var result = pascalCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("AbcDefGhi");

        opts = new Options(false, false, null, "-");
        result = pascalCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("Abc-def-ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(false, false, null, "_");
        var result = pascalCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc123456defG89hiJklMn12");

        opts = new Options(false, false, null, "-");
        result = pascalCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc123-456defG89hiJklMn12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(false, false, null, ".~!#%?");
        var result = pascalCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo(".abc~!Def#Ghi%JkLmNo?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(false, false, null, "-_");
        var result = pascalCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123abc456def");

        result = pascalCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123abc456def");

        result = pascalCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123Abc456Def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(false, false, null, "-_");
        var result = pascalCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }
    }
  }
}
