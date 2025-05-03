package tech.intellispaces.jstatements.samples;

import tech.intellispaces.jstatements.support.TesteeType;

@TesteeType
public class GenericClassWithOneTypeParameter<T> {

  public T process(T arg) {
    return null;
  }
}