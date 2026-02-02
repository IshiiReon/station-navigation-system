package servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.StationDao;
import model.StationEdge;
import model.StationNode;
import service.RouteResult;
import service.RouteService;

@WebServlet("/station")  // サーブレット有効化
public class StationResultServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        // サーバ起動時に DB アクセスはしない
        //System.out.println("StationResultServlet initialized safely");
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        int start = -1;
        int goal  = -1;

        // =========================
        // パラメータ取得と安全チェック
        // =========================
        try {
            start = Integer.parseInt(request.getParameter("start"));
            goal  = Integer.parseInt(request.getParameter("goal"));
        } catch (NumberFormatException e) {
            request.setAttribute("error", "start または goal が無効です。");
            request.getRequestDispatcher("/WEB-INF/jsp/result.jsp")
                   .forward(request, response);
            return;
        }
        
        

        // =========================
        // DAOでデータ取得
        // =========================
        StationDao dao = new StationDao();
        List<StationEdge> edges;
        Map<Integer, StationNode> nodeMap;
        try {
            edges = dao.findAllEdges();
            nodeMap = dao.findNodeMap();
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "駅情報の取得に失敗しました。");
            request.getRequestDispatcher("/WEB-INF/jsp/result.jsp")
                   .forward(request, response);
            return;
        }
        
        // =========================
        // 経路探索
        // =========================
        RouteService service = new RouteService();
        RouteResult result = service.dijkstraWithPath(edges, start, goal);

        if (result.getDistance() < 0) {
            request.setAttribute("error", "経路が見つかりませんでした。");
            request.getRequestDispatcher("/WEB-INF/jsp/result.jsp")
                   .forward(request, response);
            return;
        }

        // =========================
        // 文章生成
        // =========================
        List<Integer> path = result.getPath();
        
        StationNode startNode = nodeMap.get(path.get(0));
        StationNode goalNode  = nodeMap.get(path.get(path.size() - 1));
        
        List<String> routeTexts = service.routeText(result.getPath(), nodeMap);
        
        List<StationEdge> routeEdges =
                service.pathEdges(path, edges);
        

        // =========================
        // JSPへ渡す
        // =========================
        request.setAttribute("distance", result.getDistance());
        request.setAttribute("path", result.getPath());
        request.setAttribute("routeTexts", routeTexts);
        request.setAttribute("routeEdges", routeEdges);
        request.setAttribute("startName", startNode.getName());
        request.setAttribute("goalName", goalNode.getName());
        

        request.getRequestDispatcher("/WEB-INF/jsp/result.jsp")
               .forward(request, response);
    }
}
