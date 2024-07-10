package tech.intellispaces.framework.javastatements.statement.reference;

import tech.intellispaces.framework.javastatements.statement.StatementType;
import tech.intellispaces.framework.javastatements.statement.StatementTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class NamedTypeReferenceImpl extends AbstractTypeReference implements NamedTypeReference {
  private final String name;
  private final List<TypeBoundReference> extendedBounds;

  NamedTypeReferenceImpl(String name, List<TypeBoundReference> extendedBounds) {
    super();
    this.name = name;
    this.extendedBounds = extendedBounds;
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.NamedTypeReference;
  }

  @Override
  public String name() {
    return name;
  }

  @Override
  public List<TypeBoundReference> extendedBounds() {
    return extendedBounds;
  }

  @Override
  public TypeReference specify(Map<String, NonPrimitiveTypeReference> typeMapping) {
    TypeReference specifiedReference = typeMapping.get(name);
    if (specifiedReference != null) {
      return specifiedReference;
    }
    List<TypeBoundReference> curExtendedBounds = extendedBounds();
    List<TypeBoundReference> newExtendedBounds = new ArrayList<>();
    for (TypeBoundReference curExtendedBound : curExtendedBounds) {
      newExtendedBounds.add((TypeBoundReference) curExtendedBound.specify(typeMapping));
    }
    return new NamedTypeReferenceImpl(name, newExtendedBounds);
  }
}
