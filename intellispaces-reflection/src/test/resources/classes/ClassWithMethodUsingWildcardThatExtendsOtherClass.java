package tech.intellispaces.reflection.samples;

import java.util.Collection;

import tech.intellispaces.reflection.support.TesteeType;

@TesteeType
public class ClassWithMethodUsingWildcardThatExtendsOtherClass {

  public void methodUsingWildcardThatExtendsOtherClass(Collection<? extends Number> arg) {
  }
}