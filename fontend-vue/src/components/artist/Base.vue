<template>
  <div v-if="artistData !== undefined && artistAlbums !== undefined">
    <sidenav></sidenav>
    <player-advanced-minimal></player-advanced-minimal>
    <router-view v-bind:key="$route.fullPath"></router-view>
  </div>
</template>

<script>
import { createNamespacedHelpers } from 'vuex'
import Sidenav from './Sidenav'
import PlayerAdvancedMinimal from '@/components/artist/PlayerAdvancedMinimal'

const { mapState } = createNamespacedHelpers('artist')

export default {
  name: 'Base',
  components: {
    Sidenav,
    PlayerAdvancedMinimal
  },
  async created() {
    let token = await this.$auth_artist.getTokenSilently()
    await this.$store.dispatch('artist/getArtistData', {
      accessToken: token
    })
    await this.$store.dispatch('artist/getArtistAlbums', {
      accessToken: token
    })
  },
  // pokusaj da pricekam da se vuex store ucita pocetne podatke
  computed: {
    ...mapState({
      artistData: state => state.artistData,
      artistAlbums: state => state.artistAlbums
    })
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

.artist-body {
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
