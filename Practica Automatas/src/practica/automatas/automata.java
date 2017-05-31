/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package practica.automatas;

import java.awt.Component;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

/**
 * Clase que crea una MatrizEnForma1
 *
 * @author Felipe López
 * @author Julián Trujillo
 * @version 1.0.0
 * @since Practica Automatas 1.0.0
 */
public class automata {

    /**
     * Variable global de tipo Vector que se utiliza para guardar los símbolos
     *
     * @since Practica Automatas 1.0.0
     */
    private Vector simbolos = new Vector();
    /**
     * Variable global de tipo Vector que se utiliza para guardar los estados
     *
     * @since Practica Automatas 1.0.0
     */
    private Vector estados = new Vector();
    /**
     * Variable global de tipo String que se utiliza para guardar el nombre del
     * autómata
     *
     * @since Practica Automatas 1.0.0
     */
    private String nombre;
    /**
     * Variable global de tipo Vector que se utiliza para guardar una matriz de
     * transiciones
     *
     * @since Practica Automatas 1.0.0
     */
    private Vector transiciones[][];
    /**
     * Variable global de tipo automata que se utiliza almacenar el autómata
     * reducido
     *
     * @since Practica Automatas 1.0.0
     */
    private automata autMin;
    /**
     * Variable global de tipo Component para generar el formulario
     *
     * @since Practica Automatas 1.0.0
     */
    private Component rootPane;

    /**
     * Constructor de la clase
     *
     * @param nom String que representa el nombre del autómata
     * @param sim vector que representa los símbolos
     * @param est vector que representa los estados
     * @param trans vector bidimiensional que representa las transiciones
     * @since Practica Automatas 1.0.0
     */
    public automata(String nom, Vector sim, Vector est, Vector trans[][]) {
        nombre = nom;
        simbolos = sim;
        estados = est;
        transiciones = trans;
    }

    /**
     * Método que retorna el nombre
     *
     * @return el nombre
     * @since Practica Automatas 1.0.0
     */
    String getNombre() {
        return (nombre);
    }

    /**
     * Método que retorna los símbolos
     *
     * @return los simbolos
     * @since Practica Automatas 1.0.0
     */
    public Vector getSimbolos() {
        return simbolos;
    }

    /**
     * Método que asigna el vector de símbolos
     *
     * @param simbolos nodo anterior
     * @since Practica Automatas 1.0.0
     */
    public void setSimbolos(Vector simbolos) {
        this.simbolos = simbolos;
    }

    /**
     * Método que retorna los estados
     *
     * @return los estados
     * @since Practica Automatas 1.0.0
     */
    public Vector getEstados() {
        return estados;
    }

    /**
     * Método que asigna los estados
     *
     * @param estados los estados a asignar
     * @since Practica Automatas 1.0.0
     */
    public void setEstados(Vector estados) {
        this.estados = estados;
    }

    /**
     * Método que asigna el nombre
     *
     * @param nombre el nombre a asignar
     * @since Practica Automatas 1.0.0
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Método que retorna las transiciones
     *
     * @return las transiciones
     * @since Practica Automatas 1.0.0
     *
     */
    public Vector[][] getTransiciones() {
        return transiciones;
    }

    /**
     * Método que asígna las transiciones
     *
     * @param transiciones a asignar
     * @since Practica Automatas 1.0.0
     */
    public void setTransiciones(Vector[][] transiciones) {
        this.transiciones = transiciones;
    }

    /**
     * Método que retorna el autómata minimo
     *
     * @return el autMin
     * @since Practica Automatas 1.0.0
     */
    public automata getAutMin() {
        return autMin;
    }

    /**
     * Método que asigna el autómata mínimo
     *
     * @param autMin el autMin a asígnar
     * @since Practica Automatas 1.0.0
     */
    public void setAutMin(automata autMin) {
        this.autMin = autMin;
    }

