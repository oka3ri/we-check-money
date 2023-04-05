<template lang="pug">
div
  //- Header
  .header.bg-gradient-success.py-7.py-lg-8.pt-lg-9
    b-container
      .header-body.text-center.mb-7
        //- b-row.justify-content-center
          b-col.px-5(xl="5" lg="6" md="8")
            h1.text-white Welcome!
            p.text-lead.text-white Use these awesome forms to login or create new account in your project for free.
    .separator.separator-bottom.separator-skew.zindex-100
      svg(x="0" y="0" viewBox="0 0 2560 100" preserveAspectRatio="none" version="1.1" xmlns="http://www.w3.org/2000/svg")
        polygon.fill-default(points="2560 0 2560 100 0 100")
      
  //- Page content
  b-container.mt--8.pb-5
    b-row.justify-content-center
      b-col(lg="5" md="7")
        b-card.bg-secondary.border-0.mb-0(no-body)
          b-card-header.bg-transparent.pb-5
            .text-center.text-muted.mb-4
              small 일반 회원 로그인
            validation-observer(v-slot="{handleSubmit}" ref="formValidator")
              b-form(role="form" @submit.prevent="handleSubmit(onSubmit)")
                base-input.mb-3(alternative name="Id" :rules="{required: true, email: true}" prepend-icon="ni ni-email-83" placeholder="아이디" v-model="model.email")
                base-input.mb-3(alternative name="Password" :rules="{required: true, min: 6}" prepend-icon="ni ni-lock-circle-open" type="password" placeholder="비밀번호" v-model="model.password")
                //- b-form-checkbox(v-model="model.rememberMe") Remember me
                .text-center
                  base-button.my-4(type="primary" native-type="submit") 로그인
          b-card-body.px-lg-5.py-lg-5
            .text-muted.text-center.mt-2.mb-3
              small 소셜 계정으로 로그인
            .btn-wrapper.text-center
              .social-login-btn.naver-btn
                //- span.btn-inner--icon
                img(src="img/icons/common/naver.png")
              .social-login-btn.kakao-btn
                //- span.btn-inner--icon
                img(src="img/icons/common/kakao.png")
              .social-login-btn.google-btn
                span.btn-inner--icon
                  img(src="img/icons/common/google.png")
              //- a.btn.btn-neutral.btn-icon.naver-wrapper(href="#")
              //-   span.btn-inner--icon
              //-     img(src="img/icons/common/naver.png")
              //-   span.btn-inner--text
              //- a.btn.btn-neutral.btn-icon.kakao-wrapper(href="#")
              //-   span.btn-inner--icon
              //-     img(src="img/icons/common/kakao.png")
              //-   span.btn-inner--text
              //- a.btn.btn-neutral.btn-icon(href="#")
              //-   span.btn-inner--icon
              //-     img(src="img/icons/common/google.png")
              //-   span.btn-inner--text
        b-row.mt-3
          b-col(cols="6")
            //- TODO: router-link 부분은 추후에 싸그리 싹싹 바꿔야 함
            router-link(to="/dashboard" class="text-light")
              small 아이디 | 비밀번호 찾기
          b-col.text-right(cols="6")
            router-link.text-light(to="/register")
              small 회원가입
</template>
<script>
export default {
  data() {
    return {
      model: {
        email: "",
        password: "",
        rememberMe: false,
      },
    };
  },
  methods: {
    onSubmit() {
      // this will be called only after form is valid. You can do api call here to login
    },
  },
};
</script>
<style>
.btn-wrapper {
  display: flex;
  flex-direction: row;
  justify-content: center;
}
/*
.btn-wrapper .btn {
  width: 100%;
  margin-bottom: 5px;
}
.btn-wrapper a.btn {
  padding: 0;
  display: flex;
}
.btn-wrapper .btn-inner--text {
  width: 100%;
}
.naver-wrapper {
  background-color: #03c75a;
}
.kakao-wrapper {
  background-color: #ffeb00;
} */
.btn-wrapper .social-login-btn {
  width: 38px;
  height: 38px;
  border-radius: 100%;
  border: none;
  display: flex;
  justify-content: center;
}
.btn-wrapper .naver-btn {
  background-color: #03c75a;
}
.btn-wrapper .google-btn {
}
.btn-wrapper .kakao-btn {
  overflow: hidden;
  background-color: #ffeb00;
}
.btn-wrapper .social-login-btn img {
  width: 35px;
  /* vertical-align: top;
  width: 38px;
  height: 38px;
  border-radius: 100%;
  border: none;
  background-position: center; */
}
.naver-btn {
}
.google-btn {
}
.kakao-btn {
}
</style>
