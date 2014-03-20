<%@ include file="/WEB-INF/jsp/include.jsp"%>

<html>
<head>
	<script src="http://code.jquery.com/jquery-latest.js"></script>
	<script type="text/javascript">
	$(document).ready(function(){
	    /* Hier der jQuery-Code */
	    alert('Hallo Welt');

	    $('form').submit(function() {
	    	//alert("submit pressed!");
	        var queryString = JSON.stringify($('#form').serializeArray()); //$('#form').serialize();
	    	
	    	$.ajax({
	    		url: $(this).attr('action'),
	    		type: 'POST',
	    		data: queryString,
	    		contentType: 'application/json',
	            success: function (data) {
	                alert(queryString);
	            },
	            error: function (jqXHR, textStatus, errorThrown) {
	                alert('An error has occured!! :-(');
	            }
	    		
	    	});
	        return false;       
	    });
	    return false;
	});
	
	</script>	
</head>

<body>

	<form:form method="post" modelAttribute="questionnaire" action="addQuestionnaire" id="form">

		<c:forEach items="${allQuestions}" var="question">

			${question.questionString}<br>					

			<table>
				<tr>
					<c:forEach items="${question.answerlist.answer}" var="answer">
						<td>
						<c:choose>
							<c:when test="${question.answerlist.singleChoice == true }">
								<input type="radio" name="${question.id}">
							</c:when>
							<c:otherwise>
								<input type="checkbox" name="${question.id}">
							</c:otherwise>
						</c:choose>
						<label for="${question.id}">${answer.answerString}</label>
						<input type="hidden" name="label.${question.id}" value="${answer.answerString}">
						</td>
					</c:forEach>
				</tr>
			</table>

		</c:forEach><br>
		<input type="submit" value="Submit">

	</form:form>
</body>
</html>