package tech.intellispaces.commons.reflection.samples;

import tech.intellispaces.commons.reflection.support.TesteeType;

import java.util.List;

@TesteeType
public record RecordWithMethodUsingLocalTypeParameter() {

  public <T> List<T> methodUsingLocalTypeParameter(T arg) {
    return null;
  }
}