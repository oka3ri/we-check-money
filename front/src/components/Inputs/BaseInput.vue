<template lang="pug">
  validation-provider(:rules="rules"
    :name="name"
    v-bind="$attrs"
    v-slot="{ errors, valid, invalid, validated }")
    b-form-group
      slot(name="label")
        label(v-if="label" :class="labelClasses") {{ label }}
      div(:class="[{ 'input-group': hasIcon }, { focused: focused }, { 'input-group-alternative': alternative }, { 'has-label': label || $slots.label }, inputGroupClasses,]")
        .input-group-prepend(v-if="prependIcon || $slots.prepend")
          span.input-group-text
            slot(name="prepend")
              i(:class="prependIcon")
        slot(v-bind="slotData")
          input.form-control(:value="value"
            :type="type"
            v-on="listeners"
            v-bind="$attrs"
            :valid="valid"
            :required="required"
            :class="[{ 'is-valid': valid && validated && successMessage }, { 'is-invalid': invalid && validated }, inputClasses,]"
            :maxlength="maxlength")
        .input-group-append(v-if="appendIcon || $slots.append")
          span.input-group-text
            slot(name="append")
              i(:class="appendIcon")
        slot(name="infoBlock")
      //- .signup-check(v-if="isIdOrNickname")
        base-button.signup-check-btn 중복확인
      .signup-check(v-if="isTel")
        base-button.signup-check-btn 인증번호 받기
        //- h3 {{ isIdOrNickname }}
      //- .signup-check 중복확인
      slot(name="success")
        .valid-feedback(v-if="valid && validated && successMessage") {{ successMessage }}
      slot(name="error")
        .invalid-feedback(v-if="errors[0]" style="display: block") {{ errors[0] }}
        //- .invalid-feedback(v-if="errors[0]" style="display: block") {{ errMsg(name) }}
</template>
<script>
import { extend } from "vee-validate";

export default {
  inheritAttrs: false,
  name: "base-input",
  props: {
    required: {
      // type: Boolean,
      description: "Whether input is required (adds an asterix *)",
    },
    group: {
      type: Boolean,
      description:
        "Whether input is an input group (manual override in special cases)",
    },
    alternative: {
      type: Boolean,
      description: "Whether input is of alternative layout",
    },
    label: {
      type: String,
      description: "Input label (text before input)",
    },
    error: {
      type: String,
      description: "Input error (below input)",
    },
    successMessage: {
      type: String,
      description: "Input success message",
      default: "",
    },
    labelClasses: {
      type: String,
      description: "Input label css classes",
      default: "form-control-label",
    },
    inputClasses: {
      type: String,
      description: "Input css classes",
    },
    inputGroupClasses: {
      type: String,
      description: "Input group css classes",
    },
    value: {
      type: [String, Number],
      description: "Input value",
    },
    type: {
      type: String,
      description: "Input type",
      default: "text",
    },
    appendIcon: {
      type: String,
      description: "Append icon (right)",
    },
    prependIcon: {
      type: String,
      description: "Prepend icon (left)",
    },
    rules: {
      type: [String, Array, Object],
      description: "Vee validate validation rules",
      default: "",
    },
    name: {
      type: String,
      description: "Input name (used for validation)",
      default: "",
    },
    maxlength: {
      type: String,
    },
  },
  data() {
    return {
      focused: false,
    };
  },
  computed: {
    // err 문구 prop 받아서 문구 return
    // TODO: error를 원하는 케이스에 따라 사용 가능한지 확인 및 체크 (현재 이메일인지 유효성 체크)
    listeners() {
      return {
        ...this.$listeners,
        input: this.updateValue,
        focus: this.onFocus,
        blur: this.onBlur,
      };
    },
    slotData() {
      return {
        focused: this.focused,
        error: this.error,
        ...this.listeners,
      };
    },
    hasIcon() {
      const { append, prepend } = this.$slots;
      return (
        append !== undefined ||
        prepend !== undefined ||
        this.appendIcon !== undefined ||
        this.prependIcon !== undefined ||
        this.group
      );
    },
    isIdOrNickname() {
      if (this.name == "id" || this.name == "nickname") return true;
      return false;
    },
    isTel() {
      if (this.name == "tel") return true;
      return false;
    },
  },
  methods: {
    updateValue(evt) {
      let value = evt.target.value;
      this.$emit("input", value);
    },
    onFocus(evt) {
      this.focused = true;
      this.$emit("focus", evt);
    },
    onBlur(evt) {
      this.focused = false;
      this.$emit("blur", evt);
    },
  },
  created() {
    extend("duplicateId", {
      validate(value, args) {
        // 체크 로직
        // TODO: 아이디 중복 확인 API 요청
        // 매개변수에 따라 id와 nickname을 여기서 다 처리할 수 있는지 확인 필요
        return true;
      },
      message: "이미 존재하거나 탈퇴한 아이디입니다.",
    });
    extend("required", {
      ...this.required,
      message: "필수 입력항목입니다.",
    });
    extend("regex", {
      ...this.rules.regex,
      message: (field) => {
        if (field == "id") {
          return "6~12자의 영문 소문자, 숫자만 사용 가능합니다.";
        } else if (field == "pw") {
          return "8~20자 영문 대 소문자, 숫자, 특수문자를 사용하세요.";
        } else if (field == "pw-confirm") {
          return "비밀번호가 일치하지 않습니다.";
        } else if (field == "nickname") {
          return "2~10자 한글만 사용 가능합니다.";
        } else if (field == "tel") {
          return "형식에 맞지 않는 번호입니다.";
        } else {
          return "잘못된 값입니다.";
        }
      },
    });
    //   extend("regex", {
    //     validate(value, this.rules.regex ) {
    //   return value.length >= min && value.length <= max;
    // },
    //     message: (field, value) => {
    //       if (field == 'id') {
    //         return "6~12자의 영문 소문자, 숫자만 사용 가능합니다."
    //       }
    //     }
    //   });
  },
};
</script>
<style>
.form-group div {
  display: flex;
}
.card .form-group .signup-check .signup-check-btn {
  width: 105px;
  background-color: #ffffff;
  color: #8898aa;
  box-shadow: 0 1px 3px rgba(50, 50, 93, 0.15), 0 1px 0 rgba(0, 0, 0, 0.02);
  border: 0;
  transition: box-shadow 0.15s ease;
  padding: 0.625rem 0.25rem;
  margin-left: 10px;
}
</style>
