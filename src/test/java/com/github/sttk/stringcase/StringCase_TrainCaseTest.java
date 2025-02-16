package com.github.sttk.stringcase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import static com.github.sttk.stringcase.StringCase.*;

import static java.lang.Character.codePointAt;

@SuppressWarnings("missing-explicit-ctor")
public class StringCase_TrainCaseTest {

  @Nested
  class TrainCase {
    @Test
    void convertCamelCase() {
      var result = trainCase("abcDefGHIjk");
      assertThat(result).isEqualTo("Abc-Def-Gh-Ijk");
    }

    @Test
    void convertPascalCase() {
      var result = trainCase("AbcDefGHIjk");
      assertThat(result).isEqualTo("Abc-Def-Gh-Ijk");
    }

    @Test
    void convertSnakeCase() {
      var result = trainCase("abc_def_ghi");
      assertThat(result).isEqualTo("Abc-Def-Ghi");
    }

    @Test
    void convertKebabCase() {
      var result = trainCase("abc-def-ghi");
      assertThat(result).isEqualTo("Abc-Def-Ghi");
    }

    @Test
    void convertTrainCase() {
      var result = trainCase("Abc-Def-Ghi");
      assertThat(result).isEqualTo("Abc-Def-Ghi");
    }

    @Test
    void convertMacroCase() {
      var result = trainCase("ABC_DEF_GHI");
      assertThat(result).isEqualTo("Abc-Def-Ghi");
    }

    @Test
    void convertCobolCase() {
      var result = trainCase("ABC-DEF-GHI");
      assertThat(result).isEqualTo("Abc-Def-Ghi");
    }

    @Test
    void convertWithKeepingDigits() {
      var result = trainCase("abc123-456defG89HIJklMN12");
      assertThat(result).isEqualTo("Abc123-456-Def-G89-Hi-Jkl-Mn12");
    }

    @Test
    void convertWithSymbolsAsSeparators() {
   		var result = trainCase(":.abc~!@def#$ghi%&jk(lm)no/?");
  		assertThat(result).isEqualTo("Abc-Def-Ghi-Jk-Lm-No");
    }

    @Test
    void convertWhenStartingWithDigit() {
		  var result = trainCase("123abc456def");
		  assertThat(result).isEqualTo("123-Abc456-Def");

		  result = trainCase("123ABC456DEF");
		  assertThat(result).isEqualTo("123-Abc456-Def");

		  result = trainCase("123Abc456Def");
		  assertThat(result).isEqualTo("123-Abc456-Def");
    }

