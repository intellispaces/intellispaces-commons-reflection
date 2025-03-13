package tech.intellispaces.commons.reflection.session;

import tech.intellispaces.commons.reflection.customtype.CustomType;

/**
 * Parsing session.
 */
public interface Session {

  CustomType getType(String typeName);

  void putType(String typeName, CustomType customType);

  void clear();
}
