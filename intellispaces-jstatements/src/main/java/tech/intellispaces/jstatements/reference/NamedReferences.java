package tech.intellispaces.jstatements.reference;

import java.util.List;

import tech.intellispaces.jstatements.Statement;

public interface NamedReferences {

  static NamedReference get(String name, Statement owner, List<ReferenceBound> extendedBounds) {
    return new NamedReferenceImpl(name, owner, extendedBounds);
  }
}
