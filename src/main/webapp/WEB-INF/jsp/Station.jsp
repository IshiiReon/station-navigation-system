<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.StationNode" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>駅構内ナビ</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>

<h2 class="page-title">新宿駅構内ナビ</h2>

<div class="question-box">
	
	<form action="<%= request.getContextPath() %>/station" method="get">

	
	    <!-- ===== 出発地点 ===== -->
	    <span class="label-badge">出発地点：</span><br>
	    <select name="start" required>
	        <option value="">-- 選択してください --</option>
	        <%
	List<StationNode> startNodes =
	    (List<StationNode>) request.getAttribute("startNodes");
	if (startNodes != null) {
	    for (StationNode n : startNodes) {
	%>
	<option value="<%= n.getNodeId() %>">
	    <%= n.getName() %>（<%= n.getFloor() %>）
	</option>
	<%
	    }
	}
	%>
	
	
	    </select>
	
	    <br><br>
	
	    <!-- ===== 目的地 ===== -->
	    <span class="label-badge">目的地：</span><br>
	    <select name="goal" required>
	        <option value="">-- 選択してください --</option>
	        <%
	List<StationNode> goalNodes =
	    (List<StationNode>) request.getAttribute("goalNodes");
	if (goalNodes != null) {
	    for (StationNode n : goalNodes) {
	%>
	<option value="<%= n.getNodeId() %>">
	    <%= n.getName() %>（<%= n.getFloor() %>）
	</option>
	<%
	    }
	}
	%>
	
	    </select>
	
	    <br><br>
	<div class="button-center">
	    <button type="submit" class="search-btn">経路検索</button>
	</div>
	</form>
</div>
	

</body>
</html>
