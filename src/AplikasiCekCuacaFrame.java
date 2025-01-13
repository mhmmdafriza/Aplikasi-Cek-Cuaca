import javax.swing.JOptionPane; // Untuk menampilkan pesan dialog
import javax.swing.table.DefaultTableModel; // Untuk pengaturan model tabel jika menggunakan DefaultTableModel
import java.io.FileWriter; // Untuk menulis data ke file
import java.io.IOException; // Untuk menangani exception saat menulis ke file
import org.json.JSONObject;
import org.json.JSONArray;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.IOException;
import java.awt.event.ItemEvent;
import java.io.FileReader;
import java.util.HashMap;
import javax.swing.table.DefaultTableModel;



public class AplikasiCekCuacaFrame extends javax.swing.JFrame {

    // HashMap untuk menyimpan kota dan frekuensi pengecekan
    private HashMap<String, Integer> kotaCheckCount = new HashMap<>();
    public AplikasiCekCuacaFrame() {
        initComponents();
        loadWeatherDataFromFile();
    }
    
     private String getWeatherData(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        
        in.close();
        conn.disconnect();
        return content.toString();
    }
     
     private void addCityToFavoritesIfFrequent(String kota) {
    // Perbarui hitungan pengecekan kota
    int count = kotaCheckCount.getOrDefault(kota, 0) + 1;
    kotaCheckCount.put(kota, count);

    // Tambahkan kota ke favorit jika sudah dicek lebih dari 3 kali dan belum ada di ComboBox
    if (count >= 3 && !isCityInComboBox(kota)) {
        lokasiComboBox.addItem(kota);
    }
}

    // Method untuk mengecek apakah kota sudah ada di ComboBox
    private boolean isCityInComboBox(String kota) {
    for (int i = 0; i < lokasiComboBox.getItemCount(); i++) {
        if (lokasiComboBox.getItemAt(i).equalsIgnoreCase(kota)) {
            return true;
        }
    }
    return false;
}
    
