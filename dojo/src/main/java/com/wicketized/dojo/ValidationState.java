package com.wicketized.dojo;

public enum ValidationState {

  NORMAL(""), INCOMPLETE("Incomplete"), ERROR("Error");

  private String code;

  private ValidationState (String code) {

    this.code = code;
  }

  public String getCode () {

    return code;
  }

  public static ValidationState fromCode (String code) {

    for (ValidationState validationState : values()) {
      if (validationState.getCode().equals(code)) {

        return validationState;
      }
    }

    return null;
  }
}
