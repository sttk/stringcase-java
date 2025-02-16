package com.github.sttk.stringcase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import static com.github.sttk.stringcase.StringCase.*;

import static java.lang.Character.codePointAt;

public class StringCaseTest {
  private StringCaseTest() {}

  // pascal case

  @Nested
  class PascalCase {
    @Test
    void fromCamelCase() {
      var result = pascalCase("abcDefGHIjk");
      assertThat(result).isEqualTo("AbcDefGhIjk");
    }

    @Test
    void fromPascalCase() {
      var result = pascalCase("AbcDefGHIjk");
      assertThat(result).isEqualTo("AbcDefGhIjk");
    }

    @Test
    void fromSnakeCase() {
      var result = pascalCase("abc_def_ghi");
      assertThat(result).isEqualTo("AbcDefGhi");
    }

    @Test
    void fromKebabCase() {
      var result = pascalCase("abc-def-ghi");
      assertThat(result).isEqualTo("AbcDefGhi");
    }

    @Test
    void fromTrainCase() {
      var result = pascalCase("Abc-Def-Ghi");
      assertThat(result).isEqualTo("AbcDefGhi");
    }

    @Test
    void fromMacroCase() {
      var result = pascalCase("ABC_DEF_GHI");
      assertThat(result).isEqualTo("AbcDefGhi");
    }

    @Test
    void fromCobolCase() {
      var result = pascalCase("ABC-DEF-GHI");
      assertThat(result).isEqualTo("AbcDefGhi");
    }

    @Test
    void keepDigits() {
      var result = pascalCase("abc123-456defG789HIJklMN12");
      assertThat(result).isEqualTo("Abc123456DefG789HiJklMn12");
    }

    @Test
    void convertWhenStartingWithDigit() {
      var result = pascalCase("123abc456def");
      assertThat(result).isEqualTo("123Abc456Def");

      result = pascalCase("123ABC456DEF");
      assertThat(result).isEqualTo("123Abc456Def");
    }

    @Test
    void treatMarksAsSeparators() {
      var result = pascalCase(":.abc~!@def#$ghi%&jk(lm)no/?");
      assertThat(result).isEqualTo("AbcDefGhiJkLmNo");
    }

    @Test
    void convertEmpty() {
      var result = pascalCase("");
      assertThat(result).isEqualTo("");
    }
  }

  @Nested
  class PascalCaseWithSep {
    @Test
    void fromCamelCase() {
      var result = pascalCaseWithSep("abcDefGHIjk", "_-");
      assertThat(result).isEqualTo("AbcDefGhIjk");
    }

    @Test
    void fromPascalCase() {
      var result = pascalCaseWithSep("AbcDefGHIjk", "_-");
      assertThat(result).isEqualTo("AbcDefGhIjk");
    }

    @Test
    void fromSnakeCase() {
      var result = pascalCaseWithSep("abc_def_ghi", "_");
      assertThat(result).isEqualTo("AbcDefGhi");

      result = pascalCaseWithSep("abc_def_ghi", "-");
      assertThat(result).isEqualTo("Abc_Def_Ghi");
    }

    @Test
    void fromKebabCase() {
      var result = pascalCaseWithSep("abc-def-ghi", "-");
      assertThat(result).isEqualTo("AbcDefGhi");

      result = pascalCaseWithSep("abc-def-ghi", "_");
      assertThat(result).isEqualTo("Abc-Def-Ghi");
    }

    @Test
    void fromTrainCase() {
      var result = pascalCaseWithSep("Abc-Def-Ghi", "-");
      assertThat(result).isEqualTo("AbcDefGhi");

      result = pascalCaseWithSep("Abc-Def-Ghi", "_");
      assertThat(result).isEqualTo("Abc-Def-Ghi");
    }

    @Test
    void fromMacroCase() {
      var result = pascalCaseWithSep("ABC_DEF_GHI", "_");
      assertThat(result).isEqualTo("AbcDefGhi");

      result = pascalCaseWithSep("ABC_DEF_GHI", "-");
      assertThat(result).isEqualTo("Abc_Def_Ghi");
    }

    @Test
    void fromCobolCase() {
      var result = pascalCaseWithSep("ABC-DEF-GHI", "-");
      assertThat(result).isEqualTo("AbcDefGhi");

      result = pascalCaseWithSep("ABC-DEF-GHI", "_");
      assertThat(result).isEqualTo("Abc-Def-Ghi");
    }