    /**
     * Método que verifica si una hilera puede o no ser aceptada por un autómata
     *
     * @return String con el paso a paso de la verificación
     * @param simbolosIngresados los símbolos a evaluar
     * @param ab autómata en el que se evaluará la hilera
     * @since Practica Automatas 1.0.0
     */
    public String evaluarHilera(String simbolosIngresados, automata ab) {
        Vector simbolos = ab.getSimbolos();
        String aux = "";
        Vector estados = ab.getEstados();
        estado estActual = null;
        Iterator imprimEstados = estados.iterator();
        int contEstados = 0;
        while (imprimEstados.hasNext() && contEstados < estados.size()) {
            estado estadoAux = (estado) imprimEstados.next();
            contEstados++;
            if (estadoAux.isInicial()) {
                aux = aux + "*" + estadoAux.getIcono() + ": " + estadoAux.getDescripcion() + "\n";
            } else {
                if (estadoAux.isAceptacion()) {
                    aux = aux + estadoAux.getIcono() + ": " + estadoAux.getDescripcion() + " (Aceptación)\n";
                } else {
                    if (contEstados == estados.size()) {
                        aux = aux + estadoAux.getIcono() + ": nulo\n";
                    } else {
                        aux = aux + estadoAux.getIcono() + ": " + estadoAux.getDescripcion() + "\n";
                    }

                }
            }

        }
        Iterator estadosIterator = estados.iterator();
        while (estadosIterator.hasNext()) {
            estado estadoAux = (estado) estadosIterator.next();
            if (estadoAux.isInicial()) {
                estActual = estadoAux;
                aux = aux + "\nNos situamos en el estado inicial que es " + estActual.getIcono() + "\n";
                break;
            }
        }

        Vector[][] transicionesVector = ab.getTransiciones();
        boolean rta = estActual.isAceptacion();
        int simbolPosicion = -1;
        for (int ii = 0; ii < simbolosIngresados.length(); ii++) {
            int cont = 0;
            char simboloIngresado = simbolosIngresados.charAt(ii);
            if (ii == 0) {
                aux = aux + "\nEl primer símbolo de la hilera ingresada es " + simboloIngresado + "\n";
            } else {
                aux = aux + "\nEl siguiente símbolo de la hilera es " + simboloIngresado + "\n";
            }
            Iterator simbolosIterator = simbolos.iterator();
            while (simbolosIterator.hasNext()) {
                String dd = (String) simbolosIterator.next();
                char[] d = dd.toCharArray();
                if (d[0] == simboloIngresado) {
                    simbolPosicion = cont;
                    break;
                }
                cont++;
            }
            if (simbolPosicion != -1) {

                String estadoActual = estActual.getIcono();
                aux = aux + "El estado actual es " + estActual.getIcono() + "\n";
                int numEstActual = retornaNum(estadoActual);
                String transicionHacia = (String) transicionesVector[numEstActual][simbolPosicion + 1].get(0);
                aux = aux + "La transicion del estado " + estadoActual + " es hacia " + transicionHacia + " con el símbolo " + simboloIngresado + "\n";
                numEstActual = encuentra(transicionHacia, estados);
                estActual = (estado) estados.get(numEstActual);
                rta = estActual.isAceptacion();
            } else {
                aux = aux + "Y como no es el estado de aceptación, la hilera se rechaza.";
                JOptionPane.showMessageDialog(rootPane, "Rechaza");
            }
        }
        if (rta) {
            aux = aux + "Ahora nos situamos en el estado " + estActual.getIcono() + " y como es el estado de aceptación, la hilera se acepta.";
            JOptionPane.showMessageDialog(rootPane, "Acepta");
        } else {
            aux = aux + "Y como no es el estado de aceptación, la hilera se rechaza.";
            JOptionPane.showMessageDialog(rootPane, "Rechaza");
        }
        return aux;

    }

