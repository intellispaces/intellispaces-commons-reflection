package tech.intellispaces.reflection.samples;

import java.util.Collection;

import tech.intellispaces.reflection.support.TesteeType;

@TesteeType
public record RecordWithMethodUsingWildcardThatExtendsOtherClass() {

  public void methodUsingWildcardThatExtendsOtherClass(Collection<? extends Number> arg) {
  }
}