package intellispaces.javastatements.object.context;

import intellispaces.commons.function.CommonFunctions;
import intellispaces.javastatements.model.context.ContextTypeParameter;
import intellispaces.javastatements.model.context.NameContext;
import intellispaces.javastatements.model.reference.NamedTypeReference;
import intellispaces.javastatements.model.reference.NonPrimitiveTypeReference;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

class NameContextObject implements NameContext, NameContextBlank {
  private NameContext parentContext;
  private final Map<String, ContextTypeParameter> map;

  NameContextObject() {
    this.parentContext = NameContextBuilder.empty();
    this.map = new HashMap<>();
  }

  NameContextObject(NameContext parentContext, Map<String, ContextTypeParameter> map) {
    this.parentContext = parentContext;
    this.map = map;
  }

  @Override
  public boolean contains(String name) {
    return parentContext.contains(name) || map.containsKey(name);
  }

  @Override
  public Optional<ContextTypeParameter> get(String name) {
    return Optional.ofNullable(CommonFunctions.coalesce(
        () -> parentContext.get(name).orElse(null),
        () -> map.get(name)
    ));
  }

  @Override
  public NameContextBlank setParentContext(NameContext parentContext) {
    this.parentContext = parentContext;
    return this;
  }

  @Override
  public NameContextBlank addTypeParam(String typeParamName, NamedTypeReference type) {
    this.map.put(typeParamName, new ContextTypeParameterObject(type, null));
    return this;
  }

  @Override
  public NameContextBlank addTypeParam(String typeParamName, NamedTypeReference type, NonPrimitiveTypeReference value) {
    this.map.put(typeParamName, new ContextTypeParameterObject(type, value));
    return this;
  }
}
