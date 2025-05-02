package tech.intellispaces.statementsj.samples;

import java.util.Collection;
import java.util.List;

import tech.intellispaces.statementsj.support.TesteeType;

@TesteeType
public enum EnumWithMethodUsingWildcard {
  ;

  public List<?> methodUsingWildcard(Collection<?> arg) {
    return null;
  }
}