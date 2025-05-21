package tech.intellispaces.javareflection.reference;

import java.util.Map;
import javax.lang.model.element.TypeElement;

import org.junit.Test;

import tech.intellispaces.javareflection.AbstractCustomStatementTest;
import tech.intellispaces.javareflection.JavaStatements;
import tech.intellispaces.javareflection.customtype.CustomType;

import static org.assertj.core.api.Assertions.assertThat;

public class TypeReferenceFunctionsTest extends AbstractCustomStatementTest {

  @Test
  public void testGetTypeArgumentMapping() {
    // Given
    TypeElement typeElement = getTestElement("interfaces/InterfaceChain1.java");
    CustomType interface1 = JavaStatements.interfaceStatement(typeElement);
    CustomType interface2 = interface1.parentTypes().stream().filter(p -> p.targetType().simpleName().equals("Interface2")).findFirst().orElseThrow().targetType();
    CustomType interface3 = interface2.parentTypes().stream().filter(p -> p.targetType().simpleName().equals("Interface3")).findFirst().orElseThrow().targetType();

    // When
    Map<String, NotPrimitiveReference> typeArgumentMapping = TypeReferenceFunctions.getTypeArgumentMapping(
        interface1, interface3
    );

    // Then
    assertThat(typeArgumentMapping).hasSize(1);
    assertThat(typeArgumentMapping).containsKey("E");
    assertThat(typeArgumentMapping.get("E").asCustomTypeReference().orElseThrow().targetType().canonicalName()).isEqualTo(Integer.class.getCanonicalName());
  }
}
