<%-- 
    Document   : index
    Created on : 30 dic 2020, 14:38:46
    Author     : metal
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>
    <body>
        
        <c:choose>
            <c:when test="${sessionScope.user == null}">
                <div class="card-body">
                    <form action="login" 
                          method="post"
                          name="frmUsuarioForm"
                          id="frmUsuarioForm">

                        <div class="form-group row">
                            <label for="nombreUsuario" class="col-sm-2 col-form-label">Nombre de Usuario</label>
                            <div class="col-sm-6">
                                <input type="text"
                                       name="user"
                                       id="user"
                                       maxlength="50"
                                       required="required"
                                       placeholder="Nombre de Usuario"
                                       class="form-control"/>
                            </div>
                        </div>

                        <div class="form-group row">
                            <label for="claveUsuario" class="col-sm-2 col-form-label">Contraseña</label>
                            <div class="col-sm-6">
                                <input type="password"
                                       name="pass"
                                       id="pass"
                                       maxlength="150"
                                       required="required"
                                       placeholder="Contraseña"
                                       class="form-control"/>
                            </div>
                        </div>
                        <div class="form-group row">
                            <div class="col-sm-6">
                                <input type="submit"
                                       class="btn btn-primary"
                                       value="Ingresar"/>
                            </div>
                        </div>
                       
                    </form>
                </div>
            </c:when>
            
        </c:choose>
        <c:choose>
            <c:when test="${msg != null}">
                <div class="alert alert-danger" role="alert">
                        ${msg}
                </div>
            </c:when>
        </c:choose>



        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.min.js"></script>
    </body>
</html>
