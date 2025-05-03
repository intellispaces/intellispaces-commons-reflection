package tech.intellispaces.jstatements.customtype;

import java.lang.annotation.Annotation;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;

import tech.intellispaces.jstatements.common.JavaModelFunctions;
import tech.intellispaces.jstatements.context.TypeContext;
import tech.intellispaces.jstatements.context.TypeContexts;
import tech.intellispaces.jstatements.session.Session;

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
