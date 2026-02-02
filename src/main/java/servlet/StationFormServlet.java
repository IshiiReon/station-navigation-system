package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.StationDao;
import model.StationNode;

/**
 * 経路検索フォーム表示用サーブレット
 * URL: /stationForm
 */
@WebServlet("/stationForm")
public class StationFormServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        StationDao dao = new StationDao();
        List<StationNode> allNodes = dao.findAllNodes();

        List<StationNode> startNodes = new ArrayList<>();
        List<StationNode> goalNodes  = new ArrayList<>();

        for (StationNode n : allNodes) {

            // 出発：ホームのみ
            if ("platform".equals(n.getType())) {
                startNodes.add(n);
            }

            // 目的地：改札のみ
            if ("gate".equals(n.getType())) {
                goalNodes.add(n);
            }
        }

        request.setAttribute("startNodes", startNodes);
        request.setAttribute("goalNodes", goalNodes);

        request.getRequestDispatcher("/WEB-INF/jsp/Station.jsp")
               .forward(request, response);
    }
}

