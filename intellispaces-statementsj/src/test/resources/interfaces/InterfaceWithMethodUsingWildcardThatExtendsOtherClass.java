package tech.intellispaces.statementsj.samples;

import java.util.Collection;

import tech.intellispaces.statementsj.support.TesteeType;

@TesteeType
public interface InterfaceWithMethodUsingWildcardThatExtendsOtherClass {

  void methodUsingWildcardThatExtendsOtherClass(Collection<? extends Number> arg);
}