package com.wicketized.dojo;

import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class TimeConversionModel extends AbstractReadOnlyModel<String> {

  private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat.forPattern("'T'HH:mm:ss");

  private IModel<LocalTime> timeModel;

  public static LocalTime parseMilliseconds (String asMilliseconds) {

    return new LocalTime(Long.parseLong(asMilliseconds));
  }

  public static LocalTime parseTime (String asTime) {

    return LocalTime.parse(asTime, DATE_TIME_FORMATTER);
  }

  public static String print (LocalTime localTime) {

    return DATE_TIME_FORMATTER.print(localTime);
  }

  public TimeConversionModel (IModel<LocalTime> timeModel) {

    this.timeModel = timeModel;
  }

  public synchronized void setTimeModel (IModel<LocalTime> timeModel) {

    this.timeModel = timeModel;
  }

  @Override
  public synchronized String getObject () {

    LocalTime localTime;

    return ((localTime = timeModel.getObject()) == null) ? null : DATE_TIME_FORMATTER.print(localTime);
  }
}
