package br.com.logistica.dao;

import br.com.logistica.model.Pedido;
import br.com.logistica.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public List<Pedido> listarPedidos() throws SQLException {
        List<Pedido> pedidos = new ArrayList<>();
        String command = """
                SELECT
                id,
                cliente_id,
                data_pedido,
                volume_m3,
                peso_kg,
                status
                FROM Pedido
                """;
        try (Connection conn = Conexao.conectar()){
            PreparedStatement stmt = conn.prepareStatement(command);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                pedidos.add(new Pedido(
                        rs.getInt("id"),
                        rs.getInt("cliente_id"),
                        rs.getTimestamp("data_pedido"),
                        rs.getDouble("volume_m3"),
                        rs.getInt("peso_kg"),
                        rs.getString("status")
                ));
            }
        }
        return pedidos;
    }
}