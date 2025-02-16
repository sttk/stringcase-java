package com.github.sttk.stringcase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import static com.github.sttk.stringcase.StringCase.*;

import static java.lang.Character.codePointAt;

@SuppressWarnings("missing-explicit-ctor")
public class StringCase_KebabCaseTest {

  @Nested
  class KebabCase {
    @Test
    void convertCamelCase() {
      var result = kebabCase("abcDefGHIjk");
      assertThat(result).isEqualTo("abc-def-gh-ijk");
    }

    @Test
    void convertPascalCase() {
      var result = kebabCase("AbcDefGHIjk");
      assertThat(result).isEqualTo("abc-def-gh-ijk");
    }

    @Test
    void convertSnakeCase() {
      var result = kebabCase("abc_def_ghi");
      assertThat(result).isEqualTo("abc-def-ghi");
    }

    @Test
    void convertKebabCase() {
      var result = kebabCase("abc-def-ghi");
      assertThat(result).isEqualTo("abc-def-ghi");
    }

    @Test
    void convertTrainCase() {
      var result = kebabCase("Abc-Def-Ghi");
      assertThat(result).isEqualTo("abc-def-ghi");
    }

    @Test
    void convertMacroCase() {
      var result = kebabCase("ABC_DEF_GHI");
      assertThat(result).isEqualTo("abc-def-ghi");
    }

    @Test
    void convertCobolCase() {
      var result = kebabCase("ABC-DEF-GHI");
      assertThat(result).isEqualTo("abc-def-ghi");
    }

    @Test
    void convertWithKeepingDigits() {
      var result = kebabCase("abc123-456defG89HIJklMN12");
      assertThat(result).isEqualTo("abc123-456-def-g89-hi-jkl-mn12");
    }

    @Test
    void convertWithSymbolsAsSeparators() {
   		var result = kebabCase(":.abc~!@def#$ghi%&jk(lm)no/?");
  		assertThat(result).isEqualTo("abc-def-ghi-jk-lm-no");
    }

    @Test
    void convertWhenStartingWithDigit() {
		  var result = kebabCase("123abc456def");
		  assertThat(result).isEqualTo("123-abc456-def");

		  result = kebabCase("123ABC456DEF");
		  assertThat(result).isEqualTo("123-abc456-def");

		  result = kebabCase("123Abc456Def");
		  assertThat(result).isEqualTo("123-abc456-def");
    }

    @Test
    void convertAnEmptyString() {
		  var result = kebabCase("");
		  assertThat(result).isEqualTo("");
    }
  }

