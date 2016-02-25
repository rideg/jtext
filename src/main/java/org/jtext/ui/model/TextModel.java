package org.jtext.ui.model;

public class TextModel extends ModelBase implements DocumentModel {

    private final StringBuilder text;

    public TextModel(final String text) {
        this.text = new StringBuilder(text);
    }

    public TextModel() {
        text = new StringBuilder();
    }

    @Override
    public int length() {
        return text.length();
    }

    @Override
    public String getChars() {
        return text.toString();
    }

    @Override
    public String getChars(final int from, final int to) {
        return text.substring(from, to);
    }

    @Override
    public void deleteCharAt(final int position) {
        text.deleteCharAt(position);
        notifyListeners();
    }

    @Override
    public void deleteRegion(final int from, final int to) {
        text.delete(from, to);
        notifyListeners();
    }

    @Override
    public void insertCharAt(final int position, final char value) {
        text.insert(position, value);
        notifyListeners();
    }

    @Override
    public void insertStringAt(final int position, final String string) {
        text.insert(position, string);
        notifyListeners();
    }

}
