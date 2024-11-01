package intellispaces.common.javastatement.reference;

import intellispaces.common.javastatement.Statement;

import java.util.List;

public interface NamedReferences {

  static NamedReference get(String name, Statement owner, List<ReferenceBound> extendedBounds) {
    return new NamedReferenceImpl(name, owner, extendedBounds);
  }
}
