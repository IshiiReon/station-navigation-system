<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.StationEdge" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>経路案内結果</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>

<h2 class="page-title">駅構内ナビ ― 経路案内</h2>

<%
    Integer distance = (Integer) request.getAttribute("distance");
    List<String> routeTexts =
        (List<String>) request.getAttribute("routeTexts");
    List<StationEdge> routeEdges =
        (List<StationEdge>) request.getAttribute("routeEdges");
%>


<div class="result-box">
	<div class="route-header">
  		<div class="route-point start">
    		<span class="point-label">出発</span>
    		<span class="point-name">${startName}</span>
  		</div>

		<div class="flow-arrow">↓</div>

		<div class="route-point goal">
    		<span class="point-label">到着</span>
    		<span class="point-name">${goalName}</span>
  		</div>
  	</div>


        <!-- ===== 距離表示 ===== -->
        <%-- <div class="distance">
            合計距離：<%= distance %> m
        </div>--%>
	<div class="route-box">
        <!-- ===== 経路文章＋画像 ===== -->
        <ol>
        <%
            for (int i = 0; i < routeTexts.size(); i++) {
                String text = routeTexts.get(i);
                StationEdge edge = routeEdges.get(i);
        %>
            <li>
                <%= text %>

                <%
                    if (edge.getIMAGEPATH() != null) {
                %>
                    <div class="route-image">
                        <img src="<%= request.getContextPath() + "/" + edge.getIMAGEPATH() %>"
                             alt="進行方向の参考写真">
                    </div>
                <%
                    }
                %>
            </li>
        <%
            }
        %>
        </ol>

    </div>
</div>
</div>
<br>

<!-- ===== 戻るボタン ===== -->
<div class="button-center">
	<form action="<%= request.getContextPath() %>/stationForm" method="get">
	    <button type="submit" class="return-btn">検索画面に戻る</button>
	</form>
</div>

<%--<hr>

<!-- ===== デバッグ用 ===== -->
<h3>（デバッグ用）通過ノードID</h3>
<p>
<%
    List<Integer> path =
        (List<Integer>) request.getAttribute("path");
    for (int i = 0; i < path.size(); i++) {
        out.print(path.get(i));
        if (i < path.size() - 1) out.print(" → ");
    }
%>
</p> --%>

</body>
</html>
