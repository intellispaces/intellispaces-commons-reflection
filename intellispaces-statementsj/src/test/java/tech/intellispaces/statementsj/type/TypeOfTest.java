package tech.intellispaces.statementsj.type;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link TypeOf}.
 */
public class TypeOfTest {

  @Test
  public void test_whenString() {
    // When
    Type<String> type = new TypeOf<String>() {};

    // Then
    Assertions.assertThat(type.baseTypeReference().asCustomTypeReferenceOrElseThrow().targetType().canonicalName()).isEqualTo(String.class.getCanonicalName());
    Assertions.assertThat(type.qualifierTypeReferences()).isEmpty();
  }

  @Test
  public void test_whenStringList() {
    // When
    Type<List<String>> type = new TypeOf<List<String>>() {};

    // Then
    Assertions.assertThat(type.baseTypeReference().asCustomTypeReferenceOrElseThrow().targetType().canonicalName()).isEqualTo(List.class.getCanonicalName());
    Assertions.assertThat(type.qualifierTypeReferences()).hasSize(1);
    Assertions.assertThat(type.qualifierTypeReferences().get(0).asCustomTypeReferenceOrElseThrow().targetType().canonicalName()).isEqualTo(String.class.getCanonicalName());
  }
}
