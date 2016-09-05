package com.craftingguide.util;

import java.io.IOException;
import java.io.Writer;

public class Printer {

    public Printer(Writer writer) {
        this.writer = writer;
    }

    // Public Methods //////////////////////////////////////////////////////////////////////////////////////////////////

    public void indent() {
        this.indentation++;
    }

    public void println() throws IOException {
        this.println("");
    }

    public void println(String text) throws IOException {
        text = (text == null) ? "" : text;
        if (text.length() > 0) {
            this.resolveIndent();
        }
        this.writer.write(text);
        this.writer.write('\n');
        this.onNewLine = true;
    }

    public void print(String text) throws IOException {
        this.resolveIndent();
        this.writer.write(text);
    }

    public void outdent() {
        this.indentation = Math.max(0, this.indentation - 1);
    }

    // Private Methods /////////////////////////////////////////////////////////////////////////////////////////////////

    private void resolveIndent() throws IOException {
        if (!this.onNewLine) return;
        this.onNewLine = false;

        for (int i = 0; i < this.indentation; i++) {
            this.writer.write("    ");
        }
    }

    // Private Properties //////////////////////////////////////////////////////////////////////////////////////////////

    private int indentation = 0;

    private boolean onNewLine = true;

    private Writer writer = null;
}