    @Test
    void keepDigits() {
      var result = pascalCaseWithSep("abc123-456defG789HIJklMN12", "_");
      assertThat(result).isEqualTo("Abc123-456DefG789HiJklMn12");

      result = pascalCaseWithSep("abc123-456defG789HIJklMN12", "-");
      assertThat(result).isEqualTo("Abc123456DefG789HiJklMn12");
    }

    @Test
    void convertWhenStartingWithDigit() {
      var result = pascalCaseWithSep("123abc456def", "-_");
      assertThat(result).isEqualTo("123Abc456Def");

      result = pascalCaseWithSep("123ABC456DEF", "-_");
      assertThat(result).isEqualTo("123Abc456Def");
    }

    @Test
    void treatMarksAsSeparators() {
      var result = pascalCaseWithSep(":.abc~!@def#$ghi%&jk(lm)no/?", ":@$&()/");
      assertThat(result).isEqualTo(".Abc~!Def#Ghi%JkLmNo?");
    }

    @Test
    void convertEmpty() {
      var result = pascalCaseWithSep("", "-_");
      assertThat(result).isEqualTo("");
    }
  }

  @Nested
  class PascalCaseWithKeep {
    @Test
    void fromCamelCase() {
      var result = pascalCaseWithKeep("abcDefGHIjk", "_-");
      assertThat(result).isEqualTo("AbcDefGhIjk");
    }

    @Test
    void fromPascalCase() {
      var result = pascalCaseWithKeep("AbcDefGHIjk", "_-");
      assertThat(result).isEqualTo("AbcDefGhIjk");
    }

    @Test
    void fromSnakeCase() {
      var result = pascalCaseWithKeep("abc_def_ghi", "-");
      assertThat(result).isEqualTo("AbcDefGhi");

      result = pascalCaseWithKeep("abc_def_ghi", "_");
      assertThat(result).isEqualTo("Abc_Def_Ghi");
    }

    @Test
    void fromKebabCase() {
      var result = pascalCaseWithKeep("abc-def-ghi", "_");
      assertThat(result).isEqualTo("AbcDefGhi");

      result = pascalCaseWithKeep("abc-def-ghi", "-");
      assertThat(result).isEqualTo("Abc-Def-Ghi");
    }

    @Test
    void fromTrainCase() {
      var result = pascalCaseWithKeep("Abc-Def-Ghi", "_");
      assertThat(result).isEqualTo("AbcDefGhi");

      result = pascalCaseWithKeep("Abc-Def-Ghi", "-");
      assertThat(result).isEqualTo("Abc-Def-Ghi");
    }

    @Test
    void fromMacroCase() {
      var result = pascalCaseWithKeep("ABC_DEF_GHI", "-");
      assertThat(result).isEqualTo("AbcDefGhi");

      result = pascalCaseWithKeep("ABC_DEF_GHI", "_");
      assertThat(result).isEqualTo("Abc_Def_Ghi");
    }

    @Test
    void fromCobolCase() {
      var result = pascalCaseWithKeep("ABC-DEF-GHI", "_");
      assertThat(result).isEqualTo("AbcDefGhi");

      result = pascalCaseWithKeep("ABC-DEF-GHI", "-");
      assertThat(result).isEqualTo("Abc-Def-Ghi");
    }

    @Test
    void keepDigits() {
      var result = pascalCaseWithKeep("abc123-456defG789HIJklMN12", "-");
      assertThat(result).isEqualTo("Abc123-456DefG789HiJklMn12");

      result = pascalCaseWithKeep("abc123-456defG789HIJklMN12", "_");
      assertThat(result).isEqualTo("Abc123456DefG789HiJklMn12");
    }

    @Test
    void convertWhenStartingWithDigit() {
      var result = pascalCaseWithKeep("123abc456def", "_");
      assertThat(result).isEqualTo("123Abc456Def");

      result = pascalCaseWithKeep("123ABC456DEF", "-");
      assertThat(result).isEqualTo("123Abc456Def");
    }

    @Test
    void treatMarksAsSeparators() {
      var result = pascalCaseWithKeep(":.abc~!@def#$ghi%&jk(lm)no/?", ".~!#%?");
      assertThat(result).isEqualTo(".Abc~!Def#Ghi%JkLmNo?");
    }

    @Test
    void convertEmpty() {
      var result = pascalCaseWithKeep("", "-_");
      assertThat(result).isEqualTo("");
    }
  }

