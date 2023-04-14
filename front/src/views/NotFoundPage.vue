<template lang="pug">
div.not-found
  .header.py-7.py-lg-8.pt-lg-9
    b-row.justify-content-center
      b-col.px-5
        h1.text-center 페이지를 찾을 수 없습니다.
        p.text-lead.text-center
          | 페이지가 존재하지 않거나 사용할 수 없는 페이지입니다.
          br
          | 입력하신 주소가 정확한지 다시 한 번 확인해 주시기 바랍니다.
        a.font-weight-bold.mt-5(@click='$router.go(-1)') 이전 페이지로 돌아가기
</template>

<script>
export default {
  name: "not-found-page",
};
</script>
<style scoped>
.not-found .header {
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #f0f0f0;
}
.not-found .header .row {
  width: 600px;
  height: 500px;
  align-content: center;
  background-color: #fff;
}
.not-found .row .col {
  height: 50%;
}
</style>
