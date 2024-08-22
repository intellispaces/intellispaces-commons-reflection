package intellispaces.javastatements.type;

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
    assertThat(stringType.baseClass()).isSameAs(String.class);
    assertThat(integerType.baseClass()).isSameAs(Integer.class);
    assertThat(objectType.baseClass()).isSameAs(Object.class);

    assertThat(stringType.base().asCustomTypeReferenceOrElseThrow().targetType().canonicalName()).isSameAs(String.class.getCanonicalName());
    assertThat(integerType.base().asCustomTypeReferenceOrElseThrow().targetType().canonicalName()).isSameAs(Integer.class.getCanonicalName());
    assertThat(objectType.base().asCustomTypeReferenceOrElseThrow().targetType().canonicalName()).isSameAs(Object.class.getCanonicalName());
  }

  @Test
  public void testOf_whenList() {
    // Given
    Type<List<Integer>> listType = Types.of(List.class, Integer.class);

    // Then
    assertThat(listType.baseClass()).isSameAs(List.class);

    assertThat(listType.base().asCustomTypeReferenceOrElseThrow().targetType().canonicalName()).isSameAs(List.class.getCanonicalName());
    assertThat(listType.qualifiers()).hasSize(1);
    assertThat(listType.qualifiers().get(0).asCustomTypeReferenceOrElseThrow().targetType().canonicalName()).isSameAs(Integer.class.getCanonicalName());
  }
}
