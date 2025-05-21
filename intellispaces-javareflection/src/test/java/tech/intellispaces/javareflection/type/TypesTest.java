package tech.intellispaces.javareflection.type;

import java.util.List;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link Types}
 */
public class TypesTest {

  @Test
  public void testGet_whenBaseTypeOnly() {
    // Given
    Type<String> stringType = Types.get(String.class);
    Type<Integer> integerType = Types.get(Integer.class);
    Type<Integer> objectType = Types.get(Object.class);

    // Then
    assertThat(stringType.asClassType().baseClass()).isSameAs(String.class);
    assertThat(integerType.asClassType().baseClass()).isSameAs(Integer.class);
    assertThat(objectType.asClassType().baseClass()).isSameAs(Object.class);

    assertThat(stringType.baseTypeReference().asCustomTypeReferenceOrElseThrow().targetType().canonicalName()).isSameAs(String.class.getCanonicalName());
    assertThat(integerType.baseTypeReference().asCustomTypeReferenceOrElseThrow().targetType().canonicalName()).isSameAs(Integer.class.getCanonicalName());
    assertThat(objectType.baseTypeReference().asCustomTypeReferenceOrElseThrow().targetType().canonicalName()).isSameAs(Object.class.getCanonicalName());
  }

  @Test
  public void testGet_whenList() {
    // Given
    Type<List<Integer>> listType = Types.get(List.class, Integer.class);

    // Then
    assertThat(listType.asClassType().baseClass()).isSameAs(List.class);

    assertThat(listType.baseTypeReference().asCustomTypeReferenceOrElseThrow().targetType().canonicalName()).isSameAs(List.class.getCanonicalName());
    assertThat(listType.qualifierTypeReferences()).hasSize(1);
    assertThat(listType.qualifierTypeReferences().get(0).asCustomTypeReferenceOrElseThrow().targetType().canonicalName()).isSameAs(Integer.class.getCanonicalName());
  }
}
