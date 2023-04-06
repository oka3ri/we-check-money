<template lang="pug">
div
  //- Header
  .header.bg-gradient-success.py-7.py-lg-7.pt-lg-9
    b-container
      .header-body.text-center.mb-7
        b-row.justify-content-center
          b-col.px-5(xl="6" lg="6" md="8")
            h1.text-white wecheck에 오신 것을 환영합니다!
            p.text-lead.text-white
              | 자산의 이동을 정확하고 자세하게 
              span.highlight-info-txt check
              br
              | 모임에서 오고 가는 돈을 투명하게 
              span.highlight-info-txt check
              br
              //- spsan.highlight-info-txt wecheck 
              | 지금 바로 가입하여 이용하세요.
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
              small 일반 회원으로 가입하기
            validation-observer(v-slot="{handleSubmit}" ref="formValidator")
              b-form
                base-input.mb-3(alternative name="id" required="duplicateId" :rules="idRule" prepend-icon="ni ni-single-02" type="text" placeholder="아이디" v-model="user.id")
                //- base-input.mb-3(alternative name="pw" prepend-icon="ni ni-single-02" placeholder="닉네임" v-model="user.nickname")
                base-input.mb-3(alternative name="nickname" :rules="nameRule" prepend-icon="ni ni-badge" type="text" placeholder="닉네임" v-model="user.nickname")

                base-input.mb-3(alternative name="tel" :rules="telRule" prepend-icon="ni ni-mobile-button" type="tel" placeholder="전화번호" maxlength="13" v-model="user.tel" @input="ChangeHipenTel($event)")

                base-input.mb-3(alternative name="pw" :rules="pwdRule" prepend-icon="ni ni-lock-circle-open" type="password" placeholder="비밀번호" v-model="user.password")
                base-input.mb-3(alternative name="pw-confirm" required="confirmed:password" :rules="pwdConfirmRule" prepend-icon="ni ni-lock-circle-open" type="password" placeholder="비밀번호 확인" v-model="user.passwordConfirm")
                //- b-form-checkbox(v-model="user.rememberMe") Remember me
                .text-center
                  base-button.my-4(type="primary" native-type="button" @click='login()') 가입하기
          b-card-body.px-lg-5.py-lg-5
            .text-muted.text-center.mt-2.mb-3
              small 소셜 계정으로 가입하기
            .btn-wrapper.text-center
              .social-login-btn.naver-btn
                //- span.btn-inner--icon
                img(src="img/icons/common/naver.png")
              .social-login-btn.kakao-btn
                //- span.btn-inner--icon
                img(src="img/icons/common/kakao.png")
              .social-login-btn.google-btn
                //- span.btn-inner--icon
                img(src="img/icons/common/google.png")
        //- b-row.mt-3
          b-col(cols="6")
            //- TODO: router-link 부분은 추후에 싸그리 싹싹 바꿔야 함
            router-link.text-light(@click="$_goTo({name: 'login'})")
              small 아이디 | 비밀번호 찾기
          b-col.text-right(cols="6")
            router-link.text-light(@click="$_goTo({name: 'signup'})")
              small 회원가입
</template>
<script>
export default {
  data() {
    return {
      user: {
        id: "",
        nickname: "",
        tel: "",
        password: "",
        passwordConfirm: "",
        // rememberMe: false,
      },
      idRule: {
        required: true,
        duplicateId: "",
        // min: 6,
        // max: 12,
        regex: /^[a-z]{1}[a-z0-9]{5,11}$/,
      },
      nameRule: {
        required: true,
        // min: 2,
        // max: 10,
        regex: /^[가-힣]{2,10}$/,
      },
      telRule: {
        required: true,
        // min: 6,
        // max: 12,
        regex: /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/,
      },
      pwdRule: {
        required: true,
        // min: 10,
        // max: 20,
        regex:
          /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{7,19}$/,
      },
      pwdConfirmRule: {
        required: true,
        // min: 10,
        // max: 20,
        regex:
          /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{7,19}$/,
      },
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
    ChangeHipenTel(e) {
      // .replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');
      this.user.tel = e
        .replace(/[^0-9]/g, "")
        .replace(
          /(^02.{0}|^01.{1}|[0-9]{3,4})([0-9]{3,4})([0-9]{4})/g,
          "$1-$2-$3"
        );
    },
    // setHypenTel(value) {
    //   this.user.tel = value;
    //   console.log(this.user.tel);
    //   console.log(value);
    // },
    onSubmit() {
      // this will be called only after form is valid. You can do api call here to login
    },
  },
};
</script>
<style>
.highlight-info-txt {
  font-weight: 700;
  font-size: 18px;
  /* color: #ffff7e; */
}
.card .card-body {
  padding-top: 5px;
}
.card-body .mb-3 {
  margin-top: 0 !important;
}
.card .btn {
  width: 100%;
}
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
  width: 45px;
  height: 45px;
  border-radius: 100%;
  border: none;
  display: flex;
  justify-content: center;
  align-items: center;
}
.btn-wrapper .naver-btn {
  background-color: #03c75a;
}
.btn-wrapper .google-btn {
  background-color: #fff;
  border: 1px solid #e6e6e6;
}
.btn-wrapper .kakao-btn {
  overflow: hidden;
  background-color: #ffeb00;
}
.btn-wrapper .social-login-btn img {
  width: 30px;
  /* vertical-align: top;
  width: 38px;
  height: 38px;
  border-radius: 100%;
  border: none;
  background-position: center; */
}
.btn-wrapper .google-btn img {
  width: 30px;
  height: 30px;
}
/* .btn-wrapper .kakao-btn img {
  width: 25px;
} */
.naver-btn,
.kakao-btn {
  margin-right: 10px;
}
.google-btn {
}
.kakao-btn {
}
</style>
