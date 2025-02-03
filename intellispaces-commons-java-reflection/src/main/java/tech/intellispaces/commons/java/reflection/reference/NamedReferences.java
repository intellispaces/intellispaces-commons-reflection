package tech.intellispaces.commons.java.reflection.reference;

import tech.intellispaces.commons.java.reflection.Statement;

import java.util.List;

public interface NamedReferences {

  static NamedReference get(String name, Statement owner, List<ReferenceBound> extendedBounds) {
    return new NamedReferenceImpl(name, owner, extendedBounds);
  }
}
