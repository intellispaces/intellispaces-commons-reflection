package tech.intellispaces.jstatements.samples;

import tech.intellispaces.jstatements.support.TesteeType;

public class GenericClassWithCyclicTypeDependencyCase2 {

  @TesteeType
  public class ClassA<T1 extends ClassB<?>> {}

  public class ClassB<T2 extends ClassA<?>> {}
}
