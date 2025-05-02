package tech.intellispaces.statementsj.samples;

import java.util.List;

import tech.intellispaces.statementsj.support.TesteeType;

@TesteeType
public class ClassWithMethodUsingLocalTypeParameter {

  public <T> List<T> methodUsingLocalTypeParameter(T arg) {
    return null;
  }
}