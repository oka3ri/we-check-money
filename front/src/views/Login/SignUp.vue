<template lang="pug">
div
  //- Header
  .header.bg-gradient-success.py-7.py-lg-7.pt-lg-9
    b-container
      .header-body.text-center.mb-7
        b-row.justify-content-center
          b-col.px-5(xl="6" lg="6" md="8")
            h1.text-white wecheck에 오신 것을 환영합니다 !
            p.text-lead.text-white.signup-text
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
                base-input.mb-3(alternative name="id" :rules="idRule" prepend-icon="ni ni-single-02" type="text" placeholder="아이디" v-model="user.id" @blur='blurTest')
                base-input.mb-3(alternative name="nickname" :rules="nicknameRule" prepend-icon="ni ni-badge" type="text" placeholder="닉네임" v-model="user.nickname" @blur='blurTest')
                base-input.mb-3(alternative name="tel" ref="telInput" :rules="telRule" prepend-icon="ni ni-mobile-button" type="tel" placeholder="전화번호" maxlength="13" v-model="user.tel" @input="ChangeHipenTel($event)" @valid='checkTelValid' :disabled="!sendAuthNumStatus")
                .auth-num-check-area(v-if="telValid")
                  .auth-num-check-form
                    base-input.mb-3(alternative name="auth-number" prepend-icon="ni ni-key-25" type="text" placeholder="인증번호" maxlength="6" v-model="authNumber" :timer='timerOption')
                    .auth-num-check-button-group(v-if="!sendAuthNumStatus")  
                      base-button.signup-check-btn(@click='sendAuthNumber()') 인증번호 받기
                    .auth-num-check-button-group(v-else)
                      base-button.signup-check-btn.second-tab-btn 재전송
                      base-button.signup-check-btn.second-tab-btn 인증하기
                base-input.mb-3(alternative name="pw" :rules="pwdRule" prepend-icon="ni ni-lock-circle-open" type="password" placeholder="비밀번호" v-model="user.password")
                base-input.mb-3(alternative name="pw-confirm" required="confirmed:password" :rules="pwdConfirmRule" prepend-icon="ni ni-lock-circle-open" type="password" placeholder="비밀번호 확인" v-model="user.passwordConfirm")
                //- b-form-checkbox(v-model="user.rememberMe") Remember me
                .text-center
                  base-button.my-4(type="primary" native-type="button" @click='registerUser()') 가입하기
          b-card-body.px-lg-5.py-lg-5
            .text-muted.text-center.mt-2.mb-3
              small 소셜 계정으로 가입하기
            .btn-wrapper.text-center
              .social-login-btn.naver-btn
                //- span.btn-inner--icon
                img(src="@/assets/images/icons/common/naver.png")
              .social-login-btn.kakao-btn
                //- span.btn-inner--icon
                img(src="@/assets/images/icons/common/kakao.png")
              .social-login-btn.google-btn
                //- span.btn-inner--icon
                img(src="@/assets/images/icons/common/google.png")
        b-row.mt-3
          b-col(cols="6")
            //- TODO: router-link 부분은 추후에 싸그리 싹싹 바꿔야 함
            //- router-link.text-light(@click="$_goTo({name: 'login'})")
              small 아이디 | 비밀번호 찾기
          b-col.text-right(cols="6")
            .text-light
              small 이미 계정이 있으신가요?
              a.go-login(@click="$_goTo({name: 'login'})")
                small 로그인
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
        duplicated: 0,
        // min: 6,
        // max: 12,
        regex: /^[a-z]{1}[a-z0-9]{5,11}$/,
      },
      nicknameRule: {
        required: true,
        duplicated: 0,
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
      authNumber: "",
      timeCounter: 180,
      sendAuthNumStatus: false,
      reSendAuthNumCount: 0,
      telValid: false,
      duplicateId: false,
    };
  },
  computed: {
    remainingTime() {
      // NOTE: 초 단위를 분:초 형식으로 변환
      let time = this.timeCounter / 60;
      let minutes = parseInt(time);
      let seconds = Math.round((time - minutes) * 60);
      return `${this.convertTime(minutes, 2)}:${this.convertTime(seconds, 2)}`;
    },
    timerOption() {
      let option = {
        status: this.sendAuthNumStatus,
        time: this.remainingTime,
      };
      return option;
    },
  },
  methods: {
    registerUser() {
      // TODO: 회원 가입 API 요청
      // form의 valid 값 확인 가능한지?
    },
    sendAuthNumber() {
      // TODO: 인증번호 요청
      this.$alert("인증번호가 전송되었습니다.", "", "success");
      this.startTimer();
      this.sendAuthNumStatus = true;
    },
    blurTest(evt, type) {
      // NOTE: 0=중복아님 | 1=중복
      if (evt.target.classList.length === 1) {
        if (type == "id") {
          // TODO: 아이디 중복 검사 API 요청
          // this.idRule.duplicated = 0;
        } else if (type == "nickname") {
          // TODO: 닉네임 중복 검사 API 요청
          // this.nicknameRule.duplicated = 1;
        }
      }
    },
    checkTelValid(valid) {
      this.telValid = valid;
    },
    ChangeHipenTel(e) {
      this.user.tel = e
        .replace(/[^0-9]/g, "")
        .replace(
          /(^02.{0}|^01.{1}|[0-9]{3,4})([0-9]{3,4})([0-9]{4})/g,
          "$1-$2-$3"
        );
    },
    startTimer() {
      this.polling = setInterval(() => {
        this.timeCounter--;
        if (this.timeCounter <= 0) this.stopTimer();
      }, 1000);
    },
    stopTimer() {
      clearInterval(this.polling);
      this.$alert(
        "유효 시간이 만료되었습니다. 다시 시도해 주세요.",
        "",
        "error"
      );
      this.sendAuthNumStatus = false;
      this.authNumber = "";
      this.timeCounter = 180;
    },
    convertTime(sec, digit) {
      // NOTE: digit 자릿수로 변환
      sec = sec + "";
      if (sec.length >= digit) {
        return sec;
      } else {
        return new Array(digit - sec.length + 1).join("0") + sec;
      }
    },
  },
  beforeDestroy() {
    clearInterval(this.polling);
    this.timeCounter = 180;
  },
};
</script>
<style>
.text-right .text-light {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: flex-end;
  align-items: baseline;
}
@media (max-width: 426px) {
  .text-right small {
    word-break: keep-all;
  }
}
@media (max-width: 376px) {
  .signup-text {
    font-size: 13px;
  }
  .auth-num-check-form {
    flex-wrap: wrap;
  }
  .auth-num-check-button-group {
    margin: 0;
    margin-top: 10px;
  }
  .bg-default .header {
    padding-bottom: 50px !important;
  }
}
</style>
