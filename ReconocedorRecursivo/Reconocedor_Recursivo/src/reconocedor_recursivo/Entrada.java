package reconocedor_recursivo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Felipe
 */
public final class Entrada extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */
    private ArrayList<String> nLines;
    private ArrayList<String> terminalesDer;
    private ArrayList<String> noTerminalesIzq;
    private ArrayList<String> noTerminalesDer;
    private ArrayList<String> ladosDerechos;
    private ArrayList<String> noTerminales;
    private String so;
    private String directorioActual;

    public Entrada() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("Generador de reconocedor recursivo");
        this.setDefaultCloseOperation(Entrada.EXIT_ON_CLOSE);
        jbGenerar.setEnabled(false);
        llenarVentana();
    }

    public void llenarVentana() {
        String[] lines = areaProducciones.getText().split("\\n");
        areaNumProducciones.setText("");
        for (int i = 0; i < lines.length; i++) {
            areaNumProducciones.setText(areaNumProducciones.getText() + (i + 1) + ".\n");
        }
    }

    public void anadirNoTerminalDer(String ladoDer) {
        String noTerminal;
        while (ladoDer.length() > 0) {
            if (ladoDer.contains("<")) {
                if (ladoDer.charAt(0) == '<') {
                    noTerminal = ladoDer.substring(0, ladoDer.indexOf('>') + 1);
                    System.out.println("No terminal derecho " + noTerminal);
                    noTerminalesDer.add(noTerminal);
                    ladoDer = ladoDer.substring(ladoDer.indexOf('>') + 1);
                } else {
                    ladoDer = ladoDer.substring(1);
                }
            } else {
                break;
            }
        }
    }

    public void guardarTodo() {
        noTerminalesIzq = new ArrayList();
        noTerminalesDer = new ArrayList();
        terminalesDer = new ArrayList();
        ladosDerechos = new ArrayList();
        noTerminales = new ArrayList();
        String[] lines = areaProducciones.getText().split("\\n");
        nLines = new ArrayList();
        nLines.addAll(Arrays.asList(lines));
        for (String nLine : nLines) {
            String ladoIzq = nLine.substring(0, nLine.lastIndexOf("->") - 1).trim();
            String ladoDer = nLine.substring(nLine.lastIndexOf("->") + 3, nLine.length()).trim();
            noTerminalesIzq.add(ladoIzq);
            ladosDerechos.add(ladoDer);
            if (!noTerminales.contains(ladoIzq)) {
                noTerminales.add(ladoIzq);
            }
            String p = Character.toString(ladoDer.charAt(0));
            terminalesDer.add(p.trim());
            System.out.println("No terminal izquierdo " + ladoIzq);
            anadirNoTerminalDer(ladoDer);
        }
    }

    public boolean validarNoTerminalesDer() {
        for (String noTerminalesDer1 : noTerminalesDer) {
            if (!noTerminalesIzq.contains(noTerminalesDer1)) {
                JOptionPane.showMessageDialog(null, "La gramatica tiene no terminales sin definir",
                        "No terminales sin definir", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return true;
    }

    public boolean diferenteTerminalDer() {
        boolean tipoS = true;
        ArrayList<String> noT = (ArrayList<String>) noTerminalesIzq.clone();
        ArrayList<String> primeros = (ArrayList<String>) ladosDerechos.clone();
        String noTerminalActual;
        ArrayList<String> primeros2;
        while (noT.size() > 1) {
            noTerminalActual = noT.get(0);
            primeros2 = new ArrayList();
            for (int i = 0; i < noT.size(); i++) {
                if (noT.contains(noTerminalActual)) {
                    if (noT.get(i).equals(noTerminalActual)) {
                        primeros2.add(String.valueOf(primeros.get(i).charAt(0)));
                        noT.remove(i);
                        primeros.remove(i);
                        i--;
                    }
                } else {
                    break;
                }
            }
            String p;
            while (primeros2.size() > 1) {
                p = primeros2.get(0);
                primeros2.remove(0);
                if (primeros2.contains(p)) {
                    tipoS = false;
                    JOptionPane.showMessageDialog(null, "La gramatica tiene producciones cuyo no terminal del lado izquierdo es el \nmismo y los terminales con los cuales comienzan sus lados derechos también.",
                            "Verificacion de terminales", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        return tipoS;
    }

    public ArrayList<String> guardarNoTerminalesVivos() {
        ArrayList<String> vivos = new ArrayList();
        for (int i = 0; i < ladosDerechos.size(); i++) {
            if (!ladosDerechos.get(i).contains("<")) {
                if (!vivos.contains(noTerminalesIzq.get(i))) {
                    vivos.add(noTerminalesIzq.get(i));
                }
            }
        }
        if (!vivos.isEmpty()) {
            boolean nuevoVivo;
            boolean nuevaRonda = true;
            while (nuevaRonda) {
                nuevaRonda = false;
                for (int i = 0; i < ladosDerechos.size(); i++) {
                    if (!vivos.contains(noTerminalesIzq.get(i))) {
                        String ladoDer = ladosDerechos.get(i);
                        String noTerminal;
                        nuevoVivo = true;
                        while (ladoDer.length() > 0) {
                            if (ladoDer.charAt(0) == '<') {
                                noTerminal = ladoDer.substring(0, ladoDer.indexOf('>') + 1);
                                System.out.println("No terminal vivo " + noTerminal);
                                if (vivos.contains(noTerminal)) {
                                    ladoDer = ladoDer.substring(ladoDer.indexOf('>') + 1);
                                } else {
                                    nuevoVivo = false;
                                    break;
                                }
                            } else {
                                ladoDer = ladoDer.substring(1);
                            }
                        }
                        if (nuevoVivo) {
                            vivos.add(noTerminalesIzq.get(i));
                            nuevaRonda = true;
                        }
                    }
                }
            }
        }
        return vivos;
    }

    public boolean eliminarNoTerminalesMuertos() {
        ArrayList<String> vivos = guardarNoTerminalesVivos();
        if (!vivos.isEmpty()) {
            if (vivos.size() < noTerminales.size()) {
                ArrayList<String> muertos = new ArrayList();
                for (int i = 0; i < noTerminalesIzq.size(); i++) {
                    if (!vivos.contains(noTerminalesIzq.get(i))) {
                        noTerminalesIzq.remove(noTerminalesIzq.get(i));
                        ladosDerechos.remove(ladosDerechos.get(i));
                        i--;
                    }
                }
                for (int i = 0; i < noTerminales.size(); i++) {
                    if (!vivos.contains(noTerminales.get(i))) {
                        muertos.add(noTerminales.get(i));
                        noTerminales.remove(i);
                        i--;
                    }
                }
                for (int i = 0; i < noTerminalesDer.size(); i++) {
                    if (!vivos.contains(noTerminalesDer.get(i))) {
                        noTerminalesDer.remove(i);
                        i--;
                    }
                }
                for (int i = 0; i < ladosDerechos.size(); i++) {
                    String lado = ladosDerechos.get(i);
                    for (String muerto : muertos) {
                        if (lado.contains(muerto)) {
                            noTerminalesIzq.remove(i);
                            ladosDerechos.remove(i);
                            i--;
                            break;
                        }
                    }
                }
                JOptionPane.showMessageDialog(null, "La gramatica contiene no terminales muertos \nlos cuales serán eliminados a continuación.",
                        "Verificacion de primeros", JOptionPane.INFORMATION_MESSAGE);
                actualizarGramatica();
            }
        } else {
            return false;
        }
        return true;
    }

    public void actualizarGramatica() {
        areaNumProducciones.setText("");
        areaProducciones.setText("");
        for (int i = 0; i < noTerminalesIzq.size(); i++) {
            areaNumProducciones.setText(areaNumProducciones.getText() + (i + 1) + ".\n");
            areaProducciones.setText(areaProducciones.getText() + noTerminalesIzq.get(i) + " -> " + ladosDerechos.get(i) + "\n");
        }
    }

    public ArrayList<ArrayList<String>> grupoLadosDerechos() {
        ArrayList<ArrayList<String>> derechos = new ArrayList();
        ArrayList<String> derecho;
        ArrayList<String> noTerminalesI = (ArrayList<String>) noTerminalesIzq.clone();
        ArrayList<String> lados = (ArrayList<String>) ladosDerechos.clone();
        for (String noTerminale : noTerminales) {
            derecho = new ArrayList();
            for (int j = 0; j < noTerminalesIzq.size(); j++) {
                if (noTerminalesI.contains(noTerminale)) {
                    if (noTerminalesI.get(j).equals(noTerminale)) {
                        derecho.add(lados.get(j));
                        noTerminalesI.remove(j);
                        lados.remove(j);
                        j--;
                    }
                } else {
                    break;
                }
            }
            derechos.add(derecho);
        }
        return derechos;
    }

    public void genReconocedorRecursivo() throws IOException {
        File script = null;
        File archivo = null;
        File manifiesto = null;
        so = System.getProperty("os.name").replaceAll(" ", "");
        System.out.println(so);
        directorioActual = System.getProperty("user.dir");
        System.out.println(directorioActual);
        if (so.charAt(0) == 'W' || so.charAt(0) == 'w') {
            archivo = new File(directorioActual + "\\Reconocedor.java");
            script = new File(directorioActual + "\\scriptWindows.bat");
            manifiesto = new File(directorioActual + "\\temp.mf");
        } else if (so.charAt(0) == 'L' || so.charAt(0) == 'l') {
            archivo = new File(directorioActual + "/Reconocedor.java");
            script = new File(directorioActual + "/scriptLinux.sh");
            manifiesto = new File(directorioActual + "/temp.mf");
        } else {
            archivo = new File(directorioActual + "Reconocedor.java");
            script = new File(directorioActual + "script.sh");
            manifiesto = new File(directorioActual + "temp.mf");
        }
        if (archivo.exists()) {
            if (archivo.delete()) {
                System.out.println(true);
            }
        }
        if (script.exists()) {
            if (script.delete()) {
                System.out.println(true);
            }
        }
        if (manifiesto.exists()) {
            if (manifiesto.delete()) {
                System.out.println(true);
            }
        }
        try {
            archivo.createNewFile();
            System.out.println("Archivo creado");
            script.createNewFile();
            System.out.println("Script creado");
            manifiesto.createNewFile();
            System.out.println("Manifiesto creado");
        } catch (Exception e) {
            System.out.println("no se puede crear el archivo");
        }
        try (FileWriter batch = new FileWriter(script, true)) {
            batch.append(                    
                    "javac Reconocedor.java\r\n"                    
                    + "jar -cf Reconocedor.jar Reconocedor.class\r\n"                    
                    + "jar cmf temp.mf Reconocedor.jar Reconocedor.class\r\n"                   
                    + "java -jar Reconocedor.jar"
            );
        } catch (Exception e) {
        }
        try (FileWriter manifest = new FileWriter(manifiesto, true)) {
            manifest.append(
                    "Main-Class: Reconocedor\r\n"                 
                    + "Sealed: true"
            );
        } catch (Exception e) {
        }
        try (FileWriter codigo = new FileWriter(archivo, true)) {
            codigo.append(
                    "\n"
                    + "import java.awt.event.ActionEvent;\n"
                    + "import java.awt.event.ActionListener;\n"
                    + "import javax.swing.JLabel;\n"
                    + "import javax.swing.JButton;\n"
                    + "import javax.swing.JTextField;\n"
                    + "import javax.swing.JPanel;\n"
                    + "import javax.swing.JOptionPane;\n"
                    + "\n"
                    + "public class Reconocedor extends javax.swing.JFrame implements ActionListener{\n"
                    + "       public static void main (String[] args){\n"
                    + "         Reconocedor r = new Reconocedor();\n"
                    + "         r.setVisible(true);\n"
                    + "       }\n"
                    + "\n"
                    + "JLabel etiHileraProbar;\n"
                    + "JTextField txtHileraProbar;\n"
                    + "JButton probar;\n"
                    + "JPanel panel;\n"
                    + "private String simbolos;\n"
                    + "private char simbolo;\n"
                    + "private short i;\n"
                    + "\n"
                    + "       public Reconocedor(){\n"
                    + "         this.setLocationRelativeTo(null);\n"
                    + "         this.setResizable(false);\n"
                    + "         this.setTitle(\"Reconocedor de hileras\");\n"
                    + "         this.setDefaultCloseOperation(Reconocedor.EXIT_ON_CLOSE);\n"
                    + "         etiHileraProbar = new JLabel(\"Ingrese hilera a probar:\");\n"
                    + "         etiHileraProbar.setBounds(10,20,150,20);\n"
                    + "         txtHileraProbar = new JTextField();\n"
                    + "         txtHileraProbar.setBounds(160,20,100,20);\n"
                    + "         probar =  new JButton(\"Probar hilera\"); \n"
                    + "         probar.setBounds(270,20,150,20);\n"
                    + "         probar.addActionListener(this);\n"
                    + "         panel = new JPanel();\n"
                    + "         panel.setLayout(null);\n"
                    + "         panel.add(etiHileraProbar);\n"
                    + "         panel.add(txtHileraProbar);\n"
                    + "         panel.add(probar);\n"
                    + "         add(panel);\n"
                    + "         setSize(500,100);\n"
                    + "         setVisible(true);\n"
                    + "       }"
                    + "\n"
                    + "@Override\n"
                    + "     public void actionPerformed(ActionEvent e){\n"
                    + "         if (e.getSource()==probar){\n"
                    + "             simbolos = txtHileraProbar.getText() + \"˧\";\n"
                    + "             i = 1;\n"
                    + "             principal();\n"
                    + "         }\n"
                    + "      }\n"
                    + "     public void principal(){\n"
                    + "         String especial = \"˧\";"
                    + "        simbolo = simbolos.charAt(0);\n"
                    + "        nt" + noTerminales.get(0).substring(1, noTerminales.get(0).length() - 1) + "();\n"
                    + "        if(simbolo==especial.charAt(0)){\n"
                    + "            JOptionPane.showMessageDialog(null, \"La hilera es aceptada\",\"Reconocimiento de hilera\",JOptionPane.PLAIN_MESSAGE);\n"
                    + "        }\n"
                    + "        else{\n"
                    + "            JOptionPane.showMessageDialog(null, \"La hilera es rechazada\",\"Reconocimiento de hilera\",JOptionPane.PLAIN_MESSAGE);\n"
                    + "        }\n"
                    + "    }\n\n");

            ArrayList<ArrayList<String>> parteDerecha = grupoLadosDerechos();
            String subprogramasRecursivos = "";
            for (int i = 0; i < noTerminales.size(); i++) {
                String casos = "";
                for (int j = 0; j < parteDerecha.get(i).size(); j++) {
                    String lado = parteDerecha.get(i).get(j);
                    String caso = "            case \'" + parteDerecha.get(i).get(j).charAt(0) + "\':\n"
                            + "                simbolo = simbolos.charAt(i);\n"
                            + "                i++;\n";
                    lado = lado.substring(1);
                    int si = 0;
                    String tabulacion = "                ";
                    while (lado.length() > 0) {
                        if (lado.charAt(0) != '<') {
                            caso = caso.concat(tabulacion + "if(simbolo==\'" + lado.charAt(0) + "\'){\n"
                                    + tabulacion + "    simbolo = simbolos.charAt(i);\n"
                                    + tabulacion + "    i++;\n");
                            tabulacion = tabulacion + "    ";
                            si++;
                            lado = lado.substring(1);
                        } else {
                            caso = caso.concat(tabulacion + "nt" + lado.substring(1, lado.indexOf('>')) + "();\n");
                            lado = lado.substring(lado.indexOf('>') + 1);
                        }
                    }
                    caso = caso.concat(tabulacion + "break;\n");
                    for (int k = 0; k < si; k++) {
                        tabulacion = tabulacion.substring(0, tabulacion.length() - 4);
                        caso = caso.concat(tabulacion + "}\n");
                    }
                    if (si > 0) {
                        caso = caso.concat("                simbolo = \' \';\n"
                                + "                break;\n");
                    }
                    casos = casos.concat(caso);
                }

                String subprograma = "    public void nt" + noTerminales.get(i).substring(1, noTerminales.get(i).length() - 1) + "(){\n"
                        + "\tswitch(simbolo){\n"
                        + casos
                        + "\t    default:\n"
                        + "\t        simbolo = \' \';\n"
                        + "\t}\n"
                        + "    }\n\n";
                subprogramasRecursivos = subprogramasRecursivos.concat(subprograma);
            }
            codigo.append(subprogramasRecursivos
                    + "}");
        } catch (Exception e) {
        }
    }

    private boolean esTipoS(String s, int nProduccion) {
        boolean abreN = false;
        boolean ladoDer = false;
        boolean unTerminal = false;
        boolean unNoTerminalDerecha = false;
        boolean unNoTerminalIzquierda = false;
        boolean lambda;
        System.out.println(s.length());
        if (s.length() == 0) {
            JOptionPane.showMessageDialog(this, "La producción " + nProduccion + " es vacía");
            return (false);
        } else if (s.charAt(0) != '<') {
            JOptionPane.showMessageDialog(this, "La producción " + nProduccion + " no empieza con un no terminal");
            return (false);
        }

        for (int i = 0; i < s.length(); i++) {
            switch (s.charAt(i)) {
                case '<':
                    if (abreN) {
                        JOptionPane.showMessageDialog(this, "La producción " + nProduccion + " está abriendo un no terminal\nen la posición " + (i + 1) + " sin cerrar uno que abrió anteriormente");
                        return (false);
                    }
                    if (ladoDer && !unTerminal) {
                        JOptionPane.showMessageDialog(this, "La producción " + nProduccion + " empieza con un no terminal al lado derecho\nen la posición " + (i + 1));
                        return (false);
                    }
                    abreN = true;
                    break;

                case '-':
                    if (s.length() > i + 1) {
                        if (s.charAt(i + 1) != '>' || ladoDer) {
                            JOptionPane.showMessageDialog(this, "La producción " + nProduccion + " tiene un guión usado de\nmanera ilegal o separador (->) escrito incorrectamente\nen la posición " + (i + 1));
                            return (false);
                        } else if (abreN && !ladoDer) {
                            JOptionPane.showMessageDialog(this, "La producción " + nProduccion + " tiene un no terminal sin cerrar\n al lado izquierdo.");
                            return (false);
                        }
                    }

                    break;
                case '>':
                    if (!ladoDer) {
                        if (unNoTerminalIzquierda) {
                            if (s.charAt(i - 1) != '-') {
                                JOptionPane.showMessageDialog(this, "La producción " + nProduccion + " tiene más de un no terminal a la izquierda \no cerró varias veces un no terminal.");
                                return (false);
                            }

                        } else {
                            unNoTerminalIzquierda = true;
                        }
                    }
                    if (i >= 1) {
                        if (abreN && s.charAt(i - 1) == '<') {
                            JOptionPane.showMessageDialog(this, "La producción " + nProduccion + " tiene un no terminal\nsin nombre en la posición " + (i + 1));
                            return (false);
                        }
                        if (!abreN && s.charAt(i - 1) != '-') {
                            JOptionPane.showMessageDialog(this, "La producción " + nProduccion + " cerró un no terminal en la posición " + (i + 1) + "\nsin haberlo abierto.");
                            return (false);
                        } else if (abreN) {
                            abreN = false;
                            if (ladoDer && !unNoTerminalDerecha) {
                                unNoTerminalDerecha = true;
                            }
                        } else if (s.charAt(i - 1) == '-') {
                            ladoDer = true;
                        }
                    }
                    if (ladoDer && unTerminal && i == s.length() - 1) {
                        return (true);
                    }
                    break;
                case 'λ':
                    lambda = true;
                    if (lambda) {
                        JOptionPane.showMessageDialog(this, "La producción " + nProduccion + " tiene un λ que representa nulidad\nen la posición " + (i + 1));
                        return (false);
                    }
                    break;
                case ' ':
                    break;
                default:

                    if (!ladoDer && !abreN) {
                        JOptionPane.showMessageDialog(this, "La producción " + nProduccion + " tiene un terminal a la izquierda\nen la posición " + (i + 1));
                        return (false);
                    }
                    if (ladoDer && !unTerminal && !abreN) {
                        unTerminal = true;
                        if (i == s.length() - 1) {
                            return (true);
                        }
                    } else if (ladoDer && unTerminal && !abreN) {
                        if (ladoDer && unTerminal && i == s.length() - 1) {
                            return (true);
                        } else {
                            break;
                        }
                    }
            }
            if (i == s.length() - 1 && abreN) {
                JOptionPane.showMessageDialog(this, "La producción " + nProduccion + " tiene un no terminal sin cerrar al lado derecho.");
                return (false);
            }
        }
        JOptionPane.showMessageDialog(this, "La producción " + nProduccion + " tiene nulo su lado derecho.");
        return (false);
    }

    public void desactivaBotones() {
        areaProducciones.setEnabled(false);
        areaNumProducciones.setEnabled(false);
        jbGenerar.setEnabled(true);
        jPanel2.setEnabled(false);
        jButton0.setEnabled(false);
        jButton1.setEnabled(false);
        jButtonA.setEnabled(false);
        jButtonB.setEnabled(false);
        jButtonBorrar.setEnabled(false);
        jButtonC.setEnabled(false);
        jButtonD.setEnabled(false);
        jButtonE.setEnabled(false);
        jButtonEnter.setEnabled(false);
        jButtonFlecha.setEnabled(false);
        jButtonLambda.setEnabled(false);
        jButtonPard.setEnabled(false);
        jButtonPari.setEnabled(false);
        jButtonS.setEnabled(false);
        jButtona.setEnabled(false);
        jButtonb.setEnabled(false);
        jButtonc.setEnabled(false);
        jButtond.setEnabled(false);
        jButtone.setEnabled(false);
        jButtonf.setEnabled(false);
        jBidentificar.setEnabled(false);
        botonNuevaGramatica.setEnabled(false);
    }

    public void renovar() {
        areaProducciones.setText("");
        areaProducciones.setEnabled(true);
        areaNumProducciones.setText("");
        areaNumProducciones.setEnabled(true);
        jbGenerar.setEnabled(false);
        jPanel2.setEnabled(true);
        jButton0.setEnabled(true);
        jButton1.setEnabled(true);
        jButtonA.setEnabled(true);
        jButtonB.setEnabled(true);
        jButtonBorrar.setEnabled(true);
        jButtonC.setEnabled(true);
        jButtonD.setEnabled(true);
        jButtonE.setEnabled(true);
        jButtonEnter.setEnabled(true);
        jButtonFlecha.setEnabled(true);
        jButtonLambda.setEnabled(true);
        jButtonPard.setEnabled(true);
        jButtonPari.setEnabled(true);
        jButtonS.setEnabled(true);
        jButtona.setEnabled(true);
        jButtonb.setEnabled(true);
        jButtonc.setEnabled(true);
        jButtond.setEnabled(true);
        jButtone.setEnabled(true);
        jButtonf.setEnabled(true);
        jBidentificar.setEnabled(true);
        botonNuevaGramatica.setEnabled(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        areaProducciones = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        areaNumProducciones = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        jButtonA = new javax.swing.JButton();
        jButtonB = new javax.swing.JButton();
        jButtonD = new javax.swing.JButton();
        jButtonC = new javax.swing.JButton();
        jButtonE = new javax.swing.JButton();
        jButtonS = new javax.swing.JButton();
        jButtonLambda = new javax.swing.JButton();
        jButtonBorrar = new javax.swing.JButton();
        jButtonEnter = new javax.swing.JButton();
        jButtonFlecha = new javax.swing.JButton();
        jButton0 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButtonPard = new javax.swing.JButton();
        jButtonPari = new javax.swing.JButton();
        jButtone = new javax.swing.JButton();
        jButtonf = new javax.swing.JButton();
        jButtond = new javax.swing.JButton();
        jButtona = new javax.swing.JButton();
        jButtonc = new javax.swing.JButton();
        jButtonb = new javax.swing.JButton();
        jBidentificar = new javax.swing.JButton();
        jbGenerar = new javax.swing.JButton();
        botonNuevaGramatica = new javax.swing.JButton();
        botonSalir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        areaProducciones.setColumns(20);
        areaProducciones.setRows(5);
        areaProducciones.setText("<S> -> a<A><B><S>\n<S> -> b<C><A><C>d\n<A> -> b<A><B>\n<A> -> c<S><B>\n<A> -> d<C><C>\n<B> -> b<A><B>\n<B> -> c<S><B>\n<C> -> c<S>\n<C> -> d");
        jScrollPane1.setViewportView(areaProducciones);

        areaNumProducciones.setColumns(20);
        areaNumProducciones.setLineWrap(true);
        areaNumProducciones.setRows(5);
        areaNumProducciones.setEnabled(false);
        jScrollPane2.setViewportView(areaNumProducciones);

        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jButtonA.setText("<A>");
        jButtonA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAActionPerformed(evt);
            }
        });

        jButtonB.setText("<B>");
        jButtonB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBActionPerformed(evt);
            }
        });

        jButtonD.setText("<D>");
        jButtonD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDActionPerformed(evt);
            }
        });

        jButtonC.setText("<C>");
        jButtonC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCActionPerformed(evt);
            }
        });

        jButtonE.setText("<E>");
        jButtonE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEActionPerformed(evt);
            }
        });

        jButtonS.setText("<S>");
        jButtonS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSActionPerformed(evt);
            }
        });

        jButtonLambda.setText("λ");
        jButtonLambda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLambdaActionPerformed(evt);
            }
        });

        jButtonBorrar.setText("Borrar");
        jButtonBorrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButtonBorrarMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButtonBorrarMouseReleased(evt);
            }
        });
        jButtonBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBorrarActionPerformed(evt);
            }
        });

        jButtonEnter.setText("Enter");
        jButtonEnter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEnterActionPerformed(evt);
            }
        });

        jButtonFlecha.setText("->");
        jButtonFlecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonFlechaActionPerformed(evt);
            }
        });

        jButton0.setText("0");
        jButton0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton0ActionPerformed(evt);
            }
        });

        jButton1.setText("1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButtonPard.setText(")");
        jButtonPard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPardActionPerformed(evt);
            }
        });

        jButtonPari.setText("(");
        jButtonPari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPariActionPerformed(evt);
            }
        });

        jButtone.setText("e");
        jButtone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtoneActionPerformed(evt);
            }
        });

        jButtonf.setText("f");
        jButtonf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonfActionPerformed(evt);
            }
        });

        jButtond.setText("d");
        jButtond.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtondActionPerformed(evt);
            }
        });

        jButtona.setText("a");
        jButtona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonaActionPerformed(evt);
            }
        });

        jButtonc.setText("c");
        jButtonc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtoncActionPerformed(evt);
            }
        });

        jButtonb.setText("b");
        jButtonb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonbActionPerformed(evt);
            }
        });

        jBidentificar.setText("Identificar la grámatica");
        jBidentificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBidentificarActionPerformed(evt);
            }
        });

        jbGenerar.setText("Generar reconocedor recursivo");
        jbGenerar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbGenerarActionPerformed(evt);
            }
        });

        botonNuevaGramatica.setText("Nueva gramática");
        botonNuevaGramatica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonNuevaGramaticaActionPerformed(evt);
            }
        });

        botonSalir.setText("Salir");
        botonSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonSalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonA, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonC, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButtonLambda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonFlecha, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE))
                        .addGap(35, 35, 35))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jButtond, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtone, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jButtona, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButtonf, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButtonc, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jButtonPari, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(2, 2, 2))
                                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addGap(2, 2, 2)
                                        .addComponent(jButtonPard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(jButton0, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(9, 9, 9))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(botonNuevaGramatica)
                                .addGap(33, 33, 33)
                                .addComponent(botonSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButtonEnter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonBorrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBidentificar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbGenerar, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE))
                .addGap(28, 28, 28))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonFlecha)
                            .addComponent(jButtonBorrar))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonEnter))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonA)
                            .addComponent(jButtonB)
                            .addComponent(jButtonC))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonD)
                            .addComponent(jButtonE)
                            .addComponent(jButtonS)
                            .addComponent(jButtonLambda))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jButtonc)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButtonf))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButtona)
                                .addComponent(jButtonb))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButtond)
                                .addComponent(jButtone))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButtonPari)
                                .addComponent(jButtonPard))
                            .addComponent(jBidentificar, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jButton0)
                            .addComponent(jbGenerar))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonNuevaGramatica)
                    .addComponent(botonSalir))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonLambdaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLambdaActionPerformed
        areaProducciones.insert("λ", areaProducciones.getCaretPosition());
        areaProducciones.requestFocus();
    }//GEN-LAST:event_jButtonLambdaActionPerformed

    private void jBidentificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBidentificarActionPerformed
        String[] lines = areaProducciones.getText().split("\\n");
        boolean tipoS = true;
        llenarVentana();
        for (int i = 0; i < lines.length; i++) {
            System.out.println("Produccion :" + (i + 1));
            if (!esTipoS(lines[i], (i + 1))) {
                tipoS = false;
                break;
            }
        }
        guardarTodo();
        guardarNoTerminalesVivos();
        if (tipoS && validarNoTerminalesDer() && diferenteTerminalDer() && eliminarNoTerminalesMuertos()) {
            JOptionPane.showMessageDialog(this, "La gramatica es tipo S");
            desactivaBotones();
        } else {
            JOptionPane.showMessageDialog(this, "La gramatica no es tipo S");
        }
    }//GEN-LAST:event_jBidentificarActionPerformed

    private void jButtonFlechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonFlechaActionPerformed
        areaProducciones.insert(" -> ", areaProducciones.getCaretPosition());
        areaProducciones.requestFocus();
    }//GEN-LAST:event_jButtonFlechaActionPerformed

    private void jButtonaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonaActionPerformed
        areaProducciones.insert("a", areaProducciones.getCaretPosition());
        areaProducciones.requestFocus();
    }//GEN-LAST:event_jButtonaActionPerformed

    private void jButtonbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonbActionPerformed
        areaProducciones.insert("b", areaProducciones.getCaretPosition());
        areaProducciones.requestFocus();
    }//GEN-LAST:event_jButtonbActionPerformed

    private void jButtoncActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtoncActionPerformed
        areaProducciones.insert("c", areaProducciones.getCaretPosition());
        areaProducciones.requestFocus();
    }//GEN-LAST:event_jButtoncActionPerformed

    private void jButtondActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtondActionPerformed
        areaProducciones.insert("d", areaProducciones.getCaretPosition());
        areaProducciones.requestFocus();
    }//GEN-LAST:event_jButtondActionPerformed

    private void jButtoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtoneActionPerformed
        areaProducciones.insert("e", areaProducciones.getCaretPosition());
        areaProducciones.requestFocus();
    }//GEN-LAST:event_jButtoneActionPerformed

    private void jButtonfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonfActionPerformed
        areaProducciones.insert("f", areaProducciones.getCaretPosition());
        areaProducciones.requestFocus();
    }//GEN-LAST:event_jButtonfActionPerformed

    private void jButtonEnterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEnterActionPerformed
        areaProducciones.insert("\n", areaProducciones.getCaretPosition());
        areaProducciones.requestFocus();
    }//GEN-LAST:event_jButtonEnterActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        areaProducciones.insert("1", areaProducciones.getCaretPosition());
        areaProducciones.requestFocus();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton0ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton0ActionPerformed
        areaProducciones.insert("0", areaProducciones.getCaretPosition());
        areaProducciones.requestFocus();
    }//GEN-LAST:event_jButton0ActionPerformed

    private void jButtonBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBorrarActionPerformed

    }//GEN-LAST:event_jButtonBorrarActionPerformed

    private void jButtonAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAActionPerformed
        areaProducciones.insert("<A>", areaProducciones.getCaretPosition());
        areaProducciones.requestFocus();
    }//GEN-LAST:event_jButtonAActionPerformed

    private void jButtonBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBActionPerformed
        areaProducciones.insert("<B>", areaProducciones.getCaretPosition());
        areaProducciones.requestFocus();
    }//GEN-LAST:event_jButtonBActionPerformed

    private void jButtonSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSActionPerformed
        areaProducciones.insert("<S>", areaProducciones.getCaretPosition());
        areaProducciones.requestFocus();
    }//GEN-LAST:event_jButtonSActionPerformed

    private void jButtonCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCActionPerformed
        areaProducciones.insert("<C>", areaProducciones.getCaretPosition());
        areaProducciones.requestFocus();
    }//GEN-LAST:event_jButtonCActionPerformed

    private void jButtonDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDActionPerformed
        areaProducciones.insert("<D>", areaProducciones.getCaretPosition());
        areaProducciones.requestFocus();
    }//GEN-LAST:event_jButtonDActionPerformed

    private void jButtonEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEActionPerformed
        areaProducciones.insert("<E>", areaProducciones.getCaretPosition());
        areaProducciones.requestFocus();
    }//GEN-LAST:event_jButtonEActionPerformed

    private void jButtonBorrarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonBorrarMousePressed
        int caret = areaProducciones.getCaretPosition();
        if (caret != 0) {
            areaProducciones.setText(areaProducciones.getText().substring(0, caret - 1) + areaProducciones.getText().substring(caret, areaProducciones.getText().length()));
            areaProducciones.setCaretPosition(caret - 1);
        }
    }//GEN-LAST:event_jButtonBorrarMousePressed

    private void jButtonBorrarMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonBorrarMouseReleased
        areaProducciones.requestFocus();
    }//GEN-LAST:event_jButtonBorrarMouseReleased

    private void jButtonPariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPariActionPerformed
        areaProducciones.insert("(", areaProducciones.getCaretPosition());
        areaProducciones.requestFocus();
    }//GEN-LAST:event_jButtonPariActionPerformed

    private void jButtonPardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPardActionPerformed
        areaProducciones.insert(")", areaProducciones.getCaretPosition());
        areaProducciones.requestFocus();
    }//GEN-LAST:event_jButtonPardActionPerformed

    private void jbGenerarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbGenerarActionPerformed

        try {
            genReconocedorRecursivo();
            so = System.getProperty("os.name").replaceAll(" ", "");
            directorioActual = System.getProperty("user.dir");
            String ruta = "";
            if (so.charAt(0) == 'W' || so.charAt(0) == 'w') {
                ruta = directorioActual + "\\Reconocedor.java";
                String cmd = directorioActual + "\\scriptWindows.bat";
                Runtime.getRuntime().exec(cmd);

            } else if (so.charAt(0) == 'L' || so.charAt(0) == 'l') {
                ruta = directorioActual + "/Reconocedor.java";
                String[] cmd = {"sh", directorioActual + "/scriptLinux.sh"};
                Runtime.getRuntime().exec(cmd);
            } else {
                ruta = directorioActual + "Reconocedor.java";
                String[] cmd = {"sh", directorioActual + "script.sh"};
                Runtime.getRuntime().exec(cmd);
            }

            JOptionPane.showMessageDialog(null, "Se ha generado un archivo en: " + ruta,
                    "Satisfactorio", JOptionPane.INFORMATION_MESSAGE);
            botonNuevaGramatica.setEnabled(true);
        } catch (IOException ex) {
            Logger.getLogger(Entrada.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jbGenerarActionPerformed

    private void botonSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonSalirActionPerformed
        this.dispose();
    }//GEN-LAST:event_botonSalirActionPerformed

    private void botonNuevaGramaticaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonNuevaGramaticaActionPerformed
        renovar();
    }//GEN-LAST:event_botonNuevaGramaticaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
        }
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new Entrada().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea areaNumProducciones;
    private javax.swing.JTextArea areaProducciones;
    private javax.swing.JButton botonNuevaGramatica;
    private javax.swing.JButton botonSalir;
    private javax.swing.JButton jBidentificar;
    private javax.swing.JButton jButton0;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonA;
    private javax.swing.JButton jButtonB;
    private javax.swing.JButton jButtonBorrar;
    private javax.swing.JButton jButtonC;
    private javax.swing.JButton jButtonD;
    private javax.swing.JButton jButtonE;
    private javax.swing.JButton jButtonEnter;
    private javax.swing.JButton jButtonFlecha;
    private javax.swing.JButton jButtonLambda;
    private javax.swing.JButton jButtonPard;
    private javax.swing.JButton jButtonPari;
    private javax.swing.JButton jButtonS;
    private javax.swing.JButton jButtona;
    private javax.swing.JButton jButtonb;
    private javax.swing.JButton jButtonc;
    private javax.swing.JButton jButtond;
    private javax.swing.JButton jButtone;
    private javax.swing.JButton jButtonf;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton jbGenerar;
    // End of variables declaration//GEN-END:variables
}
