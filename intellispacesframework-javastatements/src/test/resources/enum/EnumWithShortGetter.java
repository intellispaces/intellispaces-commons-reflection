package tech.intellispacesframework.javastatements.samples;

import tech.intellispacesframework.javastatements.support.TesteeType;

@TesteeType
public enum EnumWithShortGetter {
  ;

  public short shortGetter() {
    return 0;
  }
}