    /**
     * Método que genera 5 hileras aceptada por un autómata
     *
     * @return String con el paso a paso de la verificación y las hileras
     * @param ab autómata del que se generará la hilera
     * @since Practica Automatas 1.0.0
     */
    public String genHilera(automata ab) {
        String hilera;
        String grupoHileras = "";
        Vector simbolos = ab.getSimbolos();
        Vector estados = ab.getEstados();
        estado estActual;
        String transicionHacia = "";
        String estadoActual = "";
        boolean continuar;

        Iterator imprimEstados = estados.iterator();
        int contEstados = 0;
        while (imprimEstados.hasNext() && contEstados < estados.size()) {
            estado estadoAux = (estado) imprimEstados.next();
            contEstados++;
            if (estadoAux.isInicial()) {
                grupoHileras = grupoHileras + "*" + estadoAux.getIcono() + ": " + estadoAux.getDescripcion() + "\n";
            } else {
                if (estadoAux.isAceptacion()) {
                    grupoHileras = grupoHileras + estadoAux.getIcono() + ": " + estadoAux.getDescripcion() + " (Aceptación)\n";
                } else {
                    if (contEstados == estados.size()) {
                        grupoHileras = grupoHileras + estadoAux.getIcono() + ": nulo\n";
                    } else {
                        grupoHileras = grupoHileras + estadoAux.getIcono() + ": " + estadoAux.getDescripcion() + "\n";
                    }

                }
            }

        }

        Vector[][] transicionesVector = ab.getTransiciones();
        boolean rta;
        int numEstadoActual = 0;
        int contadorRespuestas = 0;

        for (int i = 1; i <= 5; i++) {
            hilera = "";
            int simbolEscogido = 0;
            estActual = null;
            continuar = true;
            rta = false;
            Iterator estadosIterator = estados.iterator();
            while (estadosIterator.hasNext()) {
                estado estadoAux = (estado) estadosIterator.next();
                if (estadoAux.isInicial()) {
                    estActual = estadoAux;
                    break;
                }
            }

            grupoHileras = grupoHileras + "\nMe sitúo en el primer estado que es: " + estActual.getIcono() + "\n";
            if (contadorRespuestas < 5) {
                int contTransiciones = 0;
                String recorridoTrans="";
                while (!rta && continuar) {
                    estadoActual = estActual.getIcono();
                    recorridoTrans = recorridoTrans + "Si estoy en " + estadoActual;
                    numEstadoActual = retornaNum(estadoActual);
                    simbolEscogido = (int) (Math.random() * (simbolos.size()));
                    recorridoTrans = recorridoTrans + " y me entra un " + Integer.toString(simbolEscogido);
                    transicionHacia = (String) transicionesVector[numEstadoActual][simbolEscogido + 1].get(0);
                    recorridoTrans = recorridoTrans + " me voy hacia " + transicionHacia;
                    hilera = hilera + simbolos.get(simbolEscogido);
                    recorridoTrans = recorridoTrans + " y en la hilera pongo un " + simbolos.get(simbolEscogido) + "\n";
                    numEstadoActual = encuentra(transicionHacia, estados);
                    estActual = (estado) estados.get(numEstadoActual);
                    contTransiciones++;
                    rta = estActual.isAceptacion();
                    if (estActual == estados.get(estados.size() - 1)) {
                        continuar = true;
                        hilera = "";
                        recorridoTrans="";
                        contTransiciones = 0;
                        Iterator nuevoIterator = estados.iterator();
                        while (nuevoIterator.hasNext()) {
                            estado estadoAux = (estado) nuevoIterator.next();
                            if (estadoAux.isInicial()) {
                                estActual = estadoAux;
                                break;
                            }
                        }                        
                    } else {
                        if (rta && i > 1 && contTransiciones <= i) {
                            continuar = true;
                            rta = false;
                        } else {
                            if (rta) {
                                grupoHileras = grupoHileras + recorridoTrans +"Como el estado " + estActual.getIcono() + " es de aceptación terminamos.\nLa hilera " + i + " es: " + hilera + "\n\n";
                                continuar = false;
                                contadorRespuestas++;
                            }
                        }
                    }
                }
            }
        }
        return grupoHileras;
    }

    /**
     * Método retorna el número de un estado
     *
     * @return int con el número de un estado
     * @param simbolosIngresados los símbolos a evaluar
     * @param n string con el nombre de un estado
     * @since Practica Automatas 1.0.0
     */
    public int retornaNum(String n) {
        String num = "";
        for (int k = 1; k < n.length(); k++) {
            char c = n.charAt(k);
            num = num + c;
        }
        return (Integer.parseInt(num));
    }

    /**
     * Método que busca un string en un vector
     *
     * @return int con la posición de un string en un vector
     * @param a string a buscar
     * @param mi vector dónde se realiza la búsqueda
     * @since Practica Automatas 1.0.0
     */
    public int encuentra(String a, Vector mi) {
        int p = 0;
        Iterator j = mi.iterator();
        while (j.hasNext()) {
            estado ll = (estado) j.next();
            String l = (String) ll.getIcono();
            if (l.equals(a)) {
                return (p);
            }
            p++;
        }

        return -1;

    }