  // snake case

  @Nested
  class SnakeCase {
    @Test
    void fromCamelCase() {
      var result = snakeCase("abcDefGHIjk");
      assertThat(result).isEqualTo("abc_def_gh_ijk");
    }

    @Test
    void fromPascalCase() {
      var result = snakeCase("AbcDefGHIjk");
      assertThat(result).isEqualTo("abc_def_gh_ijk");
    }

    @Test
    void fromSnakeCase() {
      var result = snakeCase("abc_def_ghi");
      assertThat(result).isEqualTo("abc_def_ghi");
    }

    @Test
    void fromKebabCase() {
      var result = snakeCase("abc-def-ghi");
      assertThat(result).isEqualTo("abc_def_ghi");
    }

    @Test
    void fromTrainCase() {
      var result = snakeCase("Abc-Def-Ghi");
      assertThat(result).isEqualTo("abc_def_ghi");
    }

    @Test
    void fromMacroCase() {
      var result = snakeCase("ABC_DEF_GHI");
      assertThat(result).isEqualTo("abc_def_ghi");
    }

    @Test
    void fromCobolCase() {
      var result = snakeCase("ABC-DEF-GHI");
      assertThat(result).isEqualTo("abc_def_ghi");
    }

    @Test
    void keepDigits() {
      var result = snakeCase("abc123-456defG789HIJklMN12");
      assertThat(result).isEqualTo("abc123_456_def_g789_hi_jkl_mn12");
    }

    @Test
    void convertWhenStartingWithDigit() {
      var result = snakeCase("123abc456def");
      assertThat(result).isEqualTo("123_abc456_def");

      result = snakeCase("123ABC456DEF");
      assertThat(result).isEqualTo("123_abc456_def");
    }

    @Test
    void treatMarksAsSeparators() {
      var result = snakeCase(":.abc~!@def#$ghi%&jk(lm)no/?");
      assertThat(result).isEqualTo("abc_def_ghi_jk_lm_no");
    }

    @Test
    void convertEmpty() {
      var result = snakeCase("");
      assertThat(result).isEqualTo("");
    }
  }

  @Nested
  class SnakeCaseWithSep {
    @Test
    void fromCamelCase() {
      var result = snakeCaseWithSep("abcDefGHIjk", "_-");
      assertThat(result).isEqualTo("abc_def_gh_ijk");
    }

    @Test
    void fromPascalCase() {
      var result = snakeCaseWithSep("AbcDefGHIjk", "_-");
      assertThat(result).isEqualTo("abc_def_gh_ijk");
    }

    @Test
    void fromSnakeCase() {
      var result = snakeCaseWithSep("abc_def_ghi", "_");
      assertThat(result).isEqualTo("abc_def_ghi");

      result = snakeCaseWithSep("abc_def_ghi", "-");
      assertThat(result).isEqualTo("abc__def__ghi");
    }

    @Test
    void fromKebabCase() {
      var result = snakeCaseWithSep("abc-def-ghi", "-");
      assertThat(result).isEqualTo("abc_def_ghi");

      result = snakeCaseWithSep("abc-def-ghi", "_");
      assertThat(result).isEqualTo("abc-_def-_ghi");
    }

    @Test
    void fromTrainCase() {
      var result = snakeCaseWithSep("Abc-Def-Ghi", "-");
      assertThat(result).isEqualTo("abc_def_ghi");

      result = snakeCaseWithSep("Abc-Def-Ghi", "_");
      assertThat(result).isEqualTo("abc-_def-_ghi");
    }

    @Test
    void fromMacroCase() {
      var result = snakeCaseWithSep("ABC_DEF_GHI", "_");
      assertThat(result).isEqualTo("abc_def_ghi");

      result = snakeCaseWithSep("ABC_DEF_GHI", "-");
      assertThat(result).isEqualTo("abc__def__ghi");
    }

    @Test
    void fromCobolCase() {
      var result = snakeCaseWithSep("ABC-DEF-GHI", "-");
      assertThat(result).isEqualTo("abc_def_ghi");

      result = snakeCaseWithSep("ABC-DEF-GHI", "_");
      assertThat(result).isEqualTo("abc-_def-_ghi");
    }

