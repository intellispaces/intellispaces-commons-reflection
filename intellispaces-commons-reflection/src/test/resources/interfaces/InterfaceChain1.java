package tech.intellispaces.commons.reflection.samples;

import tech.intellispaces.commons.reflection.support.TesteeType;

public interface InterfaceChain1 {

  @TesteeType
  interface Interface1 extends Interface2<Integer> {
  }

  interface Interface2<N> extends Interface3<N> {
  }

  interface Interface3<E> {
  }
}
