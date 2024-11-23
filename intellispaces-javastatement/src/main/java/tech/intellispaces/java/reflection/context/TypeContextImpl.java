package tech.intellispaces.java.reflection.context;

import tech.intellispaces.java.reflection.reference.NamedReference;
import tech.intellispaces.java.reflection.reference.NotPrimitiveReference;
import tech.intellispaces.entity.object.ObjectFunctions;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

class TypeContextImpl implements TypeContext, TypeContextBlank {
  private TypeContext parentContext;
  private final Map<String, ContextTypeParameter> map;

  TypeContextImpl() {
    this.parentContext = TypeContexts.empty();
    this.map = new HashMap<>();
  }

  TypeContextImpl(TypeContext parentContext, Map<String, ContextTypeParameter> map) {
    this.parentContext = parentContext;
    this.map = map;
  }

  @Override
  public boolean contains(String typeParamName) {
    return parentContext.contains(typeParamName) || map.containsKey(typeParamName);
  }

  @Override
  public Optional<ContextTypeParameter> get(String typeParamName) {
    return Optional.ofNullable(ObjectFunctions.coalesce(
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
  public TypeContextBlank addTypeParam(String typeParamName, NamedReference type) {
    this.map.put(typeParamName, new ContextTypeParameterImpl(type, null));
    return this;
  }

  @Override
  public TypeContextBlank addTypeParam(
      String typeParamName, NamedReference type, NotPrimitiveReference value
  ) {
    this.map.put(typeParamName, new ContextTypeParameterImpl(type, value));
    return this;
  }
}
