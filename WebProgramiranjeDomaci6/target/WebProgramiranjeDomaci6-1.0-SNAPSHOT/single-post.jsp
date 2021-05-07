<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Single Post</title>
    <%@ include file="styles.jsp"%>
</head>
<body>
    <div class="container">
        <div class="row">
            <c:choose>
                <c:when test="${notFound}">
                    <h1>Objava sa datim id-jem nije pronadjena</h1>
                </c:when>
                <c:otherwise>
                <div class="col">
                    <h1><c:out value="${post.title}"/></h1>
                    <h6><fmt:formatDate pattern="dd.MM.yyyy" value="${post.postdate}"/></h6>
                    <h6 class="text-muted"><c:out value="${post.author}"/></h6>
                    <p><c:out value="${post.content}"/></p>
                </div>
        </div>
        <div class="row">
            <div class="col">
                <h3>Comments</h3>
                <c:if test="${comments.size() == 0}">
                    <h4>Nema komentara za ovu objavu.</h4>
                </c:if>
                <c:forEach var="comment" items="${comments}">
                    <div class="card">
                        <div class="card-body">
                            <h4 class="card-title"><c:out value="${comment.name}"/></h4>
                            <p class="card-text"><c:out value="${comment.comment}"/></p>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
        <div class="row">
            <div class="col">
                <h3>New comment</h3>
                <form method="post">
                    <div class="form-group">
                        <label for="author">Author</label>
                        <input type="text" required class="form-control" id="author" name="author">
                    </div>
                    <div class="form-group">
                        <label for="comment">Comment</label>
                        <textarea class="form-control" rows="4" required id="comment" name="comment"></textarea>
                    </div>
                    <button type="submit" class="btn btn-primary btn-lg">Comment</button>
                </form>
            </div>
                </c:otherwise>
            </c:choose>

        </div>
    </div>
</body>
</html>
