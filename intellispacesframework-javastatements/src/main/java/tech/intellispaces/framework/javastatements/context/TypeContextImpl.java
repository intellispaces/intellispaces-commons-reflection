package tech.intellispaces.framework.javastatements.context;

import tech.intellispaces.framework.commons.datahandle.HandleFunctions;
import tech.intellispaces.framework.javastatements.statement.reference.NamedTypeReference;
import tech.intellispaces.framework.javastatements.statement.reference.NonPrimitiveTypeReference;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

class TypeContextImpl implements TypeContext, TypeContextBlank {
  private TypeContext parentContext;
  private final Map<String, ContextTypeParameter> map;

  public TypeContextImpl() {
    this.parentContext = TypeContexts.empty();
    this.map = new HashMap<>();
  }

  public TypeContextImpl(TypeContext parentContext, Map<String, ContextTypeParameter> map) {
    this.parentContext = parentContext;
    this.map = map;
  }

  @Override
  public boolean contains(String typeParamName) {
    return parentContext.contains(typeParamName) || map.containsKey(typeParamName);
  }

  @Override
  public Optional<ContextTypeParameter> get(String typeParamName) {
    return Optional.ofNullable(HandleFunctions.coalesce(
        () -> parentContext.get(typeParamName).orElse(null),
        () -> map.get(typeParamName)
    ));
  }

  @Override
  public TypeContextBlank setParentContext(TypeContext parentContext) {
    this.parentContext = parentContext;
    return this;
  }

  @Override
  public TypeContextBlank addTypeParam(String typeParamName, NamedTypeReference type) {
    this.map.put(typeParamName, new ContextTypeParameterImpl(type, null));
    return this;
  }

  @Override
  public TypeContextBlank addTypeParam(String typeParamName, NamedTypeReference type, NonPrimitiveTypeReference value) {
    this.map.put(typeParamName, new ContextTypeParameterImpl(type, value));
    return this;
  }
}
