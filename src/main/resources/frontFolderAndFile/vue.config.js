module.exports = {
    devServer: {
        open: true,
        port: 8080,
        proxy: {
            '/api': {
                target: process.env.VUE_APP_URL + '/api',
                changeOrigin: true,
                pathRewrite: {
                    '^/api': ''
                }
            }
        }

    }
}
