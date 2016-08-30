package com.craftingguide;

public class CraftingGuideException extends Exception {

    public CraftingGuideException() {
        super();
    }

    public CraftingGuideException(String message) {
        super(message);
    }

    public CraftingGuideException(String message, Throwable cause) {
        super(message, cause);
    }

    // Private Class Properties ////////////////////////////////////////////////////////////////////////////////////////

    private static final long serialVersionUID = -4776659476356609918L;

}
