const {createProxyMiddleware} = require("http-proxy-middleware");

let target = "http://localhost:8080";

if (import.meta.env.PROD) {
    // TODO add aws api uri
    target = "aws-something"
}

module.exports = function (app) {
    app.use(
        "/api",
        createProxyMiddleware({
            target,
            changeOrigin: true,
        })
    )
}