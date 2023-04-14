<template lang="pug">
.footer-bar
  i.fa-solid.fa-money-bill-transfer(style="color: #EF8D4B;")
  i.fa-solid.fa-chart-pie(style="color: #EF8D4B;")
  i.fa-solid.fa-comments-dollar(style="color: #EF8D4B;")
  i.fa-solid.fa-piggy-bank(style="color: #EF8D4B;")
  i.fa-solid.fa-gear(style="color: #EF8D4B;")
</template>
<script>
export default {
  components: {},
  props: {},
  data() {
    return {};
  },
  computed: {},
  methods: {},
};
</script>
<style>
.footer-bar {
  min-width: 100%;
  display: flex;
  align-items: center;
  justify-content: space-evenly;
  border-top: 1px solid #e7e7e7;
  background-color: #fff;
  padding-bottom: 10px;
  left: 0;
  position: fixed;
  right: 0;
  bottom: 0;
  height: 6rem;
}
.footer-bar .fa-solid {
  font-size: 30px;
}
</style>
