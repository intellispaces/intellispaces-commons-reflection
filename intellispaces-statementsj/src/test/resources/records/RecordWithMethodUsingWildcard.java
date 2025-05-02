package tech.intellispaces.statementsj.samples;

import java.util.Collection;
import java.util.List;

import tech.intellispaces.statementsj.support.TesteeType;

@TesteeType
public record RecordWithMethodUsingWildcard() {

  public List<?> methodUsingWildcard(Collection<?> arg) {
    return null;
  }
}