package tech.intellispaces.statementsj.samples;

import tech.intellispaces.statementsj.support.TesteeType;

public class GenericInterfaceWithCyclicTypeDependencyCase2 {

  @TesteeType
  public interface InterfaceA<T1 extends InterfaceB<?>> {}

  public interface InterfaceB<T2 extends InterfaceA<?>> {}
}
