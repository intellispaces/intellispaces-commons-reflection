package tech.intellispaces.java.reflection.samples;

import tech.intellispaces.java.reflection.support.TesteeType;

@TesteeType
public class GenericClassWithOneTypeParameter<T> {

  public T process(T arg) {
    return null;
  }
}