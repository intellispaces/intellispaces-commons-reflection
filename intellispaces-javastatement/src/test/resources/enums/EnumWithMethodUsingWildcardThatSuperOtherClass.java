package intellispaces.common.javastatement.samples;

import intellispaces.common.javastatement.support.TesteeType;

import java.util.Collection;

@TesteeType
public enum EnumWithMethodUsingWildcardThatSuperOtherClass {
  ;

  public void methodUsingWildcardThatSuperOtherClass(Collection<? super Number[]> arg) {
  }
}