package intellispaces.common.javastatements.customtype;

import intellispaces.common.javastatements.context.TypeContext;
import intellispaces.common.javastatements.common.JavaModelFunctions;
import intellispaces.common.javastatements.context.TypeContexts;
import intellispaces.common.javastatements.session.Session;

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
