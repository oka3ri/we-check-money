<template lang="pug">
div
  //- Header
  .header.bg-gradient-success.py-7.py-lg-7.pt-lg-9
    b-container
      .header-body.text-center.mb-7
    .separator.separator-bottom.separator-skew.zindex-100
      svg(x="0" y="0" viewBox="0 0 2560 100" preserveAspectRatio="none" version="1.1" xmlns="http://www.w3.org/2000/svg")
        polygon.fill-default(points="2560 0 2560 100 0 100")
      
  //- Page content
  b-container.mt--8.pb-5
    b-row.justify-content-center
      b-col(lg="5" md="7")
        b-card.bg-secondary.border-0.mb-0(no-body)
          b-card-header.bg-transparent.px-lg-5.py-lg-5
            .text-center.text-muted.mb-4
              small 일반 회원 로그인
            validation-observer(v-slot="{handleSubmit}" ref="formValidator")
              b-form
                base-input.mb-3(alternative name="id" prepend-icon="ni ni-single-02" placeholder="아이디" v-model="user.id")
                base-input.mb-3(alternative name="pw" prepend-icon="ni ni-lock-circle-open" type="password" placeholder="비밀번호" v-model="user.password")
                //- b-form-checkbox(v-model="user.rememberMe") Remember me
                .text-center
                  base-button.my-4(type="primary" native-type="button" @click='login()') 로그인
          b-card-body.px-lg-5.py-lg-5
            .text-muted.text-center.mt-2.mb-3
              small 소셜 계정으로 로그인
            .btn-wrapper.text-center
              .social-login-btn.naver-btn
                img(src="img/icons/common/naver.png")
              .social-login-btn.kakao-btn
                img(src="img/icons/common/kakao.png")
              .social-login-btn.google-btn
                img(src="img/icons/common/google.png")
        b-row.mt-3
          b-col(cols="6")
            .text-light(@click="$_goTo({name: 'login'})")
              small 아이디 | 비밀번호 찾기
          b-col.text-right(cols="6")
            .text-light(@click="$_goTo({name: 'signup'})")
              small 회원가입
</template>
<script>
export default {
  data() {
    return {
      user: {
        id: "",
        password: "",
        // rememberMe: false,
      },
      idRegex: /^[a-zA-Z][0-9a-zA-Z]{5,11}$/,
      pwRegex:
        /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{7,19}$/,
    };
  },
  methods: {
    login() {
      if (this.user.id == "") {
        alert("아이디를 입력해 주세요.");
        return;
      } else if (this.user.password == "") {
        alert("비밀번호를 입력해 주세요.");
        return;
      }
      if (
        !this.idRegex.test(this.user.id) ||
        !this.pwRegex.test(this.user.password)
      ) {
        alert(
          "등록되지 않은 아이디이거나 아이디 또는 비밀번호를 잘못 입력했습니다."
        );
        return;
      }
    },
    onSubmit() {
      // this will be called only after form is valid. You can do api call here to login
    },
  },
};
</script>
<style></style>
