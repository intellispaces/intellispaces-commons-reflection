package tech.intellispaces.reflection.samples;

import java.util.Collection;

import tech.intellispaces.reflection.support.TesteeType;

@TesteeType
public enum EnumWithMethodUsingWildcardThatSuperOtherClass {
  ;

  public void methodUsingWildcardThatSuperOtherClass(Collection<? super Number[]> arg) {
  }
}