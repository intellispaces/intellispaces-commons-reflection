package tech.intellispaces.commons.java.reflection.customtype;

import tech.intellispaces.commons.java.reflection.common.JavaModelFunctions;
import tech.intellispaces.commons.java.reflection.context.TypeContext;
import tech.intellispaces.commons.java.reflection.context.TypeContexts;
import tech.intellispaces.commons.java.reflection.session.Session;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;

public interface Annotations {

  static AnnotationType of(Class<? extends Annotation> annotationClass) {
    return new AnnotationBasedOnLangAnnotation(annotationClass);
  }

  static AnnotationType of(TypeElement typeElement, Session session) {
    return of(typeElement, TypeContexts.empty(), session);
  }

  static AnnotationType of(TypeElement typeElement, TypeContext typeContext, Session session) {
    return JavaModelFunctions.asCustomStatement(
        typeElement,
        ElementKind.ANNOTATION_TYPE,
        Annotations::create,
        typeContext,
        session
    );
  }

  private static AnnotationType create(
      TypeElement typeElement, TypeContext typeContext, Session session
  ) {
    return new AnnotationBasedOnTypeElement(typeElement, typeContext, session);
  }
}
