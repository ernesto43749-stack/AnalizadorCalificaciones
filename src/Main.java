import javax.swing.JFileChooser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static void main(String[] args) {

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecciona tu archivo de calificaciones");
        int resultado = fileChooser.showOpenDialog(null);

        if (resultado != JFileChooser.APPROVE_OPTION) {
            System.out.println("No se seleccionó ningún archivo.");
            return;
        }

        File archivo = fileChooser.getSelectedFile();
        List<String> nombres = new ArrayList<>();
        List<Integer> calificaciones = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(archivo));
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue;
                if (linea.toLowerCase().contains("nombre")) continue;

                String[] partes = linea.split("[,; ]+");
                if (partes.length < 2) continue;

                nombres.add(partes[0]);
                calificaciones.add(Integer.parseInt(partes[1]));
            }
            br.close();

            int suma = 0, max = calificaciones.get(0), min = calificaciones.get(0);
            int indexMax = 0, indexMin = 0, aprobados = 0, reprobados = 0;

            for (int i = 0; i < calificaciones.size(); i++) {
                int cal = calificaciones.get(i);
                suma += cal;
                if (cal > max) {
                    max = cal;
                    indexMax = i;
                }
                if (cal < min) {
                    min = cal;
                    indexMin = i;
                }
                if (cal >= 70) aprobados++;
                else reprobados++;
            }

            double promedio = (double) suma / calificaciones.size();

            System.out.println("--- Analizador de Calificaciones ---");
            System.out.println("Archivo: " + archivo.getName());
            System.out.println("Promedio general: " + promedio);
            System.out.println("Mejor estudiante: " + nombres.get(indexMax) + " con " + max);
            System.out.println("Peor estudiante: " + nombres.get(indexMin) + " con " + min);
            System.out.println("Aprobados calificación igual o superior a (≥70) : " + aprobados);
            System.out.println("Reprobados calificación menor a 70: " + reprobados);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}