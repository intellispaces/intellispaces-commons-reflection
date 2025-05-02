package tech.intellispaces.statementsj.samples;

import tech.intellispaces.statementsj.support.TesteeType;

@TesteeType
public interface GenericInterfaceWithCyclicTypeDependencyCase1<T extends GenericInterfaceWithCyclicTypeDependencyCase1<T>> {
}
