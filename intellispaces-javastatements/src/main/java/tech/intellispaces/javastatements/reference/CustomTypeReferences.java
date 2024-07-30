package tech.intellispaces.javastatements.reference;

import tech.intellispaces.javastatements.context.TypeContext;
import tech.intellispaces.javastatements.context.TypeContexts;
import tech.intellispaces.javastatements.customtype.CustomType;
import tech.intellispaces.javastatements.session.Session;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import java.util.List;

public interface CustomTypeReferences {

  static CustomTypeReference of(Class<?> aClass) {
    return new CustomTypeBasedOnClassReference(aClass);
  }

  static CustomTypeReference of(TypeElement typeElement, Session session) {
    return of(typeElement, TypeContexts.empty(), session);
  }

  static CustomTypeReference of(TypeElement typeElement, TypeContext typeContext, Session session) {
    return new CustomTypeReferenceBasedOnTypeElementReference(typeElement, typeContext, session);
  }

  static CustomTypeReference of(DeclaredType declaredType, Session session) {
    return of(declaredType, TypeContexts.empty(), session);
  }

  static CustomTypeReference of(DeclaredType declaredType, TypeContext typeContext, Session session) {
    return new CustomTypeReferenceBasedOnDeclaredTypeReference(declaredType, typeContext, session);
  }

  static CustomTypeReference of(CustomType targetType) {
    return of(targetType, List.of());
  }

  static CustomTypeReference of(CustomType customType, List<NotPrimitiveTypeReference> typeArguments) {
    return new CustomTypeReferenceImpl(customType, typeArguments);
  }
}
