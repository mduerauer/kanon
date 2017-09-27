package at.gv.fhstp.mis16.kanon.algorithm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
@Component
public class KAnonAlgorithm {

    private static final Logger log = LoggerFactory.getLogger(KAnonAlgorithm.class);

    public static int PRIO_FEW_TEXT_VALS = 99;

    public static int PRIO_NUMERIC = 5;

    public static int PRIO_DATE = 1;

    public static int MAX_NUMERIC_RUNS = 10;

    public KAnonAlgorithm() {
    }

    public boolean executeOn(
            final String[][] table,
            int k
    ) {

        log.debug("executeOn() called.");

        List<DataClass> dataClasses = new ArrayList<>();
        String[][] result = table.clone();
        Map<Integer, Integer> runs = new HashMap<>();

        boolean proceed = true;
        boolean success = false;
        int loop = 0;

        determineDataClasses(result, dataClasses);

        while(proceed) {

            try {

                loop++;
                log.debug("--------------------------- << LOOP {} >> ---------------------------", loop);

                Map<String, Integer> hashes = new HashMap<>();
                List<Map<String, List<Integer>>> index = new ArrayList<>();

                indexData(result, index);
                calcHashes(result, hashes);

                int gMin = k;

                for (String hash : hashes.keySet()) {
                    int g = hashes.get(hash);
                    log.debug("Found hash {}, count: {}", new Object[]{hash, g});
                    if (g < gMin) {
                        gMin = g;
                        log.debug("gMin: {}", gMin);
                    }

                }

                if (gMin < k) {
                    log.debug("We have to reduce some quasi identifier.");
                    proceed = anonymOneCol(result, k, index, dataClasses, runs);
                } else {
                    proceed = false;
                    success = true;
                }

                log.info("Current table after {} loops: {}{}", loop, System.lineSeparator(), dumpTable(result));

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }

        if(success) {

            log.info("Found solution after {} loops: {}{}", loop, System.lineSeparator(), dumpTable(result));

        } else {
            log.warn("Did not find solution after {} loops. Last run's result:  {}{}", loop, System.lineSeparator(), dumpTable(result));
        }

        return success;
    }

    private void calcHashes(
            String[][] input,
            Map<String, Integer> hashes) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        for(int i = 0; i < input.length; i++) {
            String rowHash = calcRowHash(input[i], md);
            hashes.put(rowHash, hashes.containsKey(rowHash) ? hashes.get(rowHash) + 1 : 1);
        }
    }

    private static String hashAsString(final byte[] hash) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < hash.length; ++i) {
            sb.append(Integer.toHexString((hash[i] & 0xFF) | 0x100).substring(1,3));
        }
        return sb.toString();
    }

    private boolean anonymOneCol(
            String[][] table,
            int k,
            List<Map<String, List<Integer>>> index,
            List<DataClass> dataClasses,
            Map<Integer, Integer> runs
    ) {
        int i = 0;
        int bestCol = -1;
        int priority = 0;

        for(i = 0; i < index.size() - 1; i++) {
            Map<String, List<Integer>> colIndex = index.get(i);
            int occ = colIndex.size();

            log.debug("Analyzing col {} of type {} with {} occurrences", new Object[] { i, dataClasses.get(i), occ });

            // Reduce text columns with fewer occurrences than k
            if(occ < k && dataClasses.get(i) == DataClass.TEXT && priority < PRIO_FEW_TEXT_VALS && !runs.containsKey(i)) {
                priority = PRIO_FEW_TEXT_VALS;
                bestCol = i;
            } else if(dataClasses.get(i) == DataClass.NUMERIC && priority < PRIO_NUMERIC && (!runs.containsKey(i) || runs.get(i) < MAX_NUMERIC_RUNS)) {
                priority = PRIO_NUMERIC;
                bestCol = i;
            } else if(occ > 1 && dataClasses.get(i) == DataClass.DATE && priority < PRIO_DATE && (!runs.containsKey(i) || runs.get(i) < 3)) {
                priority = PRIO_DATE;
                bestCol = i;
            }
        }

        if(bestCol < 0) {
            return false;
        }

        int run = 0;
        if(runs.containsKey(bestCol)) {
            run = runs.get(bestCol);
        }

        if(dataClasses.get(bestCol) == DataClass.TEXT) {
            // reduce text columns by overwriting it with a star sign
            for(int r = 0; r < table.length; r ++) {
                table[r][bestCol] = "*";
            }

        } else if (dataClasses.get(bestCol) == DataClass.NUMERIC) {
            for(int r = 0; r < table.length; r ++) {

                String value = table[r][bestCol];
                int indexOfMask = value.indexOf("*");
                log.debug("Index of mask: {}", indexOfMask);
                if(indexOfMask < 0) {
                    value = value.substring(0, value.length() - 1) + "*";
                } else if(indexOfMask > 0) {
                    String oldValue = value;
                    value = oldValue.substring(0, indexOfMask - 1) + "*" + oldValue.substring(indexOfMask);
                } else if(indexOfMask == 0) {
                    run = MAX_NUMERIC_RUNS;
                }

                table[r][bestCol] = value;

            }
        } else if (dataClasses.get(bestCol) == DataClass.DATE) {
            // reduce data columns by cutting of day, months or years depending on the run value
            for(int r = 0; r < table.length; r ++) {
                String val = table[r][bestCol];
                log.debug("Current val: {} (row: {})", val, r);
                val = "*" + val.substring((run * 2) + 2);
                table[r][bestCol] = val;
            }
        }

        run++;
        runs.put(bestCol, run);

        return true;

    }

    private static String calcRowHash(final String[] row, final MessageDigest md) {
        StringBuffer rowValue = new StringBuffer();
        for(int i = 0; i < (row.length - 1); i++) {
            rowValue.append(row[i] + "|");
        }
        return hashAsString(md.digest(rowValue.toString().getBytes()));
    }

    private void determineDataClasses(final String[][] table, List<DataClass> dataClasses) {
        int cols = table[0].length;
        for(int i=0; i<cols; i++) {
            dataClasses.add(DataClass.determine(table[0][i]));
        }
    }

    private void indexData(
            String[][] table,
            List<Map<String, List<Integer>>> index
    ) {
        int r = 0, c = 0;
        int cols = table[0].length;
        for(int i=0; i<cols; i++) {
            index.add(new HashMap<>());
        }
        for(String[] row : table) {
            //log.debug("parsing row index {}", r);
            c = 0;
            for(String cell : row) {
                //log.debug("parsing cell index {} {}", r, c);

                String value = table[r][c];
                //log.debug("V: {} [{},{}]", new Object[] { value, r, c} );

                if(!index.get(c).containsKey(value)) {
                    index.get(c).put(value, new ArrayList<>());
                }

                index.get(c).get(value).add(r);
                c++;
            }

            r++;
        }
    }

    private String dumpTable(final String[][] table) {
        StringBuilder sb = new StringBuilder();
        for(int r = 0; r < table.length; r++) {

            for(int c = 0; c < table[r].length; c++) {
                sb.append(table[r][c]);
                sb.append(",");
            }

            sb.append(System.lineSeparator());
        }

        return sb.toString();
    }

    private void dumpIndexStats(
            List<Map<String, List<Integer>>> index,
            List<DataClass> dataClasses
    ) {
        int i = 0;
        for(Map<String, List<Integer>> colIndex : index) {
            for(String value : colIndex.keySet()) {
                int occ = colIndex.get(value).size();

                log.debug("col: {}, value: {}, occurences: {}, class: {} ",
                        new Object[] {i, value, occ, dataClasses.get(i).toString()});
            }

            i++;
        }
    }

}
