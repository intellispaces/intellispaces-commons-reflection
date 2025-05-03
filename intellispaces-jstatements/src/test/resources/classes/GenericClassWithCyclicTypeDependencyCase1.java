package tech.intellispaces.jstatements.samples;

import tech.intellispaces.jstatements.support.TesteeType;

@TesteeType
public class GenericClassWithCyclicTypeDependencyCase1<T extends GenericClassWithCyclicTypeDependencyCase1<T>> {
}
