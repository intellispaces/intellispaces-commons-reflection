package tech.intellispaces.javastatements.reference;

import tech.intellispaces.javastatements.Statement;
import tech.intellispaces.javastatements.StatementType;
import tech.intellispaces.javastatements.StatementTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class NamedReferenceImpl extends AbstractTypeReference implements NamedReference {
  private final String name;
  private final Statement owner;
  private final List<TypeReferenceBound> extendedBounds;

  NamedReferenceImpl(String name, Statement owner, List<TypeReferenceBound> extendedBounds) {
    super();
    this.name = name;
    this.owner = owner;
    this.extendedBounds = extendedBounds;
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.NamedReference;
  }

  @Override
  public String name() {
    return name;
  }

  @Override
  public Statement owner() {
    return owner;
  }

  @Override
  public List<TypeReferenceBound> extendedBounds() {
    return extendedBounds;
  }

  @Override
  public TypeReference specify(Map<String, NotPrimitiveTypeReference> typeMapping) {
    TypeReference specifiedReference = typeMapping.get(name);
    if (specifiedReference != null) {
      return specifiedReference;
    }
    List<TypeReferenceBound> curExtendedBounds = extendedBounds();
    List<TypeReferenceBound> newExtendedBounds = new ArrayList<>();
    for (TypeReferenceBound curExtendedBound : curExtendedBounds) {
      newExtendedBounds.add((TypeReferenceBound) curExtendedBound.specify(typeMapping));
    }
    return new NamedReferenceImpl(name, owner(), newExtendedBounds);
  }
}
