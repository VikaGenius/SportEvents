const webpack = require('webpack');

module.exports = {
  transpileDependencies: ['vue'], // или другие зависимости, которые вы хотите транспилировать
  configureWebpack: {
    plugins: [
      new webpack.DefinePlugin({
        __VUE_PROD_HYDRATION_MISMATCH_DETAILS__: JSON.stringify(true)
      })
    ]
  }
};

