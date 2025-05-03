package tech.intellispaces.jstatements.samples;

import java.util.Collection;
import java.util.List;

import tech.intellispaces.jstatements.support.TesteeType;

@TesteeType
public record RecordWithMethodUsingWildcard() {

  public List<?> methodUsingWildcard(Collection<?> arg) {
    return null;
  }
}