  @Nested
  class KebabCaseWithOptions {
    @Nested
    class NonAlphabetsAsHeadOfWord {
      @Test
      void convertCamelCase() {
        var opts = new Options(true, false, null, null);
        var result = kebabCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("abc-def-gh-ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(true, false, "", "");
        var result = kebabCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("abc-def-gh-ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(true, false, null, null);
        var result = kebabCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("abc-def-ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(true, false, null, null);
        var result = kebabCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("abc-def-ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(true, false, null, null);
        var result = kebabCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("abc-def-ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(true, false, null, null);
        var result = kebabCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("abc-def-ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(true, false, null, null);
        var result = kebabCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("abc-def-ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(true, false, null, null);
        var result = kebabCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("abc-123-456def-g-89hi-jkl-mn-12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(true, false, null, null);
        var result = kebabCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo("abc-def-ghi-jk-lm-no");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(true, false, null, null);
        var result = kebabCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123abc-456def");

        result = kebabCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123abc-456def");

        result = kebabCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123-abc-456-def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(true, false, null, null);
        var result = kebabCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsAsTailOfWord {
      @Test
      void convertCamelCase() {
        var opts = new Options(false, true, null, null);
        var result = kebabCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("abc-def-gh-ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(false, true, "", "");
        var result = kebabCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("abc-def-gh-ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(false, true, null, null);
        var result = kebabCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("abc-def-ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(false, true, null, null);
        var result = kebabCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("abc-def-ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(false, true, null, null);
        var result = kebabCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("abc-def-ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(false, true, null, null);
        var result = kebabCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("abc-def-ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(false, true, null, null);
        var result = kebabCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("abc-def-ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(false, true, null, null);
        var result = kebabCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("abc123-456-def-g89-hi-jkl-mn12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(false, true, null, null);
        var result = kebabCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo("abc-def-ghi-jk-lm-no");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(false, true, null, null);
        var result = kebabCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123-abc456-def");

        result = kebabCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123-abc456-def");

        result = kebabCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123-abc456-def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(false, true, null, null);
        var result = kebabCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsAsWord {
      @Test
      void convertCamelCase() {
        var opts = new Options(true, true, null, null);
        var result = kebabCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("abc-def-gh-ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(true, true, "", "");
        var result = kebabCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("abc-def-gh-ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(true, true, null, null);
        var result = kebabCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("abc-def-ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(true, true, null, null);
        var result = kebabCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("abc-def-ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(true, true, null, null);
        var result = kebabCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("abc-def-ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(true, true, null, null);
        var result = kebabCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("abc-def-ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(true, true, null, null);
        var result = kebabCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("abc-def-ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(true, true, null, null);
        var result = kebabCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("abc-123-456-def-g-89-hi-jkl-mn-12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(true, true, null, null);
        var result = kebabCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo("abc-def-ghi-jk-lm-no");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(true, true, null, null);
        var result = kebabCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123-abc-456-def");

        result = kebabCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123-abc-456-def");

        result = kebabCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123-abc-456-def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(true, true, null, null);
        var result = kebabCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsPartAsWord {
      @Test
      void convertCamelCase() {
        var opts = new Options(false, false, null, null);
        var result = kebabCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("abc-def-gh-ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(false, false, "", "");
        var result = kebabCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("abc-def-gh-ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(false, false, null, null);
        var result = kebabCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("abc-def-ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(false, false, null, null);
        var result = kebabCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("abc-def-ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(false, false, null, null);
        var result = kebabCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("abc-def-ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(false, false, null, null);
        var result = kebabCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("abc-def-ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(false, false, null, null);
        var result = kebabCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("abc-def-ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(false, false, null, null);
        var result = kebabCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("abc123-456def-g89hi-jkl-mn12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(false, false, null, null);
        var result = kebabCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo("abc-def-ghi-jk-lm-no");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(false, false, null, null);
        var result = kebabCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123abc456def");

        result = kebabCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123abc456def");

        result = kebabCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123-abc456-def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(false, false, null, null);
        var result = kebabCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsAsHeadOfWordAndWithSeparators {
      @Test
      void convertCamelCase() {
        var opts = new Options(true, false, "-_", null);
        var result = kebabCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("abc-def-gh-ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(true, false, "-_", "");
        var result = kebabCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("abc-def-gh-ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(true, false, "_", null);
        var result = kebabCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("abc-def-ghi");

        opts = new Options(true, false, "-", null);
        result = kebabCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("abc-_def-_ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(true, false, "-", null);
        var result = kebabCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("abc-def-ghi");

        opts = new Options(true, false, "_", null);
        result = kebabCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("abc--def--ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(true, false, "-", null);
        var result = kebabCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("abc-def-ghi");

        opts = new Options(true, false, "_", null);
        result = kebabCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("abc---def---ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(true, false, "_", null);
        var result = kebabCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("abc-def-ghi");

        opts = new Options(true, false, "-", null);
        result = kebabCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("abc-_def-_ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(true, false, "-", null);
        var result = kebabCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("abc-def-ghi");

        opts = new Options(true, false, "_", null);
        result = kebabCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("abc--def--ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(true, false, "-", null);
        var result = kebabCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("abc-123-456def-g-89hi-jkl-mn-12");

        opts = new Options(true, false, "_", null);
        result = kebabCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("abc-123-456def-g-89hi-jkl-mn-12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(true, false, ":@$&()/", null);
        var result = kebabCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo(".abc-~!-def-#-ghi-%-jk-lm-no-?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(true, false, "-", null);
        var result = kebabCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123abc-456def");

        result = kebabCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123abc-456def");

        result = kebabCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123-abc-456-def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(true, false, null, null);
        var result = kebabCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }

      @Test
      void alphabetsAndNumbersInSeparatorsAreNoEffect() {
        var opts = new Options(true, false, "-b2", null);
        var result = kebabCaseWithOptions("abc123def", opts);
        assertThat(result).isEqualTo("abc-123def");
      }
    }

    @Nested
    class NonAlphabetsAsTailOfWordAndWithSeparators {
      @Test
      void convertCamelCase() {
        var opts = new Options(false, true, "-_", null);
        var result = kebabCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("abc-def-gh-ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(false, true, "-_", "");
        var result = kebabCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("abc-def-gh-ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(false, true, "_", null);
        var result = kebabCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("abc-def-ghi");

        opts = new Options(false, true, "-", null);
        result = kebabCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("abc_-def_-ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(false, true, "-", null);
        var result = kebabCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("abc-def-ghi");

        opts = new Options(false, true, "_", null);
        result = kebabCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("abc--def--ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(false, true, "-", null);
        var result = kebabCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("abc-def-ghi");

        opts = new Options(false, true, "_", null);
        result = kebabCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("abc--def--ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(false, true, "_", null);
        var result = kebabCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("abc-def-ghi");

        opts = new Options(false, true, "-", null);
        result = kebabCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("abc_-def_-ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(false, true, "-", null);
        var result = kebabCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("abc-def-ghi");

        opts = new Options(false, true, "_", null);
        result = kebabCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("abc--def--ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(false, true, "-", null);
        var result = kebabCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("abc123-456-def-g89-hi-jkl-mn12");

        opts = new Options(false, true, "_", null);
        result = kebabCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("abc123-456-def-g89-hi-jkl-mn12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(false, true, ":@$&()/", null);
        var result = kebabCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo(".-abc~!-def#-ghi%-jk-lm-no-?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(false, true, "-", null);
        var result = kebabCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123-abc456-def");

        result = kebabCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123-abc456-def");

        result = kebabCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123-abc456-def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(false, true, "-_", null);
        var result = kebabCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }

      @Test
      void alphabetsAndNumbersInSeparatorsAreNoEffect() {
        var opts = new Options(false, true, "-b2", null);
        var result = kebabCaseWithOptions("abc123def", opts);
        assertThat(result).isEqualTo("abc123-def");
      }
    }

    @Nested
    class NonAlphabetsAsWordAndWithSeparators {
      @Test
      void convertCamelCase() {
        var opts = new Options(true, true, "-_", null);
        var result = kebabCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("abc-def-gh-ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(true, true, "-_", "");
        var result = kebabCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("abc-def-gh-ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(true, true, "_", null);
        var result = kebabCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("abc-def-ghi");

        opts = new Options(true, true, "-", null);
        result = kebabCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("abc-_-def-_-ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(true, true, "-", null);
        var result = kebabCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("abc-def-ghi");

        opts = new Options(true, true, "_", null);
        result = kebabCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("abc---def---ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(true, true, "-", null);
        var result = kebabCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("abc-def-ghi");

        opts = new Options(true, true, "_", null);
        result = kebabCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("abc---def---ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(true, true, "_", null);
        var result = kebabCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("abc-def-ghi");

        opts = new Options(true, true, "-", null);
        result = kebabCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("abc-_-def-_-ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(true, true, "-", null);
        var result = kebabCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("abc-def-ghi");

        opts = new Options(true, true, "_", null);
        result = kebabCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("abc---def---ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(true, true, "-", null);
        var result = kebabCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("abc-123-456-def-g-89-hi-jkl-mn-12");

        opts = new Options(true, true, "_", null);
        result = kebabCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("abc-123-456-def-g-89-hi-jkl-mn-12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(true, true, ":@$&()/", null);
        var result = kebabCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo(".-abc-~!-def-#-ghi-%-jk-lm-no-?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(true, true, "-_", null);
        var result = kebabCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123-abc-456-def");

        result = kebabCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123-abc-456-def");

        result = kebabCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123-abc-456-def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(true, true, "-_", null);
        var result = kebabCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }

      @Test
      void alphabetsAndNumbersInSeparatorsAreNoEffect() {
        var opts = new Options(true, true, "-b2", null);
        var result = kebabCaseWithOptions("abc123def", opts);
        assertThat(result).isEqualTo("abc-123-def");
      }
    }

    @Nested
    class NonAlphabetsAsPartOfWordAndWithSeparators {
      @Test
      void convertCamelCase() {
        var opts = new Options(false, false, "-_", null);
        var result = kebabCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("abc-def-gh-ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(false, false, "-_", "");
        var result = kebabCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("abc-def-gh-ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(false, false, "_", null);
        var result = kebabCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("abc-def-ghi");

        opts = new Options(false, false, "-", null);
        result = kebabCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("abc_def_ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(false, false, "-", null);
        var result = kebabCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("abc-def-ghi");

        opts = new Options(false, false, "_", null);
        result = kebabCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("abc-def-ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(false, false, "-", null);
        var result = kebabCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("abc-def-ghi");

        opts = new Options(false, false, "_", null);
        result = kebabCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("abc--def--ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(false, false, "_", null);
        var result = kebabCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("abc-def-ghi");

        opts = new Options(false, false, "-", null);
        result = kebabCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("abc_def_ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(false, false, "-", null);
        var result = kebabCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("abc-def-ghi");

        opts = new Options(false, false, "_", null);
        result = kebabCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("abc-def-ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(false, false, "-", null);
        var result = kebabCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("abc123-456def-g89hi-jkl-mn12");

        opts = new Options(false, false, "_", null);
        result = kebabCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("abc123-456def-g89hi-jkl-mn12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(false, false, ":@$&()/", null);
        var result = kebabCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo(".abc~!-def#-ghi%-jk-lm-no-?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(false, false, "-_", null);
        var result = kebabCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123abc456def");

        result = kebabCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123abc456def");

        result = kebabCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123-abc456-def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(false, false, "-_", null);
        var result = kebabCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }

      @Test
      void alphabetsAndNumbersInSeparatorsAreNoEffect() {
        var opts = new Options(false, false, "-b2", null);
        var result = kebabCaseWithOptions("abc123def", opts);
        assertThat(result).isEqualTo("abc123def");
      }
    }

    @Nested
    class NonAlphabetsAsHeadOfWordAndWithKeptCharacters {
      @Test
      void convertCamelCase() {
        var opts = new Options(true, false, null, "-_");
        var result = kebabCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("abc-def-gh-ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(true, false, "", "-_");
        var result = kebabCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("abc-def-gh-ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(true, false, null, "-");
        var result = kebabCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("abc-def-ghi");

        opts = new Options(true, false, null, "_");
        result = kebabCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("abc-_def-_ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(true, false, null, "_");
        var result = kebabCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("abc-def-ghi");

        opts = new Options(true, false, null, "-");
        result = kebabCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("abc--def--ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(true, false, null, "_");
        var result = kebabCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("abc-def-ghi");

        opts = new Options(true, false, null, "-");
        result = kebabCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("abc---def---ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(true, false, null, "-");
        var result = kebabCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("abc-def-ghi");

        opts = new Options(true, false, null, "_");
        result = kebabCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("abc-_def-_ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(true, false, null, "_");
        var result = kebabCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("abc-def-ghi");

        opts = new Options(true, false, null, "-");
        result = kebabCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("abc--def--ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(true, false, null, "_");
        var result = kebabCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("abc-123-456def-g-89hi-jkl-mn-12");

        opts = new Options(true, false, null, "-");
        result = kebabCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("abc-123-456def-g-89hi-jkl-mn-12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(true, false, null, ".~!#%?");
        var result = kebabCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo(".abc-~!-def-#-ghi-%-jk-lm-no-?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(true, false, null, "-");
        var result = kebabCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123abc-456def");

        result = kebabCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123abc-456def");

        result = kebabCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123-abc-456-def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(true, false, null, "-_");
        var result = kebabCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsAsTailOfWordAndWithKeptCharacters {
      @Test
      void convertCamelCase() {
        var opts = new Options(false, true, null, "-_");
        var result = kebabCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("abc-def-gh-ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(false, true, "", "-_");
        var result = kebabCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("abc-def-gh-ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(false, true, null, "-");
        var result = kebabCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("abc-def-ghi");

        opts = new Options(false, true, null, "_");
        result = kebabCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("abc_-def_-ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(false, true, null, "_");
        var result = kebabCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("abc-def-ghi");

        opts = new Options(false, true, null, "-");
        result = kebabCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("abc--def--ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(false, true, null, "_");
        var result = kebabCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("abc-def-ghi");

        opts = new Options(false, true, null, "-");
        result = kebabCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("abc--def--ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(false, true, null, "-");
        var result = kebabCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("abc-def-ghi");

        opts = new Options(false, true, null, "_");
        result = kebabCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("abc_-def_-ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(false, true, null, "_");
        var result = kebabCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("abc-def-ghi");

        opts = new Options(false, true, null, "-");
        result = kebabCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("abc--def--ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(false, true, null, "_");
        var result = kebabCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("abc123-456-def-g89-hi-jkl-mn12");

        opts = new Options(false, true, null, "-");
        result = kebabCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("abc123-456-def-g89-hi-jkl-mn12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(false, true, null, ".~!#%?");
        var result = kebabCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo(".-abc~!-def#-ghi%-jk-lm-no-?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(false, true, null, "_");
        var result = kebabCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123-abc456-def");

        result = kebabCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123-abc456-def");

        result = kebabCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123-abc456-def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(false, true, null, "-_");
        var result = kebabCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsAsWordAndWithKeptCharacters {
      @Test
      void convertCamelCase() {
        var opts = new Options(true, true, null, "-_");
        var result = kebabCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("abc-def-gh-ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(true, true, "", "-_");
        var result = kebabCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("abc-def-gh-ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(true, true, null, "-");
        var result = kebabCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("abc-def-ghi");

        opts = new Options(true, true, null, "_");
        result = kebabCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("abc-_-def-_-ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(true, true, null, "_");
        var result = kebabCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("abc-def-ghi");

        opts = new Options(true, true, null, "-");
        result = kebabCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("abc---def---ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(true, true, null, "_");
        var result = kebabCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("abc-def-ghi");

        opts = new Options(true, true, null, "-");
        result = kebabCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("abc---def---ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(true, true, null, "-");
        var result = kebabCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("abc-def-ghi");

        opts = new Options(true, true, null, "_");
        result = kebabCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("abc-_-def-_-ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(true, true, null, "_");
        var result = kebabCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("abc-def-ghi");

        opts = new Options(true, true, null, "-");
        result = kebabCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("abc---def---ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(true, true, null, "_");
        var result = kebabCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("abc-123-456-def-g-89-hi-jkl-mn-12");

        opts = new Options(true, true, null, "-");
        result = kebabCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("abc-123-456-def-g-89-hi-jkl-mn-12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(true, true, null, ".~!#%?");
        var result = kebabCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo(".-abc-~!-def-#-ghi-%-jk-lm-no-?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(true, true, null, "-_");
        var result = kebabCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123-abc-456-def");

        result = kebabCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123-abc-456-def");

        result = kebabCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123-abc-456-def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(true, true, null, "-_");
        var result = kebabCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }
    }

    @Nested
    class NonAlphabetsAsPartOfWordAndWithKeptCharacters {
      @Test
      void convertCamelCase() {
        var opts = new Options(false, false, null, "-_");
        var result = kebabCaseWithOptions("abcDefGHIjk", opts);
        assertThat(result).isEqualTo("abc-def-gh-ijk");
      }

      @Test
      void convertPascalCase() {
        var opts = new Options(false, false, "", "-_");
        var result = kebabCaseWithOptions("AbcDefGHIjk", opts);
        assertThat(result).isEqualTo("abc-def-gh-ijk");
      }

      @Test
      void convertSnakeCase() {
        var opts = new Options(false, false, null, "-");
        var result = kebabCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("abc-def-ghi");

        opts = new Options(false, false, null, "_");
        result = kebabCaseWithOptions("abc_def_ghi", opts);
        assertThat(result).isEqualTo("abc_def_ghi");
      }

      @Test
      void convertKebabCase() {
        var opts = new Options(false, false, null, "_");
        var result = kebabCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("abc-def-ghi");

        opts = new Options(false, false, null, "-");
        result = kebabCaseWithOptions("abc-def-ghi", opts);
        assertThat(result).isEqualTo("abc-def-ghi");
      }

      @Test
      void convertTrainCase() {
        var opts = new Options(false, false, null, "_");
        var result = kebabCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("abc-def-ghi");

        opts = new Options(false, false, null, "-");
        result = kebabCaseWithOptions("Abc-Def-Ghi", opts);
        assertThat(result).isEqualTo("abc--def--ghi");
      }

      @Test
      void convertMacroCase() {
        var opts = new Options(false, false, null, "-");
        var result = kebabCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("abc-def-ghi");

        opts = new Options(false, false, null, "_");
        result = kebabCaseWithOptions("ABC_DEF_GHI", opts);
        assertThat(result).isEqualTo("abc_def_ghi");
      }

      @Test
      void convertCobolCase() {
        var opts = new Options(false, false, null, "_");
        var result = kebabCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("abc-def-ghi");

        opts = new Options(false, false, null, "-");
        result = kebabCaseWithOptions("ABC-DEF-GHI", opts);
        assertThat(result).isEqualTo("abc-def-ghi");
      }

      @Test
      void convertWithKeepingDigits() {
        var opts = new Options(false, false, null, "_");
        var result = kebabCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("abc123-456def-g89hi-jkl-mn12");

        opts = new Options(false, false, null, "-");
        result = kebabCaseWithOptions("abc123-456defG89HIJklMN12", opts);
        assertThat(result).isEqualTo("abc123-456def-g89hi-jkl-mn12");
      }

      @Test
      void convertWithSymbolsAsSeparators() {
        var opts = new Options(false, false, null, ".~!#%?");
        var result = kebabCaseWithOptions(":.abc~!@def#$ghi%&jk(lm)no/?", opts);
        assertThat(result).isEqualTo(".abc~!-def#-ghi%-jk-lm-no-?");
      }

      @Test
      void convertWhenStartingWithDigit() {
        var opts = new Options(false, false, null, "-_");
        var result = kebabCaseWithOptions("123abc456def", opts);
        assertThat(result).isEqualTo("123abc456def");

        result = kebabCaseWithOptions("123ABC456DEF", opts);
        assertThat(result).isEqualTo("123abc456def");

        result = kebabCaseWithOptions("123Abc456Def", opts);
        assertThat(result).isEqualTo("123-abc456-def");
      }

      @Test
      void convertAnEmptyString() {
        var opts = new Options(false, false, null, "-_");
        var result = kebabCaseWithOptions("", opts);
        assertThat(result).isEqualTo("");
      }
    }
  }
}
