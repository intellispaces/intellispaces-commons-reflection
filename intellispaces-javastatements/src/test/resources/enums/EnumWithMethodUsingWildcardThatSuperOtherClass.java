package intellispaces.common.javastatements.samples;

import intellispaces.common.javastatements.support.TesteeType;

import java.util.Collection;

@TesteeType
public enum EnumWithMethodUsingWildcardThatSuperOtherClass {
  ;

  public void methodUsingWildcardThatSuperOtherClass(Collection<? super Number[]> arg) {
  }
}