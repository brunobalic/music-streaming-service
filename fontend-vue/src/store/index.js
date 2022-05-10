import Vue from 'vue'
import Vuex from 'vuex'
import BackendService from '../services/regular-user/BackendService'
import BackendServiceArtist  from '../services/artist/BackendService'

Vue.use(Vuex)

const moduleArtist = {
  namespaced: true,
  state: () => ({
    artistData: undefined,
    artistAlbums: undefined,
    // artistAlbums: [],
    baseUrlImgAlbum: "http://localhost:8104/api/media/image/",
    baseUrlImgArtist: "http://localhost:8104/api/media/image/",
    audioResourceBaseUrl: "http://localhost:8100/api/v1/media/audio/"
  }),
  getters: {
    artistData: state => state.artistData,
    artistImgBaseUrl: state => state.baseUrlImgArtist,
    albumImgBaseUrl: state => state.baseUrlImgAlbum,
    audioResourceBaseUrl: state => state.audioResourceBaseUrl,
    allAlbums(state) {
      return state.artistAlbums
    },
    albumById: (state) => (params) => {
      if (state.artistAlbums !== undefined) {
        const tmp = parseInt(params.id)
        return state.artistAlbums.find(al => al.id === tmp)
      }
      return null
    }
  },
  mutations: {
    setArtistData(state, artist) {
      Vue.set(state, 'artistData', artist)
    },
    setArtistAlbums(state, albums) {
      Vue.set(state, 'artistAlbums', albums)
    }
  },
  actions: {
    async getArtistData({commit}, {accessToken}) {
      try {
        const response = await BackendServiceArtist.getArtistData(accessToken)
        commit('setArtistData', response.data)
      } catch (error) {
        console.log(error, "error u getArtistData - action")
      }
    },
    async getArtistAlbums({commit}, {accessToken}) {
      try {
        const response = await BackendServiceArtist.getAllArtistAlbums(accessToken)
        // sort by release date
        let albums = response.data
        albums.sort((a, b) => {
          if (a.official_release_date > b.official_release_date) {
            return -1
          } else {
            return 1
          }
        })
        commit('setArtistAlbums', albums)
      } catch (error) {
        console.log(error, "error u getArtistAlbums - action")
      }
    }
  }
}

const moduleUser = {
  namespaced: true,
  state: () => ({
    baseUrl_imgAlbum: "http://localhost:8104/api/media/image/",
    baseUrl_imgArtist: "http://localhost:8104/api/media/image/",
    audioResourceBaseUrl: "http://localhost:8100/api/v1/media/audio/",
    s1: "This is my value in state1",
    userData: undefined,
    usersPlaylists: undefined,
    lastSearchData: undefined,
    playHistory: [],
    playerQueue: []
  }),
  getters: {
    imgArtistBaseUrl: state => state.baseUrl_imgArtist,
    imgAlbumBaseUrl: state => state.baseUrl_imgAlbum,
    audioResourceBaseUrl: state => state.audioResourceBaseUrl,
    userData: state => state.userData,
    usersPlaylists: state => state.usersPlaylists,
    lastSearchData: state => state.lastSearchData,
    playHistory: state => state.playHistory,
    playerQueue: state => state.playerQueue
  },
  mutations: {
    setUserData(state, user) {
      Vue.set(state, 'userData', user)
    },
    setUserPlaylists(state, playlists) {
      Vue.set(state, 'usersPlaylists', playlists)
    },
    setLastSearchData(state, searchData) {
      Vue.set(state, 'lastSearchData', searchData)
    },
    setPlayHistory(state, playHistory) {
      Vue.set(state, 'playHistory', playHistory.reverse())
    },
    updatePlayHistory(state, trackDto) {
      let playHistory = state.playHistory
      playHistory.length > 20 ? playHistory.shift() : playHistory.push(trackDto)
      Vue.set(state, 'playHistory', playHistory)
    },

    setPlayerQueue(state, playerQueue) {
      Vue.set(state, 'playerQueue', playerQueue)
    },
    addTrackToPlayerQueue(state, track) {
      let playerQueue = state.playerQueue
      playerQueue.push(track)
      Vue.set(state, 'playerQueue', playerQueue)
    }
  },
  actions: {
    async getUserData({commit}, {accessToken}) {
      console.log(accessToken, "ACCESS TOKEN")
      try {
        const response = await BackendService.getCurrentUser(accessToken)
        commit('setUserData', response.data)
      } catch (error) {
        console.log(error, "error u vuex getUserData()")
      }
    },
    async getUsersPlaylists({commit}, {accessToken}) {
      try {
        const response = await BackendService.getAllPlaylistsFromCurrentUser(accessToken)
        commit('setUserPlaylists', response.data)
      } catch (error) {
        console.log(error, "error u vuex getUsersPlaylists()")
      }
    },
    setLastSearchData({commit}, searchData) {
      commit('setLastSearchData', searchData)
    },
    async getPlayHistory({commit}, {accessToken}) {
      let response = await BackendService.getPlayHistory(accessToken)
      commit('setPlayHistory', response.data)
    },
    updatePlayHistory({commit}, trackDto) {
      commit('updatePlayHistory', trackDto)
    },
    setPlayerQueue({commit}, {queueList}) {
      console.log("action - setPlayerQueue")
      commit('setPlayerQueue', queueList)
    },
    addTrackToPlayerQueue({commit}, {track}) {
      commit('addTrackToPlayerQueue', track)
    }
  }
}

export default new Vuex.Store({
  modules: {
    artist: moduleArtist,
    user: moduleUser
  }
})
