package tech.intellispaces.framework.javastatements.statement.type;

import tech.intellispaces.framework.javastatements.statement.Statement;
import tech.intellispaces.framework.javastatements.statement.StatementType;
import tech.intellispaces.framework.javastatements.statement.StatementTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class NamedTypeImpl extends AbstractType implements NamedType {
  private final String name;
  private final Statement owner;
  private final List<TypeBound> extendedBounds;

  NamedTypeImpl(String name, Statement owner, List<TypeBound> extendedBounds) {
    super();
    this.name = name;
    this.owner = owner;
    this.extendedBounds = extendedBounds;
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.NamedType;
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
  public List<TypeBound> extendedBounds() {
    return extendedBounds;
  }

  @Override
  public Type specify(Map<String, NonPrimitiveType> typeMapping) {
    Type specifiedReference = typeMapping.get(name);
    if (specifiedReference != null) {
      return specifiedReference;
    }
    List<TypeBound> curExtendedBounds = extendedBounds();
    List<TypeBound> newExtendedBounds = new ArrayList<>();
    for (TypeBound curExtendedBound : curExtendedBounds) {
      newExtendedBounds.add((TypeBound) curExtendedBound.specify(typeMapping));
    }
    return new NamedTypeImpl(name, owner(), newExtendedBounds);
  }
}
