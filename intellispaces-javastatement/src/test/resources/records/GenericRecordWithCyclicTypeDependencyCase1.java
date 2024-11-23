package tech.intellispaces.java.reflection.samples;

import tech.intellispaces.java.reflection.support.TesteeType;

@TesteeType
public record GenericRecordWithCyclicTypeDependencyCase1<T extends GenericRecordWithCyclicTypeDependencyCase1<T>>() {
}