    /**
     * Método que elimina estados extraños y convierte un autómata no
     * determinístico a determinístico
     *
     * @return autómata minimizado
     * @param autOriginal autómata no determinístico
     * @param jTable1 tabla de donde se imprime el autómata determinístico
     * @param aa área de texto donde se imprime la correspondencia de estados
     * @since Practica Automatas 1.0.0
     */
    public automata convierteDeter(automata autOriginal, JTable jTable1, JTextArea aa) {
        automata autDete;
        Vector estad = new Vector();
        Vector estadosMin = new Vector();
        Vector[][] trans = autOriginal.getTransiciones();
        int j = autOriginal.getEstados().size();
        int m = autOriginal.getSimbolos().size();
        Vector[] cierres = new Vector[j];
        trans = new Vector[2][m + 1];

        int o = 1;
        Iterator kk = autOriginal.getSimbolos().iterator();
        while (kk.hasNext()) {
            String gg = (String) kk.next();
            if (gg != "λ") {
                try {
                    trans[0][o].add(gg);
                } catch (Exception e) {
                    trans[0][o] = new Vector();
                    trans[0][o].add(gg);
                }
                o++;
            }
        }

        //cierres lambda
        for (int k = 0; k < j; k++) {

            Vector lambda = new Vector();
            Vector l = (Vector) jTable1.getValueAt(k + 1, m);
            lambda = (Vector) l.clone();
            if (!existe(lambda, jTable1.getValueAt(k + 1, 0).toString())) {
                lambda.add(jTable1.getValueAt(k + 1, 0));
            }

            for (int h = 0; h < lambda.size(); h++) {

                lambda = ingresaCierre(autOriginal, lambda, h, jTable1);

            }

            cierres[k] = lambda;
        }

        //Ingresando estado inicial en el automata minimizado
        int y = 1;
        int es = 0;
        boolean acep = false;
        Iterator p = cierres[0].iterator();
        String no = "";
        while (p.hasNext()) {
            String q = (String) p.next();
            estado cons = recuperar(autOriginal, q);
            if (cons.isAceptacion()) {
                acep = true;
            }
            no = no + " " + q;
        }

        estado nuevoE1 = new estado("S" + y, no, true, acep);
        trans = añadirEst("S" + y, y, autOriginal.getSimbolos().size() + 1, acep, trans);
        estad.add(cierres[0]);
        estadosMin.add(nuevoE1);
        String desc;
        if (acep) {
            desc = " * " + "S" + y + " : " + no + " (Aceptación)";
        } else {
            desc = " * " + "S" + y + " : " + no;
        }
        y++;
        acep = false;
        es++;

        //evaluacion de cada cierre con los simbolos de entrada
        cola w = new cola();
        w.offer(cierres[0]);
        int ss = 1;
        while (!w.empty()) {

            Vector z = (Vector) w.poll();

            for (int t = 1; t < m; t++) {
                Vector vv = new Vector();
                Iterator a = z.iterator();
                while (a.hasNext()) {
                    String b = (String) a.next();
                    int x = retornaPos(b, autOriginal, jTable1);
                    if (jTable1.getValueAt(x, t) != null) {
                        Vector s = (Vector) jTable1.getValueAt(x, t);

                        Iterator c = s.iterator();
                        while (c.hasNext()) {
                            String gg = (String) c.next();
                            if (!existe(vv, gg)) {

                                vv.add(gg);
                            }
                        }
                    }
                }

                Vector v1 = new Vector();
                boolean ini = false;
                String nom = "";
                Iterator d = vv.iterator();
                while (d.hasNext()) {
                    String h1 = (String) d.next();
                    Vector q = cierres[retornaNum(h1) - 1];
                    Iterator ii = q.iterator();
                    while (ii.hasNext()) {
                        String inf = (String) ii.next();
                        estado cons = recuperar(autOriginal, inf);
                        if (cons.isAceptacion()) {
                            acep = true;
                        }
                        if (h1.equals("E1")) {
                            ini = true;
                        }
                        nom = nom + " " + inf;
                    }
                    v1 = (Vector) concatenar(v1, q).clone();

                }
                if (nom != "") {

                    int veri = existaV(v1, estad);
                    if (veri == -1) {

                        estado nuevoE = new estado("S" + y, nom, ini, acep);
                        int xx = y;
                        if (acep) {

                            desc = desc + "\n" + "S" + xx + " : " + nom + " (Aceptación)";

                        } else {

                            desc = desc + "\n" + "S" + xx + " : " + nom;

                        }

                        trans = añadirEst("S" + y, y, autOriginal.getSimbolos().size() + 1, acep, trans);
                        acep = false;
                        try {
                            trans[ss][t].add("S" + y);
                        } catch (Exception e) {
                            trans[ss][t] = new Vector();
                            trans[ss][t].add("S" + y);
                        }
                        estad.add(v1);
                        estadosMin.add(nuevoE);
                        es++;
                        y++;
                        w.offer(v1);
                        nom = "";
                    } else {
                        estado ee = (estado) estadosMin.get(veri);
                        try {
                            trans[ss][t].add(ee.getIcono());
                        } catch (Exception e) {
                            trans[ss][t] = new Vector();
                            trans[ss][t].add(ee.getIcono());
                        }

                    }
                    acep = false;
                }
            }
            ss++;
        }

        autDete = new automata(autOriginal.getNombre() + "Min", sinLambda(autOriginal.getSimbolos()), estadosMin, trans);
        autOriginal.setAutMin(autDete);
        aa.setText(desc);
        return (autOriginal);
    }

