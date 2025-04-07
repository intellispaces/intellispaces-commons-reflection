package tech.intellispaces.reflection.samples;

import tech.intellispaces.reflection.support.TesteeType;

@TesteeType
public class GenericClassWithOneTypeParameter<T> {

  public T process(T arg) {
    return null;
  }
}