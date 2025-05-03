package tech.intellispaces.jstatements.samples;

import tech.intellispaces.jstatements.support.TesteeType;

@TesteeType
public record GenericRecordWithCyclicTypeDependencyCase1<T extends GenericRecordWithCyclicTypeDependencyCase1<T>>() {
}
