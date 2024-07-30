package tech.intellispaces.javastatements.type;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link Types}
 */
public class TypesTest {

  @Test
  public void testOf_whenBaseTypeOnly() {
    // Given
    Type<String> stringType = Types.of(String.class);
    Type<Integer> integerType = Types.of(Integer.class);
    Type<Integer> objectType = Types.of(Object.class);

    // Then
    Assertions.assertThat(stringType.baseClass()).isSameAs(String.class);
    Assertions.assertThat(integerType.baseClass()).isSameAs(Integer.class);
    Assertions.assertThat(objectType.baseClass()).isSameAs(Object.class);

    Assertions.assertThat(stringType.baseType().asCustomTypeReferenceConfidently().targetType().canonicalName()).isSameAs(String.class.getCanonicalName());
    Assertions.assertThat(integerType.baseType().asCustomTypeReferenceConfidently().targetType().canonicalName()).isSameAs(Integer.class.getCanonicalName());
    Assertions.assertThat(objectType.baseType().asCustomTypeReferenceConfidently().targetType().canonicalName()).isSameAs(Object.class.getCanonicalName());
  }

  @Test
  public void testOf_whenList() {
    // Given
    Type<List<Integer>> listType = Types.of(List.class, Integer.class);

    // Then
    Assertions.assertThat(listType.baseClass()).isSameAs(List.class);

    Assertions.assertThat(listType.baseType().asCustomTypeReferenceConfidently().targetType().canonicalName()).isSameAs(List.class.getCanonicalName());
    assertThat(listType.qualifiers()).hasSize(1);
    Assertions.assertThat(listType.qualifiers().get(0).asCustomTypeReferenceConfidently().targetType().canonicalName()).isSameAs(Integer.class.getCanonicalName());
  }
}
