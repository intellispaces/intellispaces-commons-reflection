package tech.intellispaces.java.reflection.instance;

import tech.intellispaces.java.reflection.StatementType;
import tech.intellispaces.java.reflection.StatementTypes;
import tech.intellispaces.java.reflection.customtype.AnnotationFunctions;
import tech.intellispaces.java.reflection.customtype.AnnotationType;
import tech.intellispaces.java.reflection.customtype.Annotations;
import tech.intellispaces.java.reflection.session.Session;
import tech.intellispaces.action.cache.CachedSupplierActions;
import tech.intellispaces.action.supplier.SupplierAction;
import tech.intellispaces.entity.exception.NotImplementedExceptions;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Adapter of {@link AnnotationMirror} to {@link AnnotationInstance}.
 */
class AnnotationInstanceBasedOnAnnotationMirror implements AnnotationInstance {
  private final SupplierAction<AnnotationType> annotationStatementGetter;
  private final SupplierAction<Map<String, AnnotationElement>> elementsGetter;

  AnnotationInstanceBasedOnAnnotationMirror(AnnotationMirror annotationMirror, Session session) {
    this.annotationStatementGetter = CachedSupplierActions.get(annMirror ->
        Annotations.of((TypeElement) annMirror.getAnnotationType().asElement(), session), annotationMirror);
    this.elementsGetter = CachedSupplierActions.get(annMirror ->
        AnnotationFunctions.getAnnotationElements(annMirror, session).stream()
            .collect(Collectors.toMap(AnnotationElement::name, Function.identity())), annotationMirror
    );
  }

  public StatementType statementType() {
    return StatementTypes.AnnotationInstance;
  }

  @Override
  public AnnotationType annotationStatement() {
    return annotationStatementGetter.get();
  }

  @Override
  public Collection<AnnotationElement> elements() {
    return elementsGetter.get().values();
  }

  @Override
  public Optional<Instance> value() {
    return valueOf("value");
  }

  @Override
  public Optional<Instance> valueOf(String elementName) {
    return Optional.ofNullable(elementsGetter.get().get(elementName)).map(AnnotationElement::value);
  }

  @Override
  public <A extends Annotation> A asAnnotationOf(Class<A> annotationClass) {
    return AnnotationFunctions.asAnnotation(this, annotationClass);
  }

  @Override
  public String prettyDeclaration() {
    throw NotImplementedExceptions.withCode("VUTy4A");
  }
}
