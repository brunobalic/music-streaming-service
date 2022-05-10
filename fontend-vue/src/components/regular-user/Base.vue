<template>
  <div v-if="isRequiredDataLoaded">
    <sidenav></sidenav>
    <player-advanced></player-advanced>
    <router-view v-bind:key="handleRouterViewKey"></router-view>
  </div>
</template>

<script>
import { createNamespacedHelpers } from 'vuex'
import Sidenav from './Sidenav'
import PlayerAdvanced from '@/components/regular-user/PlayerAdvanced'

const { mapState } = createNamespacedHelpers('user')

export default {
  name: "Base",
  components: {
    PlayerAdvanced,
    Sidenav
  },
  mounted() {
    this.$auth_user.getTokenSilently()
        .then(response => {
          this.$store.dispatch('user/getUserData', {
            accessToken: response
          })
        })
        .catch(error => console.log(error))

    this.$auth_user.getTokenSilently()
        .then(response => {
          this.$store.dispatch('user/getUsersPlaylists', {
            accessToken: response
          })
        })
        .catch(error => console.log(error))

    this.$auth_user.getTokenSilently()
        .then(response => {
          this.$store.dispatch('user/getPlayHistory', {
            accessToken: response
          })
        })
        .catch(error => console.log(error))

    this.$auth_user.getIdTokenClaims()
        .then(response => {
          console.log(response, "id claims - user")
        })

    this.$auth_artist.getIdTokenClaims()
        .then(response => {
          console.log(response, "id claims - artist")
        })

    console.log(this.$auth_user, "this.$auth_user")
  },
  computed: {
    ...mapState({
      data1: state => state.userData,
      data2: state => state.usersPlaylists,
      data3: state => state.playHistory
    }),
    isRequiredDataLoaded() {
      return this.data1 && this.data2 && this.data3
    },
    handleRouterViewKey() {

      // console.log("++++ handle router-view KEY ")
      // console.log(this.$route.name)
      // console.log(this.$route.params)
      // console.log(this.$route.name + this.$route.params)
      // console.log(this.$route.name + this.$route.params.toString())
      // console.log(this.$route.fullPath)


      if (this.$route.name === 'u-search-advanced')
        return this.$route.name

      if (this.$route.name === 'u-search') {
        //console.log('will return: ', this.$route.name)
        return this.$route.name
      } else {
        //console.log('will return: ', this.$route.fullPath)
        return this.$route.fullPath
      }
    }
  }
}
</script>

<style lang="scss">

// SCROLLBAR
/* width */
::-webkit-scrollbar {
  width: 4px;
  background-color: #FFFFFF00;
}
/* Track */
::-webkit-scrollbar-track {
  background-color: rgba(255, 255, 255, 0);
}
/* Handle */
::-webkit-scrollbar-thumb {
  border-radius: 4px;
  background-color: #8f8f9a;
}

p {
  color: #ffffff;
}

.user-body {
  background-color: #121216;
  width: -webkit-calc(100% - 225px);
  width:    -moz-calc(100% - 225px);
  width:         calc(100% - 225px);
  /* height mi ne radi ??? */
  height: -webkit-calc(100% - 85px);
  height:    -moz-calc(100% - 85px);
  height:         calc(100% - 85px);

  min-height: calc(100vh);

  padding-bottom: calc(85px + 5px); /* 85 je velicina footera */
  float: right;
}

</style>
