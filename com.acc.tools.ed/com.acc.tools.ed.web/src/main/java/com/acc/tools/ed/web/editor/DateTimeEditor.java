package com.acc.tools.ed.web.editor;

import java.beans.PropertyEditorSupport;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.util.StringUtils;

public class DateTimeEditor extends  PropertyEditorSupport {

	private final DateTimeFormatter formatter;
    private final boolean allowEmpty;
    private final int temp=0;

    /**
     * Create a new DateTimeEditor instance, using the given format for
     * parsing and rendering.
     * <p/>
     * The "allowEmpty" parameter states if an empty String should be allowed
     * for parsing, i.e. get interpreted as null value. Otherwise, an
     * IllegalArgumentException gets thrown.
     *
     * @param dateFormat DateFormat to use for parsing and rendering
     * @param allowEmpty if empty strings should be allowed
     */
    public DateTimeEditor( String dateFormat, boolean allowEmpty ) {
        this.formatter = DateTimeFormat.forPattern( dateFormat );
        this.allowEmpty = allowEmpty;
    }


    /**
     * Format the YearMonthDay as String, using the specified format.
     *
     * @return DateTime formatted string
     */
    public String getAsText() {
        if (getValue() == null) return "";
        DateTime value = (DateTime) getValue();
        return value != null ?new DateTime( value ).toString( formatter ) : "";
    }


    /**
     * Parse the value from the given text, using the specified format.
     *
     * @param text
     * @throws IllegalArgumentException
     */
    public void setAsText( String text ) throws IllegalArgumentException {
        if (allowEmpty && StringUtils.isEmpty(text)) {
            setValue(null);
    	} else {
    		setValue( new DateTime( formatter.parseDateTime( text ) ));
    	}
    }
}
