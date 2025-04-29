package tech.intellispaces.reflection.samples;

import java.util.Collection;
import java.util.List;

import tech.intellispaces.reflection.support.TesteeType;

@TesteeType
public class ClassWithMethodUsingWildcard {

  public List<?> methodUsingWildcard(Collection<?> arg) {
    return null;
  }
}