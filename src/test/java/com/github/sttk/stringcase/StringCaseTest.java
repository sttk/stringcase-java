package com.github.sttk.stringcase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import static com.github.sttk.stringcase.StringCase.*;

import static java.lang.Character.codePointAt;

public class StringCaseTest {
  private StringCaseTest() {}

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
