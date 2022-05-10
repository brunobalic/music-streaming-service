<template>
  <div class="user-body">
    <div class="container-md">
<!--      <div class="card md-3" style="max-width: 75%; margin: auto; background-color: transparent; padding-top: 50px;  padding-bottom: 25px">-->
      <div class="m-5 p-5 pt-2">
        <div class="row g-0">
          <form v-bind:class="[loading ? 'loading' : '']">
            <!--          <div v-on:submit.prevent="createNewPlaylist" class="form-group">-->
            <div class="form-group">
              <label class="fs-5" for="plName">Playlist name</label>
              <input v-model="plName" type="text" class="form-control fs-6" id="plName" aria-describedby="emailHelp" placeholder="Enter your playlist name">
            </div>
            <br>
            <div class="form-group">
              <label class="fs-5" for="plDesc">Playlist description</label>
              <textarea v-model="plDesc" class="form-control fs-6" id="plDesc" rows="3" placeholder="Enter your playlist description here if you wish to ;)"></textarea>
            </div>
            <br>
            <template v-if="loading">
              <div class="spinner-border text-light"
                   role="status">
                <span class="visually-hidden">Creating...</span>
              </div>
            </template>
            <template v-else>
              <button v-on:click="createNewPlaylist"
                      type="button" class="btn btn-primary ps-3 pe-3">
                Create
              </button>
            </template>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import BackendService from '../../services/regular-user/BackendService'

export default {
  name: 'CreatePlaylist',
  data() {
    return {
      plName: '',
      plDesc: '',
      loading: false
    }
  },
  methods: {
    createNewPlaylist: async function() {
      this.loading = true
      console.log(this.plName, this.plDesc)

      if (this.plName === '') {
        console.log("playlist name cannot be empty")
        return
      }
      console.log("pocetak kreianja playliste")
      let token = await this.$auth_user.getTokenSilently()
      BackendService.createPlaylist(this.plName, this.plDesc, token)
          .then(response => {
            console.log(response)
            // this.$store.dispatch('user/getUserData', {
            //   accessToken: token
            // })
            this.$store.dispatch('user/getUsersPlaylists', {
              accessToken: token
            })
            this.loading = false
            this.$router.push({ name: 'user-base' })
          })
          .catch(error => console.log(error))
      // this.loading = false
    }
  }
}
</script>

<style scoped lang="scss">

form {
  button {
    height: 2.5rem;
    //background-color: #2d3f50;
    background-color: white;
    color: #2d3f50;
    //border-color: #c63a5d;
    border: none;
    border-radius: 1.25rem;
    font-weight: bold;
    &:hover {
      background-color: #c63a5d;
      border-color: #c63a5d;
    }
  }
}

.form-group {
  label {
    color: #e0e0e0;
    font-size: 1.5rem;
    float: left
  }
  input, textarea {
    background-color: #2d3f50;
    border: 0;
    color: white;
    font-size: 1.2rem;
  }
}

.loading {
  pointer-events: none;
  opacity: 75%;
}

</style>