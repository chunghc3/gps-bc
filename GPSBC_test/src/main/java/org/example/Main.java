package org.example;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.scoring.BetweennessCentrality;
import org.jgrapht.graph.*;

import java.util.*;

import static org.example.A.ConstraintA1;
import static org.example.A.ConstraintA2;
import static org.example.AB.ConstraintA1B2;
import static org.example.AC.ConstraintA1C1;
import static org.example.AC.ConstraintA1C2;
import static org.example.B.ConstraintB1;
import static org.example.B.ConstraintB2;
import static org.example.BC.*;
import static org.example.C.ConstraintC1;
import static org.example.C.ConstraintC2;

public class Main {
    public static void main(String[] args) {
        ConstraintA1C2();
    }
}
