package tech.intellispacesframework.javastatements.sample;

import tech.intellispacesframework.javastatements.support.TesteeType;

@TesteeType
public enum EnumWithCharGetter {
  ;

  public char charGetter() {
    return 'a';
  }
}