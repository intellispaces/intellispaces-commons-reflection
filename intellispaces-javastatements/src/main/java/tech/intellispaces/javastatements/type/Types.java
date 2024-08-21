package tech.intellispaces.javastatements.type;

import tech.intellispaces.javastatements.reference.CustomTypeReferences;
import tech.intellispaces.javastatements.reference.TypeReference;

import java.util.List;

public interface Types {

  static <T> Type<T> get(TypeReference typeReference) {
    return new TypeBaseOnReferenceImpl<>(typeReference);
  }

  static <R extends B, B> Type<R> of(Class<B> baseClass) {
    return new ReferenceBasedType<>(CustomTypeReferences.get(baseClass), List.of());
  }

  static <R extends B, B, Q> Type<R> of(
      Class<B> baseClass,
      Class<Q> qualifierClass
  ) {
    return new ReferenceBasedType<>(
        CustomTypeReferences.get(baseClass), List.of(
            CustomTypeReferences.get(qualifierClass)
        )
    );
  }

  static <R extends B, B, Q1, Q2> Type<R> of(
      Class<B> baseClass,
      Class<Q1> qualifierClass1,
      Class<Q2> qualifierClass2
  ) {
    return new ReferenceBasedType<>(
        CustomTypeReferences.get(baseClass), List.of(
            CustomTypeReferences.get(qualifierClass1),
            CustomTypeReferences.get(qualifierClass2)
      )
    );
  }

  static <R extends B, B, Q1, Q2, Q3> Type<R> of(
      Class<B> baseClass,
      Class<Q1> qualifierClass1,
      Class<Q2> qualifierClass2,
      Class<Q3> qualifierClass3
  ) {
    return new ReferenceBasedType<>(
        CustomTypeReferences.get(baseClass), List.of(
            CustomTypeReferences.get(qualifierClass1),
            CustomTypeReferences.get(qualifierClass2),
            CustomTypeReferences.get(qualifierClass3)
      )
    );
  }

  static <R extends B, B, Q> Type<R> of(
      Class<B> baseClass,
      Type<Q> qualifierType
  ) {
    return new ReferenceBasedType<>(
        CustomTypeReferences.get(baseClass), List.of(qualifierType.typeReference())
    );
  }

  static <R extends B, B, Q1, Q2> Type<R> of(
      Class<B> baseClass,
      Type<Q1> qualifierType1,
      Type<Q2> qualifierType2
  ) {
    return new ReferenceBasedType<>(
        CustomTypeReferences.get(baseClass), List.of(
        qualifierType1.typeReference(),
        qualifierType2.typeReference()
      )
    );
  }

  static <R extends B, B, Q1, Q2, Q3> Type<R> of(
      Class<B> baseClass,
      Type<Q1> qualifierType1,
      Type<Q2> qualifierType2,
      Type<Q3> qualifierType3
  ) {
    return new ReferenceBasedType<>(
        CustomTypeReferences.get(baseClass), List.of(
        qualifierType1.typeReference(),
        qualifierType2.typeReference(),
        qualifierType3.typeReference()
      )
    );
  }
}