    /**
     * Método simplifica un autómata
     *
     * @return String con la correspondencia de estados
     * @param abc autómata a simplificar
     * @param jTable2 tabla donde se imprime el nuevo autómata
     * @since Practica Automatas 1.0.0
     */
    public String simplificarAutomata(automata abc, JTable jTable2) {
        automata au = abc.getAutMin();
        String desc = "";
        DefaultTableModel modelo;
        Vector part0 = new Vector();
        Vector part1 = new Vector();
        Vector estadosM = au.getEstados();
        estado nulo = new estado("", "nulo", false, false);
        part0.add(nulo);
        Iterator e = estadosM.iterator();
        while (e.hasNext()) {
            estado ee = (estado) e.next();
            if (ee.isAceptacion()) {
                part1.add(ee);
            } else {
                part0.add(ee);
            }
        }
        Vector estadosMini = new Vector();
        if (!part0.isEmpty()) {
            estadosMini.add(part0);
        }
        if (!part1.isEmpty()) {
            estadosMini.add(part1);
        }
        estadosMini = minimizar(au.getSimbolos().size(), au.getTransiciones(), estadosMini);

        modelo = new DefaultTableModel(estadosMini.size() + 1, au.getSimbolos().size() + 2);
        modelo.setValueAt(null, 0, 0);

        Vector[][] tra = new Vector[estadosMini.size() + 1][au.getSimbolos().size() + 2];
        Vector esta = new Vector();
        int y = 1;

        Iterator ss = estadosMini.iterator();
        while (ss.hasNext()) {
            ss.next();
            estado nuevo = new estado("T" + y, "", false, false);
            tra[y][0] = new Vector();
            tra[y][0].add(nuevo.getIcono());
            modelo.setValueAt("T" + y, y, 0);
            y++;
            esta.add(nuevo);
        }
        for (int ii = 1; ii <= au.getSimbolos().size(); ii++) {
            modelo.setValueAt(au.getSimbolos().get(ii - 1), 0, ii);
            tra[0][ii] = new Vector();
            tra[0][ii].add(au.getSimbolos().get(ii - 1));
        }

        y = 0;
        boolean nu = true;
        estadosMini = ordena(estadosMini);
        Iterator s = estadosMini.iterator();

        while (s.hasNext()) {
            String des = "";
            String des1 = "";
            boolean ini = false;
            boolean acep = false;
            estado b;
            Vector a = (Vector) s.next();
            y++;
            Iterator s1 = a.iterator();
            while (s1.hasNext()) {
                estado es = (estado) s1.next();
                if (!es.getDescripcion().equals("nulo")) {
                    if (es.isAceptacion()) {
                        acep = true;
                    }
                    if (es.isInicial()) {
                        ini = true;

                    }
                    if (!existeString(es.getIcono(), des1)) {

                        des = conc(des, es.getDescripcion());
                        des1 = des1 + es.getIcono();
                        int x = retornaNum(es.getIcono());
                        for (int aa = 1; aa <= au.getSimbolos().size(); aa++) {
                            Vector cc = (Vector) jTable2.getValueAt(x, aa);
                            if (cc == null) {
                                modelo.setValueAt("T" + estadosMini.size(), y, aa);
                                tra[y][aa] = new Vector();
                                tra[y][aa].add("T" + estadosMini.size());
                            } else {
                                String dd = (String) cc.get(0);
                                int pos = encuentraEs(dd, estadosMini);
                                if (pos != -1) {
                                    pos++;
                                    modelo.setValueAt("T" + pos, y, aa);
                                    tra[y][aa] = new Vector();
                                    tra[y][aa].add("T" + pos);
                                }
                            }
                        }
                    }
                    int gg = 0;

                    if (acep) {
                        gg = 1;
                    }
                    modelo.setValueAt(gg, y, au.getSimbolos().size() + 1);
                    tra[y][au.getSimbolos().size() + 1] = new Vector();
                    tra[y][au.getSimbolos().size() + 1].add(gg);
                    estado ef = (estado) esta.get(y - 1);
                    ef.setDescripcion(des);
                    ef.setAceptacion(acep);
                    ef.setInicial(ini);

                } else {
                    if (des.isEmpty()) {
                        des = des + "nulo";
                    } else {
                        des = des + ", nulo";
                    }
                    for (int kk = 0; kk <= au.getSimbolos().size(); kk++) {
                        modelo.setValueAt("T" + estadosMini.size(), estadosMini.size(), kk);
                        if (kk != 0) {
                            tra[estadosMini.size()][kk] = new Vector();
                            tra[estadosMini.size()][kk].add("T" + estadosMini.size());
                        }
                    }
                    modelo.setValueAt(0, estadosMini.size(), au.getSimbolos().size() + 1);
                    nu = false;
                    y--;
                }

            }
            if (nu) {
                int xx = y;
                if (acep) {

                    if (ini) {
                        desc = desc + "\n" + " * " + "T" + xx + " : " + des + " (Aceptación)";
                    } else {
                        desc = desc + "\n" + "T" + xx + " : " + des + " (Aceptación)";
                    }
                } else {
                    if (ini) {
                        desc = desc + "\n" + " * " + "T" + xx + " : " + des;
                    } else {
                        desc = desc + "\n" + "T" + xx + " : " + des;
                    }
                }

            } else {
                int xx = y + 1;
                if (acep) {

                    if (ini) {
                        desc = desc + "\n" + " * " + "T" + xx + " : " + des + " (Aceptación)";
                    } else {
                        desc = desc + "\n" + "T" + xx + " : " + des + " (Aceptación)";
                    }
                } else {
                    if (ini) {
                        desc = desc + "\n" + " * " + "T" + xx + " : " + des;
                    } else {
                        desc = desc + "\n" + "T" + xx + " : " + des;
                    }
                }
                nu = true;
            }

        }
        automata autDete = new automata(abc.getNombre() + "Min", au.getSimbolos(), esta, tra);
        abc.setAutMin(autDete);
        jTable2.setModel(modelo);
        return (desc);
    }