    @Test
    void convertAnEmptyString() {
		  var result = trainCase("");
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
        var result = trainCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("Abc-Def-Gh-Ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(true, false, "", "");
        var result = trainCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("Abc-Def-Gh-Ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(true, false, null, null);
        var result = trainCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(true, false, null, null);
        var result = trainCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(true, false, null, null);
        var result = trainCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(true, false, null, null);
        var result = trainCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(true, false, null, null);
        var result = trainCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(true, false, null, null);
        var result = trainCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc-123-456def-G-89hi-Jkl-Mn-12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(true, false, null, null);
        var result = trainCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi-Jk-Lm-No");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(true, false, null, null);
        var result = trainCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123abc-456def");

        result = trainCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123abc-456def");

        result = trainCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123-Abc-456-Def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(true, false, null, null);
        var result = trainCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsAsTailOfWord {
      @Test
      void convertCamelCase() {
        var opts = new Options(false, true, null, null);
        var result = trainCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("Abc-Def-Gh-Ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(false, true, "", "");
        var result = trainCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("Abc-Def-Gh-Ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(false, true, null, null);
        var result = trainCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(false, true, null, null);
        var result = trainCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(false, true, null, null);
        var result = trainCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(false, true, null, null);
        var result = trainCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(false, true, null, null);
        var result = trainCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(false, true, null, null);
        var result = trainCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc123-456-Def-G89-Hi-Jkl-Mn12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(false, true, null, null);
        var result = trainCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi-Jk-Lm-No");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(false, true, null, null);
        var result = trainCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123-Abc456-Def");

        result = trainCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123-Abc456-Def");

        result = trainCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123-Abc456-Def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(false, true, null, null);
        var result = trainCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsAsWord {
      @Test
      void convertCamelCase() {
        var opts = new Options(true, true, null, null);
        var result = trainCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("Abc-Def-Gh-Ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(true, true, "", "");
        var result = trainCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("Abc-Def-Gh-Ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(true, true, null, null);
        var result = trainCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(true, true, null, null);
        var result = trainCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(true, true, null, null);
        var result = trainCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(true, true, null, null);
        var result = trainCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(true, true, null, null);
        var result = trainCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(true, true, null, null);
        var result = trainCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc-123-456-Def-G-89-Hi-Jkl-Mn-12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(true, true, null, null);
        var result = trainCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi-Jk-Lm-No");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(true, true, null, null);
        var result = trainCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123-Abc-456-Def");

        result = trainCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123-Abc-456-Def");

        result = trainCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123-Abc-456-Def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(true, true, null, null);
        var result = trainCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsPartAsWord {
      @Test
      void convertCamelCase() {
        var opts = new Options(false, false, null, null);
        var result = trainCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("Abc-Def-Gh-Ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(false, false, "", "");
        var result = trainCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("Abc-Def-Gh-Ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(false, false, null, null);
        var result = trainCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(false, false, null, null);
        var result = trainCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(false, false, null, null);
        var result = trainCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(false, false, null, null);
        var result = trainCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(false, false, null, null);
        var result = trainCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(false, false, null, null);
        var result = trainCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc123-456def-G89hi-Jkl-Mn12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(false, false, null, null);
        var result = trainCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi-Jk-Lm-No");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(false, false, null, null);
        var result = trainCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123abc456def");

        result = trainCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123abc456def");

        result = trainCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123-Abc456-Def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(false, false, null, null);
        var result = trainCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsAsHeadOfWordAndWithSeparators {
      @Test
      void convertCamelCase() {
        var opts = new Options(true, false, "-_", null);
        var result = trainCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("Abc-Def-Gh-Ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(true, false, "-_", "");
        var result = trainCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("Abc-Def-Gh-Ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(true, false, "_", null);
        var result = trainCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");

        opts = new Options(true, false, "-", null);
        result = trainCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("Abc-_def-_ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(true, false, "-", null);
        var result = trainCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");

        opts = new Options(true, false, "_", null);
        result = trainCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("Abc--def--ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(true, false, "-", null);
        var result = trainCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");

        opts = new Options(true, false, "_", null);
        result = trainCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("Abc---Def---Ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(true, false, "_", null);
        var result = trainCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");

        opts = new Options(true, false, "-", null);
        result = trainCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("Abc-_def-_ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(true, false, "-", null);
        var result = trainCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");

        opts = new Options(true, false, "_", null);
        result = trainCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("Abc--def--ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(true, false, "-", null);
        var result = trainCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc-123-456def-G-89hi-Jkl-Mn-12");

        opts = new Options(true, false, "_", null);
        result = trainCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc-123-456def-G-89hi-Jkl-Mn-12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(true, false, ":@$&()/", null);
        var result = trainCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo(".abc-~!-Def-#-Ghi-%-Jk-Lm-No-?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(true, false, "-", null);
        var result = trainCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123abc-456def");

        result = trainCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123abc-456def");

        result = trainCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123-Abc-456-Def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(true, false, null, null);
        var result = trainCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }

      @Test
      void alphabetsAndNumbersInSeparatorsAreNoEffect() {
        var opts = new Options(true, false, "-b2", null);
        var result = trainCaseWithOptions("abc123def", opts);
        assertThat(result).isEqualTo("Abc-123def");
      }
    }

    @Nested
    class NonAlphabetsAsTailOfWordAndWithSeparators {
      @Test
      void convertCamelCase() {
        var opts = new Options(false, true, "-_", null);
        var result = trainCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("Abc-Def-Gh-Ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(false, true, "-_", "");
        var result = trainCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("Abc-Def-Gh-Ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(false, true, "_", null);
        var result = trainCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");

        opts = new Options(false, true, "-", null);
        result = trainCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("Abc_-Def_-Ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(false, true, "-", null);
        var result = trainCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");

        opts = new Options(false, true, "_", null);
        result = trainCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("Abc--Def--Ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(false, true, "-", null);
        var result = trainCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");

        opts = new Options(false, true, "_", null);
        result = trainCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("Abc--Def--Ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(false, true, "_", null);
        var result = trainCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");

        opts = new Options(false, true, "-", null);
        result = trainCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("Abc_-Def_-Ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(false, true, "-", null);
        var result = trainCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");

        opts = new Options(false, true, "_", null);
        result = trainCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("Abc--Def--Ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(false, true, "-", null);
        var result = trainCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc123-456-Def-G89-Hi-Jkl-Mn12");

        opts = new Options(false, true, "_", null);
        result = trainCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc123-456-Def-G89-Hi-Jkl-Mn12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(false, true, ":@$&()/", null);
        var result = trainCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo(".-Abc~!-Def#-Ghi%-Jk-Lm-No-?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(false, true, "-", null);
        var result = trainCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123-Abc456-Def");

        result = trainCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123-Abc456-Def");

        result = trainCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123-Abc456-Def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(false, true, "-_", null);
        var result = trainCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }

      @Test
      void alphabetsAndNumbersInSeparatorsAreNoEffect() {
        var opts = new Options(false, true, "-b2", null);
        var result = trainCaseWithOptions("abc123def", opts);
        assertThat(result).isEqualTo("Abc123-Def");
      }
    }

    @Nested
    class NonAlphabetsAsWordAndWithSeparators {
      @Test
      void convertCamelCase() {
        var opts = new Options(true, true, "-_", null);
        var result = trainCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("Abc-Def-Gh-Ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(true, true, "-_", "");
        var result = trainCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("Abc-Def-Gh-Ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(true, true, "_", null);
        var result = trainCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");

        opts = new Options(true, true, "-", null);
        result = trainCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("Abc-_-Def-_-Ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(true, true, "-", null);
        var result = trainCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");

        opts = new Options(true, true, "_", null);
        result = trainCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("Abc---Def---Ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(true, true, "-", null);
        var result = trainCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");

        opts = new Options(true, true, "_", null);
        result = trainCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("Abc---Def---Ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(true, true, "_", null);
        var result = trainCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");

        opts = new Options(true, true, "-", null);
        result = trainCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("Abc-_-Def-_-Ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(true, true, "-", null);
        var result = trainCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");

        opts = new Options(true, true, "_", null);
        result = trainCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("Abc---Def---Ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(true, true, "-", null);
        var result = trainCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc-123-456-Def-G-89-Hi-Jkl-Mn-12");

        opts = new Options(true, true, "_", null);
        result = trainCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc-123-456-Def-G-89-Hi-Jkl-Mn-12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(true, true, ":@$&()/", null);
        var result = trainCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo(".-Abc-~!-Def-#-Ghi-%-Jk-Lm-No-?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(true, true, "-_", null);
        var result = trainCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123-Abc-456-Def");

        result = trainCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123-Abc-456-Def");

        result = trainCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123-Abc-456-Def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(true, true, "-_", null);
        var result = trainCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }

      @Test
      void alphabetsAndNumbersInSeparatorsAreNoEffect() {
        var opts = new Options(true, true, "-b2", null);
        var result = trainCaseWithOptions("abc123def", opts);
        assertThat(result).isEqualTo("Abc-123-Def");
      }
    }

    @Nested
    class NonAlphabetsAsPartOfWordAndWithSeparators {
      @Test
      void convertCamelCase() {
        var opts = new Options(false, false, "-_", null);
        var result = trainCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("Abc-Def-Gh-Ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(false, false, "-_", "");
        var result = trainCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("Abc-Def-Gh-Ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(false, false, "_", null);
        var result = trainCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");

        opts = new Options(false, false, "-", null);
        result = trainCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("Abc_def_ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(false, false, "-", null);
        var result = trainCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");

        opts = new Options(false, false, "_", null);
        result = trainCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("Abc-def-ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(false, false, "-", null);
        var result = trainCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");

        opts = new Options(false, false, "_", null);
        result = trainCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("Abc--Def--Ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(false, false, "_", null);
        var result = trainCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");

        opts = new Options(false, false, "-", null);
        result = trainCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("Abc_def_ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(false, false, "-", null);
        var result = trainCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");

        opts = new Options(false, false, "_", null);
        result = trainCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("Abc-def-ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(false, false, "-", null);
        var result = trainCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc123-456def-G89hi-Jkl-Mn12");

        opts = new Options(false, false, "_", null);
        result = trainCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc123-456def-G89hi-Jkl-Mn12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(false, false, ":@$&()/", null);
        var result = trainCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo(".abc~!-Def#-Ghi%-Jk-Lm-No-?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(false, false, "-_", null);
        var result = trainCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123abc456def");

        result = trainCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123abc456def");

        result = trainCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123-Abc456-Def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(false, false, "-_", null);
        var result = trainCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }

      @Test
      void alphabetsAndNumbersInSeparatorsAreNoEffect() {
        var opts = new Options(false, false, "-b2", null);
        var result = trainCaseWithOptions("abc123def", opts);
        assertThat(result).isEqualTo("Abc123def");
      }
    }

    @Nested
    class NonAlphabetsAsHeadOfWordAndWithKeptCharacters {
      @Test
      void convertCamelCase() {
        var opts = new Options(true, false, null, "-_");
        var result = trainCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("Abc-Def-Gh-Ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(true, false, "", "-_");
        var result = trainCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("Abc-Def-Gh-Ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(true, false, null, "-");
        var result = trainCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");

        opts = new Options(true, false, null, "_");
        result = trainCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("Abc-_def-_ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(true, false, null, "_");
        var result = trainCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");

        opts = new Options(true, false, null, "-");
        result = trainCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("Abc--def--ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(true, false, null, "_");
        var result = trainCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");

        opts = new Options(true, false, null, "-");
        result = trainCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("Abc---Def---Ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(true, false, null, "-");
        var result = trainCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");

        opts = new Options(true, false, null, "_");
        result = trainCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("Abc-_def-_ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(true, false, null, "_");
        var result = trainCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");

        opts = new Options(true, false, null, "-");
        result = trainCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("Abc--def--ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(true, false, null, "_");
        var result = trainCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc-123-456def-G-89hi-Jkl-Mn-12");

        opts = new Options(true, false, null, "-");
        result = trainCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc-123-456def-G-89hi-Jkl-Mn-12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(true, false, null, ".~!#%?");
        var result = trainCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo(".abc-~!-Def-#-Ghi-%-Jk-Lm-No-?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(true, false, null, "-");
        var result = trainCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123abc-456def");

        result = trainCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123abc-456def");

        result = trainCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123-Abc-456-Def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(true, false, null, "-_");
        var result = trainCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsAsTailOfWordAndWithKeptCharacters {
      @Test
      void convertCamelCase() {
        var opts = new Options(false, true, null, "-_");
        var result = trainCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("Abc-Def-Gh-Ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(false, true, "", "-_");
        var result = trainCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("Abc-Def-Gh-Ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(false, true, null, "-");
        var result = trainCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");

        opts = new Options(false, true, null, "_");
        result = trainCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("Abc_-Def_-Ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(false, true, null, "_");
        var result = trainCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");

        opts = new Options(false, true, null, "-");
        result = trainCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("Abc--Def--Ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(false, true, null, "_");
        var result = trainCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");

        opts = new Options(false, true, null, "-");
        result = trainCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("Abc--Def--Ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(false, true, null, "-");
        var result = trainCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");

        opts = new Options(false, true, null, "_");
        result = trainCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("Abc_-Def_-Ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(false, true, null, "_");
        var result = trainCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");

        opts = new Options(false, true, null, "-");
        result = trainCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("Abc--Def--Ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(false, true, null, "_");
        var result = trainCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc123-456-Def-G89-Hi-Jkl-Mn12");

        opts = new Options(false, true, null, "-");
        result = trainCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc123-456-Def-G89-Hi-Jkl-Mn12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(false, true, null, ".~!#%?");
        var result = trainCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo(".-Abc~!-Def#-Ghi%-Jk-Lm-No-?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(false, true, null, "_");
        var result = trainCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123-Abc456-Def");

        result = trainCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123-Abc456-Def");

        result = trainCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123-Abc456-Def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(false, true, null, "-_");
        var result = trainCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsAsWordAndWithKeptCharacters {
      @Test
      void convertCamelCase() {
        var opts = new Options(true, true, null, "-_");
        var result = trainCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("Abc-Def-Gh-Ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(true, true, "", "-_");
        var result = trainCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("Abc-Def-Gh-Ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(true, true, null, "-");
        var result = trainCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");

        opts = new Options(true, true, null, "_");
        result = trainCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("Abc-_-Def-_-Ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(true, true, null, "_");
        var result = trainCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");

        opts = new Options(true, true, null, "-");
        result = trainCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("Abc---Def---Ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(true, true, null, "_");
        var result = trainCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");

        opts = new Options(true, true, null, "-");
        result = trainCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("Abc---Def---Ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(true, true, null, "-");
        var result = trainCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");

        opts = new Options(true, true, null, "_");
        result = trainCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("Abc-_-Def-_-Ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(true, true, null, "_");
        var result = trainCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");

        opts = new Options(true, true, null, "-");
        result = trainCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("Abc---Def---Ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(true, true, null, "_");
        var result = trainCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc-123-456-Def-G-89-Hi-Jkl-Mn-12");

        opts = new Options(true, true, null, "-");
        result = trainCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc-123-456-Def-G-89-Hi-Jkl-Mn-12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(true, true, null, ".~!#%?");
        var result = trainCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo(".-Abc-~!-Def-#-Ghi-%-Jk-Lm-No-?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(true, true, null, "-_");
        var result = trainCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123-Abc-456-Def");

        result = trainCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123-Abc-456-Def");

        result = trainCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123-Abc-456-Def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(true, true, null, "-_");
        var result = trainCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsAsPartOfWordAndWithKeptCharacters {
      @Test
      void convertCamelCase() {
        var opts = new Options(false, false, null, "-_");
        var result = trainCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("Abc-Def-Gh-Ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(false, false, "", "-_");
        var result = trainCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("Abc-Def-Gh-Ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(false, false, null, "-");
        var result = trainCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");

        opts = new Options(false, false, null, "_");
        result = trainCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("Abc_def_ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(false, false, null, "_");
        var result = trainCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");

        opts = new Options(false, false, null, "-");
        result = trainCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("Abc-def-ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(false, false, null, "_");
        var result = trainCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");

        opts = new Options(false, false, null, "-");
        result = trainCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("Abc--Def--Ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(false, false, null, "-");
        var result = trainCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");

        opts = new Options(false, false, null, "_");
        result = trainCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("Abc_def_ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(false, false, null, "_");
        var result = trainCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("Abc-Def-Ghi");

        opts = new Options(false, false, null, "-");
        result = trainCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("Abc-def-ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(false, false, null, "_");
        var result = trainCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc123-456def-G89hi-Jkl-Mn12");

        opts = new Options(false, false, null, "-");
        result = trainCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("Abc123-456def-G89hi-Jkl-Mn12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(false, false, null, ".~!#%?");
        var result = trainCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo(".abc~!-Def#-Ghi%-Jk-Lm-No-?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(false, false, null, "-_");
        var result = trainCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123abc456def");

        result = trainCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123abc456def");

        result = trainCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123-Abc456-Def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(false, false, null, "-_");
        var result = trainCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }
    }
  }
}
