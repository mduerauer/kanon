package at.gv.fhstp.mis16.kanon.algorithm;

import javafx.scene.input.DataFormat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * This file is part of kanon.
 * <p>
 * kanon is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * kanon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 * <p>
 * Diese Datei ist Teil von kanon.
 * <p>
 * kanon ist Freie Software: Sie können es unter den Bedingungen
 * der GNU General Public License, wie von der Free Software Foundation,
 * Version 3 der Lizenz oder (nach Ihrer Wahl) jeder späteren
 * veröffentlichten Version, weiterverbreiten und/oder modifizieren.
 * <p>
 * kanon wird in der Hoffnung, dass es nützlich sein wird, aber
 * OHNE JEDE GEWÄHRLEISTUNG, bereitgestellt; sogar ohne die implizite
 * Gewährleistung der MARKTFÄHIGKEIT oder EIGNUNG FÜR EINEN BESTIMMTEN ZWECK.
 * Siehe die GNU General Public License für weitere Details.
 * <p>
 * Sie sollten eine Kopie der GNU General Public License zusammen mit diesem
 * Programm erhalten haben. Wenn nicht, siehe <http://www.gnu.org/licenses/>.
 * <p>
 * Created by n17405180 on 16.09.17.
 */
public enum DataClass {

    INDETERMINATE,
    NUMERIC,
    DATE,
    TEXT;

    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("d.m.Y");

    public static DataClass determine(String value) {

        try {
            Integer.parseInt(value);
            return NUMERIC;
        } catch(Exception e) {}

        try {
            DATE_FORMAT.parse(value);
            return DATE;
        } catch(Exception e) {}

        return TEXT;
    }


}
