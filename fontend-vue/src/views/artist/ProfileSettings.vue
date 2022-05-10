<template>
  <div class="artist-body">
    <div class="container-md mt-4 ps-5 pe-5">

      <div v-show="!editing"
          class="row g-0 ps-2 pt-4 pe-2">
        <div class="col-md-4 me-4 mb-4 text-start"
             style="width: 200px">
          <img style="width: 200px; border-radius: 50%" alt="Artist Profile Picture"
               v-bind:src="artistImgBaseUrl + artistData.pictureFilename"
               v-on:error="setAltImgArtist">
        </div>
        <div class="col-md ms-4 mb-4 d-flex flex-column text-start">
          <div class="d-flex flex-row justify-content-between">
            <h3 class="text-white">
              {{ artistData.first_name }} {{ artistData.last_name }}
            </h3>
            <button v-on:click="editProfileInfo"
                    class="edit-btn p-2 border rounded-pill border-secondary">
              <svg xmlns="http://www.w3.org/2000/svg" class="icon icon-tabler icon-tabler-edit"
                   width="24" height="24" viewBox="0 0 24 24" stroke-width="1.5"
                   stroke="currentColor" fill="none" stroke-linecap="round"
                   stroke-linejoin="round">
                <path stroke="none" d="M0 0h24v24H0z" fill="none"></path>
                <path d="M9 7h-3a2 2 0 0 0 -2 2v9a2 2 0 0 0 2 2h9a2 2 0 0 0 2 -2v-3"></path>
                <path d="M9 15h3l8.5 -8.5a1.5 1.5 0 0 0 -3 -3l-8.5 8.5v3"></path>
                <line x1="16" y1="5" x2="19" y2="8"></line>
              </svg>
              <span class="align-bottom">Edit</span>
            </button>
          </div>
          <p class="mb-auto fst-italic text-muted">{{ artistData.description }}</p>
          <div>
            <small class="text-muted fst-italic">Official website:</small>
          </div>
          <div>
            <a v-bind:href="artistData.website_link" target="_blank">{{ artistData.website_link }}</a>
          </div>
        </div>
      </div>

      <!-- Form -->
      <div v-show="editing"
           class="row g-0 ps-2 pt-4 pe-2">
        <div class="col-md-4 me-4 mb-4 text-start"
             style="width: 200px">
          <img style="width: 200px; border-radius: 50%" alt="Artist Profile Picture"
               v-bind:src="artistImgBaseUrl + artistData.pictureFilename"
               v-on:error="setAltImgArtist">
        </div>
        <div class="col-md ms-4 mb-4 d-flex flex-column text-start">
          <div class="mb-3">
            <label for="input-name" class="form-label">Name</label>
            <input v-model="newName"
                   type="text" class="form-control" id="input-name" placeholder="">
          </div>
          <div class="mb-3">
            <label for="input-desc" class="form-label">Description</label>
            <textarea v-model="newDesc"
                      class="form-control" id="input-desc" rows="3"></textarea>
          </div>
          <div class="mb-3">
            <label for="input-website" class="form-label">Official website</label>
            <input v-model="newWebsite"
                   type="url" class="form-control" id="input-website" placeholder="">
          </div>
        </div>
        <div class="d-flex flex-row justify-content-center">
          <button v-on:click="saveEdits"
                  class="edit-btn me-2 p-2 border rounded-pill bg-white text-black border-secondary">
            <svg xmlns="http://www.w3.org/2000/svg" class="icon icon-tabler icon-tabler-checkbox"
                 width="24" height="24" viewBox="0 0 24 24" stroke-width="1.5"
                 stroke="currentColor" fill="none" stroke-linecap="round" stroke-linejoin="round">
              <path stroke="none" d="M0 0h24v24H0z" fill="none"></path>
              <polyline points="9 11 12 14 20 6"></polyline>
              <path d="M20 12v6a2 2 0 0 1 -2 2h-12a2 2 0 0 1 -2 -2v-12a2 2 0 0 1 2 -2h9"></path>
            </svg>
            <span class="align-bottom">Save</span>
          </button>
          <button v-on:click="cancelEdit"
                  class="edit-btn ms-2 p-2 border rounded-pill border-secondary text-secondary">
            <svg xmlns="http://www.w3.org/2000/svg" class="icon icon-tabler icon-tabler-square-x"
                 width="24" height="24" viewBox="0 0 24 24" stroke-width="1.5"
                 stroke="currentColor" fill="none" stroke-linecap="round"
                 stroke-linejoin="round">
              <path stroke="none" d="M0 0h24v24H0z" fill="none"></path>
              <rect x="4" y="4" width="16" height="16" rx="2"></rect>
              <path d="M10 10l4 4m0 -4l-4 4"></path>
            </svg>
            <span class="align-bottom">Cancel</span>
          </button>
        </div>
      </div>

      <hr>

    </div>
  </div>
</template>

<script>
import {createNamespacedHelpers} from 'vuex'

const {mapGetters} = createNamespacedHelpers('artist')

export default {
  name: 'ProfileSettings',
  components: {},

  data() {
    return {
      editing: false,
      newName: "",
      newDesc: "",
      newWebsite: ""
    }
  },
  computed: {
    ...mapGetters({
      artistImgBaseUrl: 'artistImgBaseUrl',
      artistData: 'artistData'
    })
  },
  methods: {
    setAltImgArtist(event) {
      event.target.src = require('@/assets/artist_logo.png')
    },
    editProfileInfo() {
      this.editing = true
    },
    cancelEdit() {
      this.editing = false
    },
    saveEdits() {

      console.log('new data', this.newName, this.newDesc, this.newWebsite)

      // todo send form...

      this.editing = false
    }
  }
}
</script>

<style lang="scss" scoped>

.edit-btn {
  color: #8f8f9a;
}

.form-control, .form-control:focus {
  background: #2d3f50;
  color: white;
  border: none;
}

</style>