package com.shopin.core.framework.exception;

public class ShopinException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8710396445793589764L;
	public ShopinException() {

    }

    public ShopinException(String message) {
        super(message);
    }

    public ShopinException(String message, Throwable cause) {
        super(message, cause);
    }

    public ShopinException(Throwable cause) {
        super(cause);
    }
}
