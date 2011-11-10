package com.wicketized.extension.util;

import org.apache.wicket.ajax.AjaxRequestTarget;

public class AjaxEventPayload<T> {

  private AjaxRequestTarget target;
  private AjaxEventType eventType;
  private Class<T> valueType;
  private T value;
  private boolean updating = false;
  private boolean valid = true;

  public AjaxEventPayload (AjaxEventType type, AjaxRequestTarget target) {

    this(type, target, null, null);
  }

  public AjaxEventPayload (AjaxEventType eventType, AjaxRequestTarget target, Class<T> valueType, T value) {

    this.eventType = eventType;
    this.target = target;
    this.valueType = valueType;
    this.value = value;
  }

  public AjaxEventType getEventType () {

    return eventType;
  }

  public AjaxRequestTarget getTarget () {

    return target;
  }

  public Class<T> getValueType () {

    return valueType;
  }

  public T getValue () {

    return value;
  }

  public boolean isUpdating () {

    return updating;
  }

  public AjaxEventPayload<T> setUpdating (boolean updating) {

    this.updating = updating;

    return this;
  }

  public boolean isValid () {

    return valid;
  }

  public AjaxEventPayload<T> setValid (boolean valid) {

    this.valid = valid;

    return this;
  }
}
