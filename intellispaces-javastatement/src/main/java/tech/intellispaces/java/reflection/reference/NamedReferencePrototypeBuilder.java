package tech.intellispaces.java.reflection.reference;

import tech.intellispaces.java.reflection.Statement;

import java.util.List;

public class NamedReferencePrototypeBuilder {
  private String name;
  private List<ReferenceBound> extendedBounds;
  private Statement owner;

  NamedReferencePrototypeBuilder(NamedReference prototype) {
    this.name = prototype.name();
    this.extendedBounds = prototype.extendedBounds();
    this.owner = prototype.owner();
  }

  public NamedReferencePrototypeBuilder name(String name) {
    this.name = name;
    return this;
  }

  public NamedReferencePrototypeBuilder extendedBounds(List<ReferenceBound> extendedBounds) {
    this.extendedBounds = extendedBounds;
    return this;
  }

  public NamedReferencePrototypeBuilder owner(Statement owner) {
    this.owner = owner;
    return this;
  }

  public NamedReference build() {
    return new NamedReferenceImpl(name, owner, extendedBounds);
  }
}
