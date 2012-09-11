package com.wicketized.dojo;

public abstract class DojoAccordionContainer extends DojoStackContainer<DojoAccordionContainer> {

  public DojoAccordionContainer (String id) {

    super(id);
  }

  @Override
  public String getDojoType () {

    return "dijit.layout.AccordionContainer";
  }

  @Override
  public Class<DojoAccordionContainer> getDojoComponentClass () {

    return DojoAccordionContainer.class;
  }
}
