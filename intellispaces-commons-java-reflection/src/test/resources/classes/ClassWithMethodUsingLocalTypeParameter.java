package tech.intellispaces.commons.java.reflection.samples;

import tech.intellispaces.commons.java.reflection.support.TesteeType;

import java.util.List;

@TesteeType
public class ClassWithMethodUsingLocalTypeParameter {

  public <T> List<T> methodUsingLocalTypeParameter(T arg) {
    return null;
  }
}