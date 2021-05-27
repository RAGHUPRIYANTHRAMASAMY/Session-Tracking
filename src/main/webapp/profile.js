$(document).ready(function(){
			
	// intializing variables
	let i = 0;
	let sessionKeys;
	let activeSessionKeys;

	// function to create session display container

	function createSessionElement(value) {
		let sessionContainer = document.getElementById("session-display");
				
		let type = document.createElement('div');
		// type.appendChild(document.createTextNode("Content will goes here"));
		type.setAttribute("class", "display_session-div");

		const input = document.createElement('input');
		i = i+1;
		let inputid = "input-ses_"+i;
		input.setAttribute("value", value);
		input.setAttribute("id", inputid);
		input.setAttribute("disabled", "");
		const button = document.createElement('button');
		button.textContent = "Terminate";
		let session_del_btn = "ses-del-btn_"+i;
		button.setAttribute("id", session_del_btn);
		type.appendChild(input);
		type.appendChild(button);
		sessionContainer.appendChild(type);
	}

	// Delete Terminated Session Container

	function deleteTerminatedContainer(containerValue) {
		for (var t = 1; t <= i; ++t){
			let sessionName = $("#input-ses_" + t).val();
			if (containerValue == sessionName) {
				$("#input-ses_"+t).unwrap()
				$("#ses-del-btn_"+t).remove();
				$("#input-ses_"+t).remove();
			}
		}
	}

	// function to logout user from current terminated session
	
	function terminateLogout() {
		$.ajax({
			type: 'POST',
			url: 'terminatecurrentsession',
			success: function (result) {
				if ($.trim(result) == "true") {
					location.reload(true);
				}
			}
		})
	}

	// function to check current Session Teminated or not

	function sessionStatus() {
		$.ajax({
			type: 'POST',
			url: 'sessionstatus',
			success: function (result) {
				if ($.trim(result) == "false") {
					terminateLogout();
				}
			}
		})
	}

	// User Session Details
	function sessionDetails() {
		$.ajax({
			type: 'POST',
			url: 'usersession',
			async: false,
			success: function(sessionResult) {
				if ($.trim(sessionResult) == false) {
					Console.log("No Session Initialized");
				} else {
					sessionKeys = Object.keys(sessionResult);
					sessionKeys.forEach(element => {
						createSessionElement(element);
					});
				}
			}
		})
	}

	// checking new session added if added render it into template
	// Included with delete already terminated session
	function checkNewSession() {
		$.ajax({
			type: 'POST',
			url: 'usersession',
			async: false,
			success: function(sessionResult) {
				if ($.trim(sessionResult) == false) {
					Console.log("No Session Initialized");
				} else {
					activeSessionKeys = Object.keys(sessionResult);

					activeSessionKeys.forEach(element => {
						if (!(sessionKeys.includes(element))) {
							sessionKeys.push(element);
							createSessionElement(element);
						}
					});

					sessionKeys.forEach(element => {
						if (!(activeSessionKeys.includes(element))) {
							deleteTerminatedContainer(element);
						}
					})
				}
			}
		})
	}

	// Fetch user profile details
	function profileDetails() {
		$.ajax({
			type: 'POST',
			url: 'userprofile',
			async: false,
			success: function (result) {
				$('.user-email').html(result.email);
				$('.user-name').html(result.firstname);
				$('#First_name').val(result.firstname);
				$('#Last_name').val(result.lastname);
				$('#user_gender').val(result.gender);
				$('#user_coutry').val(result.country)
			}
		})
	}
		
	// load user profile details
	profileDetails();

	// load user session details
	sessionDetails()

	// Enabling input and select option to edit user profile details

	$('#profile-edit').click(function(){
		$('#profile-edit').css('display', 'none');
		$('.savebtn').css('display', 'block');
		$('#First_name').prop("disabled", false);
		$('#Last_name').prop("disabled", false);
		$('#user_gender').prop("disabled", false);
		$('#user_coutry').prop("disabled", false);
	})
			
	// Disable user profile fields on clicking cancel button
			
	$('#cancel').click(function(){
		$('#profile-edit').css('display', 'block');
		$('.savebtn').css('display', 'none');
		$('#First_name').prop("disabled", true);
		$('#Last_name').prop("disabled", true);
		$('#user_gender').prop("disabled", true);
		$('#user_coutry').prop("disabled", true);
		profileDetails();
	})
	
	// Update user profile details
	$('#update').click(function(){
		var Firstname = $('#First_name').val();
		var Lastname = $('#Last_name').val();
		var Gender = $('#user_gender').val();
		var Country = $('#user_coutry').val();
		$.ajax({
			type : 'POST',
			url: 'updateprofile',
			data: {firstname: Firstname, lastname: Lastname, gender: Gender, country: Country},
			async: false,
			success: function(value){
				if ($.trim(value) == "true") {
					
					$('#update').prop("disabled", true);
					$('#cancel').prop("disabled", true);
					$('#profile_alert-h3').removeClass('hide');
					$('#profile_alert-h3').addClass('show');

					setTimeout(() => {
						profileDetails();
						$('#profile_alert-h3').removeClass('show');
						$('#profile_alert-h3').addClass('hide');
						$('#profile-edit').css('display', 'block');
						$('.savebtn').css('display', 'none');
					}, 1000);

				}
			}
		})
	})
			
	// Enable update button if anyone of user field data changed

	$('#First_name').change(function(){
		$('#update').prop("disabled", false);
	})
			
	$('#Last_name').change(function(){
		$('#update').prop("disabled", false);
	})

	$('#user_gender').change(function(){
		$('#update').prop("disabled", false);
	})

	$('#user_coutry').change(function(){
		$('#update').prop("disabled", false);
	})

	// User choice to Terminate Session

	$(document).on("click", "button", function() {
		let sess_del_but = this.id;
		if (sess_del_but.includes("ses-del-btn_")) {
			let index = sess_del_but.split("_");
			let sessionName = $("#input-ses_"+index[1]).val();
			$.ajax({
				type: "POST",
				url: "terminatesession",
				data: {sessionname: sessionName},
				success: function (result) {
					if ($.trim(result) == "true") {
						$("#input-ses_"+index[1]).unwrap()
						$("#ses-del-btn_"+index[1]).remove();
						$("#input-ses_"+index[1]).remove();
						sessionKeys.pop(sessionName);
					}
					
					// $("#ses-del-btn_"+index[1]).closest('.display_session').remove();
					// $(this.id).parent().remove();
				}
			})
		}
	})

	// Fetch all User Sessions from database through servlet and dynamically render into website
	// included with check new session status
	function fetchSession() {
		if (!($.active)) {
			sessionStatus();
			checkNewSession();
		}
	}
	setInterval(fetchSession, 2000);

	// window.addEventListener('beforeunload', function (e) {
    // 	e.preventDefault();
    // 	e.returnValue = '';
	// });

})

	// adding session by user

	// $('#add-session').click(function(){
				
	// 	let session_name = $('#session_name').val();
	// 	let session_value = $('#session_value').val();
	// 	if((session_name == null || session_name == "") || (session_value == null || session_value == "") ){
	// 		alert("Session name or value cannot be empty");
	// 	}
	// 	else{
	// 		const addsession = session_name.toLowerCase();
	// 		if ((addsession == "useremail" ||addsession =="mailuser" || addsession.includes("email") || addsession.includes("user")) || addsession.length < 3){
	// 			alert("Session name invalid: Try with other session name");
	// 		}
	// 		else{
	// 			if(sessionKeys.includes(session_name)){
	// 				alert("Session already running: You cannot create session, delete this session and try again");
	// 			}
	// 			else {
	// 				$.ajax({
	// 					type : 'POST',
	// 					url : 'cookiegenerator',
	// 					data: {sessionName: session_name, sessionValue: session_value},
	// 					async: false,
	// 					success: function (result) {
	// 						console.log("Cookie added");
	// 						// location.reload(true);
	// 						createSessionElement(session_name);
	// 						$('#session_name').val("");
	// 						$('#session_value').val("");
	// 					}
	// 				})
	// 			}
	// 		}			
	// 	}
	// })