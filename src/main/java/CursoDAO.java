import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CursoDAO {
    public Curso[] obterCursos () throws Exception {
        String sql = "SELECT * FROM tb_curso";
        try(Connection conn = ConexaoBD.obtemConexao();
            PreparedStatement ps = conn.prepareStatement(sql, 
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
                ResultSet rs = ps.executeQuery()) {
            int totalCursos = 0;
            if (rs.last()){
                totalCursos = rs.getRow();
            }
            /*
            int totalCursos = rs.last()? rs.getRow(): 0;
            */
            Curso[] cursos = new Curso[totalCursos];
            //voltar ao topo do rs
            rs.beforeFirst();
            int cont = 0;
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String tipo = rs.getString("tipo");
                Curso c = new Curso(id, nome, tipo);
                cursos[cont++] = c;
            }
            return cursos;
        }
    }
}