    @Test
    void keepDigits() {
      var result = snakeCaseWithSep("abc123-456defG789HIJklMN12", "-");
      assertThat(result).isEqualTo("abc123_456_def_g789_hi_jkl_mn12");

      result = snakeCaseWithSep("abc123-456defG789HIJklMN12", "_");
      assertThat(result).isEqualTo("abc123-456_def_g789_hi_jkl_mn12");
    }

    @Test
    void convertWhenStartingWithDigit() {
      var result = snakeCaseWithSep("123abc456def", "-_");
      assertThat(result).isEqualTo("123_abc456_def");

      result = snakeCaseWithSep("123ABC456DEF", "-_");
      assertThat(result).isEqualTo("123_abc456_def");
    }

    @Test
    void treatMarksAsSeparators() {
      var result = snakeCaseWithSep(":.abc~!@def#$ghi%&jk(lm)no/?", ":@$&()/");
      assertThat(result).isEqualTo("._abc~!_def#_ghi%_jk_lm_no_?");
    }

    @Test
    void convertEmpty() {
      var result = snakeCaseWithSep("", "-_");
      assertThat(result).isEqualTo("");
    }
  }

  @Nested
  class SnakeCaseWithKeep {
    @Test
    void fromCamelCase() {
      var result = snakeCaseWithKeep("abcDefGHIjk", "_-");
      assertThat(result).isEqualTo("abc_def_gh_ijk");
    }

    @Test
    void fromPascalCase() {
      var result = snakeCaseWithKeep("AbcDefGHIjk", "_-");
      assertThat(result).isEqualTo("abc_def_gh_ijk");
    }

    @Test
    void fromSnakeCase() {
      var result = snakeCaseWithKeep("abc_def_ghi", "-");
      assertThat(result).isEqualTo("abc_def_ghi");

      result = snakeCaseWithKeep("abc_def_ghi", "_");
      assertThat(result).isEqualTo("abc__def__ghi");
    }

    @Test
    void fromKebabCase() {
      var result = snakeCaseWithKeep("abc-def-ghi", "_");
      assertThat(result).isEqualTo("abc_def_ghi");

      result = snakeCaseWithKeep("abc-def-ghi", "-");
      assertThat(result).isEqualTo("abc-_def-_ghi");
    }

    @Test
    void fromTrainCase() {
      var result = snakeCaseWithKeep("Abc-Def-Ghi", "_");
      assertThat(result).isEqualTo("abc_def_ghi");

      result = snakeCaseWithKeep("Abc-Def-Ghi", "-");
      assertThat(result).isEqualTo("abc-_def-_ghi");
    }

    @Test
    void fromMacroCase() {
      var result = snakeCaseWithKeep("ABC_DEF_GHI", "-");
      assertThat(result).isEqualTo("abc_def_ghi");

      result = snakeCaseWithKeep("ABC_DEF_GHI", "_");
      assertThat(result).isEqualTo("abc__def__ghi");
    }

    @Test
    void fromCobolCase() {
      var result = snakeCaseWithKeep("ABC-DEF-GHI", "_");
      assertThat(result).isEqualTo("abc_def_ghi");

      result = snakeCaseWithKeep("ABC-DEF-GHI", "-");
      assertThat(result).isEqualTo("abc-_def-_ghi");
    }

    @Test
    void keepDigits() {
      var result = snakeCaseWithKeep("abc123-456defG789HIJklMN12", "-");
      assertThat(result).isEqualTo("abc123-456_def_g789_hi_jkl_mn12");

      result = snakeCaseWithKeep("abc123-456defG789HIJklMN12", "_");
      assertThat(result).isEqualTo("abc123_456_def_g789_hi_jkl_mn12");
    }

    @Test
    void convertWhenStartingWithDigit() {
      var result = snakeCaseWithKeep("123abc456def", "_");
      assertThat(result).isEqualTo("123_abc456_def");

      result = snakeCaseWithKeep("123ABC456DEF", "-");
      assertThat(result).isEqualTo("123_abc456_def");
    }

    @Test
    void treatMarksAsSeparators() {
      var result = snakeCaseWithKeep(":.abc~!@def#$ghi%&jk(lm)no/?", ".~!#%?");
      assertThat(result).isEqualTo("._abc~!_def#_ghi%_jk_lm_no_?");
    }

    @Test
    void convertEmpty() {
      var result = snakeCaseWithKeep("", "-_");
      assertThat(result).isEqualTo("");
    }
  }

  // train case

