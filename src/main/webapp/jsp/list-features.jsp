<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <link rel="stylesheet" href="../css/bootstrap.min.css">   		
        <script src="../js/bootstrap.min.js"></script>
        <title>Feature Matrix - Features</title>
        <style>
         .jumbotron {
           padding: 0.5em 0.6em;
           text-align: center;
           background-color: cadetblue;
          }
        </style>
    </head>

    <body>          
        <div class="container">

           <div class="jumbotron">
             <h2 style="color:#ffffff;"> ERS Feature Matrix - Features</h2>
           </div>

            <span class="label label-default">${param.customer}</span>
            <span class="label label-primary">${param.version}</span>
            <span class="label label-info">${param.pfeature}</span>
            <p>
            <p>
          <ol class="breadcrumb">
            <li><a href="/customer?name=${param.customer}">Customers</a></li>
            <li class="active">Features</li>
          </ol>

            <!--Feature List-->
            <c:if test="${not empty message}">                
                <div class="alert alert-success">
                    ${message}
                </div>
            </c:if> 
            <form action="/feature" method="post" id="featureForm" role="form" >
                <input type="hidden" id="idFeature" name="idFeature">
                <input type="hidden" id="action" name="action">
                <c:choose>
                    <c:when test="${not empty featureList}">
                        <table  class="table">
                            <thead>
                                <tr bgcolor="white">

                                    <td style="color:cadetblue;font-weight:bold;">Sub Features</td>
                                    <td style="color:cadetblue;font-weight:bold;">Test Cases</td>
                                    <td style="color:cadetblue;font-weight:bold;">Status</td>

                                </tr>
                            </thead>
                            <c:forEach var="feature" items="${featureList}">
                                <c:set var="classSucess" value=""/>
                                <c:if test ="${idFeature == feature.id}">
                                    <c:set var="classSucess" value="info"/>
                                </c:if>
                                <tr class="${classSucess}">
                                    <td>${feature.name}</td>
                                    <td>${feature.getTestcaseSize()}</td>
                                    <c:choose>
                                        <c:when test="${feature.getStatus()}">
                                            <td><a style="color:green" href="/testcase?customer=${param.customer}&version=${param.version}&feature=${feature.name}&pfeature=${param.pfeature}">&#10004;</a></td>
                                        </c:when>
                                        <c:otherwise>
                                            <td><a style="color:red" href="/testcase?customer=${param.customer}&version=${param.version}&feature=${feature.name}&pfeature=${param.pfeature}">&#10006;</a></td>
                                        </c:otherwise>
                                    </c:choose>
                                    <td></td>
                                </tr>
                            </c:forEach>               
                        </table>  
                    </c:when>                    
                    <c:otherwise>
                        <br>           
                        <div class="alert alert-info">
                           No record found matching your criteria!
                        </div>
                    </c:otherwise>
                </c:choose>                        
            </form>
        </div>
    </body>
</html>