package tech.intellispaces.java.reflection.samples;

import tech.intellispaces.java.reflection.support.TesteeType;

import java.util.Collection;

@TesteeType
public record RecordWithMethodUsingWildcardThatSuperOtherClass() {

  public void methodUsingWildcardThatSuperOtherClass(Collection<? super Number[]> arg) {
  }
}