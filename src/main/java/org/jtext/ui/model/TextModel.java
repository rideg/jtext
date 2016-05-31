package org.jtext.ui.model;

import org.jtext.event.Notifier;
import org.jtext.ui.event.ModelChangedEvent;

import java.util.function.Consumer;

public class TextModel implements DocumentModel {

    private final StringBuilder text;
    private final Notifier<ModelChangedEvent> notifier = new Notifier<>();

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
        notifier.notifyObservers();
    }

    @Override
    public void deleteRegion(final int from, final int to) {
        text.delete(from, to);
        notifier.notifyObservers();
    }

    @Override
    public void insertCharAt(final int position, final char value) {
        text.insert(position, value);
        notifier.notifyObservers();
    }

    @Override
    public void insertStringAt(final int position, final String string) {
        text.insert(position, string);
        notifier.notifyObservers();
    }

    public void setText(final String text) {
        this.text.setLength(0);
        this.text.append(text);
    }

    @Override
    public void subscribe(final Consumer<ModelChangedEvent> observer) {
        notifier.subscribe(observer);
    }
}
