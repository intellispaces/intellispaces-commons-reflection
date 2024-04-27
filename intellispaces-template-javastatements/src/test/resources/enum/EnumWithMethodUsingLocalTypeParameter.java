package tech.intellispacesframework.javastatements.sample;

import tech.intellispacesframework.javastatements.support.TesteeType;

import java.util.List;

@TesteeType
public enum EnumWithMethodUsingLocalTypeParameter {
  ;

  public <T> List<T> methodUsingLocalTypeParameter(T arg) {
    return null;
  }
}