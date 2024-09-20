package intellispaces.common.javastatement.type;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * Tests for {@link TypeBuilder}.
 */
public class TypeBuilderTest {

  @Test
  public void test_whenString() {
    // When
    Type<String> type = new TypeBuilder<String>() {}.get();

    // Then
    Assertions.assertThat(type.baseTypeReference().asCustomTypeReferenceOrElseThrow().targetType().canonicalName()).isEqualTo(String.class.getCanonicalName());
    Assertions.assertThat(type.qualifierTypeReferences()).isEmpty();
  }

  @Test
  public void test_whenStringList() {
    // When
    Type<List<String>> type = new TypeBuilder<List<String>>() {}.get();

    // Then
    Assertions.assertThat(type.baseTypeReference().asCustomTypeReferenceOrElseThrow().targetType().canonicalName()).isEqualTo(List.class.getCanonicalName());
    Assertions.assertThat(type.qualifierTypeReferences()).hasSize(1);
    Assertions.assertThat(type.qualifierTypeReferences().get(0).asCustomTypeReferenceOrElseThrow().targetType().canonicalName()).isEqualTo(String.class.getCanonicalName());
  }
}
