package tech.intellispaces.commons.java.reflection.session;

import tech.intellispaces.commons.java.reflection.customtype.CustomType;

/**
 * Parsing session.
 */
public interface Session {

  CustomType getType(String typeName);

  void putType(String typeName, CustomType customType);

  void clear();
}
