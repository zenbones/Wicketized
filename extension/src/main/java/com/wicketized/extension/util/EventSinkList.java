package com.wicketized.extension.util;

import java.io.Serializable;
import java.util.LinkedList;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.event.IEventSink;
import org.apache.wicket.event.IEventSource;

public class EventSinkList<P> implements Serializable {

  private LinkedList<IEventSink> sinkList = new LinkedList<IEventSink>();

  public synchronized void addEventSink (IEventSink sink) {

    sinkList.add(sink);
  }

  public synchronized void removeEventSink (IEventSink sink) {

    sinkList.remove(sink);
  }

  public synchronized void send (IEventSource source, P payload) {

    for (IEventSink sink : sinkList) {
      source.send(sink, Broadcast.EXACT, payload);
    }
  }
}
