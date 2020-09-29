<template>

 <iframe :src="admin" height="100%" width="100%" frameborder="0"/>

</template>

<script>

export default {
  name: 'SpringBootAdmin',
  data: function() {
    return {
       admin: "http://localhost:8000"
    }
  }
}
</script>

<style lang="less" scopped>
</style>