     private void loadWeatherDataFromFile() {
    DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
    
    // Set header kolom jika belum ada
    if (model.getColumnCount() == 0) {
        model.addColumn("Kota");
        model.addColumn("Cuaca");
        model.addColumn("Suhu (°C)");
    }
    
    // Bersihkan data lama di JTable (opsional)
    model.setRowCount(0);

    // Baca data dari CSV dan tambahkan ke JTable
    try (BufferedReader br = new BufferedReader(new FileReader("weatherData.csv"))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            if (data.length == 3) { // Pastikan data ada 3 kolom
                // Terjemahkan deskripsi cuaca ke Bahasa Indonesia
                String kondisiCuaca = translateWeatherDescription(data[1]);
                model.addRow(new Object[]{data[0], kondisiCuaca, data[2]});
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Gagal memuat data dari file CSV.");
    }
}

     private String translateWeatherDescription(String description) {
    switch (description.toLowerCase()) {
        case "clear sky":
            return "cerah";
        case "few clouds":
            return "berawan sebagian";
        case "scattered clouds":
            return "awan tersebar";
        case "broken clouds":
            return "awan terputus";
        case "shower rain":
            return "hujan rintik-rintik";
        case "rain":
            return "hujan";
        case "thunderstorm":
            return "badai petir";
        case "snow":
            return "salju";
        case "mist":
            return "kabut";
        default:
            return description; // Kembalikan deskripsi asli jika tidak cocok
    }
}
     
     private void setWeatherIcon(String kondisiCuaca) {
    String iconPath = "";
    
    switch (kondisiCuaca.toLowerCase()) {
        case "cerah":
        case "langit cerah":
            iconPath = "/icons/cerah.png";
            break;
        case "berawan sebagian":
        case "awan tersebar":
        case "awan terputus":
        case "sedikit berawan":
        case "awan mendung":
        case "awan pecah":
            iconPath = "/icons/cloudy.png";
            break;
        case "hujan":
        case "hujan rintik-rintik":
            iconPath = "/icons/rain.png";
            break;
        case "badai petir":
            iconPath = "/icons/thunderstorm.png";
            break;
        case "kabut":
        case "kabut asap":
            iconPath = "/icons/mist.png";
            break;
        default:
            iconPath = "/icons/default.png"; // Ikon default jika kondisi tidak dikenali
    }
    
    // Set ikon cuaca ke iconLabel jika file gambar ditemukan
    java.net.URL imgURL = getClass().getResource(iconPath);
    if (imgURL != null) {
        iconLabel.setIcon(new javax.swing.ImageIcon(imgURL));
    } else {
        System.err.println("Gambar ikon cuaca tidak ditemukan: " + iconPath);
    }
}
     


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cuacaLabel = new javax.swing.JLabel();
        cekCuacaButton = new javax.swing.JButton();
        lokasiComboBox = new javax.swing.JComboBox<>();
        kotaTextField = new javax.swing.JTextField();
        simpanButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        dataTable = new javax.swing.JTable();
        iconLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Aplikasi Cek Cuaca ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 36))); // NOI18N

        jLabel1.setText("Masukan Kota");

        cuacaLabel.setText("Hasil");

        cekCuacaButton.setText("Cek Cuaca");
        cekCuacaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cekCuacaButtonActionPerformed(evt);
            }
        });

        lokasiComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                lokasiComboBoxItemStateChanged(evt);
            }
        });

        simpanButton.setText("Simpan Data");
        simpanButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simpanButtonActionPerformed(evt);
            }
        });

        dataTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Kota", "Cuaca", "Suhu"
            }
        ));
        jScrollPane1.setViewportView(dataTable);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 525, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 105, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(kotaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52)
                        .addComponent(lokasiComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cekCuacaButton)
                        .addGap(91, 91, 91)
                        .addComponent(simpanButton, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cuacaLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(iconLabel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(kotaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lokasiComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cekCuacaButton)
                    .addComponent(simpanButton))
                .addGap(30, 30, 30)
                .addComponent(cuacaLabel)
                .addGap(54, 54, 54)
                .addComponent(iconLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 78, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cekCuacaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cekCuacaButtonActionPerformed

    String kota = kotaTextField.getText();
    String apiKey = "d299c889131272a459a8a8d050ea7859"; // Ganti dengan API key Anda
    String url = "http://api.openweathermap.org/data/2.5/weather?q=" + kota + "&appid=" + apiKey + "&units=metric&lang=id";
    
    try {
        // Mengambil data dari API
        String response = getWeatherData(url);
        JSONObject jsonResponse = new JSONObject(response);
        
        // Parsing data dari JSON
        String kondisiCuaca = jsonResponse.getJSONArray("weather").getJSONObject(0).getString("description");
        kondisiCuaca = translateWeatherDescription(kondisiCuaca); // Terjemahkan jika perlu
        double suhu = jsonResponse.getJSONObject("main").getDouble("temp");
        
        // Menampilkan hasil dalam Bahasa Indonesia
        cuacaLabel.setText("Cuaca: " + kondisiCuaca + ", Suhu: " + suhu + "°C");
        
        // Atur ikon cuaca
        setWeatherIcon(kondisiCuaca);
        
        // Tambahkan kota ke favorit jika sudah dicek beberapa kali
        addCityToFavoritesIfFrequent(kota);
        
    } catch (Exception e) {
        cuacaLabel.setText("Kota tidak ditemukan atau error API.");
    }

    }//GEN-LAST:event_cekCuacaButtonActionPerformed

    private void lokasiComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_lokasiComboBoxItemStateChanged
         if (evt.getStateChange() == ItemEvent.SELECTED) {
            String selectedCity = (String) lokasiComboBox.getSelectedItem();
            kotaTextField.setText(selectedCity);
        }
    }//GEN-LAST:event_lokasiComboBoxItemStateChanged

    private void simpanButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpanButtonActionPerformed
        String kota = kotaTextField.getText();
    String apiKey = "d299c889131272a459a8a8d050ea7859"; // Ganti dengan API key Anda
    String url = "http://api.openweathermap.org/data/2.5/weather?q=" + kota + "&appid=" + apiKey + "&units=metric";
    
    try {
        // Mengambil data dari API
        String response = getWeatherData(url);
        JSONObject jsonResponse = new JSONObject(response);
        
        // Parsing data dari JSON
        String kondisiCuaca = jsonResponse.getJSONArray("weather").getJSONObject(0).getString("description");
        double suhu = jsonResponse.getJSONObject("main").getDouble("temp");
        
        // Menampilkan hasil
        cuacaLabel.setText("Cuaca: " + kondisiCuaca + ", Suhu: " + suhu + "°C");

        // Tambahkan kota ke daftar favorit jika belum ada
        boolean kotaSudahAda = false;
        for (int i = 0; i < lokasiComboBox.getItemCount(); i++) {
            if (lokasiComboBox.getItemAt(i).equalsIgnoreCase(kota)) {
                kotaSudahAda = true;
                break;
            }
        }
        if (!kotaSudahAda) {
            lokasiComboBox.addItem(kota); // Tambahkan kota ke JComboBox
        }

        // Menyimpan data ke file CSV
        try (FileWriter writer = new FileWriter("weatherData.csv", true)) {
            writer.append(kota).append(",").append(kondisiCuaca).append(",").append(String.valueOf(suhu)).append("\n");
            JOptionPane.showMessageDialog(this, "Data berhasil disimpan ke weatherData.csv.");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal menyimpan data ke file CSV.");
        }
        
    } catch (Exception e) {
        cuacaLabel.setText("Kota tidak ditemukan atau error API.");
    }
    }//GEN-LAST:event_simpanButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AplikasiCekCuacaFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AplikasiCekCuacaFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AplikasiCekCuacaFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AplikasiCekCuacaFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AplikasiCekCuacaFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cekCuacaButton;
    private javax.swing.JLabel cuacaLabel;
    private javax.swing.JTable dataTable;
    private javax.swing.JLabel iconLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField kotaTextField;
    private javax.swing.JComboBox<String> lokasiComboBox;
    private javax.swing.JButton simpanButton;
    // End of variables declaration//GEN-END:variables
}
