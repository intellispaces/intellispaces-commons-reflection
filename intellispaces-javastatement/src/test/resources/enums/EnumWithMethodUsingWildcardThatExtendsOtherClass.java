package intellispaces.common.javastatement.samples;

import intellispaces.common.javastatement.support.TesteeType;

import java.util.Collection;

@TesteeType
public enum EnumWithMethodUsingWildcardThatExtendsOtherClass {
  ;

  public void methodUsingWildcardThatExtendsOtherClass(Collection<? extends Number> arg) {
  }
}