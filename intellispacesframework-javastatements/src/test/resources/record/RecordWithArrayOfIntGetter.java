package tech.intellispacesframework.javastatements.samples;

import tech.intellispacesframework.javastatements.support.TesteeType;

@TesteeType
public record RecordWithArrayOfIntGetter() {

  public int[] arrayOfIntGetter() {
    return null;
  }
}