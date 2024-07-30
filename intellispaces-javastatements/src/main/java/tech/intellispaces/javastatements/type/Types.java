package tech.intellispaces.javastatements.type;

import tech.intellispaces.javastatements.reference.CustomTypeReferences;

import java.util.List;

public interface Types {

  static <R extends B, B> Type<R> of(Class<B> baseClass) {
    return new TypeImpl<>(CustomTypeReferences.of(baseClass), List.of());
  }

  static <R extends B, B, Q> Type<R> of(
      Class<B> baseClass,
      Class<Q> qualifierClass
  ) {
    return new TypeImpl<>(
        CustomTypeReferences.of(baseClass), List.of(
            CustomTypeReferences.of(qualifierClass)
        )
    );
  }

  static <R extends B, B, Q1, Q2> Type<R> of(
      Class<B> baseClass,
      Class<Q1> qualifierClass1,
      Class<Q2> qualifierClass2
  ) {
    return new TypeImpl<>(
        CustomTypeReferences.of(baseClass), List.of(
            CustomTypeReferences.of(qualifierClass1),
            CustomTypeReferences.of(qualifierClass2)
      )
    );
  }

  static <R extends B, B, Q1, Q2, Q3> Type<R> of(
      Class<B> baseClass,
      Class<Q1> qualifierClass1,
      Class<Q2> qualifierClass2,
      Class<Q3> qualifierClass3
  ) {
    return new TypeImpl<>(
        CustomTypeReferences.of(baseClass), List.of(
            CustomTypeReferences.of(qualifierClass1),
            CustomTypeReferences.of(qualifierClass2),
            CustomTypeReferences.of(qualifierClass3)
      )
    );
  }
}
