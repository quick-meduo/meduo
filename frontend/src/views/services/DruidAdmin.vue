<template>

 <iframe :src="druid" height="100%" width="100%" frameborder="0"/>

</template>

<script>

export default {
  name: 'DruidAdmin',
  data: function() {
    return {
       druid: "http://localhost:8001/druid"
    }
  }
}
</script>

<style lang="less" scopped>
</style>
