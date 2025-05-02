package tech.intellispaces.statementsj.samples;

import java.util.Collection;

import tech.intellispaces.statementsj.support.TesteeType;

@TesteeType
public enum EnumWithMethodUsingWildcardThatExtendsOtherClass {
  ;

  public void methodUsingWildcardThatExtendsOtherClass(Collection<? extends Number> arg) {
  }
}