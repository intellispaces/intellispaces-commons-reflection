package tech.intellispacesframework.javastatements.sample;

import tech.intellispacesframework.javastatements.support.TesteeType;

@TesteeType
public enum EnumWithShortGetter {
  ;

  public short shortGetter() {
    return 0;
  }
}