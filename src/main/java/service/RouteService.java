package service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import model.StationEdge;
import model.StationNode;

public class RouteService {

    /* =========================
     * 最短経路（ダイクストラ）
     * ========================= */
    public RouteResult dijkstraWithPath(
            List<StationEdge> edges,
            int start,
            int goal) {

        // グラフ生成
        Map<Integer, List<StationEdge>> graph = new HashMap<>();
        for (StationEdge e : edges) {
            graph.computeIfAbsent(e.from, k -> new ArrayList<>())
                 .add(e);
        }

        Map<Integer, Integer> dist = new HashMap<>();
        Map<Integer, Integer> prev = new HashMap<>();

        PriorityQueue<int[]> pq =
            new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));

        pq.add(new int[]{start, 0});
        dist.put(start, 0);

        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int node = cur[0];
            int cost = cur[1];

            if (cost > dist.get(node)) continue;
            if (node == goal) break;

            for (StationEdge e : graph.getOrDefault(node, new ArrayList<>())) {
                int next = e.to;
                int nextCost = cost + e.distance;

                if (nextCost < dist.getOrDefault(next, Integer.MAX_VALUE)) {
                    dist.put(next, nextCost);
                    prev.put(next, node);
                    pq.add(new int[]{next, nextCost});
                }
            }
        }

        // ゴール未到達対策 ★重要
        if (!dist.containsKey(goal)) {
            return new RouteResult(-1, new ArrayList<>());
        }

        // 経路復元
        List<Integer> path = new ArrayList<>();
        Integer cur = goal;
        while (cur != null) {
            path.add(cur);
            cur = prev.get(cur);
        }
        Collections.reverse(path);

        return new RouteResult(dist.get(goal), path);
    }

    /* =========================
     * 経路を人向け文章に変換
     * ========================= */
    public List<String> routeText(
            List<Integer> path,
            Map<Integer, StationNode> nodeMap) {

        List<String> texts = new ArrayList<>();

        for (int i = 0; i < path.size() - 1; i++) {
            StationNode from = nodeMap.get(path.get(i));
            StationNode to   = nodeMap.get(path.get(i + 1));

            if (from == null || to == null) continue;

            texts.add(makeSentence(from, to));
        }

        return texts;
    }
    public List<StationEdge> pathEdges(
            List<Integer> path,
            List<StationEdge> allEdges) {

        List<StationEdge> result = new ArrayList<>();

        for (int i = 0; i < path.size() - 1; i++) {
            int from = path.get(i);
            int to   = path.get(i + 1);

            for (StationEdge e : allEdges) {
                if (e.from == from && e.to == to) {
                    result.add(e);
                    break;
                }
            }
        }
        return result;
    }


    private String makeSentence(StationNode from, StationNode to) {

        String f = from.getType();
        String t = to.getType();

        if (f.equals("platform") && t.startsWith("stairs")) {
            return from.getName() + "から"
                 + to.getName() + "へ向かいます。";
        }

        if (f.startsWith("stairs") && t.equals("concourse")) {
            return to.getName() + "を進みます。";
        }

        if (f.equals("concourse") && t.equals("gate")) {
            return to.getName() + "へ向かってください。";
        }

        if (t.equals("elevator")) {
            return to.getName() + "を利用します。";
        }

        return to.getName() + "へ進みます。";
    }
}
