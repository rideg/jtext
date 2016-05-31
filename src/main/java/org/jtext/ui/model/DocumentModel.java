package org.jtext.ui.model;

import org.jtext.event.Observable;
import org.jtext.ui.event.ModelChangedEvent;

public interface DocumentModel extends Observable<ModelChangedEvent> {

    int length();

    String getChars();

    String getChars(int from, int to);

    void deleteCharAt(int position);

    void deleteRegion(int from, int to);

    void insertCharAt(int position, char value);

    void insertStringAt(int position, String string);

}
