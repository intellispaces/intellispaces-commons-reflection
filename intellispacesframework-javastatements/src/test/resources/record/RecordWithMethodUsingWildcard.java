package tech.intellispacesframework.javastatements.samples;

import tech.intellispacesframework.javastatements.support.TesteeType;

import java.util.Collection;
import java.util.List;

@TesteeType
public record RecordWithMethodUsingWildcard() {

  public List<?> methodUsingWildcard(Collection<?> arg) {
    return null;
  }
}