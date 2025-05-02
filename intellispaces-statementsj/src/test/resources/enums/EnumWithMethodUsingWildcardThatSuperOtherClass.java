package tech.intellispaces.statementsj.samples;

import java.util.Collection;

import tech.intellispaces.statementsj.support.TesteeType;

@TesteeType
public enum EnumWithMethodUsingWildcardThatSuperOtherClass {
  ;

  public void methodUsingWildcardThatSuperOtherClass(Collection<? super Number[]> arg) {
  }
}