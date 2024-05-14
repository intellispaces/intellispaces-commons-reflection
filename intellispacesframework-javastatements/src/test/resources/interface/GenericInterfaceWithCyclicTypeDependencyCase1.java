package tech.intellispacesframework.javastatements.samples;

import tech.intellispacesframework.javastatements.support.TesteeType;

@TesteeType
public interface GenericInterfaceWithCyclicTypeDependencyCase1<T extends GenericInterfaceWithCyclicTypeDependencyCase1<T>> {
}
