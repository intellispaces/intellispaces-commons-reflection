package tech.intellispacesframework.javastatements.samples;

import tech.intellispacesframework.javastatements.support.TesteeType;

import java.util.List;

@TesteeType
public interface InterfaceWithMethodUsingLocalTypeParameter {

  <T> List<T> methodUsingLocalTypeParameter(T arg);
}