const { defineConfig } = require("@vue/cli-service");
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    proxy: {
      "/api/sse": {
        target: "http://localhost:8001",
        pathRewrite: { "^/api/sse": "" },
        ws: true,
        changeOrigin: true,
      },
    },
    compress: false,
  },
});