    /**
     * Método que concatena dos string si uno no existe en el otro
     *
     * @return String con el resultado de la concatenación
     * @param a string uno
     * @param b string dos
     * @since Practica Automatas 1.0.0
     */
    public String conc(String a, String b) {
        int z = b.length();
        String con = a;
        String est = "";
        for (int i = 0; i < z; i++) {
            if (b.charAt(i) != ' ') {
                est = est + b.charAt(i);
            } else {
                if (!existeString(est, con)) {
                    con = con + " " + est;
                }
                est = "";
            }
        }
        if (!est.equals("")) {
            if (!existeString(est, con)) {
                con = con + " " + est;
            }
        }

        return con;

    }

    /**
     * Método busca un string en un vector de datos
     *
     * @return boolean con el resultado de evaluar la búsqueda
     * @param v vector donde se buscará el string
     * @param z string a buscar en el vector
     * @since Practica Automatas 1.0.0
     */
    public boolean existe(Vector v, String z) {

        if (v != null) {
            Iterator i = v.iterator();
            while (i.hasNext()) {
                if (i.next().equals(z)) {
                    return (true);
                }
            }
        }
        return (false);

    }

    /**
     * Método que ingresa los cierres lambda
     *
     * @return Vector con el resultado de la operación
     * @param a1 autómata donde se ingresarán los cierres lambda
     * @param v vector de datos
     * @param jTable1 tabla donde se muestra el resultado de la operación
     * @since Practica Automatas 1.0.0
     */
    public Vector ingresaCierre(automata a1, Vector v, int l, JTable jTable1) {
        String n = (String) v.get(l);
        estado e = recuperar(a1, n);
        Vector busq;
        if (e != null) {
            busq = null;
            int g = jTable1.getRowCount();

            for (int h = 0; h < g; h++) {
                String o = (String) jTable1.getValueAt(h + 1, 0);
                if (o.equals(e.getIcono())) {
                    busq = (Vector) jTable1.getValueAt(h + 1, a1.getSimbolos().size());
                    break;
                }
            }

            int c = 0;
            if (busq != null) {
                c = busq.size();
            }
            for (int h = 0; h < c; h++) {
                Iterator a = busq.iterator();
                while (a.hasNext()) {
                    String b = (String) a.next();
                    if (!existe(v, b)) {
                        v.add(b);
                    }
                }

            }
        }
        return (v);
    }

