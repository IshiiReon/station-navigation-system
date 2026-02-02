package dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import model.StationEdge;
import model.StationNode;

public class StationDao {

    private static String JDBC_URL;
    private static String DB_USER;
    private static String DB_PASS;

    static {
        try {
            Class.forName("org.h2.Driver");

            Properties prop = new Properties();
            InputStream is =
                StationDao.class.getResourceAsStream("/db.properties");

            if (is == null) {
                throw new RuntimeException("db.properties が見つかりません");
            }

            prop.load(is);

            JDBC_URL = prop.getProperty("db.url");
            DB_USER  = prop.getProperty("db.user");
            DB_PASS  = prop.getProperty("db.password");

        } catch (Exception e) {
            throw new RuntimeException("DB設定の読み込みに失敗しました", e);
        }
    }




    /**
     * station_node を全件取得
     */
    public List<StationNode> findAllNodes() {

        List<StationNode> list = new ArrayList<>();

        String sql = "SELECT node_id, name, floor, type,node_code FROM station_node ORDER BY node_id";

        

            try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
                 PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    StationNode n = new StationNode(
                            rs.getInt("node_id"),
                            rs.getString("name"),
                            rs.getString("floor"),
                            rs.getString("type"),
                            rs.getString("node_code")
                    );
                    list.add(n);
                }
            }

         catch (Exception e) {
            throw new RuntimeException("DBアクセスエラー", e);
        }


        return list;
    }
    
    public List<StationEdge> findAllEdges() {

        List<StationEdge> list = new ArrayList<>();

        String sql = "SELECT from_node, to_node, distance,IMAGEPATH FROM station_edge ORDER BY edge_id";

        

            try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
                 PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    StationEdge e = new StationEdge(
                            rs.getInt("from_node"),
                            rs.getInt("to_node"),
                            rs.getInt("distance"),
                            rs.getString("IMAGEPATH")
                    );
                    list.add(e);
                }
            }

         catch (Exception e) {
            throw new RuntimeException("DBアクセスエラー", e);
        }

        return list;
    }
    
    public List<StationNode> findGateNodes() {

        List<StationNode> list = new ArrayList<>();
        String sql = """
            SELECT node_id, name, floor, type, node_code
            FROM station_node
            WHERE type = 'gate'
            ORDER BY node_id
        """;

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new StationNode(
                    rs.getInt("node_id"),
                    rs.getString("name"),
                    rs.getString("floor"),
                    rs.getString("type"),
                    rs.getString("node_code")
                ));
            }
        } catch (Exception e) {
            throw new RuntimeException("DBアクセスエラー", e);
        }

        return list;
    }

    public Map<Integer, StationNode> findNodeMap() {

        Map<Integer, StationNode> map = new HashMap<>();

        String sql = "SELECT node_id, name, floor, type, node_code FROM station_node";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                StationNode n = new StationNode(
                    rs.getInt("node_id"),
                    rs.getString("name"),
                    rs.getString("floor"),
                    rs.getString("type"),
                    rs.getString("node_code")
                );
                map.put(n.getNodeId(), n);
            }

        } catch (Exception e) {
            throw new RuntimeException("DBアクセスエラー", e);
        }


        return map;
    }


}
