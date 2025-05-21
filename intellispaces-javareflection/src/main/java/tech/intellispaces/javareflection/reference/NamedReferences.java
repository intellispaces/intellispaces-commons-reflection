package tech.intellispaces.javareflection.reference;

import tech.intellispaces.javareflection.Statement;

import java.util.List;

public interface NamedReferences {

  static NamedReference get(String name, Statement owner, List<ReferenceBound> extendedBounds) {
    return new NamedReferenceImpl(name, owner, extendedBounds);
  }
}
