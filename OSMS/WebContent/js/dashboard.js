function   checkAll(){
    	var   handleEl  =  document.getElementById("checkAllHandler");   
    	var   els   =   document.getElementsByName("userIdCheckbox");
    	for(var i=0;i<els.length;i++){   
    	    els[i].checked   =   handleEl.checked;   
    	}	    
    }
    
    function deleteUser(){
    	
    	var userId = document.getElementsByName("userIdCheckbox");
    	var str = "";
    	for(var i = 0; i < userId.length; i ++){
    		if(userId[i].checked){
    			str += userId[i].value + ",";
    		}
    	}
    	if(str.length < 1){
    		alert("Please choose!");
    	} else{
    		if(confirm("Confirm to delete?")){
    			location.href="UserServlet?type=delete&ids=" + str + "&deptId=" + $("#dptId").val();
    		}
    	}
    }
    
	function usernameAvailable(){ 
		$("#usernameAdd").blur(function(){ 
			var newName = $(this).val().trim(); 
			var changeUrl = "UserServlet?type=check&newUsername="+newName; 
			$.get(changeUrl,function(str){ 
				if(str == '1' || newName == ""){ 
					$("#usernameAddDiv").addClass("has-error has-feedback");
					$("#usernameAddSpan").addClass("glyphicon glyphicon-remove form-control-feedback");
					$("#isContinue").val(false);
				}else{ 
					$("#usernameAddDiv").removeClass("has-error has-feedback");
					$("#usernameAddSpan").removeClass("glyphicon glyphicon-remove form-control-feedback");
					$("#usernameAddDiv").addClass("has-success has-feedback");
					$("#usernameAddSpan").addClass("glyphicon glyphicon-ok form-control-feedback");
					$("#isContinue").val(true);
				} 
			}); 
			return false; 
		}); 
	} 
	
	function passwordValid(){
		$("#passwordOld").blur(function(){
			var username = $("#usernameAdd").val().trim();
			var password = $(this).val().trim();
			var changeUrl = "UserServlet?type=checkPassword&username=" + username + "&password=" + password;
			$.get(changeUrl,function(str){
				if(str == '0' || password == ""){
					$("#passwordOldDiv").addClass("has-error has-feedback");
					$("#passwordOldSpan").addClass("glyphicon glyphicon-remove form-control-feedback");
					$("#isContinue").val(false);
				} else{ 
					$("#passwordOldDiv").removeClass("has-error has-feedback");
					$("#passwordOldSpan").removeClass("glyphicon glyphicon-remove form-control-feedback");
					$("#passwordOldDiv").addClass("has-success has-feedback");
					$("#passwordOldSpan").addClass("glyphicon glyphicon-ok form-control-feedback");
					$("#isContinue").val(true);
				} 
			});
		});
	}
	
	function passwordCheck(){
		$("#passwordConfirm").blur(function(){
			var password = $("#password").val().trim();
			var passConfirm = $(this).val().trim();
			if(password != passConfirm){
				$("#passwordConfirmDiv").addClass("has-error has-feedback");
				$("#passwordConfirmSpan").addClass("glyphicon glyphicon-remove form-control-feedback");
				$("#isContinue").val(false);
			} else{
				$("#passwordConfirmDiv").removeClass("has-error has-feedback");
				$("#passwordConfirmSpan").removeClass("glyphicon glyphicon-remove form-control-feedback");
				$("#passwordConfirmDiv").addClass("has-success has-feedback");
				$("#passwordConfirmSpan").addClass("glyphicon glyphicon-ok form-control-feedback");
				$("#isContinue").val(true);
			}
		});
	}
	
	function checkFillAll(){
		var username = $("#usernameAdd").val();
		var passwordOld = $("#passwordOld").val();
		var password = $("#password").val();
		var passwordConfirm = $("#passwordConfirm").val();
		var firstname = $("#firstname").val();
		var lastname = $("#lastname").val();
		var userActionType = $("#userActionType").val();
		if(username.trim() == ""){
			alert("Please fill in a valid username!");
			return false;
		}
		
		if((userActionType != "new" )&& passwordOld == ""){
			alert("Please fill in the valid old password!");
			return false;
		}
		
		if(password.trim() == "" || passwordConfirm == ""){
			alert("Please fill in a valid password!");
			return false;
		}
		
		if(firstname.trim() == ""){
			alert("Please fill in a firstname!");
			return false;
		}
		
		if(lastname.trim() == ""){
			alert("Please fill in a lastname!");
			return false;
		}
		return true;
	}
	
	function searchUserValidate() {
		var username = document.getElementsByName("username")[0];
		var firstName = document.getElementsByName("firstname")[0];
		var lastName = document.getElementsByName("lastname")[0];
		var speciality = document.getElementsByName("speciality")[0];
//		var department = document.getElementsByName("dept")[0];
		var position = document.getElementsByName("position");
		
		var n = 0;
		
		for(var i = 0 ; i < position.length; i++)
		{
			if(position[i].checked)
			{
				n++;
			}
		}
		
		if(username.value.length < 1 && firstName.value.length < 1 && lastName.value.length < 1 &&
				speciality.value.length < 1 && n < 1 ){
			alert("Search conditions can not be blank");
			return false;
		}
		return ture;
	}
	
	function displayGender(){
		var gender = $("#userGender").val();
		if(gender == '0'){
			$("#genderM").attr("checked", true);
		} else{
			$("#genderF").attr("checked", true);
		}
	}
	
	function displayPosition(){
		var gender = $("#userPosition").val();
		if(gender == '1'){
			$("#manager").attr("checked", true);
		} else{
			$("#employee").attr("checked", true);
		}
	}
	
	function setIndexWidth(){
		$(".marketing").css("width", $(".jumbotron").css("width"));
	}
	
	
	function displayProducts(){
		$("#resultsOnAmazon").show();
	}
	 
	function initialize(){ 
		usernameAvailable(); 
		passwordValid();
		passwordCheck();
		displayGender();
		displayPosition();
		setIndexWidth();
		$("#resultsOnAmazon").hide();
		}
	
	$(initialize);