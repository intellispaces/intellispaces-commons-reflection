package tech.intellispaces.javareflection.samples;

import tech.intellispaces.javareflection.support.TesteeType;

@TesteeType
public class ClassWithCharGetter {

  public char charGetter() {
    return 'a';
  }
}