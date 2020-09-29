<template>

 <iframe :src="admin" height="100%" width="100%" frameborder="0"/>

</template>

<script>

export default {
  name: 'RegistryAdmin',
  data: function() {
    return {
       admin: "http://localhost:8500"
    }
  }
}
</script>

<style lang="less" scopped>
</style>
