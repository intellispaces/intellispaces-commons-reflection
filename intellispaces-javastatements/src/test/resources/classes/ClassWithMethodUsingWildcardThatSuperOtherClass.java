package tech.intellispaces.javastatements.samples;

import tech.intellispaces.javastatements.support.TesteeType;

import java.util.Collection;

@TesteeType
public class ClassWithMethodUsingWildcardThatSuperOtherClass {

  public void methodUsingWildcardThatSuperOtherClass(Collection<? super Number[]> arg) {
  }
}