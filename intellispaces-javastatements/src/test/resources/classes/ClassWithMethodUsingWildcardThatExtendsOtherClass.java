package intellispaces.common.javastatements.samples;

import intellispaces.common.javastatements.support.TesteeType;

import java.util.Collection;

@TesteeType
public class ClassWithMethodUsingWildcardThatExtendsOtherClass {

  public void methodUsingWildcardThatExtendsOtherClass(Collection<? extends Number> arg) {
  }
}