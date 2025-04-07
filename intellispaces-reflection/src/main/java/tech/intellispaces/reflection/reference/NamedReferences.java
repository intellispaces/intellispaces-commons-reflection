package tech.intellispaces.reflection.reference;

import tech.intellispaces.reflection.Statement;

import java.util.List;

public interface NamedReferences {

  static NamedReference get(String name, Statement owner, List<ReferenceBound> extendedBounds) {
    return new NamedReferenceImpl(name, owner, extendedBounds);
  }
}
