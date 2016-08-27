package com.craftingguide.util;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.regex.Pattern;

public class PatternSwitcher<T> {

    public PatternSwitcher() {
        this(null);
    }

    public PatternSwitcher(Consumer<T> defaultConsumer) {
        this._patterns = new ArrayList<PatternElement>();
        this.setDefaultConsumer(defaultConsumer);
    }

    // Public Methods //////////////////////////////////////////////////////////////////////////////////////////////////

    public void addPattern(String patternText) {
        this.addPattern(patternText, 0, null);
    }

    public void addPattern(String patternText, Consumer<T> consumer) {
        this.addPattern(patternText, 0, consumer);
    }

    public void addPattern(String patternText, int options, Consumer<T> consumer) {
        Pattern pattern = Pattern.compile(patternText, options);
        this._patterns.add(new PatternElement(pattern, consumer));
    }

    public void addAllPatterns(Iterable<String> patternTexts) {
        for (String patternText : patternTexts) {
            this.addPattern(patternText);
        }
    }

    public void match(String text, T item) {
        for (PatternElement element : this._patterns) {
            if (element.pattern.matcher(text).matches()) {
                Consumer<T> consumer = element.consumer != null ? element.consumer : this._defaultConsumer;
                consumer.accept(item);
            }
        }
    }

    // Property Access Methods /////////////////////////////////////////////////////////////////////////////////////////

    public void setDefaultConsumer(Consumer<T> defaultConsumer) {
        this._defaultConsumer = defaultConsumer != null ? defaultConsumer : (t) -> {};
    }

    // Private Helper Classes //////////////////////////////////////////////////////////////////////////////////////////

    private class PatternElement {

        public PatternElement(Pattern pattern, Consumer<T> consumer) {
            this.pattern = pattern;
            this.consumer = consumer;
        }

        public Pattern pattern = null;

        public Consumer<T> consumer = null;
    }

    // Private Properties //////////////////////////////////////////////////////////////////////////////////////////////

    private ArrayList<PatternElement> _patterns = null;

    private Consumer<T> _defaultConsumer = null;

}
