package intellispaces.common.javastatements;

import intellispaces.common.javastatements.common.JavaModelFunctions;
import intellispaces.common.javastatements.customtype.AnnotationType;
import intellispaces.common.javastatements.customtype.Annotations;
import intellispaces.common.javastatements.customtype.ClassType;
import intellispaces.common.javastatements.customtype.Classes;
import intellispaces.common.javastatements.customtype.CustomType;
import intellispaces.common.javastatements.customtype.CustomTypes;
import intellispaces.common.javastatements.customtype.EnumType;
import intellispaces.common.javastatements.customtype.Enums;
import intellispaces.common.javastatements.customtype.InterfaceType;
import intellispaces.common.javastatements.customtype.Interfaces;
import intellispaces.common.javastatements.customtype.RecordType;
import intellispaces.common.javastatements.customtype.Records;
import intellispaces.common.javastatements.reference.CustomTypeReference;
import intellispaces.common.javastatements.reference.CustomTypeReferences;
import intellispaces.common.javastatements.session.Session;
import intellispaces.common.javastatements.session.Sessions;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;

/**
 * Java statements facade.
 */
public interface JavaStatements {

  static Statement statement(Element element) {
    return JavaModelFunctions.of(element);
  }

  static CustomType customTypeStatement(Class<?> aClass) {
    return CustomTypes.of(aClass);
  }

  static CustomType customTypeStatement(TypeElement typeElement) {
    return CustomTypes.of(typeElement, Sessions.get());
  }

  static CustomType customTypeStatement(TypeElement typeElement, Session session) {
    return CustomTypes.of(typeElement, session);
  }

  static ClassType classStatement(TypeElement typeElement) {
    return Classes.of(typeElement, Sessions.get());
  }

  static ClassType classStatement(TypeElement typeElement, Session session) {
    return Classes.of(typeElement, session);
  }

  static InterfaceType interfaceStatement(TypeElement typeElement) {
    return Interfaces.of(typeElement, Sessions.get());
  }

  static InterfaceType interfaceStatement(TypeElement typeElement, Session session) {
    return Interfaces.of(typeElement, session);
  }

  static RecordType recordStatement(TypeElement typeElement) {
    return Records.of(typeElement, Sessions.get());
  }

  static RecordType recordStatement(TypeElement typeElement, Session session) {
    return Records.of(typeElement, session);
  }

  static EnumType enumStatement(TypeElement typeElement) {
    return Enums.of(typeElement, Sessions.get());
  }

  static EnumType enumStatement(TypeElement typeElement, Session session) {
    return Enums.of(typeElement, session);
  }

  static AnnotationType annotationStatement(TypeElement typeElement) {
    return Annotations.of(typeElement, Sessions.get());
  }

  static AnnotationType annotationStatement(TypeElement typeElement, Session session) {
    return Annotations.of(typeElement, session);
  }

  static CustomTypeReference customTypeReference(Class<?> aClass) {
    return CustomTypeReferences.get(aClass);
  }

  static CustomTypeReference customTypeReference(TypeElement typeElement) {
    return CustomTypeReferences.get(typeElement, Sessions.get());
  }

  static CustomTypeReference customTypeReference(TypeElement typeElement, Session session) {
    return CustomTypeReferences.get(typeElement, session);
  }

  static CustomTypeReference customTypeReference(DeclaredType declaredType) {
    return CustomTypeReferences.get(declaredType, Sessions.get());
  }

  static CustomTypeReference customTypeReference(DeclaredType declaredType, Session session) {
    return CustomTypeReferences.get(declaredType, session);
  }
}
