package tech.intellispaces.javastatements.samples;

import tech.intellispaces.javastatements.support.TesteeType;

@TesteeType
public record GenericRecordWithCyclicTypeDependencyCase1<T extends GenericRecordWithCyclicTypeDependencyCase1<T>>() {
}
