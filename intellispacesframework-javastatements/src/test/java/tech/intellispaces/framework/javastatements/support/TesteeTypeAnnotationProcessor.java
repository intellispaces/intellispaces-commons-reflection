package tech.intellispaces.framework.javastatements.support;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.util.Set;

public class TesteeTypeAnnotationProcessor extends AbstractProcessor {

  private TypeElement testee;

  @Override
  public SourceVersion getSupportedSourceVersion() {
    return SourceVersion.RELEASE_17;
  }

  @Override
  public Set<String> getSupportedAnnotationTypes() {
    return Set.of(TesteeType.class.getName());
  }

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    for (var annotatedElement : roundEnv.getElementsAnnotatedWith(TesteeType.class)) {
      this.testee = (TypeElement) annotatedElement;
    }
    return true;
  }

  public TypeElement testee() {
    return testee;
  }
}
