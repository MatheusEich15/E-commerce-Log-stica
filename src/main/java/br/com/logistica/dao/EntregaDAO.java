package br.com.logistica.dao;

import br.com.logistica.dto.EntregaDetalhadaDTO;
import br.com.logistica.dto.RelatorioEntregaMotoristaDTO;
import br.com.logistica.dto.RelatorioEntregasAtrasadasDTO;
import br.com.logistica.model.Entrega;
import br.com.logistica.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EntregaDAO {
    public void criarEntrega(Entrega entrega) throws SQLException {
        String command = """
                INSERT INTO Entrega
                (pedido_id, motorista_id, data_saida, status)
                VALUES
                (?, ?, ?, ?);
                """;
        try (Connection conn = Conexao.conectar()){
            PreparedStatement stmt = conn.prepareStatement(command);
            stmt.setInt(1, entrega.getPedidoId());
            stmt.setInt(2, entrega.getMotoristaId());
            stmt.setTimestamp(3, entrega.getDataSaida());
            stmt.setString(4, entrega.getStatus());

            stmt.executeUpdate();
        }
    }

    public List<Entrega> listarEntregas() throws SQLException {
        List<Entrega> entregas = new ArrayList<>();
        String command = """
                SELECT
                id,
                pedido_id,
                motorista_id,
                data_saida,
                data_entrega,
                status
                FROM Entrega
                """;
        try (Connection conn = Conexao.conectar()) {
            PreparedStatement stmt = conn.prepareStatement(command);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                entregas.add(new Entrega(
                        rs.getInt("id"),
                        rs.getInt("pedido_id"),
                        rs.getInt("motorista_id"),
                        rs.getTimestamp("data_saida"),
                        rs.getTimestamp("data_entrega"),
                        rs.getString("status")
                ));
            }
        }
        return entregas;
    }

    public void atualizarEntrega(Entrega entrega) throws SQLException{
        String command = """
                UPDATE Entrega
                SET
                data_entrega = ?,
                status = ?
                WHERE id = ?
                """;
        try (Connection conn = Conexao.conectar()){
            PreparedStatement stmt = conn.prepareStatement(command);
            stmt.setTimestamp(1, entrega.getDataEntrega());
            stmt.setString(2, entrega.getStatus());
            stmt.setInt(3, entrega.getId());

            stmt.executeUpdate();
        }
    }

    public List<EntregaDetalhadaDTO> listarEntregaClienteMotorista() throws SQLException{
        List<EntregaDetalhadaDTO> entregaDetalhadaDTOS = new ArrayList<>();
        String command = """
                SELECT
                Entrega.id,
                pedido_id,
                Cliente.nome,
                Motorista.nome,
                data_saida,
                data_entrega,
                Entrega.status
                FROM Entrega
                join Pedido on Entrega.pedido_id = Pedido.id
                join Cliente on Pedido.cliente_id = Cliente.id
                join Motorista on Entrega.motorista_id = Motorista.id;
                """;
        try (Connection conn = Conexao.conectar()) {
            PreparedStatement stmt = conn.prepareStatement(command);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                entregaDetalhadaDTOS.add(new EntregaDetalhadaDTO(
                        rs.getInt("Entrega.id"),
                        rs.getInt("pedido_id"),
                        rs.getString("Cliente.nome"),
                        rs.getString("Motorista.nome"),
                        rs.getTimestamp("data_saida"),
                        rs.getTimestamp("data_entrega"),
                        rs.getString("Entrega.status")
                ));
            }
        }
        return entregaDetalhadaDTOS;
    }

    public List<RelatorioEntregasAtrasadasDTO> listarEntregasAtrasadas() throws SQLException{
        List<RelatorioEntregasAtrasadasDTO> relatorioEntregasAtrasadasDTOS = new ArrayList<>();
        String command = """
                SELECT Cliente.cidade, COUNT(Entrega.id)
                FROM Entrega
                JOIN Pedido ON Entrega.pedido_id = Pedido.id
                JOIN Cliente ON Pedido.cliente_id = Cliente.id
                WHERE Entrega.status = 'ATRASADA'
                GROUP BY Cliente.cidade
                ORDER BY COUNT(Entrega.id) DESC
                """;
        try (Connection conn = Conexao.conectar()) {
            PreparedStatement stmt = conn.prepareStatement(command);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                relatorioEntregasAtrasadasDTOS.add(new RelatorioEntregasAtrasadasDTO(
                    rs.getString("cidade"),
                    rs.getInt("COUNT(Entrega.id)")
                ));
            }
        }
        return relatorioEntregasAtrasadasDTOS;
    }
}
