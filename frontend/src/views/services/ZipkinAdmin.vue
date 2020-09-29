<template>

 <iframe :src="admin" height="100%" width="100%" frameborder="0"/>

</template>

<script>

export default {
  name: 'ZipkinAdmin',
  data: function() {
    return {
      admin: "http://localhost:9411"
    }
  }
}
</script>

<style lang="less" scopped>
</style>
