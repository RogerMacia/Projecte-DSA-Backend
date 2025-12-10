
/*
    Configura la url si se cambia la url base.
*/

config = {
    //url: "http://localhost:8080/example/swagger.json",
    url: `${SWAGGER_URL}/swagger.json`,
    dom_id: '#swagger-ui',
    deepLinking: true,
    presets: [
        SwaggerUIBundle.presets.apis,
        SwaggerUIStandalonePreset
    ],
    plugins: [
        SwaggerUIBundle.plugins.DownloadUrl
    ],
    layout: "StandaloneLayout"
}