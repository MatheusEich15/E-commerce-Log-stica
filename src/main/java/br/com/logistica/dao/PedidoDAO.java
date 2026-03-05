package br.com.logistica.dao;

import br.com.logistica.dto.BuscarPedidoCPFDTO;
import br.com.logistica.dto.RelatorioPedidoEstadoDTO;
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

    public void atualizarPedido(Pedido pedido) throws SQLException{
        String command = """
                UPDATE Pedido
                SET
                status = ?
                WHERE id = ?;
                """;
        try (Connection conn = Conexao.conectar()) {
            PreparedStatement stmt = conn.prepareStatement(command);
            stmt.setString(1, pedido.getStatus());
            stmt.setInt(2, pedido.getId());

            stmt.executeUpdate();
        }
    }

    public List<RelatorioPedidoEstadoDTO> listarPedidoEstado() throws SQLException{
        List<RelatorioPedidoEstadoDTO> relatorioPedidoEstadoDTOS = new ArrayList<>();
        String command = """
                SELECT Cliente.estado, COUNT(Pedido.id)
                FROM Pedido
                JOIN Cliente ON Pedido.cliente_id = Cliente.id
                WHERE Pedido.status = 'PENDENTE'
                GROUP BY Cliente.estado
                ORDER BY COUNT(Pedido.id) DESC;
                """;
        try (Connection conn = Conexao.conectar()) {
            PreparedStatement stmt = conn.prepareStatement(command);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                relatorioPedidoEstadoDTOS.add(new RelatorioPedidoEstadoDTO(
                    rs.getString("estado"),
                    rs.getInt("COUNT(Pedido.id)")
                ));
            }
        }
        return relatorioPedidoEstadoDTOS;
    }

    public List<BuscarPedidoCPFDTO> buscarPedidoCPF(String CPF) throws SQLException{
        List<BuscarPedidoCPFDTO> buscarPedidoCPFDTOS = new ArrayList<>();
        String command = """
                SELECT
                Cliente.nome,
                Cliente.cpf_cnpj,
                Pedido.id,
                Pedido.data_pedido,
                Pedido.volume_m3,
                Pedido.peso_kg,
                Pedido.status
                FROM Pedido
                JOIN Cliente ON Pedido.cliente_id = Cliente.id
                WHERE Cliente.cpf_cnpj LIKE ?
                """;
        try (Connection conn = Conexao.conectar()) {
            PreparedStatement stmt = conn.prepareStatement(command);
            stmt.setString(1, "%" + CPF + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                buscarPedidoCPFDTOS.add(new BuscarPedidoCPFDTO(
                    rs.getString("nome"),
                    rs.getString("cpf_cnpj"),
                    rs.getInt("id"),
                    rs.getTimestamp("data_pedido"),
                    rs.getDouble("volume_m3"),
                    rs.getInt("peso_kg"),
                    rs.getString("status")
                ));
            }
        }
        return buscarPedidoCPFDTOS;
    }
}