    /**
     * Método que recupera un estado de un autómata
     *
     * @return estado de un autómata
     * @param autt los símbolos a evaluar
     * @param n autómata en el que se evaluará la hilera
     * @since Practica Automatas 1.0.0
     */
    public estado recuperar(automata autt, String n) {
        Vector v = (Vector) autt.getEstados();
        Iterator a = v.iterator();
        while (a.hasNext()) {
            estado e = (estado) a.next();
            if (e.getIcono().equals(n)) {
                return (e);
            }
        }
        return (null);

    }

    /**
     * Método añade estados a un vector bidimensional de estados
     *
     * @return Vector bidimensional de estados
     * @param min string con el estado a añadir
     * @param i int número de filas
     * @param n int número de columnas
     * @param trans vector de transiciones
     * @since Practica Automatas 1.0.0
     */
    public Vector[][] añadirEst(String min, int i, int n, boolean ac, Vector[][] trans) {
        Vector[][] nueva = new Vector[i + 1][n];
        for (int j = 0; j < i; j++) {
            for (int k = 0; k < n; k++) {
                try {
                    nueva[j][k] = (Vector) trans[j][k];
                } catch (Exception e) {
                    nueva[j][k] = new Vector();
                    nueva[j][k] = (Vector) trans[j][k];

                }
            }

        }
        try {
            nueva[i][0].add(min);
        } catch (Exception e) {
            nueva[i][0] = new Vector();
            nueva[i][0].add(min);
            nueva[i][n - 1] = new Vector();
            if (ac) {
                nueva[i][n - 1].add(1);
            } else {

                nueva[i][n - 1].add(0);
            }
        }
        return nueva;
    }

    /**
     * Método que retorna posición de un estado en una tabla
     *
     * @return int con la posición del estado
     * @param n String a buscar
     * @param a autómata del que se toma el tamaño del vector de estados
     * @param jTable1 tabla en la que se busca la posición del estado
     * @since Practica Automatas 1.0.0
     */
    public int retornaPos(String n, automata a, JTable jTable1) {
        int max = a.getEstados().size() + 1;

        for (int l = 1; l < max; l++) {
            if (jTable1.getValueAt(l, 0).toString().equals(n)) {
                return (l);
            }
        }
        return (-1);
    }

    /**
     * Método que une dos vectores
     *
     * @return Vector con la unión de dos vectores
     * @param a1 vector uno
     * @param a2 vector dos
     * @since Practica Automatas 1.0.0
     */
    public Vector concatenar(Vector a1, Vector a2) {
        Iterator a = a2.iterator();
        while (a.hasNext()) {
            String bb = (String) a.next();
            if (!existe(a1, bb)) {
                a1.add(bb);
            }
        }
        return (a1);
    }

    /**
     * Método que compara el contenido de dos vectores
     *
     * @return int con resultado de la operación
     * @param a vector uno
     * @param b vector dos
     * @since Practica Automatas 1.0.0
     */
    public int existaV(Vector a, Vector b) {
        boolean rta = false;
        int cont = 0;
        int est = 0;
        Iterator c = b.iterator();
        while (c.hasNext()) {
            Vector hh = (Vector) c.next();
            est++;
            if (a.size() == hh.size()) {
                Iterator a1 = a.iterator();
                Iterator ah = hh.iterator();
                while (a1.hasNext()) {
                    String b1 = (String) a1.next();
                    ah = hh.iterator();
                    while (ah.hasNext()) {
                        String bh = (String) ah.next();
                        if (b1.equals(bh)) {
                            cont++;
                        }
                    }
                }
                if (cont == a.size()) {
                    return (est - 1);

                } else {
                    cont = 0;
                }

            }
        }
        return (-1);
    }

    /**
     * Método añade símbolos lambda a un vector
     *
     * @return Vector con los lambda
     * @param con vector con estados
     * @since Practica Automatas 1.0.0
     */
    public Vector sinLambda(Vector con) {
        Vector b = new Vector();
        Iterator a = con.iterator();
        while (a.hasNext()) {
            String aa = a.next().toString();
            if (!aa.equals("λ")) {
                b.add(aa);
            }
        }
        return (b);
    }

