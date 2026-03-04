package br.com.logistica.dao;

import br.com.logistica.model.HistoricoEntrega;
import br.com.logistica.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HistoricoEntregaDAO {
    public void criarHistoricoEntrega(HistoricoEntrega historicoEntrega) throws SQLException {
        String command = """
                INSERT INTO HistoricoEntrega
                (entrega_id, data_evento, descricao)
                VALUES
                (?, ?, ?);
                """;
        try (Connection conn = Conexao.conectar()){
            PreparedStatement stmt = conn.prepareStatement(command);
            stmt.setInt(1, historicoEntrega.getEntregaId());
            stmt.setTimestamp(2, historicoEntrega.getDataEvento());
            stmt.setString(3, historicoEntrega.getDescricao());

            stmt.executeUpdate();
        }
    }
}
