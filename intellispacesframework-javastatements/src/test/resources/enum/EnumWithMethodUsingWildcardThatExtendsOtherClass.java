package tech.intellispacesframework.javastatements.samples;

import tech.intellispacesframework.javastatements.support.TesteeType;

import java.util.Collection;

@TesteeType
public enum EnumWithMethodUsingWildcardThatExtendsOtherClass {
  ;

  public void methodUsingWildcardThatExtendsOtherClass(Collection<? extends Number> arg) {
  }
}