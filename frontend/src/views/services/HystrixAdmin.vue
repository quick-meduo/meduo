<template>

 <iframe :src="admin" height="100%" width="100%" frameborder="0"/>

</template>

<script>

export default {
  name: 'HystrixAdmin',
  data: function() {
    return {
      admin: "http://localhost:8501/hystrix"
    }
  }
}
</script>

<style lang="less" scopped>
</style>
