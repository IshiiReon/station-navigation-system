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
<%
List<StationNode> startNodes =
    (List<StationNode>) request.getAttribute("startNodes");
%>


<h2 class="page-title">新宿駅構内ナビ</h2>

<div class="question-box">
	
	<form action="<%= request.getContextPath() %>/station" method="get">

	
	    <!-- ===== 出発地点（番線） ===== -->
<span class="label-badge">出発地点：</span><br>

<select id="platformSelect">
    <option value="">-- 番線を選択 --</option>
    <option value="1">1番線</option>
</select>

<br><br>

<!-- ===== 出発地点（号車） ===== -->
<select id="carSelect" name="start" required disabled>
    <option value="">-- 号車を選択 --</option>
</select>

<div class="flow-arrow">↓</div>

	
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

<script>
const platformSelect = document.getElementById("platformSelect");
const carSelect = document.getElementById("carSelect");

// JSPから nodeId を JS に渡す
const platformNodes = {
<%
    if (startNodes != null) {
        for (StationNode n : startNodes) {
            // 1番線の platform node だけ対象
            if ("platform".equals(n.getType()) && n.getName().startsWith("1番線")) {
%>
    "<%= n.getName().replace("1番線 ", "") %>": "<%= n.getNodeId() %>",
<%
            }
        }
    }
%>
};

platformSelect.addEventListener("change", () => {
    carSelect.innerHTML = '<option value="">-- 号車を選択 --</option>';
    carSelect.disabled = true;

    if (platformSelect.value === "1") {
        carSelect.disabled = false;

        for (const car in platformNodes) {
            const opt = document.createElement("option");
            opt.value = platformNodes[car]; // ← NODE_ID
            opt.textContent = car;
            carSelect.appendChild(opt);
        }
    }
});
</script>

	

</body>
</html>
