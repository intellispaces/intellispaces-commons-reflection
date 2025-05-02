package tech.intellispaces.statementsj.reference;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link PrimitiveReferences} class.
 */
public class PrimitiveReferencesTest {

  @Test
  public void testGet() {
    assertThat(PrimitiveReferences.get("boolean")).isSameAs(PrimitiveReferences.Boolean);
    assertThat(PrimitiveReferences.get("char")).isSameAs(PrimitiveReferences.Char);
    assertThat(PrimitiveReferences.get("byte")).isSameAs(PrimitiveReferences.Byte);
    assertThat(PrimitiveReferences.get("short")).isSameAs(PrimitiveReferences.Short);
    assertThat(PrimitiveReferences.get("long")).isSameAs(PrimitiveReferences.Long);
    assertThat(PrimitiveReferences.get("int")).isSameAs(PrimitiveReferences.Int);
    assertThat(PrimitiveReferences.get("float")).isSameAs(PrimitiveReferences.Float);
    assertThat(PrimitiveReferences.get("double")).isSameAs(PrimitiveReferences.Double);
  }
}
