/**
 * Copyright (c) 2012 North Carolina State University
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.extensiblecatalog.ncip.v2.symphony.json;

import java.io.IOException;
import java.math.BigDecimal;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.extensiblecatalog.ncip.v2.service.Version1CurrencyCode;

/**
 * More accurately, a US Dollar amount.  Well, sort of; due to formatting rules generally this will be
 * reported in <em>cents</em>, but the only recognized name of the currency is "US Dollar".  Also includes
 * methods for basic math.
 */
public class Amount {

	private int dollars = 0;
	
	private int cents = 0;

	public Amount() {

	}
	
	public Amount(Amount amt) {
		this.dollars = amt.dollars;
		this.cents = amt.cents;
	}
	
	public Amount(String dollarAmount) {
		this(parseString(dollarAmount));
	}

    public Amount(int dollars, int cents) {
        this.dollars = dollars;
        this.cents = cents;
    }

    /**
     * Parses a string of the form "$##.##" into an Amount instance; the leading
     * dollar sign is optional.
     * @param dollarAmount
     * @return
     */
	public static Amount parseString(String dollarAmount) {
		String [] parts = dollarAmount.substring(1).split("\\.");
        if ( parts[0].startsWith("$") ) {
            parts[0] = parts[0].substring(1);
        }
		Amount amt = new Amount();
		amt.setDollars( Integer.parseInt(parts[0], 10));
		amt.setCents( Integer.parseInt(parts[1], 10));
		return amt;
	}


	/**
	 * @return the dollars
	 */
	public int getDollars() {
		return dollars;
	}


	/**
	 * @param dollars the dollars to set
	 */
	public void setDollars(int dollars) {
		this.dollars = dollars;
	}


	/**
	 * @return the cents
	 */
	public int getCents() {
		return cents;
	}


	/**
	 * @param cents the cents to set
	 */
	public void setCents(int cents) {
		this.cents = cents;
	}
	
	public static class Deserializer extends JsonDeserializer<Amount>{
		

		@Override
		public Amount deserialize(JsonParser parser, DeserializationContext ctx)
				throws IOException, JsonProcessingException {
			String value = parser.nextTextValue();
			return Amount.parseString(value);
		}
	}

    /**
     *
     * @return
     */
	public String toString() {
		return String.format("$%d.%02d", dollars,cents);
	}

    public int getCentTotal() {
        return this.dollars * 100 + this.cents;
    }

    public Amount add(Amount amt) {
        this.dollars += amt.dollars;
        this.cents += amt.cents;
        while ( this.cents >= 100 ) {
            this.dollars += 1;
            this.cents -= 100;
        }
        return this;
    }

    public Amount subtract(Amount amt) {
        this.dollars -= amt.dollars;
        this.cents -= amt.cents;
        while ( this.cents <= -100 ) {
            this.dollars -=1;
            this.cents += 100;
        }
        return this;
    }

    /**
     * Gets a representation of this object suitable for inclusion in an NCIP
     * response.
     * @return
     */
    public org.extensiblecatalog.ncip.v2.service.Amount toNCIPAmount() {
        org.extensiblecatalog.ncip.v2.service.Amount amt = new org.extensiblecatalog.ncip.v2.service.Amount();
        amt.setCurrencyCode(Version1CurrencyCode.USD);
        int valueInCents = 100 * dollars + cents;
        amt.setMonetaryValue( new BigDecimal(String.valueOf(valueInCents)) );
        return amt;
    }

	
}
