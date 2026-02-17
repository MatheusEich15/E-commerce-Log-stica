package br.com.logistica.dao;

import br.com.logistica.model.Pedido;
import br.com.logistica.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PedidoDAO {
    public void criarPedido(Pedido pedido) throws SQLException {
        String command = """
                INSERT INTO Pedido
                (cliente_id, data_pedido, volume_m3, peso_kg, status)
                VALUES
                (?, ?, ?, ?, ?);
                """;
        try (Connection conn = Conexao.conectar()){
            PreparedStatement stmt = conn.prepareStatement(command);
            stmt.setInt(1, pedido.getClienteId());
            stmt.setTimestamp(2, pedido.getDataPedido());
            stmt.setDouble(3, pedido.getVolumeM3());
            stmt.setInt(4, pedido.getPesoKg());
            stmt.setString(5, pedido.getStatus());

            stmt.executeUpdate();
        }
    }
}