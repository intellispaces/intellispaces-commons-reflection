package intellispaces.javastatements.sample;

import intellispaces.javastatements.support.TesteeType;

public class GenericClassWithCyclicTypeDependencyCase2 {

  @TesteeType
  public class ClassA<T1 extends ClassB<?>> {}

  public class ClassB<T2 extends ClassA<?>> {}
}
