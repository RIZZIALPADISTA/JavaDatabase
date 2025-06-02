import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Mahasiswa {
    String sql;
    String url = "jdbc:mysql://localhost:3306/kuliah";
    String user = "root";
    String password = "";
    Connection koneksi;
   
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public void readMahasiswa() {
        sql = "SELECT * FROM mahaiswa";
        try {
            koneksi = getConnection();
            Statement st = koneksi.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                System.out.print(rs.getString(1) + "|");
                System.out.print(rs.getString(2) + "|");
                System.out.print(rs.getString(3) + "|");
                System.out.print(rs.getString(4) + "|\n");
                }
            } catch (SQLException e) {
                e.printStackTrace();
        }
    }
    public void insertMahasiswa(String nim, String nama, String alamat, String gender) {
        sql = "INSERT INTO mahaiswa (NIM, NAMA, ALAMAT, JENIS_KELAMIN) VALUES ( ?, ?, ?, ?)";
        boolean validasi = true;
        if(nim.length() < 11) {
            System.out.println("NIM harus 11 karakter");
            validasi = false;
        }
        if(nama.length() < 2) {
            System.out.println("NAMA harus lebih dari 2 karakter");
            validasi = false;
        }
        if(alamat.length() < 5) {
            System.out.println("ALAMAT harus lebih dari 5 karakter");
            validasi = false;
        }
        // ENUM HARUS SAMA DENGAN VALUE DI DATABASE NYA
        if(gender != "L" && gender != "P") {
            System.out.println("JENIS KELAMIN harus L atau P");
            validasi = false;
        }
        if(validasi == true) {
            try {
                koneksi = getConnection();
                PreparedStatement ps = koneksi.prepareStatement(sql);
                ps.setString(1, nim);
                ps.setString(2, nama);
                ps.setString(3, alamat);
                ps.setString(4, gender);

                int result = ps.executeUpdate();
                // Mengecek apakah data berhasil disimpan
                if (result > 0) {
                    System.out.println("Data berhasil disimpan");
                } else {
                    System.out.println("Data gagal disimpan");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
