<template>
  <div>
    <albums-list v-bind:albums-list="favAlbums"
                 v-bind:show-artist-name="true">
    </albums-list>
  </div>
</template>

<script>
import {createNamespacedHelpers} from 'vuex'
import BackendService from '../../../services/regular-user/BackendService'
import AlbumsList from '@/components/regular-user/lists/AlbumsList'

const {mapState} = createNamespacedHelpers('user')

export default {
  name: 'ListFavAlbums',
  components: {
    AlbumsList
  },
  data() {
    return {
      favAlbums: []
    }
  },
  computed: {
    ...mapState({
      userData: state => state.userData
    })
  },
  async mounted() {
    let token = await this.$auth_user.getTokenSilently()
    this.userData.favouriteAlbums.forEach(element => {
      BackendService.getAlbumData(element.album, token)
          .then(response => {
            this.favAlbums.push(response.data)
          })
          .catch(error => console.log(error))
    })
  }
}
</script>

<style lang="scss" scoped>

</style>