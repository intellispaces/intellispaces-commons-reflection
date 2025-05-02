package tech.intellispaces.statementsj.samples;

import java.util.List;

import tech.intellispaces.statementsj.support.TesteeType;

@TesteeType
public record RecordWithMethodUsingLocalTypeParameter() {

  public <T> List<T> methodUsingLocalTypeParameter(T arg) {
    return null;
  }
}