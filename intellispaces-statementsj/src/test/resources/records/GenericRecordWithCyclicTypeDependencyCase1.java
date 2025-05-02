package tech.intellispaces.statementsj.samples;

import tech.intellispaces.statementsj.support.TesteeType;

@TesteeType
public record GenericRecordWithCyclicTypeDependencyCase1<T extends GenericRecordWithCyclicTypeDependencyCase1<T>>() {
}
