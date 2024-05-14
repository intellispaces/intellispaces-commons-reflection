package tech.intellispacesframework.javastatements.samples;

import tech.intellispacesframework.javastatements.support.TesteeType;

@TesteeType
public record GenericRecordWithCyclicTypeDependencyCase1<T extends GenericRecordWithCyclicTypeDependencyCase1<T>>() {
}
