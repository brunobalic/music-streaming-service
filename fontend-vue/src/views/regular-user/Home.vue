<template>
  <div class="user-body">
    <div class="container-md mt-4 ps-5 pe-5 pb-5">
      <personal-playlists></personal-playlists>
      <hr>
      <play-history-list v-bind:track-list="playHistory"></play-history-list>
    </div>
  </div>
</template>

<script>
import { createNamespacedHelpers } from 'vuex'
import PersonalPlaylists from '@/components/regular-user/lists/PersonalPlaylists'
import PlayHistoryList from '@/components/regular-user/lists/PlayHistoryList'

const { mapGetters, mapActions } = createNamespacedHelpers('user')

export default {
  name: 'Home',
  components: {
    PersonalPlaylists,
    PlayHistoryList
  },
  computed: {
    ...mapGetters({
      playHistory: 'playHistory'
    })
  },
  async mounted() {
    let token = await this.$auth_user.getTokenSilently()
    await this.getPlayHistory({ accessToken: token })
  },
  methods: {
    ...mapActions({
      getPlayHistory: 'getPlayHistory'
    })
  }
}
</script>

<style lang="scss" scoped>

</style>