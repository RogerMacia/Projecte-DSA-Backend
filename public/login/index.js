const LOGIN_URL = `${BASE_URL}/user/login`;
// "http://localhost:8080/example/eetacbros/user/login"
console.log("Calling:", LOGIN_URL);

$.postJSON = function (url, data, callback) {
    return jQuery.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: 'POST',
        url: url,
        data: JSON.stringify(data),
        dataType: 'json',
        success: callback
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

    $.postJSON(LOGIN_URL, credentials, (data, status) => {
        console.log(`Status: ${status}`);

        if (status === "success") {
            showNotification("✅ Login successful! Redirecting...");

             //Store user data (Note: In production, consider security implications)
             localStorage.setItem("userId", data.id);
             localStorage.setItem("username", data.username);

            setTimeout(() => {
                window.location.href = "./shop";
            }, 1000);
        }
    })
    .fail((jqXHR) => {
        if (jqXHR.status === 400) {
            showNotification("❌ Incorrect password!");
        } else if (jqXHR.status === 404) {
            showNotification("❌ User not found!");
        } else {
            showNotification("❌ Server or connection error!");
        }
    }
    );
}

function onReadyDocument() {
    console.log("Initializing LOGIN...");

    // Uncomment if you want to check for existing session
     const userId = localStorage.getItem("userId");
     if (userId) {
         console.log(`User already logged in with ID: ${userId}. Redirecting to shop...`);
         window.location.href = "./shop";
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