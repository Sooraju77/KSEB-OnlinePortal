function validateConsumer(){
		if(document.addconsumer.firstname.value==""){
			alert("Please enter first name of consumer");
			document.getElementById("firstname").focus();
			return false;
		}
		if(document.addconsumer.lastname.value==""){
			alert("Please enter last name of consumer");
			document.getElementById("lastname").focus();
			return false;
		}
		if(document.addconsumer.phoneno.value==""){
			alert("Please enter phoneno of consumer");
			document.getElementById("phoneno").focus();
			return false;
		}
		if(document.addconsumer.emailid.value==""){
			alert("Please enter emailid of consumer");
			document.getElementById("emailid").focus();
			return false;
		}
		if(document.addconsumer.city.value==""){
			alert("Please enter city of consumer");
			document.getElementById("city").focus();
			return false;
		}
		if(document.addconsumer.pincode.value==""){
			alert("Please enter pincode of consumer");
			document.getElementById("pincode").focus();
			return false;
		}
		if(document.addconsumer.meterno.value==""){
			alert("Please enter meterno of consumer");
			document.getElementById("meterno").focus();
			return false;
		}
		if(document.addconsumer.postno.value==""){
			alert("Please enter postno of consumer");
			document.getElementById("postno").focus();
			return false;
		}
		if(document.addconsumer.connectiontype.value==""){
			alert("Please enter connectiontype of consumer");
			document.addconsumer.connectiontype.focus();
			return false;
		}
		
		
		
	}