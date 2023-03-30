module.exports = {
  css: {
    loaderOptions: {
      sass: {
        additionalData: `
          @import '@/aseets/scss/argon-dashboard.scss';
        `,
      },
    },
  },
  lintOnSave: false,
};
