package tech.intellispaces.reflection.samples;

import java.util.Collection;

import tech.intellispaces.reflection.support.TesteeType;

@TesteeType
public enum EnumWithMethodUsingWildcardThatExtendsOtherClass {
  ;

  public void methodUsingWildcardThatExtendsOtherClass(Collection<? extends Number> arg) {
  }
}