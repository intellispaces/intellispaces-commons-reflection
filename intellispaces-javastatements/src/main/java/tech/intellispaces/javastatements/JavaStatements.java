package tech.intellispaces.javastatements;

import tech.intellispaces.javastatements.common.JavaModelFunctions;
import tech.intellispaces.javastatements.customtype.AnnotationStatements;
import tech.intellispaces.javastatements.customtype.AnnotationType;
import tech.intellispaces.javastatements.customtype.ClassType;
import tech.intellispaces.javastatements.customtype.Classes;
import tech.intellispaces.javastatements.customtype.CustomType;
import tech.intellispaces.javastatements.customtype.CustomTypes;
import tech.intellispaces.javastatements.customtype.EnumType;
import tech.intellispaces.javastatements.customtype.Enums;
import tech.intellispaces.javastatements.customtype.InterfaceType;
import tech.intellispaces.javastatements.customtype.Interfaces;
import tech.intellispaces.javastatements.customtype.RecordType;
import tech.intellispaces.javastatements.customtype.Records;
import tech.intellispaces.javastatements.reference.CustomTypeReference;
import tech.intellispaces.javastatements.reference.CustomTypeReferences;
import tech.intellispaces.javastatements.session.Session;
import tech.intellispaces.javastatements.session.Sessions;

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
    return AnnotationStatements.of(typeElement, Sessions.get());
  }

  static AnnotationType annotationStatement(TypeElement typeElement, Session session) {
    return AnnotationStatements.of(typeElement, session);
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
