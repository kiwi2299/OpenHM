<%-- 
    Document   : display
    Created on : 12/07/2022, 01:47:39 PM
    Author     : kiwir
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <link rel="stylesheet"
        href="https://cdn.jsdelivr.net/gh/openlayers/openlayers.github.io@master/en/v6.14.1/css/ol.css" type="text/css">
    <link rel="stylesheet" href="styles/styles.css">
    <script src="https://cdn.jsdelivr.net/gh/openlayers/openlayers.github.io@master/en/v6.14.1/build/ol.js"></script>
    
    <link rel="stylesheet" type="text/css" href="styles/timeline.css">

    <!-- // Add jQuery -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>

    <script src="js/timeline.js"></script>

    <title>Open History Mapper</title>
</head>

<body>
    <jsp:include page="${menu}"/>
    <div>
        <table class="table table-stripef">
                <thead>
                    <tr>
                        <th>id</th>
                        <th>Name</th>
                        <th>Geom</th>
                        
                    </tr>
                </thead>
                <tbody>
                    <c:forEach
                        var="dto" 
                        items="${listGeom}">
                        <tr>
                            <td>
                                <c:out value="${dto.entidad.id}"/>
                            </td>
                            <td>
                                <c:out value="${dto.entidad.name}"/>
                            </td>
                            <td>
                                <c:out value="${dto.entidad.geom}"/>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        
    </div>

   
</body>

</html>