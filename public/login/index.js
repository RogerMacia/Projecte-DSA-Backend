const BASE_URL = "http://localhost:8080/dsaApp/eetacbros/user/login";

function onClearBtnClick() {
console.log("loginClearBtn clicked!");
$("#loginUsernameTbx").val('');
$("#loginPasswordTbx").val('');
}

function onLoginBtnClick() {
console.log("loginBtn clicked!");
alert("LOGIN CLICAT");

let username = $("#loginUsernameTbx").val();
let password = $("#loginPasswordTbx").val();

if (!username || !password) {
showBubble("Cal omplir usuari i contrasenya!");
return;
}

let credentials = { username: username, password: password };
let buffer = JSON.stringify(credentials);
console.log(buffer);

$.postJSON(BASE_URL, credentials, (data, status) => {
console.log(`Status: ${status} \n${JSON.stringify(data)}`);

// Si el backend retorna 200 OK
showBubble("Login correcte!");


}).fail((jqXHR) => {
console.log("Error en login:", jqXHR.status, jqXHR.responseText);

if (jqXHR.status === 401) {
showBubble("Usuari o contrasenya incorrectes!");
} else {
showBubble("Error del servidor o de connexió!");
}
});
}

function onReadyDocument() {
console.log("Initializing LOGIN...");
$("#app").slideUp("slow");
$("#app").show("slow");

$("#loginClearBtn").click(onClearBtnClick);
$("#loginBtn").click(onLoginBtnClick);
}

function showBubble(text) {
$("#res").fadeIn("slow");
$("#res").text(text);
$("#res").delay(3000).fadeOut("slow");
}


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