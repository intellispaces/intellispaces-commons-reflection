package intellispaces.common.javastatements.samples;

import intellispaces.common.javastatements.support.TesteeType;

import java.util.List;

@TesteeType
public record RecordWithMethodUsingLocalTypeParameter() {

  public <T> List<T> methodUsingLocalTypeParameter(T arg) {
    return null;
  }
}