package tech.intellispacesframework.javastatements.sample;

import tech.intellispacesframework.javastatements.support.TesteeType;

@TesteeType
public class GenericClassWithOneTypeParameter<T> {

  public T process(T arg) {
    return null;
  }
}