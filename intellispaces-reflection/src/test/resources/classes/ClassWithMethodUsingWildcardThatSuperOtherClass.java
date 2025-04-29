package tech.intellispaces.reflection.samples;

import java.util.Collection;

import tech.intellispaces.reflection.support.TesteeType;

@TesteeType
public class ClassWithMethodUsingWildcardThatSuperOtherClass {

  public void methodUsingWildcardThatSuperOtherClass(Collection<? super Number[]> arg) {
  }
}