package tech.intellispacesframework.javastatements.sample;

import tech.intellispacesframework.javastatements.support.TesteeType;

import java.util.Collection;

@TesteeType
public interface InterfaceWithMethodUsingWildcardThatSuperOtherClass {

  void methodUsingWildcardThatSuperOtherClass(Collection<? super Number[]> arg);
}