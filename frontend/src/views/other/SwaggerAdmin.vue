<template>

 <iframe :src="swagger" height="100%" width="100%" frameborder="0"/>

</template>

<script>

export default {
  name: 'SwaggerAdmin',
  data: function() {
    return {
      swagger: "http://localhost:8001/doc.html"
    }
  }
}
</script>

<style lang="less" scopped>
</style>
