<template>
  <div class="home">
    <img alt="Vue logo" src="../assets/logo.png">
    <h2>Looks like you got lost...</h2>

    <p v-if="$auth_user.isAuthenticated">
      user is authenticated
    </p>

    <p v-if="$auth_artist.isAuthenticated">
      artist is authenticated
    </p>

    <!-- user not logged in - display link to go to welcome page -->
    <div v-if="!$auth_user.isAuthenticated && !$auth_artist.isAuthenticated">
      <router-link v-bind:to="{ name: 'Index' }">
        <span>Go To Home Page</span>
      </router-link>
    </div>

    <div v-else>
      <router-link v-if="$auth_user.isAuthenticated" v-bind:to="{ name: 'u-home' }">
        <span>Go Home</span>
      </router-link>

      <router-link v-if="$auth_artist.isAuthenticated" v-bind:to="{ name: 'a-library' }">
        <span>Go Home</span>
      </router-link>
    </div>

  </div>
</template>

<script>
export default {
  name: 'PageNotFound',
  mounted() {
    console.log('++++++', this.$auth_artist)
    console.log('------', this.$auth_user)
  }
}
</script>

<style scoped>

</style>