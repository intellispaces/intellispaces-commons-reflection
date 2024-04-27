package tech.intellispacesframework.javastatements.sample;

import tech.intellispacesframework.javastatements.support.TesteeType;

public class GenericInterfaceWithCyclicTypeDependencyCase2 {

  @TesteeType
  public interface InterfaceA<T1 extends InterfaceB<?>> {}

  public interface InterfaceB<T2 extends InterfaceA<?>> {}
}
