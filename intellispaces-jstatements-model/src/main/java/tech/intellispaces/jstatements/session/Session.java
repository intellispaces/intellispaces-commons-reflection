package tech.intellispaces.jstatements.session;

import tech.intellispaces.jstatements.customtype.CustomType;

/**
 * Parsing session.
 */
public interface Session {

  CustomType getType(String typeName);

  void putType(String typeName, CustomType customType);

  void clear();
}
