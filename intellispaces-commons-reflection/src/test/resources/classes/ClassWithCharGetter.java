package tech.intellispaces.commons.reflection.samples;

import tech.intellispaces.commons.reflection.support.TesteeType;

@TesteeType
public class ClassWithCharGetter {

  public char charGetter() {
    return 'a';
  }
}