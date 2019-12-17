package com.akgs.problems.google.typeconversion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        // write your code here
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Number of rates in conversion table:");
        try {
            String line = br.readLine();
            int n = Integer.parseInt(line);
            System.out.println("Enter conversion rates:");
            HashMap<String, HashMap<String, Double>> oMap = new HashMap<>();
            HashSet<String> allTypes = new HashSet();
            //Reading line format: frm,to,val
            for (int i = 0; i < n; i++) {
                line = br.readLine();
                String[] words = line.split(",");
                if (oMap.get(words[0]) == null)
                    oMap.put(words[0], new HashMap<>());
                oMap.get(words[0]).put(words[1], Double.parseDouble(words[2]));
                allTypes.add(words[0]);
                allTypes.add(words[1]);
            }

            System.out.print("Convert from :");
            String frmType = br.readLine();
            System.out.print("Convert to :");
            String toType = br.readLine();
            System.out.print("Value :");
            line = br.readLine();
            Double iVal = Double.parseDouble(line);

            double adjMat[][] = new double[allTypes.size()][allTypes.size()];
            for (int i = 0; i < allTypes.size(); i++)
                Arrays.fill(adjMat[i], new Double(-1));
            HashMap<String, Integer> strToIdx = new HashMap<>();
            HashMap<Integer, String> idxToStr = new HashMap<>();
            int count = 0;
            for (Map.Entry entry : oMap.entrySet()) {
                String frm = (String) entry.getKey();
                if (strToIdx.get(frm) == null) {
                    strToIdx.put(frm, count++);
                    idxToStr.put(count - 1, frm);
                }
                for (Map.Entry entry1 : ((HashMap<String, Integer>) entry.getValue()).entrySet()) {
                    String to = (String) entry1.getKey();
                    if (strToIdx.get(to) == null) {
                        strToIdx.put(to, count++);
                        idxToStr.put(count - 1, to);
                    }
                    adjMat[strToIdx.get(frm)][strToIdx.get(to)] = (double) entry1.getValue();
                    adjMat[strToIdx.get(to)][strToIdx.get(frm)] =  1D / (double) entry1.getValue();
                }
            }
            //printAdjMatrix(strToIdx, idxToStr, adjMat);
            //traverse
            for(Map.Entry entry : oMap.entrySet()) {
                String frm = (String)entry.getKey();
                LinkedList<String> path = new LinkedList<>();
                path.add(frm);
                traverse(path, adjMat, strToIdx, idxToStr);
            }

            //printAdjMatrix(strToIdx, idxToStr, adjMat);
            System.out.println("Converted value: "+ adjMat[strToIdx.get(frmType)][strToIdx.get(toType)]*iVal);
            //printAdjMatrix(strToIdx, idxToStr, adjMat);
        } catch (IOException e) {
        }
    }

    private static void traverse(LinkedList<String> path, double[][] adjMat, HashMap<String, Integer> strToIdx, HashMap<Integer, String> idxToStr) {
        String curr = path.getFirst();
        Integer currIdx = strToIdx.get(curr);
        for (int i = 0; i<adjMat[currIdx].length; i++) {
            if ( i != currIdx && adjMat[currIdx][i] != -1) {
                String nxt = idxToStr.get(i);
                if ( !path.contains(nxt) ) {
                    for (int j=path.size() - 1; path.size() > 1 && j > 0; j--) {
                        String node = path.get(j);
                        int nodeIdx = strToIdx.get(node);
                        adjMat[nodeIdx][i] = adjMat[nodeIdx][currIdx] * adjMat[currIdx][i];
                        adjMat[i][nodeIdx] = 1 / adjMat[nodeIdx][i];
                    }
                    LinkedList<String> clonedPath = (LinkedList<String>) path.clone();
                    clonedPath.push(nxt);
                    traverse(clonedPath, adjMat, strToIdx, idxToStr);
                }
            }
        }
    }

    private static void printAdjMatrix(HashMap<String, Integer> strToIdx, HashMap<Integer, String> idxToStr, double[][] adjMat) {
        System.out.print(" ");
        for (int i=0;i<idxToStr.size();i++) {
            System.out.print("   " + idxToStr.get(i));
        }
        System.out.println();
        for (int i=0;i<idxToStr.size();i++) {
            System.out.print(idxToStr.get(i));
            for (int j = 0; j < adjMat[i].length; j++) {
                System.out.print(" " + adjMat[i][j]);
            }
            System.out.println();
        }
    }
}
