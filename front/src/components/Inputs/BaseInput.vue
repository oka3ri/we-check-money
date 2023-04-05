<template>
  <validation-provider
    :rules="rules"
    :name="name"
    v-bind="$attrs"
    v-slot="{ errors, valid, invalid, validated }"
  >
    <b-form-group>
      <slot name="label">
        <label v-if="label" :class="labelClasses">
          {{ label }}
        </label>
      </slot>
      <div
        :class="[
          { 'input-group': hasIcon },
          { focused: focused },
          { 'input-group-alternative': alternative },
          { 'has-label': label || $slots.label },
          inputGroupClasses,
        ]"
      >
        <div v-if="prependIcon || $slots.prepend" class="input-group-prepend">
          <span class="input-group-text">
            <slot name="prepend">
              <i :class="prependIcon"></i>
            </slot>
          </span>
        </div>
        <slot v-bind="slotData">
          <input
            :value="value"
            :type="type"
            v-on="listeners"
            v-bind="$attrs"
            :valid="valid"
            :required="required"
            class="form-control"
            :class="[
              { 'is-valid': valid && validated && successMessage },
              { 'is-invalid': invalid && validated },
              inputClasses,
            ]"
          />
        </slot>
        <div v-if="appendIcon || $slots.append" class="input-group-append">
          <span class="input-group-text">
            <slot name="append">
              <i :class="appendIcon"></i>
            </slot>
          </span>
        </div>
        <slot name="infoBlock"></slot>
      </div>
      <slot name="success">
        <div class="valid-feedback" v-if="valid && validated && successMessage">
          {{ successMessage }}
        </div>
      </slot>
      <slot name="error">
        <div v-if="errors[0]" class="invalid-feedback" style="display: block">
          {{ errMsg(name) }}
        </div>
      </slot>
    </b-form-group>
  </validation-provider>
</template>
<script>
export default {
  inheritAttrs: false,
  name: "base-input",
  props: {
    required: {
      type: Boolean,
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
    errMsg(type) {
      if (type == "id") {
        return "잘못된 아이디입니다.";
      } else if (type == "pw") {
        return "잘못된 패스워드입니다.";
      }
    },
  },
};
</script>
<style></style>
