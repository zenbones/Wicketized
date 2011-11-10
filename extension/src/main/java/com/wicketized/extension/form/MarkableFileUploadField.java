package com.wicketized.extension.form;

import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.model.util.ListModel;

public class MarkableFileUploadField extends FileUploadField {

  private boolean marked = false;

  public MarkableFileUploadField (String id) {

    super(id);
  }

  public MarkableFileUploadField (String id, ListModel<FileUpload> model) {

    super(id, model);
  }

  public boolean isMarked () {

    return marked;
  }

  public void setMarked (boolean marked) {

    this.marked = marked;
  }

  @Override
  protected void onBeforeRender () {

    marked = false;

    super.onBeforeRender();
  }
}
