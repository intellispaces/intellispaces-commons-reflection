package tech.intellispaces.java.reflection.samples;

import tech.intellispaces.java.reflection.samples.TestRecord;
import tech.intellispaces.java.reflection.support.TesteeType;

@TesteeType
public interface InterfaceWithRecordGetter {

  TestRecord recordGetter();
}