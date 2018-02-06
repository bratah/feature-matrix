<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <link rel="stylesheet" href="../css/bootstrap.min.css">   		
        <script src="../js/bootstrap.min.js"></script>
        <title>Feature Matrix - Testcases</title>
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
             <h2 style="color:#ffffff;">Feature Matrix - Testcases</h2>
           </div>

            <span class="label label-default">${param.customer}</span>
            <span class="label label-primary">${param.version}</span>
            <span class="label label-info">${param.feature}</span>
            <p>
            <p>
          <ol class="breadcrumb">
            <li><a href="/customer?name=${param.customer}">Customers</a></li>
            <li><a href="/feature?customer=${param.customer}&version=${param.version}&pfeature=${param.pfeature}">Features</a></li>
            <li class="active">Testcases</li>
          </ol>

            <!--Feature List-->
            <c:if test="${not empty message}">                
                <div class="alert alert-success">
                    ${message}
                </div>
            </c:if> 
            <form action="/testcase" method="post" id="testcaseForm" role="form" >
                <input type="hidden" id="idTestcase" name="idTestcase">
                <input type="hidden" id="action" name="action">
                <c:choose>
                    <c:when test="${not empty testcaseList}">
                        <table  class="table">
                            <thead>
                                <tr>

                                    <td style="color:cadetblue;font-weight:bold;">Testcase</td>
                                    <td style="color:cadetblue;font-weight:bold;">Count</td>
                                    <td style="color:cadetblue;font-weight:bold;">Status</td>

                                </tr>
                            </thead>
                            <c:forEach var="testcase" items="${testcaseList}">
                                <c:set var="classSucess" value=""/>
                                <c:if test ="${idTestcase == testcase.id}">
                                    <c:set var="classSucess" value="info"/>
                                </c:if>
                                <tr class="${classSucess}">
                                    <td>${testcase.name}</td>
                                    <td>${testcase.count}</td>
                                    <c:choose>
                                        <c:when test="${testcase.getStatus()}">
                                            <td style="color:green">PASS</td>
                                        </c:when>
                                        <c:otherwise>
                                            <td style="color:red">FAIL</td>
                                        </c:otherwise>
                                    </c:choose>
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