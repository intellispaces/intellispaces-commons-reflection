package tech.intellispaces.jstatements.instance;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.TypeElement;

import tech.intellispaces.actions.cache.CachedSupplierActions;
import tech.intellispaces.actions.supplier.SupplierAction;
import tech.intellispaces.commons.exception.NotImplementedExceptions;
import tech.intellispaces.jstatements.StatementType;
import tech.intellispaces.jstatements.StatementTypes;
import tech.intellispaces.jstatements.customtype.AnnotationFunctions;
import tech.intellispaces.jstatements.customtype.AnnotationType;
import tech.intellispaces.jstatements.customtype.Annotations;
import tech.intellispaces.jstatements.session.Session;

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
