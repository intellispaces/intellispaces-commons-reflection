package tech.intellispacesframework.javastatements.samples;

import tech.intellispacesframework.javastatements.support.TesteeType;

import java.util.List;

@TesteeType
public class ClassWithMethodUsingLocalTypeParameter {

  public <T> List<T> methodUsingLocalTypeParameter(T arg) {
    return null;
  }
}