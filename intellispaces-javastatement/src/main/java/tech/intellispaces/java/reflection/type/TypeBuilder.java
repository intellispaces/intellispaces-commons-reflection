package tech.intellispaces.java.reflection.type;

import tech.intellispaces.java.reflection.reference.TypeReference;
import tech.intellispaces.java.reflection.reference.TypeReferences;

import java.lang.reflect.ParameterizedType;

public abstract class TypeBuilder<T> {
  private final DerivedType<T> type;

  protected TypeBuilder() {
    java.lang.reflect.Type reflectType = getClass().getGenericSuperclass();
    ParameterizedType parameterizedType = (ParameterizedType) reflectType;
    java.lang.reflect.Type[] typeArguments = parameterizedType.getActualTypeArguments();
    TypeReference typeReference = TypeReferences.of(typeArguments[0]);
    this.type = DerivedTypes.get(typeReference);
  }

  public DerivedType<T> get() {
    return type;
  }
}
