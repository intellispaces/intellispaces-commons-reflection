package tech.intellispaces.java.reflection.samples;

import tech.intellispaces.java.reflection.support.TesteeType;

import java.util.Collection;

@TesteeType
public class ClassWithMethodUsingWildcardThatExtendsOtherClass {

  public void methodUsingWildcardThatExtendsOtherClass(Collection<? extends Number> arg) {
  }
}