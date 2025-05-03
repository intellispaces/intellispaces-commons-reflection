package tech.intellispaces.jstatements.samples;

import java.util.Collection;

import tech.intellispaces.jstatements.support.TesteeType;

@TesteeType
public enum EnumWithMethodUsingWildcardThatSuperOtherClass {
  ;

  public void methodUsingWildcardThatSuperOtherClass(Collection<? super Number[]> arg) {
  }
}