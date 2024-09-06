package intellispaces.common.javastatement.samples;

import intellispaces.common.javastatement.support.TesteeType;

public class GenericInterfaceWithCyclicTypeDependencyCase2 {

  @TesteeType
  public interface InterfaceA<T1 extends InterfaceB<?>> {}

  public interface InterfaceB<T2 extends InterfaceA<?>> {}
}
