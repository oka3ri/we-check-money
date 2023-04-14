<template lang="pug">
  validation-provider(:rules="rules"
    :name="name"
    v-bind="$attrs"
    v-slot="{ errors, valid, invalid, validated }")
    b-form-group
      slot(name="label")
        label(v-if="label" :class="labelClasses") {{ label }}
      div(:class="[{ 'input-group': hasIcon }, { focused: focused }, { 'input-group-alternative': alternative }, { 'has-label': label || $slots.label }, inputGroupClasses, { 'input-disabled': !disabled }]")
        .input-group-prepend(v-if="prependIcon || $slots.prepend")
          span.input-group-text
            slot(name="prepend")
              i(:class="prependIcon")
        slot(v-bind="slotData")
          input.form-control(
            :value="value"
            :type="type"
            v-on="listeners"
            v-bind="$attrs"
            :valid="valid"
            :required="required"
            :class="[{ 'is-valid': valid && validated && successMessage }, { 'is-invalid': invalid && validated }, inputClasses,]"
            :maxlength="maxlength"
            :disabled="!disabled"
            @input="checkTelValid(valid)"
          )
        .input-group-append(v-if="appendIcon || $slots.append")
          span.input-group-text
            slot(name="append")
              i(:class="appendIcon")
        //- .input-group-timer(v-if="timer !== ''")
        h3.input-group-timer(v-if="timer.status") {{ timer.time }}
        slot(name="infoBlock")
      slot(name="success")
        .valid-feedback(v-if="valid && validated && successMessage") {{ successMessage }}
      slot(name="error")
        .invalid-feedback(v-if="errors[0]" style="display: block") {{ errors[0] }}
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
    disabled: {
      type: Boolean,
      default: true,
    },
    timer: {
      type: Object,
      description: "유효시간 표시",
      default: () => {
        return {
          status: false,
          time: "",
        };
      },
    },
  },
  data() {
    return {
      focused: false,
    };
  },
  computed: {
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
      this.$emit("blur", evt, this.name);
    },
    checkTelValid(valid) {
      if (this.name == "tel") {
        this.$emit("valid", valid);
      }
    },
  },
  created() {
    extend("required", {
      ...this.required,
      message: (field) => {
        if (field == "auth-number") return;
        return "필수 입력항목입니다.";
      },
      // message: "필수 입력항목입니다.",
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
    extend("duplicated", {
      validate(value, args) {
        // NOTE: args == 0: 중복 아님 | args == 1: 중복
        if (args[0]) {
          return false;
        }
        return true;
      },
      message: (field) => {
        if (field == "id") {
          return "이미 존재하거나 탈퇴한 아이디입니다.";
        } else if (field == "nickname") {
          return "이미 존재하는 닉네임입니다.";
        }
      },
    });
  },
};
</script>
<style>
.input-group-timer {
  /* line-height: 43px; */
  font-size: 14px;
  margin: 0;
  margin-right: 10px;
  color: #8898aa;
  font-weight: normal;
}
.form-group .input-group {
  align-items: center;
  background-color: #fff;
}
.form-group .input-disabled {
  background-color: #e9ecef;
}
.form-group .input-disabled .input-group-text {
  background-color: #e9ecef;
}
</style>
