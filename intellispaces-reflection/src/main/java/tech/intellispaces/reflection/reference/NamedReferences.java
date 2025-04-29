package tech.intellispaces.reflection.reference;

import java.util.List;

import tech.intellispaces.reflection.Statement;

public interface NamedReferences {

  static NamedReference get(String name, Statement owner, List<ReferenceBound> extendedBounds) {
    return new NamedReferenceImpl(name, owner, extendedBounds);
  }
}
