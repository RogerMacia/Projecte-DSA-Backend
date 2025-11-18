const BASE_URL = "http://localhost:8080/example/eetacbros/user/login";

 function onClearBtnClick() {
     $("#loginUsernameTbx").val('');
     $("#loginPasswordTbx").val('');
 }

 function onLoginBtnClick() {
     console.log("loginBtn clicked!");

     let username = $("#loginUsernameTbx").val();
     let password = $("#loginPasswordTbx").val();

     if (!username || !password) {
         showBubble("Cal omplir usuari i contrasenya!");
         return;
     }

     let credentials = { username: username, password: password };

     $.postJSON(BASE_URL, credentials, (data, status) => {
         console.log(`Status: ${status}`);

         if (status === "success") {

             localStorage.setItem("userId", data.id);

             localStorage.setItem("username", data.username);

             window.location.href = "./shop";
         }

     }).fail((jqXHR) => {

         if (jqXHR.status === 400) {
             showBubble("Contrasenya incorrecta!");
         } else if (jqXHR.status === 404) {
             showBubble("No s'ha trobat l'usuari!");
         } else {
             showBubble("Error del servidor o de connexió!");
         }
     });
 }

 function onReadyDocument() {
     console.log("Initializing LOGIN...");
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

 $(document).ready(onReadyDocument);;