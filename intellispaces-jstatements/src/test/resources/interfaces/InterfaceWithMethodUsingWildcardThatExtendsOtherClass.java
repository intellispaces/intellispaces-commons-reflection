package tech.intellispaces.jstatements.samples;

import java.util.Collection;

import tech.intellispaces.jstatements.support.TesteeType;

@TesteeType
public interface InterfaceWithMethodUsingWildcardThatExtendsOtherClass {

  void methodUsingWildcardThatExtendsOtherClass(Collection<? extends Number> arg);
}