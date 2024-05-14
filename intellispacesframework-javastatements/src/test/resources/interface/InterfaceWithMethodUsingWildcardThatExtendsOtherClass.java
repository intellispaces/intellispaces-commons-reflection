package tech.intellispacesframework.javastatements.samples;

import tech.intellispacesframework.javastatements.support.TesteeType;

import java.util.Collection;

@TesteeType
public interface InterfaceWithMethodUsingWildcardThatExtendsOtherClass {

  void methodUsingWildcardThatExtendsOtherClass(Collection<? extends Number> arg);
}