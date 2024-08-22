package intellispaces.javastatements.samples;

import intellispaces.javastatements.support.TesteeType;

import java.util.Collection;
import java.util.List;

@TesteeType
public class ClassWithMethodUsingWildcard {

  public List<?> methodUsingWildcard(Collection<?> arg) {
    return null;
  }
}