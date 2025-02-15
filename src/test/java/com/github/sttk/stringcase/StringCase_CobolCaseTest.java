package com.github.sttk.stringcase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import static com.github.sttk.stringcase.StringCase.*;

import static java.lang.Character.codePointAt;

@SuppressWarnings("missing-explicit-ctor")
public class StringCase_CobolCaseTest {

  @Nested
  class CobolCase {
    void convertCamelCase() {
      var result = cobolCase("abcDefGHIjk");
      assertThat(result).isEqualTo("ABC-DEF-GH-IJK");
    }

    @Test
    void convertPascalCase() {
      var result = cobolCase("AbcDefGHIjk");
      assertThat(result).isEqualTo("ABC-DEF-GH-IJK");
    }

    @Test
    void convertSnakeCase() {
      var result = cobolCase("abc_def_ghi");
      assertThat(result).isEqualTo("ABC-DEF-GHI");
    }

    @Test
    void convertKebabCase() {
      var result = cobolCase("abc-def-ghi");
      assertThat(result).isEqualTo("ABC-DEF-GHI");
    }

    @Test
    void convertTrainCase() {
      var result = cobolCase("Abc-Def-Ghi");
      assertThat(result).isEqualTo("ABC-DEF-GHI");
    }

    @Test
    void convertMacroCase() {
      var result = cobolCase("ABC_DEF_GHI");
      assertThat(result).isEqualTo("ABC-DEF-GHI");
    }

    @Test
    void convertCobolCase() {
      var result = cobolCase("ABC-DEF-GHI");
      assertThat(result).isEqualTo("ABC-DEF-GHI");
    }

    @Test
    void convertWithKeepingDigits() {
      var result = cobolCase("abc123-456defG89HIJklMN12");
      assertThat(result).isEqualTo("ABC123-456-DEF-G89-HI-JKL-MN12");
    }

    @Test
    void convertWithSymbolsAsSeparators() {
   		var result = cobolCase(":.abc~!@def#$ghi%&jk(lm)no/?");
  		assertThat(result).isEqualTo("ABC-DEF-GHI-JK-LM-NO");
    }

    @Test
    void convertWhenStartingWithDigit() {
		  var result = cobolCase("123abc456def");
		  assertThat(result).isEqualTo("123-ABC456-DEF");

		  result = cobolCase("123ABC456DEF");
		  assertThat(result).isEqualTo("123-ABC456-DEF");

		  result = cobolCase("123Abc456Def");
		  assertThat(result).isEqualTo("123-ABC456-DEF");
    }

    @Test
    void convertAnEmptyString() {
		  var result = cobolCase("");
		  assertThat(result).isEqualTo("");
    }
  }

