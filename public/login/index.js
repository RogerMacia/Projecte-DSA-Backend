
const LOGIN_URL = `${BASE_URL}/user/login`;
console.log("Calling:", LOGIN_URL);

$.postJSON = function (url, data, callback, errorCallback) { // Added errorCallback parameter
    return jQuery.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: 'POST',
        url: url,
        data: JSON.stringify(data),
        dataType: 'json',
        success: callback,
        error: errorCallback // Pass errorCallback to jQuery.ajax
    });
};

function onClearBtnClick() {
    $("#loginUsernameTbx").val('');
    $("#loginPasswordTbx").val('');
}

function onLoginBtnClick() {
    console.log("loginBtn clicked!");

    let username = $("#loginUsernameTbx").val();
    let password = $("#loginPasswordTbx").val();


    if (!username || !password) {
        showNotification("⚠️ Please fill in username and password!");
        return;
    }

    let credentials = { username: username, password: password };

    $.postJSON(LOGIN_URL, credentials,
        (data, status) => { // Success callback
            console.log(`Status: ${status}`);

            if (status === "success") {
                showNotification("✅ Login successful! Redirecting...");

                //Store user data (Note: In production, consider security implications)
                localStorage.setItem("userId", data.id);
                localStorage.setItem("username", data.username);
                localStorage.setItem("password", data.password); // Store password in localStorage
                // localStorage.setItem("coins", data.coins); // Removed: coins should be fetched from API
                // localStorage.setItem("email",data.email); // Removed: email should be fetched from API


                setTimeout(() => {
                    window.location.href = "../profile";
                }, 1000);
            }
        },
        (jqXHR, textStatus, errorThrown) => { // Error callback
            console.error(`Error Status: ${textStatus}, Error Thrown: ${errorThrown}`);
            console.error(`Response Text: ${jqXHR.responseText}`);
            try {
                const errorResponse = JSON.parse(jqXHR.responseText);
                if (errorResponse.message) {
                    showNotification(`❌ ${errorResponse.message}`);
                } else {
                    showNotification("❌ An unknown error occurred during login.");
                }
            } catch (e) {
                showNotification("❌ An unexpected error occurred. Please try again.");
            }
        }
    );
}



function onReadyDocument() {
    console.log("Initializing LOGIN...");

    // Uncomment if you want to check for existing session
     const userId = localStorage.getItem("userId");
     if (userId) {
         console.log(`User already logged in with ID: ${userId}. Redirecting to profile...`);
         window.location.href = "../profile";
         return;
     }

    $("#loginClearBtn").click(onClearBtnClick);
    $("#loginBtn").click(onLoginBtnClick);

    // Allow Enter key to submit
    $("#loginPasswordTbx").keypress(function(e) {
        if (e.which === 13) {
            onLoginBtnClick();
        }
    });
}

function showNotification(text) {
    const notification = $("#notification");
    $("#notificationText").text(text);
    notification.removeClass("hidden");

    setTimeout(() => {
        notification.addClass("hidden");
    }, 3000);
}

$(document).ready(onReadyDocument);
