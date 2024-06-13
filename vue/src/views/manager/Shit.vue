<template>
 <div>
   <div style="color: red; font-size: 200px">
     SHIT!
   </div>


 </div>
</template>

<script>
export default {

  name: "Home",

  date() {
    return {
      user: JSON.parse(localStorage.getItem('honey-user') || {}),
    }
  },

  created() {

  },

  methods() {

  }
}


</script>

<style scoped>

</style>