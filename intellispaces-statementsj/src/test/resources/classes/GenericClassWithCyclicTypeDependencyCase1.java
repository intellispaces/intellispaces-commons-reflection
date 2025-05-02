package tech.intellispaces.statementsj.samples;

import tech.intellispaces.statementsj.support.TesteeType;

@TesteeType
public class GenericClassWithCyclicTypeDependencyCase1<T extends GenericClassWithCyclicTypeDependencyCase1<T>> {
}