    public Vector minimizar(int sim, Vector[][] tr, Vector mini) {
        Vector tra;
        int pos = 0;
        int ss = -1;
        int con = 0;
        boolean crearon = false;
        tra = new Vector();
        Iterator min = mini.iterator();
        while (min.hasNext()) {

            Vector aa = (Vector) min.next();
            for (int ii = 1; ii <= sim; ii++) {
                ss = -1;
                Iterator a1 = aa.iterator();
                tra = new Vector();
                Vector us = new Vector();
                us.add(con);
                tra.add(us);

                while (a1.hasNext()) {
                    String t;
                    estado ae = (estado) a1.next();
                    if (ae.getIcono().equals("")) {
                        t = "";
                    } else {
                        int x = retornaNum(ae.getIcono());
                        if (tr[x][ii] == null) {
                            t = "";
                        } else {
                            t = (String) tr[x][ii].get(0).toString();
                        }
                    }
                    pos = encuentraEs(t, mini);
                    if (pos != -1) {

                        if (ss == -1) {
                            ss = pos;
                        }
                        if (pos != ss) {

                            int poss = encuenTra(Integer.toString(pos), tra);
                            if (poss != -1) {
                                Vector re = (Vector) tra.get(poss);
                                re.add(ae);
                            } else {

                                Vector bb = new Vector();
                                bb.add(pos);
                                bb.add(ae);
                                tra.add(bb);
                            }
                        }
                    }
                }
                if (tra.size() > 1) {
                    mini = creaPart(tra, mini);
                    crearon = true;
                }
            }
            con++;
            if (crearon) {
                return (minimizar(sim, tr, mini));
            }
        }

        return mini;
    }

    public int encuenTra(String a, Vector mi) {
        int p = 0;
        Iterator j = mi.iterator();
        while (j.hasNext()) {
            Vector b = (Vector) j.next();
            Iterator k = b.iterator();
            while (k.hasNext()) {
                String l = (String) k.next().toString();
                if (l.equals(a)) {
                    return (p);
                }
            }
            p++;
        }

        return -1;

    }

    public Vector creaPart(Vector a, Vector b) {
        Vector aaa = (Vector) a.get(0);
        int p = (Integer) aaa.get(0);
        a.remove(0);
        Iterator a1 = a.iterator();
        while (a1.hasNext()) {
            Vector aa = (Vector) a1.next();
            b.add(aa);
            aa.remove(0);
            Iterator aux = aa.iterator();
            while (aux.hasNext()) {
                estado es = (estado) aux.next();
                Vector h = (Vector) b.get(p);
                h.remove(es);
            }
        }
        return b;
    }

    public Vector ordena(Vector a) {
        int co = 0;
        int cc = 0;
        Iterator a1 = a.iterator();
        while (a1.hasNext()) {
            Vector b = (Vector) a1.next();
            Iterator b1 = b.iterator();
            while (b1.hasNext()) {
                estado ee = (estado) b1.next();
                if (ee.isInicial()) {
                    if (cc != 0) {
                        if (a.size() > 1) {
                            a = intercambia(a, 0, co);
                            a = intercambia(a, co, a.size() - 1);
                        } else {
                            b = intercambiaEs(b, 0, cc);
                            b = intercambiaEs(b, cc, b.size() - 1);
                            a.setElementAt(b, co);
                        }
                        return a;
                    }

                }
                cc++;
            }
            co++;
        }

        return null;

    }

    public Vector intercambia(Vector a, int posA, int posB) {
        Vector aux = (Vector) a.get(posA);
        a.setElementAt(a.get(posB), posA);
        a.setElementAt(aux, posB);
        return a;
    }

    public Vector intercambiaEs(Vector a, int posA, int posB) {
        estado aux = (estado) a.get(posA);
        a.setElementAt(a.get(posB), posA);
        a.setElementAt(aux, posB);
        return a;
    }

    public boolean existeString(String a, String b) {
        char c;
        int j = b.length();
        String es = "";
        for (int ii = 0; ii < j; ii++) {
            char h = b.charAt(ii);
            if (h == ' ') {
                if (es.equals(a)) {
                    return (true);
                } else {
                    es = "";
                }

            } else {
                es = es + h;
            }
        }

        return false;
    }

    /**
     * Método que busca un estado en un vector
     *
     * @return int con posición de un string
     * @param a String a buscar
     * @param mi vector de búsqueda
     * @since Practica Automatas 1.0.0
     */
    public int encuentraEs(String a, Vector mi) {
        int p = 0;
        Iterator j = mi.iterator();
        while (j.hasNext()) {
            Vector b = (Vector) j.next();
            Iterator k = b.iterator();
            while (k.hasNext()) {
                estado ll = (estado) k.next();
                String l = (String) ll.getIcono();
                if (l.equals(a)) {
                    return (p);
                }
            }
            p++;
        }

        return -1;

    }
}
