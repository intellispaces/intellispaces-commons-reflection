package tech.intellispaces.jstatements.samples;

import java.util.List;

import tech.intellispaces.jstatements.support.TesteeType;

@TesteeType
public enum EnumWithMethodUsingLocalTypeParameter {
  ;

  public <T> List<T> methodUsingLocalTypeParameter(T arg) {
    return null;
  }
}