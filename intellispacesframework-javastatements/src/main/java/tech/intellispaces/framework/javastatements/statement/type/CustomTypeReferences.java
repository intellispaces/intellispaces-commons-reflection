package tech.intellispaces.framework.javastatements.statement.type;

import tech.intellispaces.framework.javastatements.context.TypeContext;
import tech.intellispaces.framework.javastatements.context.TypeContexts;
import tech.intellispaces.framework.javastatements.session.Session;
import tech.intellispaces.framework.javastatements.statement.custom.CustomStatement;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import java.util.List;

public interface CustomTypeReferences {

  static CustomType of(Class<?> aClass) {
    return new CustomTypeBasedOnClass(aClass);
  }

  static CustomType of(TypeElement typeElement, Session session) {
    return of(typeElement, TypeContexts.empty(), session);
  }

  static CustomType of(TypeElement typeElement, TypeContext typeContext, Session session) {
    return new CustomTypeReferenceBasedOnTypeElement(typeElement, typeContext, session);
  }

  static CustomType of(DeclaredType declaredType, Session session) {
    return of(declaredType, TypeContexts.empty(), session);
  }

  static CustomType of(DeclaredType declaredType, TypeContext typeContext, Session session) {
    return new CustomTypeReferenceBasedOnDeclaredType(declaredType, typeContext, session);
  }

  static CustomType of(CustomStatement targetType) {
    return of(targetType, List.of());
  }

  static CustomType of(CustomStatement targetType, List<NotPrimitiveType> typeArguments) {
    return new CustomTypeImpl(targetType, typeArguments);
  }
}