  @Nested
  class TrainCase {
    @Test
    void fromCamelCase() {
      var result = trainCase("abcDefGHIjk");
      assertThat(result).isEqualTo("Abc-Def-Gh-Ijk");
    }

    @Test
    void fromPascalCase() {
      var result = trainCase("AbcDefGHIjk");
      assertThat(result).isEqualTo("Abc-Def-Gh-Ijk");
    }

    @Test
    void fromSnakeCase() {
      var result = trainCase("abc_def_ghi");
      assertThat(result).isEqualTo("Abc-Def-Ghi");
    }

    @Test
    void fromKebabCase() {
      var result = trainCase("abc-def-ghi");
      assertThat(result).isEqualTo("Abc-Def-Ghi");
    }

    @Test
    void fromTrainCase() {
      var result = trainCase("Abc-Def-Ghi");
      assertThat(result).isEqualTo("Abc-Def-Ghi");
    }

    @Test
    void fromMacroCase() {
      var result = trainCase("ABC_DEF_GHI");
      assertThat(result).isEqualTo("Abc-Def-Ghi");
    }

    @Test
    void fromCobolCase() {
      var result = trainCase("ABC-DEF-GHI");
      assertThat(result).isEqualTo("Abc-Def-Ghi");
    }

    @Test
    void keepDigits() {
      var result = trainCase("abc123-456defG789HIJklMN12");
      assertThat(result).isEqualTo("Abc123-456-Def-G789-Hi-Jkl-Mn12");
    }

    @Test
    void convertWhenStartingWithDigit() {
      var result = trainCase("123abc456def");
      assertThat(result).isEqualTo("123-Abc456-Def");

      result = trainCase("123ABC456DEF");
      assertThat(result).isEqualTo("123-Abc456-Def");
    }

    @Test
    void treatMarksAsSeparators() {
      var result = trainCase(":.abc~!@def#$ghi%&jk(lm)no/?");
      assertThat(result).isEqualTo("Abc-Def-Ghi-Jk-Lm-No");
    }

    @Test
    void convertEmpty() {
      var result = trainCase("");
      assertThat(result).isEqualTo("");
    }
  }

  @Nested
  class TrainCaseWithSep {
    @Test
    void fromCamelCase() {
      var result = trainCaseWithSep("abcDefGHIjk", "_-");
      assertThat(result).isEqualTo("Abc-Def-Gh-Ijk");
    }

    @Test
    void fromPascalCase() {
      var result = trainCaseWithSep("AbcDefGHIjk", "_-");
      assertThat(result).isEqualTo("Abc-Def-Gh-Ijk");
    }

    @Test
    void fromSnakeCase() {
      var result = trainCaseWithSep("abc_def_ghi", "_");
      assertThat(result).isEqualTo("Abc-Def-Ghi");

      result = trainCaseWithSep("abc_def_ghi", "-");
      assertThat(result).isEqualTo("Abc_-Def_-Ghi");
    }

    @Test
    void fromKebabCase() {
      var result = trainCaseWithSep("abc-def-ghi", "-");
      assertThat(result).isEqualTo("Abc-Def-Ghi");

      result = trainCaseWithSep("abc-def-ghi", "_");
      assertThat(result).isEqualTo("Abc--Def--Ghi");
    }

    @Test
    void fromTrainCase() {
      var result = trainCaseWithSep("Abc-Def-Ghi", "-");
      assertThat(result).isEqualTo("Abc-Def-Ghi");

      result = trainCaseWithSep("Abc-Def-Ghi", "_");
      assertThat(result).isEqualTo("Abc--Def--Ghi");
    }

    @Test
    void fromMacroCase() {
      var result = trainCaseWithSep("ABC_DEF_GHI", "_");
      assertThat(result).isEqualTo("Abc-Def-Ghi");

      result = trainCaseWithSep("ABC_DEF_GHI", "-");
      assertThat(result).isEqualTo("Abc_-Def_-Ghi");
    }

    @Test
    void fromCobolCase() {
      var result = trainCaseWithSep("ABC-DEF-GHI", "-");
      assertThat(result).isEqualTo("Abc-Def-Ghi");

      result = trainCaseWithSep("ABC-DEF-GHI", "_");
      assertThat(result).isEqualTo("Abc--Def--Ghi");
    }

