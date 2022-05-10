<template>
  <transition name="fade">
    <!-- Scrollable modal -->
    <div v-if="show">
      <div class="modal fade show" id="exampleModalScrollable" tabindex="-1" aria-labelledby="exampleModalScrollableTitle" aria-modal="true" role="dialog">
        <div class="modal-dialog modal-dialog-scrollable">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title" id="exampleModalScrollableTitle">Add or Remove from playlists</h5>
              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div id="style-3" class="modal-body">
              <ul>
                <li class="list-group-item" v-for="pl in usersPlaylists" v-bind:key="pl.id">
                  <div class="container">
                    <div class="row">
                      <div class="col-1">
                        <div v-if="isRequestedTrackOnPlaylist(pl.id)">
                          <button v-on:click="removeFromPlaylist(pl.id)">
                            <span class="icon">
                              <svg xmlns="http://www.w3.org/2000/svg" class="icon icon-tabler icon-tabler-minus" width="24" height="24" viewBox="0 0 24 24" stroke-width="2" stroke="currentColor" fill="none" stroke-linecap="round" stroke-linejoin="round">
                                <path stroke="none" d="M0 0h24v24H0z" fill="none"></path>
                                <line x1="5" y1="12" x2="19" y2="12"></line>
                              </svg>
                            </span>
                          </button>
                        </div>
                        <div v-else>
                          <button v-on:click="addToPlaylist(pl.id)">
                            <span class="icon">
                              <svg xmlns="http://www.w3.org/2000/svg" class="icon icon-tabler icon-tabler-plus" width="24" height="24" viewBox="0 0 24 24" stroke-width="2" stroke="currentColor" fill="none" stroke-linecap="round" stroke-linejoin="round">
                                <path stroke="none" d="M0 0h24v24H0z" fill="none"></path>
                                <line x1="12" y1="5" x2="12" y2="19"></line>
                                <line x1="5" y1="12" x2="19" y2="12"></line>
                              </svg>
                            </span>
                          </button>
                        </div>
                      </div>
                      <div class="col-11">
                        <span class="title">{{ pl.name }}</span>
                      </div>
                    </div>
                  </div>
                </li>
              </ul>
            </div>
            <div class="modal-footer">
              <button v-on:click="closeModal" type="button" class="btn btn-secondary" data-bs-dismiss="modal">OK</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </transition>

</template>

<script>
import { createNamespacedHelpers } from 'vuex'
import BackendService from '../../services/regular-user/BackendService'

const { mapGetters, mapActions } = createNamespacedHelpers('user')

export default {
  name: 'AddToPlaylistModal',
  data() {
    return {
      show: false,
      requestedTrackId: undefined
    }
  },
  computed: {
    ...mapGetters({
      userData: 'userData',
      usersPlaylists: 'usersPlaylists'
    })
  },
  methods: {
    ...mapActions({
      updateUserData: 'getUserData',
      updateUserPlaylists: 'getUsersPlaylists'
    }),
    closeModal() {
      this.show = false;
    },
    openModal(trackId) {
      this.show = true;
      this.requestedTrackId = trackId
    },
    isRequestedTrackOnPlaylist(playlistId) {
      return this.usersPlaylists.find(playlist => {
        if (playlist.id === playlistId) {
          return playlist.tracks.find(track => {
            return track.track.toString() === this.requestedTrackId.toString()
          })
        }
      })
    },
    async addToPlaylist(playlistId) {
      let token = await this.$auth_user.getTokenSilently()
      BackendService.addTrackToPlaylist(this.requestedTrackId, playlistId, token)
          .then(response => {
            console.log(response.data, "add to pl")
            this.updateUserData({ accessToken: token})
            this.updateUserPlaylists({ accessToken: token})
          })
          .catch(error => console.log(error))
    },
    async removeFromPlaylist(playlistId) {
      let token = await this.$auth_user.getTokenSilently()
      BackendService.removeTrackFromPlaylist(this.requestedTrackId, playlistId, token)
          .then(response => {
            console.log(response, "remove from pl")
            this.updateUserData({ accessToken: token})
            this.updateUserPlaylists({ accessToken: token})
          })
          .catch(error => console.log(error))
    }
  }
}
</script>

<style lang="scss" scoped>

#exampleModalScrollable {
  display: block;
}

.modal-content {
  background-color: #181818;
  border: 1px solid #2d3f50;
  border-radius: 10px;
  .modal-header {
    border-color: #2d3f50;
    h5 {
      color: #8f8f9a;
    }
    button {
      color: #8f8f9a;
    }
  }
  .modal-body {
    ul {
      padding: 0;
      margin: 0;
    }
    .list-group-item {
      border-left: 0;
      border-top: 0;
      border-right: 0;
      background-color: transparent;
      border-bottom: 1px #2d3f50;
    }
    .icon {
      color: #8f8f9a;
    }
    .title {
      color: white;
    }
  }
  .modal-footer {
    border-color: #2d3f50;
    button {
      border-radius: 18px;
      color: #2d3f50;
      background-color: white;
    }
  }
}

// SCROLLBAR
#style-3::-webkit-scrollbar-track {
  //-webkit-box-shadow: inset 0 0 4px rgb(143, 143, 154);
  background-color: rgba(255, 255, 255, 0);
}
#style-3::-webkit-scrollbar {
  width: 4px;
  background-color: #FFFFFF00;
}
#style-3::-webkit-scrollbar-thumb {
  border-radius: 4px;
  background-color: #8f8f9a;
}

</style>