  @Nested
  class CobolCaseWithOptions {
    @Nested
    class NonAlphabetsAsHeadOfWord {
      @Test
      void convertCamelCase() {
        var opts = new Options(true, false, null, null);
        var result = cobolCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("ABC-DEF-GH-IJK");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(true, false, "", "");
        var result = cobolCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("ABC-DEF-GH-IJK");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(true, false, null, null);
        var result = cobolCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("ABC-DEF-GHI");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(true, false, null, null);
        var result = cobolCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("ABC-DEF-GHI");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(true, false, null, null);
        var result = cobolCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("ABC-DEF-GHI");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(true, false, null, null);
        var result = cobolCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("ABC-DEF-GHI");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(true, false, null, null);
        var result = cobolCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("ABC-DEF-GHI");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(true, false, null, null);
        var result = cobolCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("ABC-123-456DEF-G-89HI-JKL-MN-12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(true, false, null, null);
        var result = cobolCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo("ABC-DEF-GHI-JK-LM-NO");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(true, false, null, null);
        var result = cobolCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123ABC-456DEF");

        result = cobolCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123ABC-456DEF");

        result = cobolCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123-ABC-456-DEF");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(true, false, null, null);
        var result = cobolCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsAsTailOfWord {
      @Test
      void convertCamelCase() {
        var opts = new Options(false, true, null, null);
        var result = cobolCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("ABC-DEF-GH-IJK");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(false, true, "", "");
        var result = cobolCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("ABC-DEF-GH-IJK");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(false, true, null, null);
        var result = cobolCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("ABC-DEF-GHI");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(false, true, null, null);
        var result = cobolCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("ABC-DEF-GHI");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(false, true, null, null);
        var result = cobolCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("ABC-DEF-GHI");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(false, true, null, null);
        var result = cobolCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("ABC-DEF-GHI");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(false, true, null, null);
        var result = cobolCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("ABC-DEF-GHI");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(false, true, null, null);
        var result = cobolCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("ABC123-456-DEF-G89-HI-JKL-MN12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(false, true, null, null);
        var result = cobolCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo("ABC-DEF-GHI-JK-LM-NO");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(false, true, null, null);
        var result = cobolCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123-ABC456-DEF");

        result = cobolCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123-ABC456-DEF");

        result = cobolCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123-ABC456-DEF");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(false, true, null, null);
        var result = cobolCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsAsWord {
      @Test
      void convertCamelCase() {
        var opts = new Options(true, true, null, null);
        var result = cobolCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("ABC-DEF-GH-IJK");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(true, true, "", "");
        var result = cobolCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("ABC-DEF-GH-IJK");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(true, true, null, null);
        var result = cobolCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("ABC-DEF-GHI");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(true, true, null, null);
        var result = cobolCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("ABC-DEF-GHI");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(true, true, null, null);
        var result = cobolCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("ABC-DEF-GHI");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(true, true, null, null);
        var result = cobolCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("ABC-DEF-GHI");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(true, true, null, null);
        var result = cobolCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("ABC-DEF-GHI");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(true, true, null, null);
        var result = cobolCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("ABC-123-456-DEF-G-89-HI-JKL-MN-12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(true, true, null, null);
        var result = cobolCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo("ABC-DEF-GHI-JK-LM-NO");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(true, true, null, null);
        var result = cobolCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123-ABC-456-DEF");

        result = cobolCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123-ABC-456-DEF");

        result = cobolCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123-ABC-456-DEF");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(true, true, null, null);
        var result = cobolCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsPartAsWord {
      @Test
      void convertCamelCase() {
        var opts = new Options(false, false, null, null);
        var result = cobolCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("ABC-DEF-GH-IJK");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(false, false, "", "");
        var result = cobolCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("ABC-DEF-GH-IJK");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(false, false, null, null);
        var result = cobolCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("ABC-DEF-GHI");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(false, false, null, null);
        var result = cobolCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("ABC-DEF-GHI");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(false, false, null, null);
        var result = cobolCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("ABC-DEF-GHI");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(false, false, null, null);
        var result = cobolCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("ABC-DEF-GHI");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(false, false, null, null);
        var result = cobolCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("ABC-DEF-GHI");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(false, false, null, null);
        var result = cobolCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("ABC123-456DEF-G89HI-JKL-MN12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(false, false, null, null);
        var result = cobolCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo("ABC-DEF-GHI-JK-LM-NO");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(false, false, null, null);
        var result = cobolCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123ABC456DEF");

        result = cobolCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123ABC456DEF");

        result = cobolCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123-ABC456-DEF");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(false, false, null, null);
        var result = cobolCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsAsHeadOfWordAndWithSeparators {
      @Test
      void convertCamelCase() {
        var opts = new Options(true, false, "-_", null);
        var result = cobolCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("ABC-DEF-GH-IJK");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(true, false, "-_", "");
        var result = cobolCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("ABC-DEF-GH-IJK");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(true, false, "_", null);
        var result = cobolCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("ABC-DEF-GHI");

        opts = new Options(true, false, "-", null);
        result = cobolCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("ABC-_DEF-_GHI");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(true, false, "-", null);
        var result = cobolCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("ABC-DEF-GHI");

        opts = new Options(true, false, "_", null);
        result = cobolCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("ABC--DEF--GHI");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(true, false, "-", null);
        var result = cobolCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("ABC-DEF-GHI");

        opts = new Options(true, false, "_", null);
        result = cobolCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("ABC---DEF---GHI");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(true, false, "_", null);
        var result = cobolCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("ABC-DEF-GHI");

        opts = new Options(true, false, "-", null);
        result = cobolCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("ABC-_DEF-_GHI");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(true, false, "-", null);
        var result = cobolCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("ABC-DEF-GHI");

        opts = new Options(true, false, "_", null);
        result = cobolCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("ABC--DEF--GHI");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(true, false, "-", null);
        var result = cobolCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("ABC-123-456DEF-G-89HI-JKL-MN-12");

        opts = new Options(true, false, "_", null);
        result = cobolCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("ABC-123-456DEF-G-89HI-JKL-MN-12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(true, false, ":@$&()/", null);
        var result = cobolCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo(".ABC-~!-DEF-#-GHI-%-JK-LM-NO-?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(true, false, "-", null);
        var result = cobolCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123ABC-456DEF");

        result = cobolCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123ABC-456DEF");

        result = cobolCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123-ABC-456-DEF");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(true, false, null, null);
        var result = cobolCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }

      @Test
      void alphabetsAndNumbersInSeparatorsAreNoEffect() {
        var opts = new Options(true, false, "-b2", null);
        var result = cobolCaseWithOptions("abc123def", opts);
        assertThat(result).isEqualTo("ABC-123DEF");
      }
    }

    @Nested
    class NonAlphabetsAsTailOfWordAndWithSeparators {
      @Test
      void convertCamelCase() {
        var opts = new Options(false, true, "-_", null);
        var result = cobolCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("ABC-DEF-GH-IJK");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(false, true, "-_", "");
        var result = cobolCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("ABC-DEF-GH-IJK");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(false, true, "_", null);
        var result = cobolCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("ABC-DEF-GHI");

        opts = new Options(false, true, "-", null);
        result = cobolCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("ABC_-DEF_-GHI");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(false, true, "-", null);
        var result = cobolCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("ABC-DEF-GHI");

        opts = new Options(false, true, "_", null);
        result = cobolCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("ABC--DEF--GHI");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(false, true, "-", null);
        var result = cobolCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("ABC-DEF-GHI");

        opts = new Options(false, true, "_", null);
        result = cobolCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("ABC--DEF--GHI");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(false, true, "_", null);
        var result = cobolCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("ABC-DEF-GHI");

        opts = new Options(false, true, "-", null);
        result = cobolCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("ABC_-DEF_-GHI");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(false, true, "-", null);
        var result = cobolCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("ABC-DEF-GHI");

        opts = new Options(false, true, "_", null);
        result = cobolCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("ABC--DEF--GHI");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(false, true, "-", null);
        var result = cobolCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("ABC123-456-DEF-G89-HI-JKL-MN12");

        opts = new Options(false, true, "_", null);
        result = cobolCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("ABC123-456-DEF-G89-HI-JKL-MN12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(false, true, ":@$&()/", null);
        var result = cobolCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo(".-ABC~!-DEF#-GHI%-JK-LM-NO-?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(false, true, "-", null);
        var result = cobolCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123-ABC456-DEF");

        result = cobolCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123-ABC456-DEF");

        result = cobolCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123-ABC456-DEF");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(false, true, "-_", null);
        var result = cobolCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }

      @Test
      void alphabetsAndNumbersInSeparatorsAreNoEffect() {
        var opts = new Options(false, true, "-b2", null);
        var result = cobolCaseWithOptions("abc123def", opts);
        assertThat(result).isEqualTo("ABC123-DEF");
      }
    }

    @Nested
    class NonAlphabetsAsWordAndWithSeparators {
      @Test
      void convertCamelCase() {
        var opts = new Options(true, true, "-_", null);
        var result = cobolCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("ABC-DEF-GH-IJK");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(true, true, "-_", "");
        var result = cobolCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("ABC-DEF-GH-IJK");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(true, true, "_", null);
        var result = cobolCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("ABC-DEF-GHI");

        opts = new Options(true, true, "-", null);
        result = cobolCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("ABC-_-DEF-_-GHI");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(true, true, "-", null);
        var result = cobolCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("ABC-DEF-GHI");

        opts = new Options(true, true, "_", null);
        result = cobolCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("ABC---DEF---GHI");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(true, true, "-", null);
        var result = cobolCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("ABC-DEF-GHI");

        opts = new Options(true, true, "_", null);
        result = cobolCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("ABC---DEF---GHI");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(true, true, "_", null);
        var result = cobolCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("ABC-DEF-GHI");

        opts = new Options(true, true, "-", null);
        result = cobolCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("ABC-_-DEF-_-GHI");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(true, true, "-", null);
        var result = cobolCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("ABC-DEF-GHI");

        opts = new Options(true, true, "_", null);
        result = cobolCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("ABC---DEF---GHI");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(true, true, "-", null);
        var result = cobolCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("ABC-123-456-DEF-G-89-HI-JKL-MN-12");

        opts = new Options(true, true, "_", null);
        result = cobolCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("ABC-123-456-DEF-G-89-HI-JKL-MN-12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(true, true, ":@$&()/", null);
        var result = cobolCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo(".-ABC-~!-DEF-#-GHI-%-JK-LM-NO-?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(true, true, "-_", null);
        var result = cobolCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123-ABC-456-DEF");

        result = cobolCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123-ABC-456-DEF");

        result = cobolCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123-ABC-456-DEF");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(true, true, "-_", null);
        var result = cobolCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }

      @Test
      void alphabetsAndNumbersInSeparatorsAreNoEffect() {
        var opts = new Options(true, true, "-b2", null);
        var result = cobolCaseWithOptions("abc123def", opts);
        assertThat(result).isEqualTo("ABC-123-DEF");
      }
    }

    @Nested
    class NonAlphabetsAsPartOfWordAndWithSeparators {
      @Test
      void convertCamelCase() {
        var opts = new Options(false, false, "-_", null);
        var result = cobolCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("ABC-DEF-GH-IJK");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(false, false, "-_", "");
        var result = cobolCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("ABC-DEF-GH-IJK");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(false, false, "_", null);
        var result = cobolCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("ABC-DEF-GHI");

        opts = new Options(false, false, "-", null);
        result = cobolCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("ABC_DEF_GHI");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(false, false, "-", null);
        var result = cobolCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("ABC-DEF-GHI");

        opts = new Options(false, false, "_", null);
        result = cobolCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("ABC-DEF-GHI");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(false, false, "-", null);
        var result = cobolCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("ABC-DEF-GHI");

        opts = new Options(false, false, "_", null);
        result = cobolCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("ABC--DEF--GHI");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(false, false, "_", null);
        var result = cobolCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("ABC-DEF-GHI");

        opts = new Options(false, false, "-", null);
        result = cobolCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("ABC_DEF_GHI");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(false, false, "-", null);
        var result = cobolCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("ABC-DEF-GHI");

        opts = new Options(false, false, "_", null);
        result = cobolCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("ABC-DEF-GHI");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(false, false, "-", null);
        var result = cobolCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("ABC123-456DEF-G89HI-JKL-MN12");

        opts = new Options(false, false, "_", null);
        result = cobolCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("ABC123-456DEF-G89HI-JKL-MN12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(false, false, ":@$&()/", null);
        var result = cobolCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo(".ABC~!-DEF#-GHI%-JK-LM-NO-?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(false, false, "-_", null);
        var result = cobolCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123ABC456DEF");

        result = cobolCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123ABC456DEF");

        result = cobolCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123-ABC456-DEF");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(false, false, "-_", null);
        var result = cobolCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }

      @Test
      void alphabetsAndNumbersInSeparatorsAreNoEffect() {
        var opts = new Options(false, false, "-b2", null);
        var result = cobolCaseWithOptions("abc123def", opts);
        assertThat(result).isEqualTo("ABC123DEF");
      }
    }

    @Nested
    class NonAlphabetsAsHeadOfWordAndWithKeptCharacters {
      @Test
      void convertCamelCase() {
        var opts = new Options(true, false, null, "-_");
        var result = cobolCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("ABC-DEF-GH-IJK");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(true, false, "", "-_");
        var result = cobolCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("ABC-DEF-GH-IJK");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(true, false, null, "-");
        var result = cobolCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("ABC-DEF-GHI");

        opts = new Options(true, false, null, "_");
        result = cobolCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("ABC-_DEF-_GHI");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(true, false, null, "_");
        var result = cobolCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("ABC-DEF-GHI");

        opts = new Options(true, false, null, "-");
        result = cobolCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("ABC--DEF--GHI");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(true, false, null, "_");
        var result = cobolCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("ABC-DEF-GHI");

        opts = new Options(true, false, null, "-");
        result = cobolCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("ABC---DEF---GHI");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(true, false, null, "-");
        var result = cobolCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("ABC-DEF-GHI");

        opts = new Options(true, false, null, "_");
        result = cobolCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("ABC-_DEF-_GHI");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(true, false, null, "_");
        var result = cobolCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("ABC-DEF-GHI");

        opts = new Options(true, false, null, "-");
        result = cobolCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("ABC--DEF--GHI");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(true, false, null, "_");
        var result = cobolCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("ABC-123-456DEF-G-89HI-JKL-MN-12");

        opts = new Options(true, false, null, "-");
        result = cobolCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("ABC-123-456DEF-G-89HI-JKL-MN-12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(true, false, null, ".~!#%?");
        var result = cobolCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo(".ABC-~!-DEF-#-GHI-%-JK-LM-NO-?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(true, false, null, "-");
        var result = cobolCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123ABC-456DEF");

        result = cobolCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123ABC-456DEF");

        result = cobolCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123-ABC-456-DEF");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(true, false, null, "-_");
        var result = cobolCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsAsTailOfWordAndWithKeptCharacters {
      @Test
      void convertCamelCase() {
        var opts = new Options(false, true, null, "-_");
        var result = cobolCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("ABC-DEF-GH-IJK");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(false, true, "", "-_");
        var result = cobolCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("ABC-DEF-GH-IJK");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(false, true, null, "-");
        var result = cobolCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("ABC-DEF-GHI");

        opts = new Options(false, true, null, "_");
        result = cobolCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("ABC_-DEF_-GHI");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(false, true, null, "_");
        var result = cobolCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("ABC-DEF-GHI");

        opts = new Options(false, true, null, "-");
        result = cobolCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("ABC--DEF--GHI");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(false, true, null, "_");
        var result = cobolCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("ABC-DEF-GHI");

        opts = new Options(false, true, null, "-");
        result = cobolCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("ABC--DEF--GHI");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(false, true, null, "-");
        var result = cobolCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("ABC-DEF-GHI");

        opts = new Options(false, true, null, "_");
        result = cobolCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("ABC_-DEF_-GHI");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(false, true, null, "_");
        var result = cobolCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("ABC-DEF-GHI");

        opts = new Options(false, true, null, "-");
        result = cobolCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("ABC--DEF--GHI");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(false, true, null, "_");
        var result = cobolCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("ABC123-456-DEF-G89-HI-JKL-MN12");

        opts = new Options(false, true, null, "-");
        result = cobolCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("ABC123-456-DEF-G89-HI-JKL-MN12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(false, true, null, ".~!#%?");
        var result = cobolCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo(".-ABC~!-DEF#-GHI%-JK-LM-NO-?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(false, true, null, "_");
        var result = cobolCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123-ABC456-DEF");

        result = cobolCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123-ABC456-DEF");

        result = cobolCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123-ABC456-DEF");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(false, true, null, "-_");
        var result = cobolCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsAsWordAndWithKeptCharacters {
      @Test
      void convertCamelCase() {
        var opts = new Options(true, true, null, "-_");
        var result = cobolCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("ABC-DEF-GH-IJK");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(true, true, "", "-_");
        var result = cobolCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("ABC-DEF-GH-IJK");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(true, true, null, "-");
        var result = cobolCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("ABC-DEF-GHI");

        opts = new Options(true, true, null, "_");
        result = cobolCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("ABC-_-DEF-_-GHI");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(true, true, null, "_");
        var result = cobolCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("ABC-DEF-GHI");

        opts = new Options(true, true, null, "-");
        result = cobolCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("ABC---DEF---GHI");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(true, true, null, "_");
        var result = cobolCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("ABC-DEF-GHI");

        opts = new Options(true, true, null, "-");
        result = cobolCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("ABC---DEF---GHI");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(true, true, null, "-");
        var result = cobolCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("ABC-DEF-GHI");

        opts = new Options(true, true, null, "_");
        result = cobolCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("ABC-_-DEF-_-GHI");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(true, true, null, "_");
        var result = cobolCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("ABC-DEF-GHI");

        opts = new Options(true, true, null, "-");
        result = cobolCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("ABC---DEF---GHI");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(true, true, null, "_");
        var result = cobolCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("ABC-123-456-DEF-G-89-HI-JKL-MN-12");

        opts = new Options(true, true, null, "-");
        result = cobolCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("ABC-123-456-DEF-G-89-HI-JKL-MN-12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(true, true, null, ".~!#%?");
        var result = cobolCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo(".-ABC-~!-DEF-#-GHI-%-JK-LM-NO-?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(true, true, null, "-_");
        var result = cobolCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123-ABC-456-DEF");

        result = cobolCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123-ABC-456-DEF");

        result = cobolCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123-ABC-456-DEF");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(true, true, null, "-_");
        var result = cobolCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsAsPartOfWordAndWithKeptCharacters {
      @Test
      void convertCamelCase() {
        var opts = new Options(false, false, null, "-_");
        var result = cobolCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("ABC-DEF-GH-IJK");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(false, false, "", "-_");
        var result = cobolCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("ABC-DEF-GH-IJK");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(false, false, null, "-");
        var result = cobolCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("ABC-DEF-GHI");

        opts = new Options(false, false, null, "_");
        result = cobolCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("ABC_DEF_GHI");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(false, false, null, "_");
        var result = cobolCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("ABC-DEF-GHI");

        opts = new Options(false, false, null, "-");
        result = cobolCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("ABC-DEF-GHI");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(false, false, null, "_");
        var result = cobolCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("ABC-DEF-GHI");

        opts = new Options(false, false, null, "-");
        result = cobolCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("ABC--DEF--GHI");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(false, false, null, "-");
        var result = cobolCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("ABC-DEF-GHI");

        opts = new Options(false, false, null, "_");
        result = cobolCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("ABC_DEF_GHI");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(false, false, null, "_");
        var result = cobolCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("ABC-DEF-GHI");

        opts = new Options(false, false, null, "-");
        result = cobolCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("ABC-DEF-GHI");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(false, false, null, "_");
        var result = cobolCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("ABC123-456DEF-G89HI-JKL-MN12");

        opts = new Options(false, false, null, "-");
        result = cobolCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("ABC123-456DEF-G89HI-JKL-MN12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(false, false, null, ".~!#%?");
        var result = cobolCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo(".ABC~!-DEF#-GHI%-JK-LM-NO-?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(false, false, null, "-_");
        var result = cobolCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123ABC456DEF");

        result = cobolCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123ABC456DEF");

        result = cobolCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123-ABC456-DEF");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(false, false, null, "-_");
        var result = cobolCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }
    }
  }
}
