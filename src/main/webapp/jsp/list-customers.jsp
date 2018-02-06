<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
    <head>
        <link rel="stylesheet" href="../css/bootstrap.min.css">   		
        <script src="../js/bootstrap.min.js"></script>
        <title>Feature Matrix</title>
        <style>
         .jumbotron {
           padding: 0.5em 0.6em;
           text-align: center;
           background-color: cadetblue;
          }
          /*
           div {
              overflow-x: scroll;
            }

            .headcol {
              position: absolute;
            }
          */
        </style>
    </head>

    <body>
        <div class="container">

           <div class="jumbotron">
             <h2 style="color:#ffffff;">Feature Matrix</h2>
           </div>

<div class="container-fluid">
        <div class="row">
            <div class="col-sm-3" align="left">
                <div class="form-group" role="group" aria-label="...">
                    <select name="cust" class="form-control" onchange="this.options[this.selectedIndex].value && (window.location = this.options[this.selectedIndex].value);">
                        <option value="">Select Customer...</option>
                        <option value="/customer?name=customer-1">Customer 1</option>
                        <option value="/customer?name=customer-2">Customer 2</option>
                        <option value="/customer?name=customer-3">Customer 3</option>
                        <option value="/customer?name=customer-4">Customer 4</option>
                    </select>
                </div>
            </div>
            <div class="col-sm-9" align="right">
                <button title="Available and working" type="button" style="color:green; border: 0 none;" class="btn btn-default btn-xs"><img height="15px" width="15px" src="../images/success.png"> Works</button>
                <button title="Available but not working" type="button" style="color:red; border: 0 none;" class="btn btn-default btn-xs"><img height="15px" width="15px" src="../images/error.png"> Broken</button>
                <button title="Available but no automated test case. Working status unknown." type="button" style="color:#d1ce0c; border: 0 none;" class="btn btn-default btn-xs"><img height="15px" width="15px" src="../images/warning.png"> Incomplete</button>
                <button title="Not available" type="button" style="color:black; border: 0 none;" class="btn btn-default btn-xs"><img height="15px" width="15px" src="../images/forbidden.png"> Unavailable</button>
            </div>
        </div>
    </div>
    <p>

            <form action="/customer" method="post" id="customerForm" role="form">
                <input type="hidden" id="idCustomer" name="idCustomer">
                <input type="hidden" id="action" name="action">

                <c:choose>
                    <c:when test="${not empty featureMatrix}">
                        <table  class="table">
                        <c:forEach var="featureSet" items="${featureMatrix}" varStatus="featureIndex">
                                <tr>
                                <c:forEach var="val" items="${featureSet}" varStatus="customerIndex">
                                    <% pageContext.setAttribute("newLineChar", "\n"); %>
                                    <c:set var="formattedVer" value="${fn:replace(featureMatrix[0][customerIndex.index], newLineChar, ' ')}" />
                                    <c:set var="custVer" value="${fn:split(formattedVer, ' ')}" />

                                    <c:choose>
                                        <c:when test="${customerIndex.index == 0}">
                                           <td class="headcol" style="white-space:nowrap;">${val}</td>
                                        </c:when>
                                        <c:otherwise>
                                          <c:choose>
                                                <c:when test="${featureIndex.index == 0}">
                                                   <td><a style="color:cadetblue;white-space:pre-line;font-weight:bold;" href="/feature?customer=${custVer[0]}&version=${custVer[1]}">${val}</a></td>
                                                </c:when>
                                              <c:when test="${val == '0'}">
                                                  <td><a style="color:green" href="/feature?customer=${custVer[0]}&version=${custVer[1]}&pfeature=${featureMatrix[featureIndex.index][0]}">&#10004;</a></td>
                                              </c:when>
                                              <c:when test="${val == '1'}">
                                                  <td><a style="color:red" href="/feature?customer=${custVer[0]}&version=${custVer[1]}&pfeature=${featureMatrix[featureIndex.index][0]}">&#10006;</a></td>
                                              </c:when>
                                              <c:when test="${val == '2'}">
                                                  <td><a style="color:grey" href="/feature?customer=${custVer[0]}&version=${custVer[1]}&pfeature=${featureMatrix[featureIndex.index][0]}">&empty;</a></td>
                                              </c:when>
                                          </c:choose>
                                        </c:otherwise>
                                    </c:choose>

                                </c:forEach>
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