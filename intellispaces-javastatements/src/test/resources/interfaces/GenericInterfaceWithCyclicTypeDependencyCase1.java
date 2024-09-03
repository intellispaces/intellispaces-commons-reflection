package intellispaces.common.javastatements.samples;

import intellispaces.common.javastatements.support.TesteeType;

@TesteeType
public interface GenericInterfaceWithCyclicTypeDependencyCase1<T extends GenericInterfaceWithCyclicTypeDependencyCase1<T>> {
}
