package tech.intellispaces.statementsj.reference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import tech.intellispaces.statementsj.Statement;
import tech.intellispaces.statementsj.StatementType;
import tech.intellispaces.statementsj.StatementTypes;

class NamedReferenceImpl extends AbstractTypeReference implements NamedReference {
  private final String name;
  private final Statement owner;
  private final List<ReferenceBound> extendedBounds;

  NamedReferenceImpl(String name, Statement owner, List<ReferenceBound> extendedBounds) {
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
  public List<ReferenceBound> extendedBounds() {
    return extendedBounds;
  }

  @Override
  public TypeReference effective(Map<String, NotPrimitiveReference> typeMapping) {
    TypeReference specifiedReference = typeMapping.get(name);
    if (specifiedReference != null) {
      return specifiedReference;
    }
    List<ReferenceBound> curExtendedBounds = extendedBounds();
    List<ReferenceBound> newExtendedBounds = new ArrayList<>();
    for (ReferenceBound curExtendedBound : curExtendedBounds) {
      newExtendedBounds.add((ReferenceBound) curExtendedBound.effective(typeMapping));
    }
    return new NamedReferenceImpl(name, owner(), newExtendedBounds);
  }

  @Override
  public boolean isVoidType() {
    return false;
  }
}
