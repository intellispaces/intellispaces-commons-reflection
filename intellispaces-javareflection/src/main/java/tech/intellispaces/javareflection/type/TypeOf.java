package tech.intellispaces.javareflection.type;

import tech.intellispaces.commons.type.ClassType;
import tech.intellispaces.javareflection.reference.TypeReference;
import tech.intellispaces.javareflection.reference.TypeReferences;

import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class TypeOf<T> implements Type<T> {
  private final Type<T> type;

  protected TypeOf() {
    java.lang.reflect.Type reflectType = getClass().getGenericSuperclass();
    ParameterizedType parameterizedType = (ParameterizedType) reflectType;
    java.lang.reflect.Type[] typeArguments = parameterizedType.getActualTypeArguments();
    TypeReference typeReference = TypeReferences.of(typeArguments[0]);
    this.type = Types.get(typeReference);
  }

  @Override
  public TypeReference typeReference() {
    return type.typeReference();
  }

  @Override
  public TypeReference baseTypeReference() {
    return type.baseTypeReference();
  }

  @Override
  public List<TypeReference> qualifierTypeReferences() {
    return type.qualifierTypeReferences();
  }

  @Override
  public tech.intellispaces.commons.type.Type<?> baseType() {
    return type.baseType();
  }

  @Override
  public List<tech.intellispaces.commons.type.Type<?>> qualifierTypes() {
    return type.qualifierTypes();
  }

  @Override
  public ClassType<T> asClassType() {
    return type.asClassType();
  }
}
