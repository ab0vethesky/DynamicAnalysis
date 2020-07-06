public String attributeCaseFix(String columnName) {
    if (m_checkForUpperCaseNames) {
      String ucname = columnName.toUpperCase();
      if (ucname.equals(EXP_TYPE_COL.toUpperCase())) {
        return EXP_TYPE_COL;
      } else if (ucname.equals(EXP_SETUP_COL.toUpperCase())) {
        return EXP_SETUP_COL;
      } else if (ucname.equals(EXP_RESULT_COL.toUpperCase())) {
        return EXP_RESULT_COL;
      } else {
        return columnName;
      }
    } else if (m_checkForLowerCaseNames) {
      String ucname = columnName.toLowerCase();
      if (ucname.equals(EXP_TYPE_COL.toLowerCase())) {
        return EXP_TYPE_COL;
      } else if (ucname.equals(EXP_SETUP_COL.toLowerCase())) {
        return EXP_SETUP_COL;
      } else if (ucname.equals(EXP_RESULT_COL.toLowerCase())) {
        return EXP_RESULT_COL;
      } else {
        return columnName;
      }
    } else {
      return columnName;
    }
  }