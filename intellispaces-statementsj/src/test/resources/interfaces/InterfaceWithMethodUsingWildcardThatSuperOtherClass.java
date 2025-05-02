package tech.intellispaces.statementsj.samples;

import java.util.Collection;

import tech.intellispaces.statementsj.support.TesteeType;

@TesteeType
public interface InterfaceWithMethodUsingWildcardThatSuperOtherClass {

  void methodUsingWildcardThatSuperOtherClass(Collection<? super Number[]> arg);
}