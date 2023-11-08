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
    public void inserirCurso (Curso curso) throws Exception {
        String sql = "INSERT INTO tb_curso (nome, tipo) VALUES (?, ?)";
        try (Connection conn = ConexaoBD.obtemConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, curso.getNome());
            ps.setString(2, curso.getTipo());
            ps.execute();
        }
    }
    public void atualizarCurso (Curso curso) throws Exception{
        String sql = "UPDATE tb_curso SET nome = ?, tipo = ? WHERE id = ?";
        try (Connection conn = ConexaoBD.obtemConexao();
              PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, curso.getNome());
            ps.setString(2, curso.getTipo());
            ps.setInt(3, curso.getId());
            ps.execute();
        }
    }
}
