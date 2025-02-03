package tech.intellispaces.commons.java.reflection.samples;

import tech.intellispaces.commons.java.reflection.support.TesteeType;

@TesteeType
public class GenericClassWithCyclicTypeDependencyCase1<T extends GenericClassWithCyclicTypeDependencyCase1<T>> {
}
