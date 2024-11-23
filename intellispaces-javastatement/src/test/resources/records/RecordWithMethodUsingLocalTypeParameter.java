package tech.intellispaces.java.reflection.samples;

import tech.intellispaces.java.reflection.support.TesteeType;

import java.util.List;

@TesteeType
public record RecordWithMethodUsingLocalTypeParameter() {

  public <T> List<T> methodUsingLocalTypeParameter(T arg) {
    return null;
  }
}