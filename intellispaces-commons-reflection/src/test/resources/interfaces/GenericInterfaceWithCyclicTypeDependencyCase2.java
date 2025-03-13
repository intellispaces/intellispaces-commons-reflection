package tech.intellispaces.commons.reflection.samples;

import tech.intellispaces.commons.reflection.support.TesteeType;

public class GenericInterfaceWithCyclicTypeDependencyCase2 {

  @TesteeType
  public interface InterfaceA<T1 extends InterfaceB<?>> {}

  public interface InterfaceB<T2 extends InterfaceA<?>> {}
}
