package tech.intellispaces.commons.java.reflection.samples;

import tech.intellispaces.commons.java.reflection.support.TesteeType;

import java.util.Collection;
import java.util.List;

@TesteeType
public class ClassWithMethodUsingWildcard {

  public List<?> methodUsingWildcard(Collection<?> arg) {
    return null;
  }
}