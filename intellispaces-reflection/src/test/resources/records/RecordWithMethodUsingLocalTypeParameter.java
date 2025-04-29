package tech.intellispaces.reflection.samples;

import java.util.List;

import tech.intellispaces.reflection.support.TesteeType;

@TesteeType
public record RecordWithMethodUsingLocalTypeParameter() {

  public <T> List<T> methodUsingLocalTypeParameter(T arg) {
    return null;
  }
}