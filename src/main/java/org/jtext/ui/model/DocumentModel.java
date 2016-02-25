package org.jtext.ui.model;

public interface DocumentModel {

    int length();

    String getChars();

    String getChars(int from, int to);

    void deleteCharAt(int position);

    void deleteRegion(int from, int to);

    void insertCharAt(int position, char value);

    void insertStringAt(int position, String string);


}
