let BASE_URL;

if (window.location.host.includes("localhost")) {
    BASE_URL = "http://localhost:8080/example/eetacbros";
    SWAGGER_URL = "http://localhost:8080/example";
}
else {
    BASE_URL = "https://dsa3.upc.edu/example/eetacbros";
    SWAGGER_URL = "https://dsa3.upc.edu/example";
}