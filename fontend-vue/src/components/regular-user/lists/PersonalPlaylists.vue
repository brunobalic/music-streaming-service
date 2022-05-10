<template>
  <div>
    <template v-if="userPlaylists.length > 0">
      <div class="d-flex align-items-center ms-3">
        <h5 class="m-0 me-3 text-grey">My Playlists</h5>
        <router-link v-bind:to="{ name: 'u-create-playlist' }"
                     class="btn-create-pl">
          Create new here
        </router-link>
      </div>
      <div class="row mt-3 justify-content-start">
        <div v-for="pl in userPlaylists"
             v-bind:key="pl.id"
             class="col-md-3" style="min-width: 220px">
          <div class="row">
            <div class="col-md-6 pe-1">
              <div class="position-relative">
                <svg class="" fill="none" stroke="currentColor"
                     stroke-linecap="round" stroke-linejoin="round"
                     stroke-width="2" viewBox="0 0 24 24" width="96" height="96"
                     xmlns="http://www.w3.org/2000/svg">
                  <path d="M0 0h24v24H0z" fill="none" stroke="none"></path>
                  <circle cx="14" cy="17" r="3"></circle>
                  <path d="M17 17v-13h4"></path>
<!--                  <path d="M13 5h-10"></path>-->
<!--                  <line x1="3" x2="13" y1="9" y2="9"></line>-->
<!--                  <path d="M9 13h-6"></path>-->
                </svg>
                <button class="me-2 btn-play position-absolute"
                        v-on:click="playSelectedPlaylist(pl.id)"
                        style="color: white; top: 12px; left: 16px;">
                  <svg class="" width="24" height="24" viewBox="0 0 24 24" stroke-width="1"
                       stroke="currentColor" fill="none" stroke-linecap="round"
                       stroke-linejoin="round" xmlns="http://www.w3.org/2000/svg">
                    <path stroke="none" d="M0 0h24v24H0z" fill="none"></path>
                    <path d="M7 4v16l13 -8z"></path>
                  </svg>
                </button>
              </div>
            </div>
            <div class="col-md-6 ps-0 d-flex flex-column text-start">
              <div>
                <router-link v-bind:to="{ name: 'u-playlist', params: { id: pl.id } }">
                  <p class="mb-1">{{ pl.name }}</p>
                </router-link>
                <small class="text-muted fst-italic">{{ pl.publish_date }}</small>
                <br>
                <small class="text-muted fst-italic">Nb. of tracks: {{ pl.number_of_tracks }}</small>
              </div>
            </div>
          </div>
        </div>

      </div>
    </template>
    <template v-else>
      <div class="d-flex align-items-center ms-3">
        <h5 class="m-0 me-3 text-grey">My Playlists</h5>
      </div>
      <div class="d-flex flex-column ms-3 text-start">
        <p class="ms-3 mt-3 text-muted fst-italic">You haven't created any playlist.</p>
        <div class="d-inline-flex">
          <router-link v-bind:to="{ name: 'u-create-playlist' }"
                       class="btn-create-pl">
            <h6 class="my-1">Create one here</h6>
          </router-link>
        </div>
      </div>
    </template>
  </div>
</template>

<script>
import { createNamespacedHelpers } from 'vuex'
import BackendService from '@/services/regular-user/BackendService'

const { mapGetters } = createNamespacedHelpers('user')

export default {
  name: 'PersonalPlaylists',
  computed: {
    ...mapGetters({
      userData: 'userData',
      userPlaylists: 'usersPlaylists',
      audioResourceBaseUrl: 'audioResourceBaseUrl'
    })
  },
  methods: {
    createPlayerQueueList(trackDto) {
      return {
        trackId: trackDto.id,
        title: trackDto.title,
        duration: trackDto.duration_sec,
        artist: trackDto.artistObj.first_name + " " + trackDto.artistObj.last_name,
        audioUrl: this.audioResourceBaseUrl + trackDto.id,
        imageUrl: "http://localhost:8104/api/media/image/" + trackDto.albumData.filename
      }
    },
    async playSelectedPlaylist(playlistId) {
      let token = await this.$auth_user.getTokenSilently()
      let plData = (await BackendService.getPlaylistData(playlistId, token)).data
      plData.tracks.forEach(track => {
        BackendService.getTrack(track.track, token)
            .then(res => {
              track.trackObj = res.data
              this.$root.$emit('pl-add-to-end-of-queue', this.createPlayerQueueList(res.data))
            })
      })
    }
  }
}
</script>

<style lang="scss" scoped>

.text-grey {
  color: #8f8f9a;
}

a {
  text-decoration: none;
  //color: #2d3f50;
  color: white;
  &.btn-create-pl {
    //background-color: white;
    background-color: #2d3f50;
    //border: 1px solid white;
    padding: 7px 15px 7px 15px;
    border-radius: 28px;
    transition: .3s;
    &:hover {
      background-color: #c63a5d;
      border-color: #c63a5d;
      color: white;
    }
  }
}

.btn-play {
  background-color: white;
  width: 2.5rem;
  height: 2.5rem;
  border-radius: 50%;
  transition: .3s;
  svg {
    color: #2d3f50;
    fill: #2d3f50;
  }
  &:hover {
    background-color: #c63a5d;
    border-color: #c63a5d;
    svg {
      color: white;
      fill: white;
    }
  }
}

</style>