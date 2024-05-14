package tech.intellispacesframework.javastatements.samples;

import tech.intellispacesframework.javastatements.support.TesteeType;

@TesteeType
public enum EnumWithCharGetter {
  ;

  public char charGetter() {
    return 'a';
  }
}