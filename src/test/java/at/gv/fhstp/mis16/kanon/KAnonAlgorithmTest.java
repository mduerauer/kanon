package at.gv.fhstp.mis16.kanon;

import at.gv.fhstp.mis16.kanon.algorithm.KAnonAlgorithm;
import at.gv.fhstp.mis16.kanon.config.ApplicationConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

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

public class KAnonAlgorithmTest {

    private KAnonAlgorithm algorithm;

    @Before
    public void init() {
        algorithm = new KAnonAlgorithm();
    }

    @Test
    public void shouldExecuteWithK4OnTable1() {
        assertNotNull(algorithm);
        boolean result = algorithm.executeOn(TestData.TABLE1, 4);
        assertTrue(result);
    }

    @Test
    public void shouldExecuteWithK4OnTable2() {
        assertNotNull(algorithm);
        boolean result = algorithm.executeOn(TestData.TABLE2, 4);
        assertTrue(result);
    }

    @Test
    public void shouldFailWithK10OnTable2() {
        assertNotNull(algorithm);
        boolean result = algorithm.executeOn(TestData.TABLE2, 10);
        assertFalse(result);
    }

}
