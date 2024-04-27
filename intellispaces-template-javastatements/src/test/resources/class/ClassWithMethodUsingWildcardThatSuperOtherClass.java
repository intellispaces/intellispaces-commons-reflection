package tech.intellispacesframework.javastatements.sample;

import tech.intellispacesframework.javastatements.support.TesteeType;

import java.util.Collection;

@TesteeType
public class ClassWithMethodUsingWildcardThatSuperOtherClass {

  public void methodUsingWildcardThatSuperOtherClass(Collection<? super Number[]> arg) {
  }
}