<template lang="pug">
.container
  .row.mt-lg-n10.mt-md-n11.mt-n10.justify-content-center
    .col-xl-4.col-lg-5.col-md-7.mx-auto
      .card.z-index-0
        .card-header.text-center.pt-4
          h5 Login
        .row.px-xl-5.px-sm-4.px-3
          .col-3.ms-auto.px-1
            a.btn.btn-outline-light.w-100
              svg(width="24px" height="32px" viewBox="0 0 64 64" version="1.1")
                g(stroke="none" stroke-width="1" fill="none" fill-rule="evenodd")
                  g(transform="translate(3.000000, 3.000000)" fill-rule="nonzero")
                    circle(fill="#3C5A9A" cx="29.5091719" cy="29.4927506" r="29.4882047")
                    path(d="M39.0974944,9.05587273 L32.5651312,9.05587273 C28.6886088,9.05587273 24.3768224,10.6862851 24.3768224,16.3054653 C24.395747,18.2634019 24.3768224,20.1385313 24.3768224,22.2488655 L19.8922122,22.2488655 L19.8922122,29.3852113 L24.5156022,29.3852113 L24.5156022,49.9295284 L33.0113092,49.9295284 L33.0113092,29.2496356 L38.6187742,29.2496356 L39.1261316,22.2288395 L32.8649196,22.2288395 C32.8649196,22.2288395 32.8789377,19.1056932 32.8649196,18.1987181 C32.8649196,15.9781412 35.1755132,16.1053059 35.3144932,16.1053059 C36.4140178,16.1053059 38.5518876,16.1085101 39.1006986,16.1053059 L39.1006986,9.05587273 L39.0974944,9.05587273 L39.0974944,9.05587273 Z"
                          fill="#FFFFFF")
          .col-3.px-1
            a.btn.btn-outline-light.w-100
              svg(width="24px" height="32px" viewBox="0 0 64 64" version="1.1")
                g(stroke="none" stroke-width="1" fill="none" fill-rule="evenodd")
                  g(transform="translate(3.000000, 3.000000)" fill-rule="nonzero")
                    circle(fill="#3C5A9A" cx="29.5091719" cy="29.4927506" r="29.4882047")
                    path(d="M39.0974944,9.05587273 L32.5651312,9.05587273 C28.6886088,9.05587273 24.3768224,10.6862851 24.3768224,16.3054653 C24.395747,18.2634019 24.3768224,20.1385313 24.3768224,22.2488655 L19.8922122,22.2488655 L19.8922122,29.3852113 L24.5156022,29.3852113 L24.5156022,49.9295284 L33.0113092,49.9295284 L33.0113092,29.2496356 L38.6187742,29.2496356 L39.1261316,22.2288395 L32.8649196,22.2288395 C32.8649196,22.2288395 32.8789377,19.1056932 32.8649196,18.1987181 C32.8649196,15.9781412 35.1755132,16.1053059 35.3144932,16.1053059 C36.4140178,16.1053059 38.5518876,16.1085101 39.1006986,16.1053059 L39.1006986,9.05587273 L39.0974944,9.05587273 L39.0974944,9.05587273 Z"
                          fill="#FFFFFF")
          .col-3.me-auto.px-1
            a.btn.btn-outline-light.w-100
              svg(width="24px" height="32px" viewBox="0 0 64 64" version="1.1")
                g(stroke="none" stroke-width="1" fill="none" fill-rule="evenodd")
                  g(transform="translate(3.000000, 3.000000)" fill-rule="nonzero")
                    circle(fill="#3C5A9A" cx="29.5091719" cy="29.4927506" r="29.4882047")
                    path(d="M39.0974944,9.05587273 L32.5651312,9.05587273 C28.6886088,9.05587273 24.3768224,10.6862851 24.3768224,16.3054653 C24.395747,18.2634019 24.3768224,20.1385313 24.3768224,22.2488655 L19.8922122,22.2488655 L19.8922122,29.3852113 L24.5156022,29.3852113 L24.5156022,49.9295284 L33.0113092,49.9295284 L33.0113092,29.2496356 L38.6187742,29.2496356 L39.1261316,22.2288395 L32.8649196,22.2288395 C32.8649196,22.2288395 32.8789377,19.1056932 32.8649196,18.1987181 C32.8649196,15.9781412 35.1755132,16.1053059 35.3144932,16.1053059 C36.4140178,16.1053059 38.5518876,16.1085101 39.1006986,16.1053059 L39.1006986,9.05587273 L39.0974944,9.05587273 L39.0974944,9.05587273 Z"
                          fill="#FFFFFF")
          .mt-2.position-relative.text-center
            p.text-sm.font-weight-bold.mb-2.text-secondary.text-border.d-inline.z-index-2.bg-white.px-3 or
        .card-body
          form(role='form')
            argon-input(type="text" placeholder="Id" aria-label="Id")
            //- argon-input(type="email" placeholder="Email" aria-label="Email")
            argon-input(type="password" placeholder="Password" aria-label="Password")
            //- argon-checkbox(checked)
              label.form-check-label(for='flexCheckDefault') I agree the 
                a.text-dark.font-weight-bolder Terms and Conditions
            .text-center
              argon-button.my-4.mb-2(fullWidth color="dark" variant="gradient") 로그인
            p.text-sm.mt-3.mb-0
              a.text-dark.font-weight-bolder 아이디 | 비밀번호 찾기
</template>

<script>
import ArgonInput from '../../components/ArgonInput.vue';
import ArgonCheckbox from '../../components/ArgonCheckbox.vue';
import ArgonButton from '../../components/ArgonButton.vue';
const body = document.getElementsByTagName('body')[0];

export default {
  name: 'login',
  components: {
    ArgonInput,
    ArgonCheckbox,
    ArgonButton,
  },
  created() {
    // this.$store.commit('setLoginStates', true);
    // body.classList.remove('bg-gray-100');
  },
  beforeUnmount() {
    // this.$store.commit('setLoginStates', false);
    // body.classList.add('bg-gray-100');
  },
};
</script>
<style>
/* TODO: important 추후에 제거 */
/* .mt-lg-n10,
.mt-md-n11 {
  margin-top: 0 !important;
} */
.container .mt-lg-n10 {
  height: 100vh;
}
.row .col-xl-4 {
  display: flex;
  align-items: center;
}
</style>