    @Test
    void keepDigits() {
      var result = trainCaseWithSep("abc123-456defG789HIJklMN12", "-");
      assertThat(result).isEqualTo("Abc123-456-Def-G789-Hi-Jkl-Mn12");

      result = trainCaseWithSep("abc123-456defG789HIJklMN12", "_");
      assertThat(result).isEqualTo("Abc123-456-Def-G789-Hi-Jkl-Mn12");
    }

    @Test
    void convertWhenStartingWithDigit() {
      var result = trainCaseWithSep("123abc456def", "-_");
      assertThat(result).isEqualTo("123-Abc456-Def");

      result = trainCaseWithSep("123ABC456DEF", "-_");
      assertThat(result).isEqualTo("123-Abc456-Def");
    }

    @Test
    void treatMarksAsSeparators() {
      var result = trainCaseWithSep(":.abc~!@def#$ghi%&jk(lm)no/?", ":@$&()/");
      assertThat(result).isEqualTo(".-Abc~!-Def#-Ghi%-Jk-Lm-No-?");
    }

    @Test
    void convertEmpty() {
      var result = trainCaseWithSep("", "-_");
      assertThat(result).isEqualTo("");
    }
  }

  @Nested
  class TrainCaseWithKeep {
    @Test
    void fromCamelCase() {
      var result = trainCaseWithKeep("abcDefGHIjk", "_-");
      assertThat(result).isEqualTo("Abc-Def-Gh-Ijk");
    }

    @Test
    void fromPascalCase() {
      var result = trainCaseWithKeep("AbcDefGHIjk", "_-");
      assertThat(result).isEqualTo("Abc-Def-Gh-Ijk");
    }

    @Test
    void fromSnakeCase() {
      var result = trainCaseWithKeep("abc_def_ghi", "-");
      assertThat(result).isEqualTo("Abc-Def-Ghi");

      result = trainCaseWithKeep("abc_def_ghi", "_");
      assertThat(result).isEqualTo("Abc_-Def_-Ghi");
    }

    @Test
    void fromKebabCase() {
      var result = trainCaseWithKeep("abc-def-ghi", "_");
      assertThat(result).isEqualTo("Abc-Def-Ghi");

      result = trainCaseWithKeep("abc-def-ghi", "-");
      assertThat(result).isEqualTo("Abc--Def--Ghi");
    }

    @Test
    void fromTrainCase() {
      var result = trainCaseWithKeep("Abc-Def-Ghi", "_");
      assertThat(result).isEqualTo("Abc-Def-Ghi");

      result = trainCaseWithKeep("Abc-Def-Ghi", "-");
      assertThat(result).isEqualTo("Abc--Def--Ghi");
    }

    @Test
    void fromMacroCase() {
      var result = trainCaseWithKeep("ABC_DEF_GHI", "-");
      assertThat(result).isEqualTo("Abc-Def-Ghi");

      result = trainCaseWithKeep("ABC_DEF_GHI", "_");
      assertThat(result).isEqualTo("Abc_-Def_-Ghi");
    }

    @Test
    void fromCobolCase() {
      var result = trainCaseWithKeep("ABC-DEF-GHI", "_");
      assertThat(result).isEqualTo("Abc-Def-Ghi");

      result = trainCaseWithKeep("ABC-DEF-GHI", "-");
      assertThat(result).isEqualTo("Abc--Def--Ghi");
    }

    @Test
    void keepDigits() {
      var result = trainCaseWithKeep("abc123-456defG789HIJklMN12", "-");
      assertThat(result).isEqualTo("Abc123-456-Def-G789-Hi-Jkl-Mn12");

      result = trainCaseWithKeep("abc123-456defG789HIJklMN12", "_");
      assertThat(result).isEqualTo("Abc123-456-Def-G789-Hi-Jkl-Mn12");
    }

    @Test
    void convertWhenStartingWithDigit() {
      var result = trainCaseWithKeep("123abc456def", "_");
      assertThat(result).isEqualTo("123-Abc456-Def");

      result = trainCaseWithKeep("123ABC456DEF", "-");
      assertThat(result).isEqualTo("123-Abc456-Def");
    }

    @Test
    void treatMarksAsSeparators() {
      var result = trainCaseWithKeep(":.abc~!@def#$ghi%&jk(lm)no/?", ".~!#%?");
      assertThat(result).isEqualTo(".-Abc~!-Def#-Ghi%-Jk-Lm-No-?");
    }

    @Test
    void convertEmpty() {
      var result = trainCaseWithKeep("", "-_");
      assertThat(result).isEqualTo("");
    }
  }
}
