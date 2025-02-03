package tech.intellispaces.commons.java.reflection.samples;

import tech.intellispaces.commons.java.reflection.support.TesteeType;

@TesteeType
public interface GenericInterfaceWithCyclicTypeDependencyCase1<T extends GenericInterfaceWithCyclicTypeDependencyCase1<T>> {
}
