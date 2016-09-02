package com.craftingguide.util;

import java.io.IOException;
import java.io.Writer;

public class Printer {

    public Printer(Writer writer) {
        this._writer = writer;
    }

    // Public Methods //////////////////////////////////////////////////////////////////////////////////////////////////

    public void indent() {
        this._indentation++;
    }

    public void line() throws IOException {
        this.line("");
    }

    public void line(String text) throws IOException {
        this.resolveIndent();
        this._writer.write(text);
        this._writer.write('\n');
        this._onNewLine = true;
    }

    public void text(String text) throws IOException {
        this.resolveIndent();
        this._writer.write(text);
    }

    public void outdent() {
        this._indentation = Math.max(0, this._indentation - 1);
    }

    // Private Methods /////////////////////////////////////////////////////////////////////////////////////////////////

    private void resolveIndent() throws IOException {
        if (!this._onNewLine) return;
        this._onNewLine = false;

        for (int i = 0; i < this._indentation; i++) {
            this._writer.write("    ");
        }
    }

    // Private Properties //////////////////////////////////////////////////////////////////////////////////////////////

    private int _indentation = 0;

    private boolean _onNewLine = true;

    private Writer _writer = null;
}
