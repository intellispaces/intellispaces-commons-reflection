package tech.intellispaces.jstatements.samples;

import java.util.List;

import tech.intellispaces.jstatements.support.TesteeType;

@TesteeType
public class ClassWithMethodUsingLocalTypeParameter {

  public <T> List<T> methodUsingLocalTypeParameter(T arg) {
    return null;
  }
}