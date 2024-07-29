package tech.intellispaces.javastatements;

import tech.intellispaces.javastatements.session.Session;
import tech.intellispaces.javastatements.session.Sessions;
import tech.intellispaces.javastatements.statement.Statement;
import tech.intellispaces.javastatements.statement.Statements;
import tech.intellispaces.javastatements.statement.reference.CustomTypeReference;
import tech.intellispaces.javastatements.statement.reference.CustomTypeReferences;
import tech.intellispaces.javastatements.statement.customtype.AnnotationStatements;
import tech.intellispaces.javastatements.statement.customtype.AnnotationType;
import tech.intellispaces.javastatements.statement.customtype.ClassStatements;
import tech.intellispaces.javastatements.statement.customtype.ClassType;
import tech.intellispaces.javastatements.statement.customtype.CustomType;
import tech.intellispaces.javastatements.statement.customtype.CustomTypes;
import tech.intellispaces.javastatements.statement.customtype.EnumStatements;
import tech.intellispaces.javastatements.statement.customtype.EnumType;
import tech.intellispaces.javastatements.statement.customtype.InterfaceStatements;
import tech.intellispaces.javastatements.statement.customtype.InterfaceType;
import tech.intellispaces.javastatements.statement.customtype.RecordStatements;
import tech.intellispaces.javastatements.statement.customtype.RecordType;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;

/**
 * Java statements facade.
 */
public interface JavaStatements {

  static Statement statement(Element element) {
    return Statements.of(element);
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
    return ClassStatements.of(typeElement, Sessions.get());
  }

  static ClassType classStatement(TypeElement typeElement, Session session) {
    return ClassStatements.of(typeElement, session);
  }

  static InterfaceType interfaceStatement(TypeElement typeElement) {
    return InterfaceStatements.of(typeElement, Sessions.get());
  }

  static InterfaceType interfaceStatement(TypeElement typeElement, Session session) {
    return InterfaceStatements.of(typeElement, session);
  }

  static RecordType recordStatement(TypeElement typeElement) {
    return RecordStatements.of(typeElement, Sessions.get());
  }

  static RecordType recordStatement(TypeElement typeElement, Session session) {
    return RecordStatements.of(typeElement, session);
  }

  static EnumType enumStatement(TypeElement typeElement) {
    return EnumStatements.of(typeElement, Sessions.get());
  }

  static EnumType enumStatement(TypeElement typeElement, Session session) {
    return EnumStatements.of(typeElement, session);
  }

  static AnnotationType annotationStatement(TypeElement typeElement) {
    return AnnotationStatements.of(typeElement, Sessions.get());
  }

  static AnnotationType annotationStatement(TypeElement typeElement, Session session) {
    return AnnotationStatements.of(typeElement, session);
  }

  static CustomTypeReference customTypeReference(Class<?> aClass) {
    return CustomTypeReferences.of(aClass);
  }

  static CustomTypeReference customTypeReference(TypeElement typeElement) {
    return CustomTypeReferences.of(typeElement, Sessions.get());
  }

  static CustomTypeReference customTypeReference(TypeElement typeElement, Session session) {
    return CustomTypeReferences.of(typeElement, session);
  }

  static CustomTypeReference customTypeReference(DeclaredType declaredType) {
    return CustomTypeReferences.of(declaredType, Sessions.get());
  }

  static CustomTypeReference customTypeReference(DeclaredType declaredType, Session session) {
    return CustomTypeReferences.of(declaredType, session);
  }
}
