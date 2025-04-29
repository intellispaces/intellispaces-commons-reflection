package tech.intellispaces.reflection.samples;

import java.util.Collection;

import tech.intellispaces.reflection.support.TesteeType;

@TesteeType
public interface InterfaceWithMethodUsingWildcardThatExtendsOtherClass {

  void methodUsingWildcardThatExtendsOtherClass(Collection<? extends Number> arg);
}