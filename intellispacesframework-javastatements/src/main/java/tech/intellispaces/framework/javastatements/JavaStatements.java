package tech.intellispaces.framework.javastatements;

import tech.intellispaces.framework.javastatements.session.Session;
import tech.intellispaces.framework.javastatements.session.Sessions;
import tech.intellispaces.framework.javastatements.statement.Statement;
import tech.intellispaces.framework.javastatements.statement.Statements;
import tech.intellispaces.framework.javastatements.statement.custom.AnnotationStatement;
import tech.intellispaces.framework.javastatements.statement.custom.AnnotationStatements;
import tech.intellispaces.framework.javastatements.statement.custom.ClassStatement;
import tech.intellispaces.framework.javastatements.statement.custom.ClassStatements;
import tech.intellispaces.framework.javastatements.statement.custom.CustomType;
import tech.intellispaces.framework.javastatements.statement.custom.CustomTypes;
import tech.intellispaces.framework.javastatements.statement.custom.EnumStatement;
import tech.intellispaces.framework.javastatements.statement.custom.EnumStatements;
import tech.intellispaces.framework.javastatements.statement.custom.InterfaceStatement;
import tech.intellispaces.framework.javastatements.statement.custom.InterfaceStatements;
import tech.intellispaces.framework.javastatements.statement.custom.RecordStatement;
import tech.intellispaces.framework.javastatements.statement.custom.RecordStatements;
import tech.intellispaces.framework.javastatements.statement.reference.CustomTypeReference;
import tech.intellispaces.framework.javastatements.statement.reference.CustomTypeReferences;

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

  static ClassStatement classStatement(TypeElement typeElement) {
    return ClassStatements.of(typeElement, Sessions.get());
  }

  static ClassStatement classStatement(TypeElement typeElement, Session session) {
    return ClassStatements.of(typeElement, session);
  }

  static InterfaceStatement interfaceStatement(TypeElement typeElement) {
    return InterfaceStatements.of(typeElement, Sessions.get());
  }

  static InterfaceStatement interfaceStatement(TypeElement typeElement, Session session) {
    return InterfaceStatements.of(typeElement, session);
  }

  static RecordStatement recordStatement(TypeElement typeElement) {
    return RecordStatements.of(typeElement, Sessions.get());
  }

  static RecordStatement recordStatement(TypeElement typeElement, Session session) {
    return RecordStatements.of(typeElement, session);
  }

  static EnumStatement enumStatement(TypeElement typeElement) {
    return EnumStatements.of(typeElement, Sessions.get());
  }

  static EnumStatement enumStatement(TypeElement typeElement, Session session) {
    return EnumStatements.of(typeElement, session);
  }

  static AnnotationStatement annotationStatement(TypeElement typeElement) {
    return AnnotationStatements.of(typeElement, Sessions.get());
  }

  static AnnotationStatement annotationStatement(TypeElement typeElement, Session session) {
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
