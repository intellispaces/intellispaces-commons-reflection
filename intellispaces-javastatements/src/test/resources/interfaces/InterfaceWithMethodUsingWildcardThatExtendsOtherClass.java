package tech.intellispaces.javastatements.samples;

import tech.intellispaces.javastatements.support.TesteeType;

import java.util.Collection;

@TesteeType
public interface InterfaceWithMethodUsingWildcardThatExtendsOtherClass {

  void methodUsingWildcardThatExtendsOtherClass(Collection<? extends Number> arg);
}