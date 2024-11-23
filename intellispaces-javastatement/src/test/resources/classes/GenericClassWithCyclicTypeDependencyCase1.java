package tech.intellispaces.java.reflection.samples;

import tech.intellispaces.java.reflection.support.TesteeType;

@TesteeType
public class GenericClassWithCyclicTypeDependencyCase1<T extends GenericClassWithCyclicTypeDependencyCase1<T>> {
}
