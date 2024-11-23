package tech.intellispaces.java.reflection.samples;

import tech.intellispaces.java.reflection.support.TesteeType;

@TesteeType
public interface GenericInterfaceWithCyclicTypeDependencyCase1<T extends GenericInterfaceWithCyclicTypeDependencyCase1<T>> {
}
