<template>

 <iframe :src="baidu" height="100%" width="100%" frameborder="0"/>

</template>

<script>

export default {
  name: 'NewUser',
  data: function() {
    return {
       baidu: "http://www.baidu.com"
    }
  },
  computed:{
    curRouter: function(){
      return this.$route.params.title;
    }
  }
}
</script>

<style lang="less" scopped>
</style>
