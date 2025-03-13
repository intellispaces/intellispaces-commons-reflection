package tech.intellispaces.commons.reflection.samples;

import tech.intellispaces.commons.reflection.support.TesteeType;

@TesteeType
public class GenericClassWithOneTypeParameter<T> {

  public T process(T arg) {
    return null;
  }
}