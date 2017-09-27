package at.gv.fhstp.mis16.kanon;

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
public final class TestData {

    public static final String[][] TABLE1 = {
            {"01.01.2001", "1001", "m", "Catholic"},
            {"01.01.2001", "1002", "m", "Jew"},
            {"03.01.2001", "1003", "m", "Moslem"},
            {"04.01.2001", "1004", "w", "Catholic"},
            {"01.02.2001", "2001", "m", "Catholic"},
            {"02.02.2001", "2002", "w", "Jew"},
            {"03.02.2001", "2003", "m", "Moslem"},
            {"04.02.2001", "2004", "m", "Catholic"}
    };

    public static final String[][] TABLE2 = {
            {"01.02.2001", "01.01.2001", "1001", "m", "Catholic"},
            {"01.02.2001", "01.01.2001", "1001", "m", "Jew"},
            {"01.02.2001", "03.01.2001", "1003", "m", "Moslem"},
            {"01.02.2001", "04.01.2001", "1004", "w", "Catholic"},
            {"01.01.2001", "01.02.2001", "2001", "m", "Catholic"},
            {"01.01.2001", "02.02.2001", "2002", "w", "Jew"},
            {"01.01.2001", "03.02.2001", "2003", "m", "Moslem"},
            {"01.01.2001", "04.02.2001", "2004", "m", "Catholic"}
    };

}
